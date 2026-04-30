package application.model;

public abstract class Anuncio
{
    protected double preco;
    protected String status = "Disponivel";
    protected Livro livro;
    protected Usuario vendedor;
    protected String descricao;
    
    public abstract void exibirResumo();
    
    public abstract double taxaPlataforma();
    
    public abstract void alterarStatus();
}