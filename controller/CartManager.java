package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Anuncio;
import model.AnuncioVenda;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<Anuncio> items;

    private CartManager() {
        this.items = new ArrayList<>();
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

    public void addItem(Anuncio anuncio) {
        if (anuncio == null) return;
        
        // Evita duplicados
        for (Anuncio item : items) {
            if (item.getLivro() != null && anuncio.getLivro() != null &&
                item.getLivro().getIsbn() != null && 
                item.getLivro().getIsbn().equals(anuncio.getLivro().getIsbn())) {
                
                showFeedback("Aviso", "Item Duplicado", "Este livro já está no seu carrinho.");
                return;
            }
        }
        
        items.add(anuncio);
        showFeedback("Sucesso", "Adicionado ao Carrinho", "O livro '" + anuncio.getLivro().getTitulo() + "' foi adicionado ao seu carrinho.");
    }

    public void removeItem(Anuncio anuncio) {
        if (anuncio == null) return;
        items.remove(anuncio);
    }

    public void clear() {
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
