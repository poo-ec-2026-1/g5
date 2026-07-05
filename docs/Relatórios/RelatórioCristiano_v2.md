# Relatório Individual - Projeto POO (TradeLibrary)

**Projeto:** TradeLibrary (Plataforma de Troca e Venda de Livros Usados)  
**Desenvolvedor:** Cristiano Ribeiro  
**Atribuição:** Arquiteto de Software e Responsável por Documentação e UML  

---

## 1. Atribuição de Cargo e Atividades Realizadas

### Atividades da Primeira Etapa
Na primeira etapa do projeto, assumi a responsabilidade de estruturar e padronizar o repositório de desenvolvimento do grupo. Minhas principais contribuições foram:
1.  **Arquitetura do Repositório**: Criação e estruturação inicial da pasta de documentação técnica, separando-a do código-fonte para manter a organização.
2.  **Modularização da Documentação**: Divisão de um documento único e extenso em arquivos específicos com foco em responsabilidades distintas: diretrizes de arquitetura (`arquitetura.md`), regras de negócio (`regras-de-negocio.md`) e guias de contribuição (`contribuicao.md`).
3.  **Guia de Contribuição Técnico**: Elaboração de padrões de nomenclatura, regras de formatação e procedimentos de versionamento com Git para mitigar conflitos de código entre os desenvolvedores de front-end e back-end.
4.  **Diagramação Inicial**: Modelagem preliminar do domínio através de diagramas de classes e fluxos de casos de uso (processo de troca de livros) para orientar o desenvolvimento.

### Novas Atividades e Melhorias (Segunda Etapa)
Nesta etapa final, conduzi a revisão geral do projeto e a correção das inconsistências apontadas na avaliação anterior:
1.  **Padronização e Reorganização de Pastas**: Renomeação da pasta física `Documentation/` para `docs/` para unificar o padrão de caminhos com as referências descritas no `README.md` e nos relatórios da equipe.
2.  **Tradução e Ajuste de Documentos**: Renomeação e tradução dos arquivos principais para o português (`arquitetura.md`, `regras-de-negocio.md` e `contribuicao.md`) para melhor alinhamento com a linguagem acadêmica adotada.
3.  **Correção e Standardização de Links e Caminhos**: Varredura minuciosa de toda a documentação para substituir referências a diretórios absolutos locais (por exemplo, caminhos contendo pastas locais pessoais dos desenvolvedores) por caminhos relativos ao repositório, garantindo que os hiperlinks e imagens funcionem perfeitamente no GitHub.
4.  **Documentação de Novas Funcionalidades**: Atualização dos guias de arquitetura e regras de negócio para documentar os módulos de carrinho de compras (`CartItem`, `CartItemRepository`), propostas de troca (`Proposta`, `PropostaRepository`) e histórico de transações (`Transacao`, `TransacaoRepository`) integrados ao modelo de banco de dados SQLite.
5.  **Refatoração e Expansão da Documentação UML**: Redesenho completo e geração dos arquivos de código-fonte do PlantUML (`.puml`) e suas respectivas imagens exportadas (`.png`) para o Diagrama de Classes, Diagrama de Casos de Uso e Diagramas de Sequência para os fluxos de troca e compra de livros.
6.  **Atualização do README.md**: Consolidação do arquivo de apresentação do projeto para refletir a nova estrutura de pastas, tecnologias de persistência local, funcionalidades finais implementadas, equipe e visualização dos diagramas UML atualizados.

---

## 2. Correções Efetuadas Pós-Feedback do Professor

Para solucionar as deficiências apontadas pelo professor na avaliação da primeira etapa, foram adotadas as seguintes correções técnicas:

| Problema Apontado | Causa Identificada | Solução Implementada |
| :--- | :--- | :--- |
| **Documentação UML ausente/incompleta** | Diagramas limitados ao escopo inicial, sem cobrir o carrinho, propostas e transações. | Modelagem completa de todas as classes de domínio, repositórios e relacionamentos. Geração de diagramas de sequência para trocas e compras. |
| **Diagramas UML incorretos** | Representações de herança e relacionamentos de persistência ORMLite inconsistentes com o código. | Correção das relações de dependência, associação e especialização polimórfica (classes `AnuncioVenda` e `AnuncioTroca` estendendo `Anuncio` e implementando `Publicavel`). |
| **Arquivos `.puml` ausentes** | O repositório continha apenas imagens estáticas em PNG, sem os arquivos de origem UML. | Inclusão de todos os arquivos de código PlantUML (`.puml`) organizados na estrutura de diretórios (`docs/uml/` e `docs/uml/diagramas-sequencia/`). |
| **Documentação desatualizada** | Manuais não descreviam os novos recursos do banco de dados local SQLite e fluxo de dados do carrinho. | Atualização detalhada do fluxo de dados MVC e persistência ORM no documento de arquitetura e regras de negócio. |
| **README incompleto** | README não documentava as melhorias implementadas, dependências e links de navegação. | Reformulação completa do `README.md` com instruções detalhadas de configuração (VM arguments, bibliotecas locais) e lista de melhorias. |

---

## 3. Contribuições Técnicas e Engenharia UML

Como Arquiteto de Software, as principais contribuições técnicas nesta etapa envolveram a representação precisa do sistema:
*   **Diagrama de Classes**: Mapeamento polimórfico de `Anuncio` (venda e troca), relações de persistência do ORMLite e o modelo de banco de dados SQLite unificado.
*   **Diagramas de Sequência**: 
    *   *Fluxo de Escambo (Troca)*: Detalhamento da interação entre `TradeManager`, `PropostaRepository`, `ProposalsController` e as ações de aceitação e rejeição concorrente de propostas.
    *   *Fluxo de Compra*: Detalhamento do ciclo de vida dos itens de venda desde o `CartManager` até a gravação na tabela `transacoes` e remoção do catálogo.
*   **Automação na Geração de Diagramas**: Desenvolvimento de um script em Python (`render_puml.py`) que processa os arquivos de texto `.puml`, realiza a compressão Deflate e a codificação customizada em Base64 e consome a API do servidor oficial do PlantUML para baixar automaticamente os diagramas em formato `.png` de alta qualidade, garantindo sincronia imediata entre código UML e imagem.

---

## 4. Dificuldades Encontradas e Soluções Adotadas

1.  **Distorções Visuais no Mermaid**: Na primeira etapa, a equipe enfrentou problemas de layout na renderização nativa de diagramas Mermaid no GitHub.  
    *   *Solução*: Transição definitiva para o PlantUML, gerando arquivos de texto editáveis `.puml` e compilando-os para imagens estáticas PNG hospedadas no repositório.
2.  **Inconsistências de Caminhos entre Desenvolvedores**: O uso de múltiplos sistemas operacionais (Windows e Linux) causou a inserção inadvertida de caminhos absolutos locais nos arquivos markdown de documentação.  
    *   *Solução*: Padronização rigorosa de todos os links internos para caminhos relativos ao repositório, testados e validados para garantir navegação adequada diretamente na interface web do GitHub.
3.  **Configuração de VM Arguments do JavaFX**: Membros do grupo encontraram problemas de execução local devido à falta de módulos gráficos configurados.  
    *   *Solução*: Configuração centralizada de arquivos de execução no VS Code (`.vscode/launch.json`) e documentação detalhada das instruções para Eclipse e linha de comando no guia de contribuição.

---

## 5. Aprendizado Obtido e Considerações Gerais

O desenvolvimento deste projeto consolidou conceitos teóricos de **Programação Orientada a Objetos (POO)** e **Engenharia de Software** em um ambiente prático:
*   **Padrão MVC**: Entendimento prático de como isolar regras de negócio e persistência na camada Model, evitando que controladores JavaFX fiquem poluídos com lógica de dados.
*   **UML e Engenharia Reversa**: Aplicação prática de modelagem de diagramas que refletem fielmente o código-fonte executável, servindo como ferramenta de comunicação entre o design do sistema e sua implementação.
*   **Gestão de Versionamento e Configuração**: Prática no gerenciamento de repositórios, resolução de conflitos de merge e a importância de manter um ambiente de desenvolvimento reprodutível e bem documentado para o sucesso da equipe.


---
## 6. Principais Commits

- `9775162` docs: atualiza README com diagramas e consolida arquivos de documentação.
- `78e4c65` docs: cria pasta `docs` e organiza arquivos.
- `53e8ba7` fix: renomeia pasta e arquivos removendo caracteres especiais.
- `8aee74e` docs: adiciona imagens dos diagramas ao README.

## 7. Atuação Além da Atribuição

Além das atividades de arquitetura e documentação, prestei suporte à equipe na resolução de conflitos de Git (como erros *non-fast-forward*), orientação sobre `git pull`, reversão de commits e gerenciamento de branches. Também auxiliei na configuração do JavaFX, solucionando problemas relacionados ao erro `Module javafx.controls not found` por meio da configuração correta do ambiente de execução.

## 8. Trabalhos Futuros

- Atualizar as regras de negócio conforme a definição da logística de entrega dos livros.
- Documentar as práticas de segurança, incluindo criptografia de senhas.
- Ampliar a padronização de JavaDoc no projeto.


## Vídeo de Apresentação:
https://drive.google.com/file/d/1R2alUw2cRjDqvjDbs7KvDMR3yz2uGI4Y/view?usp=drive_link
