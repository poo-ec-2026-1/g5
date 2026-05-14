@startuml
skinparam classAttributeIconSize 0

interface Publicavel {
  + publicar() : void
}

abstract class Anuncio {
  # preco : double
  # status : String
  # descricao : String
  + exibirResumo() : void
  + taxaPlataforma() : double
  + alterarStatus() : void
}

class AnuncioVenda {
  + AnuncioVenda(livro: Livro, vendedor: Usuario, preco: double, descricao: String)
  + exibirResumo() : void
  + taxaPlataforma() : double
  + alterarStatus() : void
  + publicar() : void
}

class AnuncioTroca {
  - procura : String
  + AnuncioTroca(livro: Livro, vendedor: Usuario, procura: String, descricao: String)
  + exibirResumo() : void
  + taxaPlataforma() : double
  + alterarStatus() : void
  + publicar() : void
}

class Usuario {
  - nome : String
  - email : String
  - fone : String
  + Usuario(nome: String, email: String, fone: String)
  + info() : String
}

class Livro {
  - titulo : String
  - autor : String
  - isbn : String
  - estado : String
  + Livro(titulo: String, autor: String, isbn: String, estado: String)
  + info() : String
}

Anuncio "1" o-- "1" Usuario : possui vendedor >
Anuncio "1" o-- "1" Livro : possui livro >

Anuncio <|-- AnuncioVenda
Anuncio <|-- AnuncioTroca

Publicavel <|.. AnuncioVenda
Publicavel <|.. AnuncioTroca

@enduml
