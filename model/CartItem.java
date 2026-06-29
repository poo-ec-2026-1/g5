package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "itens_carrinho")
public class CartItem {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "usuario_email", canBeNull = false)
    private Usuario usuario;

    @DatabaseField(columnName = "anuncio_id", canBeNull = false)
    private int anuncioId;

    @DatabaseField(columnName = "tipo_anuncio", canBeNull = false)
    private String tipoAnuncio;

    // Construtor sem argumentos exigido pelo ORMLite
    public CartItem() {
    }

    public CartItem(Usuario usuario, int anuncioId, String tipoAnuncio) {
        this.usuario = usuario;
        this.anuncioId = anuncioId;
        this.tipoAnuncio = tipoAnuncio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public int getAnuncioId() { return anuncioId; }
    public void setAnuncioId(int anuncioId) { this.anuncioId = anuncioId; }

    public String getTipoAnuncio() { return tipoAnuncio; }
    public void setTipoAnuncio(String tipoAnuncio) { this.tipoAnuncio = tipoAnuncio; }
}
