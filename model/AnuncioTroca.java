package model;

public class AnuncioTroca extends Anuncio implements Publicavel{
    private String procura;
    
    public AnuncioTroca(Livro livro, Usuario vendedor, String procura, String descricao) {
        this.livro = livro;
        this.vendedor = vendedor;
        this.preco = 10.0;
        this.procura = procura;
        this.descricao = descricao;
    }
    
    @Override
    public void exibirResumo(){
        System.out.println( "Livro: " + livro.info() +
                            "\nVendedor: " + vendedor.info() +
                            "\nProcura: " + procura +
                            "\nDescrição: " + descricao + "\n");
    }
    
    @Override
    public double taxaPlataforma(){
        return 2.0;
    }
    
    @Override
    public void alterarStatus(){
        if(this.status == "Disponivel"){
           this.status = "Trocado";
           System.out.println("Status trocado de Disponivel para Trocado");
        } else if(this.status == "Trocado"){
           this.status = "Disponivel";
           System.out.println("Status trocado de Trocado para Disponivel");
        }
    }
    
    @Override
    public void publicar(){
        System.out.println("Publicando anuncio de TROCA: " + this.livro.getTitulo() + " por " + this.procura);
    }

    public Livro getLivro() { return livro; }
    public Usuario getVendedor() { return vendedor; }
    public double getPreco() { return preco; }
    public String getDescricao() { return descricao; }
    public String getProcura() { return procura; }
    
    public void setLivro(Livro novoLivro) { livro = novoLivro; }
    public void setVendedor(Usuario novoVendedor) { vendedor = novoVendedor; }
    public void setPreco(double preco) { this.preco = preco; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setProcura(String procura) { this.procura = procura; }
}
