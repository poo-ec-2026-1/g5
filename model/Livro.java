package application.model;

public class Livro {
    private String titulo;
    private String autor;
    private String isbn;
    private String estado;

    public Livro(String titulo, String autor, String isbn, String estado) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.estado = estado;
    }

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