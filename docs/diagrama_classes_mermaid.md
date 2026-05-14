classDiagram
    class Publicavel {
        &lt;&lt;interface&gt;&gt;
        +publicar() void
    }

    class Anuncio {
        &lt;&lt;abstract&gt;&gt;
        #preco : double
        #status : String
        #descricao : String
        +exibirResumo()* void
        +taxaPlataforma()* double
        +alterarStatus()* void
    }

    class AnuncioVenda {
        +AnuncioVenda(livro: Livro, vendedor: Usuario, preco: double, descricao: String)
        +exibirResumo() void
        +taxaPlataforma() double
        +alterarStatus() void
        +publicar() void
    }

    class AnuncioTroca {
        -procura : String
        +AnuncioTroca(livro: Livro, vendedor: Usuario, procura: String, descricao: String)
        +exibirResumo() void
        +taxaPlataforma() double
        +alterarStatus() void
        +publicar() void
    }

    class Usuario {
        -nome : String
        -email : String
        -fone : String
        +Usuario(nome: String, email: String, fone: String)
        +info() String
    }

    class Livro {
        -titulo : String
        -autor : String
        -isbn : String
        -estado : String
        +Livro(titulo: String, autor: String, isbn: String, estado: String)
        +info() String
    }
    
    Anuncio o-- Usuario : possui vendedor
    Anuncio o-- Livro : possui livro
    
    Anuncio &lt;|-- AnuncioVenda
    Anuncio &lt;|-- AnuncioTroca
    
    Publicavel &lt;|.. AnuncioVenda
    Publicavel &lt;|.. AnuncioTroca
    
