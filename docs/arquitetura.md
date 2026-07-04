# Arquitetura do Software - TradeLibrary

Este documento descreve as decisões arquiteturais, o fluxo de dados, a estrutura de persistência local e os algoritmos de busca e filtragem do sistema **TradeLibrary**.

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
    *   *Exemplos*: [Usuario.java](../model/Usuario.java), [Anuncio.java](../model/Anuncio.java), [Database.java](../model/Database.java), [AnuncioRepository.java](../model/AnuncioRepository.java).
*   **`view`**: Composto pelas telas declarativas em FXML e folhas de estilo CSS. Define a aparência visual da aplicação.
    *   *Exemplos*: [CatalogView.fxml](../view/CatalogView.fxml), [application.css](../view/application.css).
*   **`controller`**: Camada intermediária que responde às interações na View, valida os dados de entrada, coordena a navegação e invoca os métodos de manipulação do Model.
    *   *Exemplos*: [CatalogController.java](../controller/CatalogController.java), [AddBookController.java](../controller/AddBookController.java).

---

## 2. Fluxo de Dados do Sistema

O fluxo típico de dados segue um ciclo bem definido, exemplificado abaixo pelo processo de cadastro de um novo livro:

1.  **Entrada do Usuário (View)**: O usuário preenche os campos de título, autor e preço em [AddBookView.fxml](../view/AddBookView.fxml) e clica no botão "Salvar".
2.  **Captura e Validação (Controller)**: O [AddBookController](../controller/AddBookController.java) intercepta o evento através do método `salvarAnuncio()`, lê os valores dos campos de texto, valida as entradas (como impedir preço nulo ou título vazio) e exibe feedbacks visuais de erro caso necessário.
3.  **Encaminhamento ao Modelo (Model)**:
    *   Se válido, o Controller cria instâncias de [Livro](../model/Livro.java) e de uma subclasse de [Anuncio](../model/Anuncio.java) (como [AnuncioVenda](../model/AnuncioVenda.java)).
    *   O Controller instancia o repositório correspondente ([AnuncioRepository](../model/AnuncioRepository.java)) e chama o método de persistência (por exemplo, `cadastrarAnuncioVenda()`).
4.  **Gravação em Banco**: O repositório usa o driver SQLite via ORMLite para registrar o objeto no arquivo de banco de dados `tradelibrary.db`.
5.  **Atualização da View**: O Controller limpa os campos do formulário e emite mensagens de sucesso. Quando o usuário navega para a aba Catálogo, o [CatalogController](../controller/CatalogController.java) é inicializado, consultando o repositório para carregar dinamicamente os novos cards na interface gráfica.

### Fluxo para Compra de Livros (Carrinho)
1.  **Carrinho (View)**: O usuário abre [CartView.fxml](../view/CartView.fxml) e clica em "Finalizar Compra".
2.  **Processamento (Controller/Manager)**: O [CartController](../controller/CartController.java) delega para o [CartManager](../controller/CartManager.java).
3.  **Transação e Exclusão (Model)**: O `CartManager` itera sobre os itens do carrinho, registra uma instância de [Transacao](../model/Transacao.java) via [TransacaoRepository](../model/TransacaoRepository.java), e remove o [Anuncio](../model/Anuncio.java) do catálogo via [AnuncioRepository](../model/AnuncioRepository.java). Em seguida, limpa os itens do banco de dados na tabela de carrinho por meio do [CartItemRepository](../model/CartItemRepository.java).

### Fluxo para Troca de Livros (Propostas)
1.  **Detalhes do Livro (View)**: O usuário visualiza um anúncio de troca em [BookDetailsView.fxml](../view/BookDetailsView.fxml) e clica em "Solicitar Troca".
2.  **Solicitação (Controller/Manager)**: O [TradeManager](../controller/TradeManager.java) exibe uma caixa de diálogo para que o proponente digite o título do livro oferecido.
3.  **Registro (Model)**: Uma nova [Proposta](../model/Proposta.java) com status `"PENDENTE"` é criada e salva através do [PropostaRepository](../model/PropostaRepository.java).
4.  **Aceitação (Controller)**: O proprietário do livro visualiza a proposta pendente em [ProposalsView.fxml](../view/ProposalsView.fxml) (gerenciada por [ProposalsController](../controller/ProposalsController.java)) e clica em "Aceitar".
5.  **Finalização (Model)**: O `ProposalsController` altera o status da proposta para `"ACEITA"`, rejeita as demais propostas concorrentes para o mesmo anúncio definindo-as como `"REJEITADA"`, registra a [Transacao](../model/Transacao.java) de troca, remove o anúncio do catálogo e fornece as informações de contato do proponente.

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
*   **Tabela `itens_carrinho`**:
    Mapeia os itens adicionados ao carrinho de compras de um usuário.
    ```java
    @DatabaseTable(tableName = "itens_carrinho")
    public class CartItem {
        @DatabaseField(generatedId = true, columnName = "id")
        private int id;
        @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "usuario_email", canBeNull = false)
        private Usuario usuario;
        @DatabaseField(columnName = "anuncio_id", canBeNull = false)
        private int anuncioId;
        @DatabaseField(columnName = "tipo_anuncio", canBeNull = false)
        private String tipoAnuncio; // "VENDA" ou "TROCA"
    }
    ```
*   **Tabela `propostas`**:
    Mapeia propostas de escambo/troca recebidas pelos anunciantes.
    ```java
    @DatabaseTable(tableName = "propostas")
    public class Proposta {
        @DatabaseField(generatedId = true, columnName = "id")
        private int id;
        @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "anuncio_id", canBeNull = false)
        private AnuncioTroca anuncio;
        @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "proponente_email", canBeNull = false)
        private Usuario proponente;
        @DatabaseField(columnName = "livro_oferecido", canBeNull = false)
        private String livroOferecido;
        @DatabaseField(columnName = "status", canBeNull = false)
        private String status = "PENDENTE"; // PENDENTE, ACEITA, REJEITADA
    }
    ```
*   **Tabela `transacoes`**:
    Armazena o histórico de todas as transações finalizadas na plataforma.
    ```java
    @DatabaseTable(tableName = "transacoes")
    public class Transacao {
        @DatabaseField(generatedId = true, columnName = "id")
        private int id;
        @DatabaseField(columnName = "tipo", canBeNull = false)
        private String tipo; // "TROCA" ou "VENDA"
        @DatabaseField(columnName = "livro_titulo", canBeNull = false)
        private String livroTitulo;
        @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "vendedor_email", canBeNull = false)
        private Usuario vendedor;
        @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "comprador_email", canBeNull = false)
        private Usuario comprador;
        @DatabaseField(columnName = "detalhes", canBeNull = false)
        private String detalhes;
        @DatabaseField(columnName = "data_transacao", canBeNull = false)
        private String dataTransacao;
    }
    ```

### Classe de Conexão: `Database`

A inicialização e conexão com o banco de dados são centralizadas na classe [Database.java](../model/Database.java). Ela implementa o padrão Singleton para o objeto `ConnectionSource` e cria as tabelas automaticamente se estas não existirem na primeira execução do aplicativo:

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
            TableUtils.createTableIfNotExists(connectionSource, CartItem.class);
            TableUtils.createTableIfNotExists(connectionSource, Proposta.class);
            TableUtils.createTableIfNotExists(connectionSource, Transacao.class);
        }
        return connectionSource;
    }
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
