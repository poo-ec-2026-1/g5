# Relatório Individual – Projeto de Programação Orientada a Objetos

**Projeto**: TradeLibrary
---
O TradeLibrary é um projeto desenvolvido para facilitar a compra, venda e troca de livros novos e seminovos entre usuários. A plataforma busca promover o acesso à leitura por meio da reutilização de livros, oferecendo um ambiente simples e organizado para anúncios e negociações.

## 1. Atribuição de cargo e tarefas

### Cargo: Líder da Equipe

### Responsabilidades

- Auxiliar na organização geral do projeto.
- Manter a comunicação entre os integrantes da equipe.
- Participar das decisões relacionadas ao desenvolvimento do sistema.
- Acompanhar o andamento das atividades e esclarecer dúvidas quando necessário.
- Contribuir para a integração das partes desenvolvidas pelos membros do grupo.

### O que foi exercido na prática

Durante o desenvolvimento do projeto, atuei como líder da equipe, participando da organização geral das atividades e auxiliando na comunicação entre os integrantes. Busquei acompanhar o progresso do desenvolvimento, colaborar na resolução de dúvidas e contribuir para que as diferentes partes do sistema funcionassem de forma integrada. Além disso, participei diretamente da implementação de funcionalidades relacionadas à segurança e autenticação dos usuários.

---

## 2. Contribuição de acordo com a atribuição

### O que foi cumprido

Além das atividades relacionadas à liderança, participei do desenvolvimento de funcionalidades importantes para o sistema. Minha principal contribuição técnica foi a implementação de mecanismos de segurança para autenticação dos usuários, incluindo a criptografia de senhas e a adaptação do fluxo de login para trabalhar com essas informações de forma segura.

### Commits mais relevantes

#### 1. Implementação da lógica de segurança e criptografia de senhas (SHA-256)

**Commit:** `Implementa criptografia SHA-256 para protecao de senhas`

Neste commit foi desenvolvida a lógica responsável pela criptografia das senhas dos usuários utilizando o algoritmo SHA-256. Essa implementação aumentou a segurança do sistema ao evitar o armazenamento de senhas em texto puro no banco de dados.

#### 2. Integração da criptografia ao cadastro de usuários e melhoria da experiência do usuário

**Commit:** `Integra SecurityUtils e alertas nativos no RegisterController`

Neste commit foi realizada a integração da criptografia de senhas ao processo de cadastro de usuários. Além disso, foram adicionadas caixas de diálogo para informar erros e sucessos durante as operações realizadas pelo usuário, tornando a utilização do sistema mais intuitiva.

#### 3. Atualização do processo de autenticação para validação de senhas criptografadas

**Commit:** `Atualiza repositorio de usuario para validar senhas criptografadas`

Neste commit foi ajustado o fluxo de login para realizar corretamente a validação das senhas criptografadas. Também foram realizadas correções para garantir compatibilidade com a versão Java 8 utilizada no projeto.

### Principais dificuldades

- Compreender a melhor forma de implementar a criptografia de senhas sem comprometer o funcionamento do sistema.
- Integrar as funcionalidades desenvolvidas por diferentes membros da equipe.
- Entender e adaptar partes do código que haviam sido desenvolvidas por outros integrantes.
- Garantir que o processo de cadastro e login funcionasse corretamente após a implementação dos mecanismos de segurança.

---

## 3. Contribuição além do atribuído

Além das atividades relacionadas à liderança e ao desenvolvimento das funcionalidades de segurança, também contribuí com a documentação do projeto de forma presencial, auxiliando os integrantes da equipe durante sua elaboração, embora não tenha realizado commits específicos relacionados a essa atividade.

Também ajudei o integrante responsável pela função de Arquiteto de Software na elaboração da documentação do projeto, contribuindo principalmente em tópicos relacionados à Engenharia de Requisitos e auxiliando na organização das informações necessárias para compor o documento final.

---

## 4. Considerações gerais

### O que aprendi

A realização deste projeto proporcionou aprendizado tanto na área técnica quanto no trabalho em equipe. Durante o desenvolvimento, aprendi mais sobre conceitos de segurança em software, especialmente relacionados à proteção de senhas e autenticação de usuários.

Também adquiri maior experiência na utilização do Git e GitHub para controle de versões, na integração de funcionalidades desenvolvidas por diferentes pessoas e na organização de um projeto de software utilizando os conceitos de Programação Orientada a Objetos.

Além disso, o papel de liderança permitiu desenvolver habilidades de comunicação, colaboração e resolução de problemas, que são importantes em projetos realizados em equipe.

### Trabalhos futuros

Como possíveis melhorias futuras, o sistema poderia receber novos recursos para aumentar a segurança dos usuários, melhorias na interface gráfica e refinamentos em algumas funcionalidades para tornar a experiência de utilização ainda mais completa.

### Conclusão

O desenvolvimento deste projeto foi uma experiência importante para aplicar na prática os conceitos estudados durante a disciplina de Programação Orientada a Objetos. A participação como líder e desenvolvedor permitiu acompanhar diferentes etapas do projeto e contribuir diretamente para funcionalidades relevantes do sistema, especialmente na área de segurança e autenticação de usuários.

Além dos conhecimentos técnicos adquiridos, o trabalho também contribuiu para o desenvolvimento de habilidades relacionadas à colaboração e ao desenvolvimento de software em equipe.
