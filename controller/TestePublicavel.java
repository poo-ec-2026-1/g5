package controller;

import model.*;

public class TestePublicavel
{
    public static void main(String[] args){
        Publicavel anuncio1 = new AnuncioVenda(
            new Livro("Clean Code","Robert C. Martin","9780132350884","Usado - bom estado"),
            new Usuario("Antonio Helio Cabral", "antoniohc1994@email.com", "(62) 9 9685-9588"),
            45.0,
            "Livro bem conservado"
        );
        
        Publicavel anuncio2 = new AnuncioTroca(
            new Livro("Java: Como Programar","Deitel","9788576052452","Usado - com marcas"),
            new Usuario("Luis Augusto Fischer", "luisaf1977@email.com", "(18) 9 6814-2561"),
            "Python: Guia Definitivo",
            "Algumas paginas grifadas"
        );
        
        Publicavel[] listaAnuncios = {anuncio1, anuncio2};
        
        System.out.println("--- Processando Postagens ---");
        for (Publicavel p : listaAnuncios){
            p.publicar();
        }
    }
}
