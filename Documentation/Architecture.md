# Arquitetura do Software - TradeLibrary

Este documento descreve as decisões arquiteturais, o fluxo de dados, a estrutura de persistência local e os algoritmos para busca e filtragem do sistema **TradeLibrary**.

---

## 1. Padrão Arquitetural Model-View-Controller (MVC)

O sistema utiliza o padrão arquitetural **MVC (Model-View-Controller)** para garantir uma clara separação de responsabilidades. Isso facilita a manutenção, teste e evolução de cada parte do sistema de forma independente.

```
       ┌────────────────────────┐
       │          VIEW          │◄────────────────┐
       │ (FXML / CSS / JavaFX)  │                 │
       └───────────┬────────────┘                 │
                   │                              │
                   │ Interação do                 │ Atualização
                   │ Usuário                      │ da UI
                   ▼                              │
       ┌────────────────────────┐                 │
       │       CONTROLLER       │─────────────────┘
       │    (Java Controllers)  │
       └───────────┬────────────┘
                   │
                   │ Manipula /
                   │ Consulta
                   ▼
       ┌────────────────────────┐
       │         MODEL          │
       │  (Entidades / DAOs /   │
       │    ORMLite + SQLite)   │
       └────────────────────────┘
```

### Divisão de Pacotes no Projeto

*   **`model`**: Contém o modelo de domínio do negócio, incluindo a lógica de acesso a dados (Repositories/DAOs) e a persistência local.
    *   *Exemplos*: [Usuario.java](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Usuario.java), [Anuncio.java](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Anuncio.java), [Database.java](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Database.java), [AnuncioRepository.java](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/AnuncioRepository.java).
*   **`view`**: Composto pelas telas declarativas em FXML e folhas de estilo CSS. Define a aparência visual da aplicação.
    *   *Exemplos*: [CatalogView.fxml](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/view/CatalogView.fxml), [application.css](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/view/application.css).
*   **`controller`**: Camada intermediária que responde às interações na View, valida os dados de entrada, coordena a navegação e invoca os métodos de manipulação do Model.
    *   *Exemplos*: [CatalogController.java](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/controller/CatalogController.java), [AddBookController.java](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/controller/AddBookController.java).

---

## 2. Fluxo de Dados do Sistema

O fluxo típico de dados segue um ciclo bem definido, exemplificado abaixo pelo processo de cadastro de um novo livro:

1.  **Entrada do Usuário (View)**: O usuário preenche os campos de título, autor e preço em [AddBookView.fxml](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/view/AddBookView.fxml) e clica no botão "Salvar".
2.  **Captura e Validação (Controller)**: O [AddBookController](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/controller/AddBookController.java#L69-L115) intercepta o evento através do método `salvarAnuncio()`, lê os valores dos campos de texto, valida as entradas (como impedir preço nulo ou título vazio) e exibe feedbacks visuais de erro caso necessário.
3.  **Encaminhamento ao Modelo (Model)**:
    *   Se válido, o Controller cria instâncias de [Livro](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Livro.java) e de uma subclasse de [Anuncio](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Anuncio.java) (como [AnuncioVenda](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/AnuncioVenda.java)).
    *   O Controller instancia o repositório correspondente ([AnuncioRepository](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/AnuncioRepository.java)) e chama o método de persistência (por exemplo, `cadastrarAnuncioVenda()`).
4.  **Gravação em Banco**: O repositório usa o driver SQLite via ORMLite para registrar o objeto no arquivo de banco de dados `tradelibrary.db`.
5.  **Atualização da View**: O Controller limpa os campos do formulário e emite mensagens de sucesso no log. Quando o usuário navega para a aba Catálogo, o [CatalogController](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/controller/CatalogController.java) é inicializado, consultando o repositório para carregar dinamicamente os novos cards na interface gráfica.

---

## 3. Persistência de Dados Local

A persistência de dados no TradeLibrary é construída sobre o **SQLite**, gerenciado de forma simplificada usando o framework ORM leve **ORMLite** (Object-Relational Mapping Lite).

### Estrutura do Banco de Dados

O banco de dados é um arquivo local denominado `tradelibrary.db` localizado na raiz do projeto. As tabelas são definidas via anotações em Java diretamente nas classes de modelo:

*   **Tabela `usuarios`**:
    ```java
    @DatabaseTable(tableName = "usuarios")
    public class Usuario {
        @DatabaseField(id = true, columnName = "email")
        private String email; // Chave primária de tipo String
        
        @DatabaseField(columnName = "nome", canBeNull = false)
        private String nome;
        
        @DatabaseField(columnName = "fone", canBeNull = false)
        private String fone;
        
        @DatabaseField(columnName = "senha", canBeNull = false)
        private String senha;
        // ...
    }
    ```
*   **Tabela `livros`**:
    ```java
    @DatabaseTable(tableName = "livros")
    public class Livro {
        @DatabaseField(generatedId = true, columnName = "id")
        private int id; // Chave primária autoincrementada
        
        @DatabaseField(columnName = "titulo", canBeNull = false)
        private String titulo;
        // ...
    }
    ```
*   **Tabelas `anuncios_venda` e `anuncios_troca`**:
    Utilizam uma estratégia de mapeamento de herança por tabelas de classes concretas. Os atributos genéricos herdados da classe abstrata `Anuncio` são compartilhados em ambas as tabelas:
    ```java
    public abstract class Anuncio {
        @DatabaseField(generatedId = true, columnName = "id")
        protected int id;
        
        @DatabaseField(columnName = "preco", canBeNull = false)
        protected double preco;
        
        @DatabaseField(columnName = "status", canBeNull = false)
        protected String status = "Disponivel";
        
        @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "livro_id", canBeNull = false)
        protected Livro livro; // Chave estrangeira para a tabela de livros
        
        @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "vendedor_email", canBeNull = false)
        protected Usuario vendedor; // Chave estrangeira para a tabela de usuários
        
        @DatabaseField(columnName = "descricao")
        protected String descricao;
        // ...
    }
    ```

### Classe de Conexão: `Database`

A inicialização e conexão com o banco de dados são centralizadas na classe [Database.java](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Database.java). Ela implementa o padrão Singleton para o objeto `ConnectionSource` e cria as tabelas automaticamente se estas não existirem na primeira execução do aplicativo:

```java
public class Database {
    private static final String DB_URL = "jdbc:sqlite:tradelibrary.db";
    private static ConnectionSource connectionSource;

    public static synchronized ConnectionSource getConnectionSource() throws SQLException {
        if (connectionSource == null) {
            connectionSource = new JdbcConnectionSource(DB_URL);
            
            // Inicialização DDL automática do banco de dados
            TableUtils.createTableIfNotExists(connectionSource, Usuario.class);
            TableUtils.createTableIfNotExists(connectionSource, Livro.class);
            TableUtils.createTableIfNotExists(connectionSource, AnuncioVenda.class);
            TableUtils.createTableIfNotExists(connectionSource, AnuncioTroca.class);
        }
        return connectionSource;
    }
    // ...
}
```

---

## 4. Estrutura de Dados e Algoritmos de Busca/Filtragem em Memória

Atualmente, o sistema TradeLibrary lê todos os dados do banco SQLite e gera uma coleção dinâmica em memória (através de `ArrayList` retornados pelo ORMLite).

Para implementar funcionalidades de busca avançada e filtragem rápida dentro da aplicação sem sobrecarregar o banco de dados SQLite com múltiplas queries, recomenda-se a utilização de algoritmos baseados em **Java Streams (API de Streams do Java 8+)**.

### Algoritmo de Busca e Filtragem Recomendado

Abaixo está o contrato de serviço recomendado para buscas em memória utilizando Java Streams, aproveitando o polimorfismo das classes de anúncios:

```java
package model;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogoBuscaService {

    /**
     * Filtra anúncios em memória com base em critérios textuais e categorias.
     * 
     * @param anuncios Lista completa de anúncios recuperada do banco.
     * @param termoBusca Termo digitado pelo usuário (busca no título ou autor).
     * @param tipoDesejado Filtro de tipo ("Venda", "Troca" ou "Todos").
     * @param estadoLivro Estado de conservação ("Novo", "Seminovo", "Usado" ou "Todos").
     * @return Lista filtrada de anúncios.
     */
    public static List<Anuncio> filtrar(List<Anuncio> anuncios, String termoBusca, String tipoDesejado, String estadoLivro) {
        String termoNormalizado = termoBusca == null ? "" : termoBusca.toLowerCase().trim();

        return anuncios.stream()
            .filter(anuncio -> {
                // 1. Filtro por termo de busca (Título ou Autor)
                if (termoNormalizado.isEmpty()) return true;
                if (anuncio.getLivro() == null) return false;
                
                String titulo = anuncio.getLivro().getTitulo().toLowerCase();
                String autor = anuncio.getLivro().getAutor().toLowerCase();
                return titulo.contains(termoNormalizado) || autor.contains(termoNormalizado);
            })
            .filter(anuncio -> {
                // 2. Filtro por tipo de anúncio (Venda vs Troca)
                if ("Todos".equalsIgnoreCase(tipoDesejado) || tipoDesejado == null) return true;
                if ("Venda".equalsIgnoreCase(tipoDesejado)) {
                    return anuncio instanceof AnuncioVenda;
                }
                if ("Troca".equalsIgnoreCase(tipoDesejado)) {
                    return anuncio instanceof AnuncioTroca;
                }
                return false;
            })
            .filter(anuncio -> {
                // 3. Filtro por estado de conservação
                if ("Todos".equalsIgnoreCase(estadoLivro) || estadoLivro == null) return true;
                if (anuncio.getLivro() == null) return false;
                return estadoLivro.equalsIgnoreCase(anuncio.getLivro().getEstado());
            })
            .collect(Collectors.toList());
    }
}
```

### Análise de Complexidade Algorítmica
*   **Tempo**: O algoritmo possui complexidade linear $\mathcal{O}(N)$ onde $N$ é a quantidade de anúncios carregados na memória. Como o catálogo de uma aplicação local opera com milhares (ou poucas dezenas de milhares) de itens, a operação de filtragem em memória utilizando Streams ocorre em poucos milissegundos, garantindo alta performance e feedback instantâneo na UI.
*   **Espaço**: A complexidade de espaço é $\mathcal{O}(M)$, onde $M$ é a quantidade de elementos que satisfazem o predicado e são agrupados na nova sublista gerada pelo `.collect(Collectors.toList())`.
