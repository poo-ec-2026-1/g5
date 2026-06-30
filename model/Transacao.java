package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "transacoes")
public class Transacao {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "tipo", canBeNull = false)
    private String tipo; // "TROCA" ou "VENDA"

    @DatabaseField(columnName = "livro_titulo", canBeNull = false)
    private String livroTitulo;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "vendedor_email", canBeNull = false)
    private Usuario vendedor;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "comprador_email", canBeNull = false)
    private Usuario comprador;

    @DatabaseField(columnName = "detalhes", canBeNull = false)
    private String detalhes; // "Troca por: [Livro]" ou "R$ [Preço]"

    @DatabaseField(columnName = "data_transacao", canBeNull = false)
    private String dataTransacao;

    public Transacao() {
    }

    public Transacao(String tipo, String livroTitulo, Usuario vendedor, Usuario comprador, String detalhes, String dataTransacao) {
        this.tipo = tipo;
        this.livroTitulo = livroTitulo;
        this.vendedor = vendedor;
        this.comprador = comprador;
        this.detalhes = detalhes;
        this.dataTransacao = dataTransacao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getLivroTitulo() { return livroTitulo; }
    public void setLivroTitulo(String livroTitulo) { this.livroTitulo = livroTitulo; }

    public Usuario getVendedor() { return vendedor; }
    public void setVendedor(Usuario vendedor) { this.vendedor = vendedor; }

    public Usuario getComprador() { return comprador; }
    public void setComprador(Usuario comprador) { this.comprador = comprador; }

    public String getDetalhes() { return detalhes; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }

    public String getDataTransacao() { return dataTransacao; }
    public void setDataTransacao(String dataTransacao) { this.dataTransacao = dataTransacao; }
}
