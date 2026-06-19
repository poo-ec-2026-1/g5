package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "usuarios")
public class Usuario {

    @DatabaseField(columnName = "nome", canBeNull = false)
    private String nome;

    @DatabaseField(id = true, columnName = "email")
    private String email;

    @DatabaseField(columnName = "fone", canBeNull = false)
    private String fone;

    @DatabaseField(columnName = "senha", canBeNull = false)
    private String senha;

    // Construtor sem argumentos exigido pelo ORMLite
    public Usuario() {
    }

    public Usuario(String nome, String email, String fone) {
        this.nome = nome;
        this.email = email;
        this.fone = fone;
        this.senha = "";
    }

    public Usuario(String nome, String email, String fone, String senha) {
        this.nome = nome;
        this.email = email;
        this.fone = fone;
        this.senha = senha;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getFone() { return fone; }
    public String getSenha() { return senha; }
    
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setFone(String fone) { this.fone = fone; }
    public void setSenha(String senha) { this.senha = senha; }

    public String info() {
        return nome + " (" + email + " / " + fone + ")";
    }
}

