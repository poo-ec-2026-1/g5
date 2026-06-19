package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "livros")
public class Livro {
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "titulo", canBeNull = false)
    private String titulo;

    @DatabaseField(columnName = "autor", canBeNull = false)
    private String autor;

    @DatabaseField(columnName = "isbn")
    private String isbn;

    @DatabaseField(columnName = "estado", canBeNull = false)
    private String estado;

    // Construtor sem argumentos exigido pelo ORMLite
    public Livro() {
    }

    public Livro(String titulo, String autor, String isbn, String estado) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getIsbn() { return isbn; }
    public String getEstado() { return estado; }
    
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setEstado(String estado) { this.estado = estado; }

    public String info() {
        return titulo + " - " + autor + " (" + estado + ")";
    }
}
