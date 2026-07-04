# Relatório de desempenho pessoal - Dermival (engenheiro de QA/Testes)

**Aluno:** Dermival Barbosa De Oliveira Filho  
**Matrícula:** 202506938  

---

## 1. Atuação e objetivos:
Nesse projeto fui designado para ser o engenheiro de QA/Teste e atuei trabalhando no projeto TradeLibrary, em que foquei em garantir o domínio estrutural das classes de teste e assegurar a robustez do fluxo CRUD da aplicação. Atuei diretamente no isolamento de falhas, validação de regras de negócio essenciais e garantia de estabilidade arquitetural do software com foco na consistência dos dados do controlador de operações do sistema.

---

## 2. Contribuições técnicas:
Abaixo estão listados os commits mais relevantes realizados no repositório oficial do projeto para fundamentar o esqueleto e a integridade da suíte de validação contínua da Etapa 1:

| Nome do commit: | Detalhamento: |
| :--- | :--- |
| **Cria esqueleto da classe de testes para validacao do CRUD** | Criação e estruturação inicial do arquivo ControllerTest.java dentro do pacote correto da arquitetura (controller). Implementação do esqueleto de testes focado em cenários negativos de cadastros duplicados, nulos ou com campos em branco. |
| **Implementa cenario de teste para operacao de exclusao no CRUD** | Desenvolvimento de lógica de validação estrutural para a operação de exclusão (Delete). Garante que o controlador responda de forma segura a tentativas de eliminação de registros inexistentes, evitando exceções em tempo de execução. |
| **Adiciona validacao para impedir atualizacao de dados invalidos** | Implementação de caso de teste preventivo voltado para a integridade da operação de alteração (Update), certificando que o sistema barre inserções inconsistentes ou campos vazios durante modificações de registros existentes. |
| **Implementa cenario de sucesso para busca por ID no CRUD** | Criação de esqueleto técnico para validação do fluxo operacional positivo da rotina de consulta individualizada (Read), garantindo que IDs válidos retornem com sucesso dados perfeitamente consitentes. |
| **Adiciona teste de comportamento para listagem vazia** | Garantia da robustez e resiliência da listagem geral do CRUD em cenários de banco de dados zerado, certificando que o sistema exiba uma estrutura de tratamento limpa sem quebrar a execução ou gerar exceções não capturadas. |

---

## 3. Autoavaliação e critérios de qualidade atendido.
Esse projeto foi muito importante para o meu conhecimento, pois me ensinou a como trabalhar com essa linguagem de programação de uma maneira muito melhor, além de poder trabalhar em grupo que consequentemente irá me ajudar no futuro, nesse sentido fui recomendado ao trabalho de teste no que me fez melhorar no sentido de fazer modificações e testes em códigos.
