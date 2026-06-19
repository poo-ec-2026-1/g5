# Regras de Negócio - TradeLibrary

Este documento descreve as regras de negócio que governam o sistema **TradeLibrary**, definindo o modelo de domínio, os fluxos principais de venda e troca, e o funcionamento das taxas e transações.

---

## 1. Entidades de Domínio

O domínio de negócios do TradeLibrary é composto por três entidades principais mapeadas em código Java:

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
Representado pela classe [Usuario](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Usuario.java). Representa um membro cadastrado no sistema que pode anunciar livros para venda ou troca, além de visualizar ofertas de terceiros.
*   **Identificador Único**: E-mail (`email`).
*   **Dados Cadastrais obrigatórios**: Nome completo (`nome`), telefone para contato (`fone`) e senha (`senha`).

### B. Livro
Representado pela classe [Livro](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Livro.java). Representa a obra física que está sendo disponibilizada.
*   **Dados obrigatórios**: Título (`titulo`), Autor (`autor`) e Estado de conservação (`estado`).
*   **Dado opcional**: Código de registro ISBN (`isbn`).
*   **Estados de conservação aceitos**:
    *   `Novo`: Sem marcas de uso.
    *   `Seminovo`: Marcas imperceptíveis ou mínimas de manuseio.
    *   `Usado`: Sinais claros de uso (grifos, páginas amareladas, etc.).

### C. Anúncio
Representado pela classe abstrata [Anuncio](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Anuncio.java). Representa a intenção de disponibilizar um livro na plataforma.
*   **Atributos básicos**:
    *   `id`: Código único autogerado.
    *   `livro`: O objeto [Livro](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Livro.java) associado.
    *   `vendedor`: O [Usuario](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Usuario.java) que publicou a oferta.
    *   `status`: Situação do anúncio, inicializado como `"Disponivel"`.
    *   `descricao`: Texto explicativo livre.
*   **Especializações (Polimorfismo)**:
    *   [AnuncioVenda](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/AnuncioVenda.java): Anúncio voltado para a venda financeira de um exemplar. Possui um preço definido pelo usuário.
    *   [AnuncioTroca](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/AnuncioTroca.java): Anúncio voltado para escambo de livros. Contém o atributo `procura` que indica a obra que o anunciante gostaria de receber em troca.

---

## 2. Fluxos Principais do Sistema

### 1. Fluxo de Venda de um Livro
A venda permite que um usuário desapegue de um livro em troca de uma quantia em dinheiro.

#### Processo Passo a Passo:
1.  **Acesso ao Cadastro**: O usuário logado clica em "Cadastrar Livro" no painel principal.
2.  **Preenchimento de Dados**: O usuário preenche as informações do livro (Título, Autor, ISBN opcional e seleciona o Estado de conservação).
3.  **Seleção do Tipo de Anúncio**: Seleciona a opção **"Venda"**. Isso faz com que a interface oculte a caixa de busca e exiba o campo **"Preço"**.
4.  **Validação dos Dados (Controller)**:
    *   O título e o autor devem ser preenchidos.
    *   O preço deve ser um número decimal válido maior que zero (ex.: `R$ 45.50`).
5.  **Persistência**:
    *   O sistema cria e registra o [Livro](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/Livro.java) na tabela de livros.
    *   O sistema cria o [AnuncioVenda](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/AnuncioVenda.java) e o persiste associado ao usuário logado e ao livro recém-criado.
6.  **Disponibilização**: O anúncio passa a constar no catálogo com o preço estipulado e status `"Disponivel"`.
7.  **Finalização**: Quando a venda ocorre, o status do anúncio é atualizado para `"Vendido"`, ocultando-o de futuras negociações.

```java
// Trecho de validação do formulário no AddBookController
if (rbVenda.isSelected()) {
    try {
        double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
        if (preco <= 0) {
            mostrarErro(txtPreco, lblErroPreco, "O preço deve ser maior que zero.");
            valido = false;
        }
    } catch (NumberFormatException e) {
        mostrarErro(txtPreco, lblErroPreco, "Preço inválido. Digite apenas números.");
        valido = false;
    }
}
```

---

### 2. Fluxo de Troca de um Livro
A troca possibilita escambo direto entre usuários que possuem interesses mútuos.

#### Processo Passo a Passo:
1.  **Acesso ao Cadastro**: O usuário logado clica em "Cadastrar Livro".
2.  **Preenchimento de Dados**: O usuário preenche as informações do seu livro disponível.
3.  **Seleção do Tipo de Anúncio**: Seleciona a opção **"Troca"**. O campo "Preço" é ocultado e o campo **"O que você procura?"** é exibido.
4.  **Definição do Desejo**: O anunciante preenche o título ou descrição do livro desejado no campo de procura (ex.: "Código Limpo de Robert Martin").
5.  **Validação dos Dados**:
    *   O título e o autor do livro anunciado devem ser preenchidos.
    *   O campo "O que você procura?" deve conter informações válidas de busca.
6.  **Persistência**:
    *   O sistema registra o livro.
    *   O sistema cria o [AnuncioTroca](file:///c:/Users/crist/Downloads/g5-main%20(2)/g5-main/model/AnuncioTroca.java) e o persiste. Por padrão do sistema, o valor interno de preço de anúncios de troca é definido em R$ 10,00 no construtor da classe.
7.  **Disponibilização**: O anúncio é exibido no catálogo com a etiqueta indicando "Troca por: [Obra procurada]" e status `"Disponivel"`.
8.  **Finalização**: Após a negociação de troca, o status é alterado para `"Trocado"`.

---

## 3. Simulação de Pagamento Digital e Taxas

A plataforma não intermedia pagamentos de forma direta através de transações de cartão de crédito ou gateways bancários integrados na interface do usuário (UI). Em vez disso, a plataforma exibe as informações de contato do vendedor (e-mail e telefone) para que os usuários combinem a forma de pagamento e entrega externamente.

No entanto, o código-fonte simula o cálculo de **Taxas da Plataforma** por meio do método abstrato polimórfico `taxaPlataforma()` definido na classe base `Anuncio`.

### Regras de Cobrança (Simuladas no Domínio)
As duas subclasses implementam essa cobrança de formas distintas:

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
