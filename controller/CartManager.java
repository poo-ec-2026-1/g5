package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Anuncio;
import model.AnuncioVenda;
import model.Usuario;
import model.AnuncioRepository;
import model.CartItem;
import model.CartItemRepository;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<Anuncio> items;
    private final CartItemRepository cartItemRepo;

    private CartManager() {
        this.items = new ArrayList<>();
        this.cartItemRepo = new CartItemRepository();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public List<Anuncio> getItems() {
        return new ArrayList<>(items);
    }

    public void loadCartForUser(Usuario usuario) {
        if (usuario == null) return;
        items.clear();
        
        List<CartItem> dbItems = cartItemRepo.buscarPorUsuario(usuario);
        AnuncioRepository anuncioRepo = new AnuncioRepository();
        
        for (CartItem dbItem : dbItems) {
            Anuncio anuncio = null;
            if ("VENDA".equals(dbItem.getTipoAnuncio())) {
                anuncio = anuncioRepo.buscarVendaPorId(dbItem.getAnuncioId());
            } else if ("TROCA".equals(dbItem.getTipoAnuncio())) {
                anuncio = anuncioRepo.buscarTrocaPorId(dbItem.getAnuncioId());
            }
            
            if (anuncio != null) {
                items.add(anuncio);
            } else {
                // Remove o item órfão do banco de dados (se o livro foi comprado por outro ou excluído)
                cartItemRepo.remover(usuario, dbItem.getAnuncioId(), dbItem.getTipoAnuncio());
            }
        }
    }

    public void addItem(Anuncio anuncio) {
        if (anuncio == null) return;
        
        // Impede adicionar livro postado por si mesmo
        Usuario logado = SessionManager.getInstance().getUsuarioLogado();
        if (logado != null && anuncio.getVendedor() != null && 
            logado.getEmail().equals(anuncio.getVendedor().getEmail())) {
            showFeedback("Aviso", "Ação Inválida", "Você não pode adicionar seu próprio livro ao carrinho.");
            return;
        }
        
        // Evita duplicados
        for (Anuncio item : items) {
            if (item.getLivro() != null && anuncio.getLivro() != null &&
                item.getLivro().getIsbn() != null && 
                item.getLivro().getIsbn().equals(anuncio.getLivro().getIsbn())) {
                
                showFeedback("Aviso", "Item Duplicado", "Este livro já está no seu carrinho.");
                return;
            }
        }
        
        // Salva persistência se houver usuário logado
        if (logado != null) {
            String tipo = (anuncio instanceof AnuncioVenda) ? "VENDA" : "TROCA";
            cartItemRepo.salvar(new CartItem(logado, anuncio.getId(), tipo));
        }
        
        items.add(anuncio);
        showFeedback("Sucesso", "Adicionado ao Carrinho", "O livro '" + anuncio.getLivro().getTitulo() + "' foi adicionado ao seu carrinho.");
    }

    public void removeItem(Anuncio anuncio) {
        if (anuncio == null) return;
        
        Usuario logado = SessionManager.getInstance().getUsuarioLogado();
        if (logado != null) {
            String tipo = (anuncio instanceof AnuncioVenda) ? "VENDA" : "TROCA";
            cartItemRepo.remover(logado, anuncio.getId(), tipo);
        }
        
        items.remove(anuncio);
    }

    public void clear() {
        Usuario logado = SessionManager.getInstance().getUsuarioLogado();
        if (logado != null) {
            cartItemRepo.limparParaUsuario(logado);
        }
        items.clear();
    }

    public double getTotal() {
        double total = 0;
        for (Anuncio item : items) {
            if (item instanceof AnuncioVenda) {
                total += item.getPreco();
            }
        }
        return total;
    }

    public void checkout() {
        if (items.isEmpty()) {
            showFeedback("Aviso", "Carrinho Vazio", "Seu carrinho está vazio!");
            return;
        }
        
        model.AnuncioRepository repo = new model.AnuncioRepository();
        for (Anuncio item : items) {
            repo.removerAnuncio(item);
        }
        
        showFeedback("Sucesso", "Compra Finalizada", "Compra finalizada com sucesso! Total: R$ " + String.format("%.2f", getTotal()));
        clear();
    }

    private void showFeedback(String title, String header, String content) {
        if ("Aviso".equalsIgnoreCase(title)) {
            AlertHelper.showWarning(title, header, content);
        } else {
            AlertHelper.showInfo(title, header, content);
        }
    }
}
