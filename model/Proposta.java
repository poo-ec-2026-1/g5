package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "propostas")
public class Proposta {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "anuncio_id", canBeNull = false)
    private AnuncioTroca anuncio;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "proponente_email", canBeNull = false)
    private Usuario proponente;

    @DatabaseField(columnName = "livro_oferecido", canBeNull = false)
    private String livroOferecido;

    @DatabaseField(columnName = "status", canBeNull = false)
    private String status = "PENDENTE"; // PENDENTE, ACEITA, REJEITADA

    public Proposta() {
    }

    public Proposta(AnuncioTroca anuncio, Usuario proponente, String livroOferecido) {
        this.anuncio = anuncio;
        this.proponente = proponente;
        this.livroOferecido = livroOferecido;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public AnuncioTroca getAnuncio() { return anuncio; }
    public void setAnuncio(AnuncioTroca anuncio) { this.anuncio = anuncio; }

    public Usuario getProponente() { return proponente; }
    public void setProponente(Usuario proponente) { this.proponente = proponente; }

    public String getLivroOferecido() { return livroOferecido; }
    public void setLivroOferecido(String livroOferecido) { this.livroOferecido = livroOferecido; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
