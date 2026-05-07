package model;

public class Usuario {
    private String nome;
    private String email;
    private String fone;

    public Usuario(String nome, String email, String fone) {
        this.nome = nome;
        this.email = email;
        this.fone = fone;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getFone() { return fone; }
    
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setFone(String fone) { this.fone = fone; }

    public String info() {
        return nome + " (" + email + " / " + fone + ")";
    }
}
