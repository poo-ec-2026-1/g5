package model;

public class AnuncioVenda extends Anuncio implements Publicavel {
    
    public AnuncioVenda(Livro livro, Usuario vendedor, double preco, String descricao) {
        this.livro = livro;
        this.vendedor = vendedor;
        this.preco = preco;
        this.descricao = descricao;
    }
    
    @Override
    public void exibirResumo(){
        System.out.println( "Livro: " + livro.info() +
                            "\nVendedor: " + vendedor.info() +
                            "\nPreço: R$ " + preco +
                            "\nDescrição: " + descricao + "\n");
    }
    
    @Override
    public double taxaPlataforma(){
        return this.preco * 0.05;
    }
    
    @Override
    public void alterarStatus(){
        if(this.status == "Disponivel"){
           this.status = "Vendido";
           System.out.println("Status trocado de Disponivel para Vendido");
        } else if(this.status == "Vendido"){
           this.status = "Disponivel";
           System.out.println("Status trocado de Vendido para Disponivel");
        }
    }
    
    @Override
    public void publicar(){
        System.out.println("Publicando an�ncio de VENDA: " + this.livro.getTitulo() + " por R$ " + this.preco);
    }

    public Livro getLivro() { return livro; }
    public Usuario getVendedor() { return vendedor; }
    public double getPreco() { return preco; }
    public String getDescricao() { return descricao; }
    
    public void setLivro(Livro novoLivro) { livro = novoLivro; }
    public void setVendedor(Usuario novoVendedor) { vendedor = novoVendedor; }
    public void setPreco(double preco) { this.preco = preco; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
