# Relatório de Desenvolvimento - Backend (TradeLibrary)

Este documento apresenta o relatório detalhado das atividades de desenvolvimento, responsabilidades, contribuições e aprendizados voltados à engenharia de **Backend** no projeto **TradeLibrary**.

---

## 1. Atribuição de Cargo e Tarefas

### Atribuição A Priori e Responsabilidades
A atribuição principal estabelecida foi a de **Desenvolvedor Backend**, com as seguintes responsabilidades planejadas:
*   Modelagem do banco de dados relacional local (SQLite).
*   Configuração e integração do ORM (**ORMLite**) para persistência de dados.
*   Implementação das classes de domínio (Modelos) e seus respectivos Repositórios (DAO).
*   Criação de rotinas de segurança, como a criptografia/hashing de senhas.
*   Gestão das sessões ativas do usuário e controle de autenticação.

### O que foi Exercido na Prática
Na prática, além das responsabilidades puramente de infraestrutura e banco de dados, o papel englobou a ponte direta com os controladores JavaFX (Controller) para garantir que a lógica de negócios fluísse corretamente para a interface visual. Isso incluiu estruturar gerenciadores de estado em memória (como o carrinho de compras) e a validação em tempo de execução dos fluxos de banco de dados diretamente na UI.

---

## 2. Contribuição de Acordo com a Atribuição

### O que foi Cumprido
Toda a base de dados local do TradeLibrary foi implementada com sucesso. Os modelos de dados foram mapeados por meio de anotações do ORMLite e o banco de dados autogerido (`tradelibrary.db`) foi estabelecido de forma estável.

### Os 3 Commits Mais Relevantes
Abaixo estão detalhados os três commits centrais de contribuição ao backend:

1.  **Implementa persistência de livros/anúncios e tela de detalhes do catálogo**  
    *   **SHA:** `7a5bd6a7f6304aaa899dabf57532157c6607bf16`  
    *   **Descrição:** Configuração inicial e mapeamento relacional das entidades de anúncios (`AnuncioVenda` e `AnuncioTroca`), relacionamento com `Livro` e `Usuario`, além do fornecimento dos métodos de inserção e exclusão em lote no `AnuncioRepository`. Também estruturou os dados carregados na tela de detalhes do produto.

2.  **Adicionada função de carrinho de compra**  
    *   **SHA:** `8bf2908fc8cebc45833b76c5760f7274332ade6a`  
    *   **Descrição:** Criação do componente `CartManager` como um padrão Singleton, gerenciando a inclusão temporária de itens em memória, validação contra duplicações baseada em ISBN, cálculo de somas e fluxo de checkout que remove fisicamente os anúncios vendidos do banco de dados SQLite.

3.  **Criado protótipo de sistema de troca**  
    *   **SHA:** `498253c209e6b4bf8aa66069dee7074364bc8331`  
    *   **Descrição:** Criação do módulo `TradeManager` especializado para lidar com anúncios que utilizam o formato de escambo (troca por procura). Implementou o fluxo interativo que coleta a oferta de livro do usuário interessado, atualiza o status no banco e expõe as informações do proprietário para o fechamento do negócio de forma dinâmica.

### O que não deu para Cumprir e Dificuldades
*   **Completar o Sistema de Troca:** Não foi possível completar na totalidade o sistema de trocas no aplicativo, pois ainda restam dificuldades logísticas a serem resolvidas pela equipe sobre como exatamente as trocas físicas de livros serão efetuadas e validadas entre os usuários finais.
*   **Dificuldades:** A principal dificuldade foi planejar a logística da transação física direta dos livros e as regras de controle do banco de dados, além de gerenciar a dependência circular de inicialização na carga de testes do banco de dados.

---

## 3. Contribuição Além do Atribuído

Embora a atribuição inicial tenha sido estritamente voltada ao desenvolvimento do backend, também exerci papel de suporte ao time de frontend e engenharia de software para destravar o projeto e melhorar a experiência de uso.

### Commits Extras Mais Relevantes
*   **Correção em erro no login de usuário** (SHA: `876fd9acd051013d98b07db8a2388346a6af0f31`)  
    *   *Descrição:* Resolução de falha crítica de autenticação decorrente de senhas salvas em formato plano de cargas demo anteriores, além da correção de bug de criptografia dupla (double-hashing) que invalidava o login automático durante o fluxo de cadastro.
*   **Tirada função de preencher catálogo vazio** (SHA: `89219e06db3d37e203e201da77ea207b790899fd`)  
    *   *Descrição:* Remoção do loop infinito de preenchimento automático de livros demo ao zerar o banco de dados. Permitiu que o catálogo ficasse devidamente vazio e mostrasse a mensagem "Nenhum livro no catálogo" de forma transparente e amigável.

### Atividades de Apoio Técnico Geral
*   **Resolução de Erros de Ambientes de Execução:**  
    Ajustei o arquivo de configuração de inicialização [launch.json](file:///home/brunba/Documents/Shit/Escuela/g5/.vscode/launch.json) que estava corrompido com erros de sintaxe (aspas não escapadas nos parâmetros da VM do JavaFX), o que impedia qualquer desenvolvedor de rodar ou depurar o software localmente.
*   **Adaptação nos Arquivos FXML e Estilos:**  
    Para suportar os novos comportamentos (Carrinho e Trocar), modifiquei diretamente os arquivos de visualização [BookCard.fxml](file:///home/brunba/Documents/Shit/Escuela/g5/view/BookCard.fxml) and [BookDetailsView.fxml](file:///home/brunba/Documents/Shit/Escuela/g5/view/BookDetailsView.fxml) e os vinculei de maneira dinâmica aos controladores para ajustar textos e botões conforme o tipo do anúncio.

---

## 4. Considerações Gerais

### Aprendizados
*   Prática aprofundada na arquitetura MVC em desktop com JavaFX.
*   Uso prático do ORMLite e o mapeamento de classes abstratas/heranças polimórficas (como `Anuncio` estendendo em `AnuncioVenda` e `AnuncioTroca`).
*   Importância de desacoplar a lógica de tela da lógica de negócios, o que permitiu criar o carrinho e o sistema de trocas isoladamente e plugá-los com facilidade.

### Trabalhos Futuros Pendentes
*   **Finalizar o Sistema de Troca:** Completar o sistema de troca com a troca de livros no catálogo e/ou um saldo de livros para trocar. 
*   **Validação de Usuário Dono:** Impedir que o usuário adicione ao carrinho ou tente trocar um livro que ele mesmo cadastrou.
*   **Persistência do Carrinho:** Salvar os itens do carrinho no banco SQLite por sessão do usuário, para que não se percam caso o aplicativo seja reiniciado inesperadamente.

### Conclusão
A implementação das regras do backend e do banco de dados SQLite provou-se sólida e flexível, permitindo a expansão ágil de novas regras de negócio essenciais (como o carrinho de compras e propostas de escambo) sem comprometer a integridade e as boas práticas de orientação a objetos acordadas com a equipe.
