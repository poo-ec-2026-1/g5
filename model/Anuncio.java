package model;

import com.j256.ormlite.field.DatabaseField;

public abstract class Anuncio
{
    @DatabaseField(generatedId = true, columnName = "id")
    protected int id;

    @DatabaseField(columnName = "preco", canBeNull = false)
    protected double preco;

    @DatabaseField(columnName = "status", canBeNull = false)
    protected String status = "Disponivel";

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "livro_id", canBeNull = false)
    protected Livro livro;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "vendedor_email", canBeNull = false)
    protected Usuario vendedor;

    @DatabaseField(columnName = "descricao")
    protected String descricao;

    // Construtor sem argumentos exigido pelo ORMLite
    public Anuncio() {
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public Usuario getVendedor() { return vendedor; }
    public void setVendedor(Usuario vendedor) { this.vendedor = vendedor; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public abstract void exibirResumo();
    
    public abstract double taxaPlataforma();
    
    public abstract void alterarStatus();
}
