# Guia de Contribuição - TradeLibrary

Este guia foi criado para orientar os desenvolvedores do grupo sobre os padrões de desenvolvimento adotados e as instruções de configuração do ambiente local.

---

## 1. Princípios de Organização de Código (Não Poluição dos Controllers)

Para manter a testabilidade, legibilidade e manutenibilidade do sistema, é fundamental respeitar o limite entre a lógica da interface do usuário (UI) e a lógica de negócios da aplicação.

### O Papel do Controller (Camada View-Control)
Os controllers JavaFX devem lidar **exclusivamente** com tarefas de apresentação e controle de eventos gráficos.
*   **O que deve ficar no Controller**:
    *   Leitura de dados de campos de texto, comboboxes e seleções gráficas.
    *   Formatação e limpeza de erros visuais básicos (ex.: colorir borda do campo se vazio).
    *   Encaminhamento de chamadas para repositórios ou classes de serviço.
    *   Controle de fluxo de navegação entre telas (carregamento de FXMLs).
*   **O que NÃO deve ficar no Controller (Regras de Negócio poluidoras)**:
    *   Consultas SQL diretas ou instanciação de DAOs do ORMLite para consultas brutas arbitrárias.
    *   Cálculo de taxas de comissão ou outras fórmulas financeiras da plataforma.
    *   Validação de regras complexas do domínio (ex.: verificar duplicidades complexas de usuários ou estados de anúncios).
    *   Estruturas de inicialização estática de conexões de banco de dados.

### Exemplo Prático de Separação de Responsabilidade

#### ❌ Abordagem Incorreta (Controller Poluído)
```java
// Dentro do AddBookController...
@FXML
private void salvarAnuncio() {
    double preco = Double.parseDouble(txtPreco.getText());
    // LÓGICA DE NEGÓCIO E PERSISTÊNCIA MISTURADA:
    double taxa = preco * 0.05; // Regra de negócio inserida no Controller!
    if (taxa < 1.0) { 
        System.out.println("Erro: taxa mínima de R$ 1,00 não atendida.");
        return;
    }
    
    // Conexão direta com banco executada no Controller:
    ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:tradelibrary.db");
    Dao<AnuncioVenda, Integer> dao = DaoManager.createDao(conn, AnuncioVenda.class);
    dao.create(new AnuncioVenda(...));
}
```

####   Abordagem Correta (Arquitetura Limpa)
```java
// No Controller (apenas captação e chamada):
@FXML
private void salvarAnuncio() {
    // 1. Validação visual
    if (txtPreco.getText().isEmpty()) return;
    
    // 2. Delegação para o Repositório/Serviço no Model
    AnuncioRepository repo = new AnuncioRepository();
    repo.cadastrarAnuncioVenda(anuncio); // O Model se encarrega de persistir e processar
}

// No Model (AnuncioVenda.java):
@Override
public double taxaPlataforma() {
    return this.preco * 0.05; // Regra encapsulada no domínio correto
}
```

---

## 2. Como Configurar e Executar o Projeto

### Pré-requisitos de Software
*   **JDK 25 ou superior** (Recomendado: [Eclipse Temurin](https://adoptium.net/))
*   **JavaFX SDK 26** (Baixar da página oficial da [Gluon](https://gluonhq.com/products/javafx/))

### Cópia de Dependências Nativas (Importante)
A pasta `lib/` do repositório já contém as dependências `.jar` do JavaFX e do ORMLite. No entanto, para que os gráficos carreguem corretamente em ambientes Windows, você deve copiar os arquivos dinâmicos `.dll` da pasta `bin/` do seu SDK baixado e colá-los na pasta `lib/` do projeto:

```
Copiar de: C:\caminho-para-seu-sdk\javafx-sdk-26\bin\*.dll
Colar em:  <raiz-do-projeto-g5>\lib\
```

---

### Execução no VS Code
1.  Instale o **Java Extension Pack** disponibilizado pela Microsoft.
2.  Abra a pasta do projeto no VS Code.
3.  Vá até a aba **Run and Debug** (Ctrl+Shift+D).
4.  Selecione a configuração **"Run Main (JavaFX)"** e clique no botão de reprodução (F5).
5.  *Nota: O arquivo `.vscode/launch.json` já vem pré-configurado no repositório com os argumentos VM adequados, incluindo o caminho `-Djava.library.path` apontando para a pasta local `lib/`.*

---

### Execução no Eclipse IDE
1.  Importe o projeto na workspace: **File → Import → General → Existing Projects into Workspace**.
2.  Selecione a pasta raiz do projeto e clique em **Finish**.
3.  Clique com o botão direito na classe [Main.java](../Main.java) e escolha **Run As → Run Configurations**.
4.  Selecione a aba **Arguments** e, no campo **VM Arguments**, adicione o seguinte comando (ajustando o caminho para o seu JavaFX SDK local):
    ```bash
    --module-path "C:\caminho-para-seu-sdk\javafx-sdk-26\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics
    ```
5.  Clique em **Apply** e depois em **Run**.
