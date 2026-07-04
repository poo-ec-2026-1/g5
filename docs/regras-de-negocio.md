# Regras de Negócio - TradeLibrary

Este documento descreve as regras de negócio que governam o sistema **TradeLibrary**, definindo o modelo de domínio, os fluxos principais de venda e troca, e o funcionamento das taxas, carrinho e transações.

---

## 1. Entidades de Domínio

O domínio de negócios do TradeLibrary é composto por entidades mapeadas em código Java e persistidas no SQLite:

```
                    ┌───────────────┐
                    │    Usuario    │
                    └───────┬───────┘
                            │ 1
                            │ vende/troca
                            ▼ 0..*
                    ┌───────────────┐
                    │    Anuncio    │
                    └───────┬───────┘
                            │ 1
                            │ descreve
                            ▼ 
                    ┌───────────────┐
                    │     Livro     │
                    └───────────────┘
```

### A. Usuário
Representado pela classe [Usuario](../model/Usuario.java). Representa um membro cadastrado no sistema que pode anunciar livros para venda ou troca, além de visualizar ofertas de terceiros.
*   **Identificador Único**: E-mail (`email`).
*   **Dados Cadastrais obrigatórios**: Nome completo (`nome`), telefone para contato (`fone`) e senha (`senha`).

### B. Livro
Representado pela classe [Livro](../model/Livro.java). Representa a obra física que está sendo disponibilizada.
*   **Dados obrigatórios**: Título (`titulo`), Autor (`autor`) e Estado de conservação (`estado`).
*   **Dado opcional**: Código de registro ISBN (`isbn`).
*   **Estados de conservação aceitos**:
    *   `Novo`: Sem marcas de uso.
    *   `Seminovo`: Marcas imperceptíveis ou mínimas de manuseio.
    *   `Usado`: Sinais claros de uso (grifos, páginas amareladas, etc.).

### C. Anúncio
Representado pela classe abstrata [Anuncio](../model/Anuncio.java). Representa a intenção de disponibilizar um livro na plataforma.
*   **Atributos básicos**:
    *   `id`: Código único autogerado.
    *   `livro`: O objeto [Livro](../model/Livro.java) associado.
    *   `vendedor`: O [Usuario](../model/Usuario.java) que publicou a oferta.
    *   `status`: Situação do anúncio, inicializado como `"Disponivel"`.
    *   `descricao`: Texto explicativo livre.
*   **Especializações (Polimorfismo)**:
    *   [AnuncioVenda](../model/AnuncioVenda.java): Anúncio voltado para a venda financeira de um exemplar. Possui um preço definido pelo usuário.
    *   [AnuncioTroca](../model/AnuncioTroca.java): Anúncio voltado para escambo de livros. Contém o atributo `procura` que indica a obra que o anunciante gostaria de receber em troca.

---

## 2. Fluxos Principais do Sistema

### 1. Fluxo de Venda de um Livro
A venda permite que um usuário desapegue de um livro em troca de uma quantia em dinheiro.

#### Processo Passo a Passo:
1.  **Acesso ao Cadastro**: O usuário logado clica em "Cadastrar Livro" no painel principal.
2.  **Preenchimento de Dados**: O usuário preenche as informações do livro.
3.  **Seleção do Tipo de Anúncio**: Seleciona a opção **"Venda"**. Isso faz com que a interface exiba o campo **"Preço"** e oculte o campo de procura.
4.  **Validação dos Dados (Controller)**:
    *   O título e o autor devem ser preenchidos.
    *   O preço deve ser um número decimal válido maior que zero.
5.  **Persistência**:
    *   O sistema cria e registra o [Livro](../model/Livro.java) na tabela de livros.
    *   O sistema cria o [AnuncioVenda](../model/AnuncioVenda.java) e o persiste associado ao usuário logado e ao livro recém-criado.
6.  **Disponibilização**: O anúncio passa a constar no catálogo com o preço estipulado e status `"Disponivel"`.
7.  **Finalização**: Quando a venda ocorre através do Carrinho, o anúncio é excluído do banco de dados para evitar compras duplicadas, e uma transação é registrada.

---

### 2. Fluxo de Carrinho de Compras e Checkout
O carrinho de compras permite que um comprador agrupe vários anúncios de venda para fechamento unificado.

#### Regras do Carrinho:
1.  **Restrição de Auto-compra**: Um usuário não pode adicionar seus próprios livros ao seu carrinho.
2.  **Restrição de Duplicidade**: Um livro não pode ser adicionado ao carrinho mais de uma vez. O sistema verifica isso usando o ISBN do livro.
3.  **Persistência local**: Os itens do carrinho são salvos no banco de dados local na tabela `itens_carrinho` mapeando a associação de [CartItem](../model/CartItem.java).
4.  **Checkout (Finalização de Compra)**:
    *   Para cada item de venda no carrinho, o sistema gera um registro de transação de tipo `"VENDA"` com o preço e a data do sistema.
    *   O anúncio é excluído fisicamente do catálogo usando o repositório correspondente.
    *   Os registros do carrinho são apagados para o usuário.
    *   O sistema exibe uma mensagem de confirmação com o total pago.

---

### 3. Fluxo de Troca de um Livro e Propostas
A troca possibilita escambo direto entre usuários que possuem interesses mútuos.

#### Processo Passo a Passo:
1.  **Cadastro do Anúncio**: O usuário logado cadastra o seu livro disponível e seleciona **"Troca"**, inserindo o livro que procura no campo de procura (ex.: "Código Limpo").
2.  **Solicitação de Troca**: Um usuário interessado abre os detalhes do anúncio e clica em **"Solicitar Troca"**. O sistema exibe um diálogo para que ele digite o nome do livro que está oferecendo em troca.
3.  **Registro de Proposta**:
    *   Uma proposta é gerada vinculando o anúncio de troca, o proponente e o título do livro oferecido.
    *   A proposta é persistida na tabela `propostas` com o status inicial `"PENDENTE"`.
4.  **Avaliação pelo Anunciante**:
    *   O anunciante visualiza a lista de propostas pendentes enviadas para seus livros.
    *   Caso decida **Aceitar**:
        1. A proposta aceita tem seu status alterado para `"ACEITA"`.
        2. Todas as outras propostas pendentes para aquele mesmo anúncio são atualizadas para `"REJEITADA"`.
        3. Uma transação de tipo `"TROCA"` é gravada na tabela `transacoes`.
        4. O anúncio de troca é excluído do catálogo.
        5. O sistema exibe na tela os dados de contato do proponente (nome, telefone e e-mail) para que a troca física seja combinada.
    *   Caso decida **Rejeitar**:
        1. O status daquela proposta específica é alterado para `"REJEITADA"`.
        2. A proposta desaparece da lista de pendências e o anúncio de troca permanece disponível no catálogo.

---

## 3. Simulação de Pagamento Digital e Taxas

A plataforma não intermedia pagamentos de forma direta através de transações financeiras reais. No entanto, o código-fonte simula o cálculo de **Taxas da Plataforma** por meio do método abstrato polimórfico `taxaPlataforma()` definido na classe base `Anuncio`.

### Regras de Cobrança (Simuladas no Domínio)

As duas subclasses de anúncio implementam essa cobrança de formas distintas:

#### 1. Taxa para Anúncios de Venda (`AnuncioVenda`)
Para sustentar a operação de vendas no ecossistema, a plataforma calcula uma taxa de comissão de **5%** sobre o valor de venda do livro.
```java
@Override
public double taxaPlataforma() {
    return this.preco * 0.05; // 5% do preço final do anúncio
}
```

#### 2. Taxa para Anúncios de Troca (`AnuncioTroca`)
Para as trocas de livros, o sistema adota uma taxa fixa de conveniência de **R$ 2,00**, independentemente de quais obras estão sendo permutadas.
```java
@Override
public double taxaPlataforma() {
    return 2.0; // Taxa fixa simbólica para trocas
}
```
*Nota: A fim de manter compatibilidade com a modelagem do banco de dados, o construtor do `AnuncioTroca` define internamente o atributo de preço herdado como `10.0`.*
