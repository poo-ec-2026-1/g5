# Relatório Individual - Projeto POO (TradeLibrary)

**Projeto:** TradeLibrary (Plataforma de Troca e Venda de Livros)

**Desenvolvedor:** Cristiano Ribeiro (Arquiteto de Software e Documentação)

---

# 1. Atribuição de Cargo e Tarefas

## Responsabilidades Iniciais

Assumi o papel de **Arquiteto de Software focado na Documentação**. Minha responsabilidade inicial era estruturar o projeto, definir padrões de código, documentar as regras de negócio de forma clara e assegurar a correta aplicação do padrão **MVC (Model-View-Controller)**.

O objetivo era criar um repositório organizado, onde qualquer desenvolvedor pudesse compreender rapidamente a lógica do sistema e contribuir de forma eficiente.

## Atuação Prática

Na prática, minhas atividades foram além da redação técnica. Atuei na gestão ativa do repositório, elaborei diagramas estruturais do sistema, organizei a árvore de diretórios e resolvi conflitos de versionamento (**Git**) da equipe, otimizando significativamente o fluxo de trabalho entre os times de Front-end e Back-end.

---

# 2. Contribuição de Acordo com a Atribuição

## O que Foi Cumprido

Consegui estruturar um repositório profissional e coeso. Fragmentei a documentação, que antes estava concentrada em um único arquivo extenso, em documentos menores e modulares, reduzindo conflitos de integração entre os desenvolvedores.

Também desenvolvi diagramas de classes, mapeei fluxos de casos de uso (como o processo de troca de livros) e aprimorei o arquivo `README.md` com instruções detalhadas de execução e demonstrações visuais da interface por meio de GIFs.

## Principais Documentos Produzidos

### ARCHITECTURE.md

Documentação do funcionamento interno do sistema, fluxo de dados e estratégia de persistência local utilizando SQLite e ORMLite.

### BUSINESS_RULES.md

Descrição detalhada dos processos de venda e troca de livros, incluindo regras de negócio e cálculo de taxas simuladas.

### CONTRIBUTING.md

Guia de contribuição para a equipe contendo:

* Regras de Código Limpo;
* Convenções de nomenclatura;
* Procedimentos de versionamento;
* Configuração do ambiente para VS Code e Eclipse.

## Meus 3 Principais Commits

### 1. docs: atualiza README com diagramas e consolida arquivos de documentacao

**SHA:** `9775162`

**O que foi realizado:**

* Inserção da árvore de diretórios;
* Implementação de diagramas;
* Representação visual da estrutura do código;
* Documentação do fluxo de troca de livros diretamente no GitHub.

### 2. docs: cria pasta docs e organiza os arquivos de documentacao

**SHA:** `78e4c65`

**O que foi realizado:**

* Reestruturação completa da documentação;
* Criação da pasta `docs/`;
* Organização dos arquivos;
* Correção dos links internos para navegação adequada.

### 3. fix: renomeia pasta e arquivos removendo caracteres especiais

**SHA:** `53e8ba7`

**O que foi realizado:**

* Remoção de acentos e caracteres especiais dos nomes de arquivos;
* Correção de problemas de encoding;
* Garantia de compatibilidade entre diferentes sistemas operacionais.

## Dificuldades Encontradas

O principal desafio técnico foi a renderização de diagramas Mermaid no GitHub, que ocasionalmente apresentava distorções de layout.

Como solução, os diagramas foram convertidos em imagens estáticas e armazenados localmente no repositório.

Outro desafio recorrente foi garantir que todos os membros da equipe consultassem e aplicassem corretamente as diretrizes definidas no arquivo `CONTRIBUTING.md` antes de realizar novos envios de código.

---

# 3. Contribuição Além do Atribuído

Embora meu foco inicial fosse a documentação e o planejamento arquitetural, também atuei ativamente na resolução de problemas de infraestrutura e configuração de ambiente para evitar bloqueios no desenvolvimento.

## Como Ajudei a Equipe

### Suporte com Git e Resolução de Conflitos

Auxiliei os membros da equipe na resolução de conflitos de repositório, incluindo erros de sincronização como:

```bash
non-fast-forward
```

Também orientei sobre:

* Utilização correta do `git pull`;
* Reversão de commits incorretos;
* Exclusão de branches criadas por engano;
* Boas práticas de versionamento.

### Correção de Erro no JavaFX

Identifiquei e corrigi o erro:

```text
Module javafx.controls not found
```

O problema estava relacionado ao uso da execução genérica do Java no VS Code.

A solução consistiu em orientar a equipe a utilizar o perfil correto de execução através da funcionalidade **Run and Debug**, restabelecendo o ambiente de testes de todos os integrantes.

## Commit Extra de Suporte

### docs: adiciona imagens dos diagramas e atualiza visualizacao do README

**SHA:** `8aee74e`

**O que foi realizado:**

* Inclusão das versões `.png` dos diagramas;
* Armazenamento na pasta de recursos do projeto;
* Garantia de estabilidade visual da documentação em casos de falha na renderização nativa.

---

# 4. Considerações Gerais

## O que Aprendi

Este projeto consolidou meus conhecimentos práticos em Engenharia de Software.

Durante o desenvolvimento, aprofundei significativamente minhas habilidades em:

* Versionamento com Git;
* Resolução de conflitos;
* Organização de histórico de alterações;
* Documentação técnica utilizando Markdown;
* Estruturação arquitetural de sistemas orientados a objetos.

Na prática, compreendi que uma arquitetura bem definida e uma documentação clara são elementos fundamentais para possibilitar a integração harmoniosa entre Front-end e Back-end.

## Trabalhos Futuros Pendentes

Algumas atividades permanecem previstas para evolução futura do projeto:

* Atualização do documento `BUSINESS_RULES.md` após a definição da logística final de entrega física dos livros;
* Documentação detalhada das práticas de segurança implementadas no sistema, incluindo criptografia de senhas;
* Padronização da utilização de comentários técnicos (JavaDoc) em todo o código-fonte para facilitar a manutenção e escalabilidade.

---

# Conclusão

Concluo que o papel da arquitetura de software transcende o desenho estrutural do código. Ele envolve atuar como uma ponte entre Front-end e Back-end, facilitar a comunicação entre os integrantes da equipe e organizar o ambiente de desenvolvimento.

Durante o projeto, ficou evidente que antecipar problemas operacionais e estabelecer processos bem definidos permite que os desenvolvedores concentrem seus esforços na implementação da lógica de negócio, reduzindo gargalos técnicos.

Além disso, a elaboração de uma documentação acessível e bem estruturada mostrou-se um dos maiores ativos para a manutenção e escalabilidade futura do sistema.

O repositório entregue reflete um trabalho maduro, focado na experiência do desenvolvedor, na organização do projeto e alinhado às boas práticas exigidas pelo mercado de desenvolvimento de software.
