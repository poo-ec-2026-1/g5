# Relatório de Desenvolvimento - Backend (TradeLibrary) - Versão Finalizada

Este documento apresenta o relatório detalhado de conclusão das atividades de desenvolvimento, responsabilidades, contribuições e aprendizados voltados à engenharia de **Backend** no projeto **TradeLibrary**.

---

## 1. Atribuição de Cargo e Tarefas

### Atribuição A Priori e Responsabilidades
A atribuição principal estabelecida foi a de **Desenvolvedor Backend**, com as seguintes responsabilidades planejadas:
*   Modelagem do banco de dados relacional local (SQLite).
*   Configuração e integração do ORM (**ORMLite**) para persistência de dados.
*   Implementação das classes de domínio (Modelos) e seus respectivos Repositórios (DAO).
*   Gestão das sessões ativas do usuário e controle de autenticação.

### O que foi Exercido na Prática
Na prática, além das responsabilidades puramente de infraestrutura e banco de dados, o papel englobou a ponte direta com os controladores JavaFX (Controller) para garantir que a lógica de negócios fluísse corretamente para a interface visual. Isso incluiu estruturar gerenciadores de estado em memória (como o carrinho de compras) e a persistência por usuário no SQLite.

---

## 2. Contribuição de Acordo com a Atribuição

### O que foi Cumprido
Toda a base de dados local do TradeLibrary foi implementada com sucesso. Os modelos de dados foram mapeados por meio de anotações do ORMLite e o banco de dados autogerido (`tradelibrary.db`) foi estabelecido de forma estável. Adicionalmente, todos os recursos planejados foram integrados a nível de banco de dados, incluindo a persistência do carrinho e a gestão de transações.

### Os 3 Commits Mais Relevantes
Abaixo estão detalhados os três commits centrais de contribuição ao backend:

1.  **Implementa persistência de livros/anúncios e tela de detalhes do catálogo**  
    *   **SHA:** `7a5bd6a7f6304aaa899dabf57532157c6607bf16`  
    *   **Descrição:** Configuração inicial e mapeamento relacional das entidades de anúncios (`AnuncioVenda` e `AnuncioTroca`), relacionamento com `Livro` e `Usuario`, além do fornecimento dos métodos de inserção e exclusão em lote no `AnuncioRepository`. Também estruturou os dados carregados na tela de detalhes do produto.

2.  **Adicionada função de carrinho de compra**  
    *   **SHA:** `8bf2908fc8cebc45833b76c5760f7274332ade6a`  
    *   **Descrição:** Criação do componente `CartManager`, gerenciando a inclusão temporária de itens em memória, cálculo de somas e fluxo de checkout que remove fisicamente os anúncios vendidos do banco de dados SQLite.

3.  **Criado protótipo de sistema de troca**  
    *   **SHA:** `498253c209e6b4bf8aa66069dee7074364bc8331`  
    *   **Descrição:** Criação do módulo `TradeManager` especializado para lidar com anúncios que utilizam o formato de troca. Implementou o fluxo interativo que coleta a oferta de livro do usuário interessado, atualiza o status no banco e expõe as informações do proprietário.

### Dificuldades e Soluções
*   **Completar o Sistema de Trocas (Resolvido):** As dificuldades logísticas iniciais sobre como as trocas seriam processadas foram solucionadas através de um fluxo assíncrono de **Propostas de Troca** (`Proposta`) com status (`PENDENTE`, `ACEITA`, `REJEITADA`) no banco de dados. O livro permanece no catálogo até que a proposta seja expressamente aceita pelo vendedor, evitando furos no estoque.
*   **Registro das Transações (Resolvido):** Criou-se a entidade `Transacao` para registrar o histórico financeiro e de trocas do aplicativo, salvando informações detalhadas e logs temporais de compras finalizadas e escambos aprovados.
*   **Dificuldades:** Gerenciamento da dependência circular de inicialização na carga de testes do banco de dados e controle concorrente de DAOs do ORMLite resolvidos centralizando as tabelas no singleton da classe `Database`.

---

## 3. Contribuição Além do Atribuído

Embora a atribuição inicial tenha sido estritamente voltada ao desenvolvimento do backend, exerci um papel fundamental de suporte ao time de frontend e engenharia de software para destravar o projeto e melhorar a experiência de uso.

### Commits Extras Mais Relevantes
*   **Impede vendas e trocas de livros postados por si mesmo** (SHA: `944583604a35c8b8c0b9972cbcde9a6c9db597b7`)
    *   *Descrição:* Dinamicamente altera o botão no catálogo, para que, caso o livro visualizado seja do próprio usuário, ele não possa ser adicionado ao carrinho ou proposto uma troca.
*   **Exige login após cadastro de novo usuário** (SHA: `50853d03b229492bc907b4b88495f70e4ee67780`)
    *   *Descrição:* Configuração da propriedade `defaultButton` no botão de login para disparar a autenticação com a tecla *Enter*, e alteração do fluxo de cadastro para redirecionar o usuário à tela de login após a criação da conta.
*   **Ajuste nos menus e botões de catálogo e proposta** (SHA: `890e682e457bcce25c5e5afb444ac33d86715bee`)
    *   *Descrição:* Ajustes visuais nos botões do catálogo, que não cabiam o texto corretamente, e no ícone de propostas.

### Frontend básico por necessidade
*   **Criação do frontend do catálogo**  
    Ao criar o catálogo no backend, para verificar o funcionamento, também criei o básico da UI do catálogo, que foi posteriormente refinada pelo Guilherme. 
*   **Criação do frontend do carrinho**  
    A mesma situação do catálogo ocorreu no carrinho.

---

## 4. Considerações Gerais

### Aprendizados
*   Prática aprofundada na arquitetura MVC em desktop com JavaFX.
*   Uso prático do ORMLite e o mapeamento de classes abstratas/heranças polimórficas (como `Anuncio` estendendo em `AnuncioVenda` e `AnuncioTroca`).
*   Importância de desacoplar a lógica de tela da lógica de negócios, o que permitiu criar o carrinho e o sistema de trocas isoladamente e plugá-los com facilidade.
*   Desenvolvimento de regras rígidas de validação de negócios tanto no nível visual quanto nos repositórios.

### Trabalhos Futuros Pendentes (Próximos Passos)
Com a conclusão da persistência do carrinho, controle de propostas de troca e histórico de transações, o TradeLibrary atingiu um excelente patamar de maturidade backend. Ficam pendentes para futuras iterações:
*   **Integração de Gateways de Pagamento:** Conectar a finalização de compras (tipo `"VENDA"`) com APIs externas de pagamento (ex: Pix ou Stripe).
*   **Chat Interno entre Usuários:** Implementar um chat direto por WebSockets ou persistido no banco para que as pessoas combinem a entrega de trocas de forma integrada.
*   **Rastreamento e Logística:** Adicionar campos de endereço e cálculo de frete para entrega física.

### Conclusão
A implementação das regras do backend e do banco de dados SQLite provou-se sólida e flexível, permitindo a expansão ágil de novas regras de negócio essenciais (como a persistência do carrinho, controle transacional e propostas de troca) sem comprometer a integridade e as boas práticas de orientação a objetos acordadas com a equipe.

## 5. Apresentação em vídeo do relatório
* [Vídeo apresentando o relatório (Google Drive)](https://drive.google.com/file/d/1bT0Ax3xtllFQzIUJvWUjWvcZ0qtCGT6m/view?usp=sharing)
