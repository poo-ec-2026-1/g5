# TradeLibrary - Troca e Venda de Livros Usados

<p align="center">
  <img src="resources/images/librarylogo1.png" alt="TradeLibrary Logo" width="200">
</p>


## Pré-requisitos

- **JDK 25 ou superior** — recomendado: [Eclipse Temurin](https://adoptium.net/)
- **JavaFX SDK 26** — baixar em: [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)

## Configuração inicial (faça uma vez)

### 1. Clonar o repositório

```bash
git clone https://github.com/<seu-usuario>/g5.git
cd g5
```

### 2. Adicionar os arquivos nativos do JavaFX na pasta `lib/`

A pasta `lib/` já contém os arquivos `.jar` do JavaFX.
Você precisa copiar os arquivos `.dll` (Windows) da pasta `bin/` do seu SDK para a pasta `lib/` do projeto:

```
Copie de: C:\caminho-para-seu-sdk\javafx-sdk-26\bin\*.dll
     Para: g5\lib\
```

> ⚠️ Os `.dll` não são versionados no Git por serem específicos de cada sistema operacional.

---

## Como executar

### VS Code
1. Abra a pasta `g5/` no VS Code
2. Instale a extensão **"Extension Pack for Java"** se ainda não tiver
3. Vá em **Run and Debug** (Ctrl+Shift+D)
4. Escolha **"Run Main (JavaFX)"** para abrir a interface gráfica
5. Ou escolha **"Run TestePublicavel"** / **"Run TesteMarketplace"** para os testes em console

### Eclipse IDE
1. Importe o projeto: **File → Import → Existing Projects into Workspace**
2. Configure os VM Arguments na Run Configuration do `Main`:
   - **Run → Run Configurations → Arguments → VM arguments:**
   ```
   --module-path "C:\caminho-para-seu-sdk\javafx-sdk-26\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics
   ```
3. Para `TestePublicavel` e `TesteMarketplace` não é necessário nenhuma configuração extra.

---

## Estrutura do projeto

```
g5/
├── controller/         # Classes de controle (ViewController, Testes)
├── model/              # Classes de domínio (Livro, Usuario, Anuncio...)
├── view/               # Interface gráfica (View.fxml, application.css)
├── resources/          # Recursos estáticos (imagens)
├── lib/                # JARs do JavaFX (DLLs devem ser adicionadas localmente)
├── Main.java           # Ponto de entrada da aplicação JavaFX
└── .vscode/            # Configurações de execução para VS Code
```
