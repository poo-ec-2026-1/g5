package controller;

import model.*;

public class TesteMarketplace {
    public static void main(String[] args) {

        Usuario usuario1 = new Usuario("Antonio Helio Cabral", "antoniohc1994@email.com", "(62) 9 9685-9588");
        Usuario usuario2 = new Usuario("Luís Augusto Fischer", "luisaf1977@email.com", "(18) 9 6814-2561");
        
        System.out.println("Usuários criados");

        Livro livro1 = new Livro(
            "Clean Code",
            "Robert C. Martin",
            "9780132350884",
            "Usado - bom estado"
        );

        Livro livro2 = new Livro(
            "Java: Como Programar",
            "Deitel",
            "9788576052452",
            "Usado - com marcas"
        );

        System.out.println("Livros criados");
        
        AnuncioVenda anuncio1 = new AnuncioVenda(
            livro1,
            usuario1,
            80.0,
            "Livro bem conservado."
        );

        AnuncioTroca anuncio2 = new AnuncioTroca(
            livro2,
            usuario2,
            "Curso intensivo de Python",
            "Algumas páginas grifadas."
        );
        
        System.out.println("Anúncios criados");

        System.out.println("Anúncios:");
        anuncio1.exibirResumo();
        System.out.println();
        anuncio2.exibirResumo();
        
        System.out.println("Taxa da plataforma para os anuncios: ");
        System.out.println("Anuncio 1: " + anuncio1.taxaPlataforma());
        System.out.println("Anuncio 2: " + anuncio2.taxaPlataforma());
        System.out.println();
        
        System.out.println("Alterando status dos anuncios: ");
        anuncio1.alterarStatus();
        anuncio2.alterarStatus();
        System.out.println();
        
        System.out.println("Anuncios com status alterado: ");
        anuncio1.exibirResumo();
        anuncio2.exibirResumo();

        System.out.println("\nTeste Concluído");
    }
}
