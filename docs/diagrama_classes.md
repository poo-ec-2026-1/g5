### Código PlantUML do Diagrama de Classes
```
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
```

### Imagem gerada pelo código
![Diagrama](https://img.plantuml.biz/plantuml/png/pLHDImCn4BtdLmnwgTGMlPOYxL7m85QzpsPJ2MOpPPAKuiT_DvlzJAdeHV2qvCtCc_TcCravZ-n3POHxrNQ7Z1L8WywjlMTT1Azk9TcDVbDm9OIsNl4MfOBxK1WjSQyCl0k0AUmQW2SNi80zwL9y2e65yupIDvImji5AJQdY77Qi9CNaacAfIb0KuuEBs2Osjoy9AvMJ79bf34z17NIX-K6vKD7GiuvuFE2zGOzRuWfJh6yn1JJH0l8cTSh4PXgVbIsnjJQ69aRlcHPmLtykOHynLH8luCa5P1sXPAjhUJd8xzHV_8NyR_z0PkgHIJQcPb4coS2O3tMSznkdYQ9Z-2TUMtcjJKkL6dkSWQfGcnpQabNv85gIILq--4kbmx4kovrgksqSjbIzg3J4LfBNFXZA5M3mn3caNM6FbCVrA8_M815F6if1MQ8RZZNLo4MY-L5ujwndrsT0irdwThDTm8wS2xg_5N1pCh-ntMUd6nLJ-zpbHqmShzV9IBgEGeoUdEN7V9xNVHDiIrSn9xvnNm00)
