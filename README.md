# Sistema de Gerenciamento de Extensão - UFMA

## 🚀 Sobre o projeto

Sistema desenvolvido para a disciplina de Laboratório de Programação 2 (LP2) do curso de Ciência da Computação da Universidade Federal do Maranhão (UFMA).

O sistema gerencia atividades de extensão universitária, permitindo que:
- **Discentes** se inscrevam em oportunidades de extensão.
- **Docentes** criem e validem essas oportunidades.
- **Coordenadores** acompanhem o aproveitamento e emitam certificados.

## 🛠️ Tecnologias Utilizadas

- **Java 21**: Versão da linguagem de programação.
- **Spring Boot**: Framework principal para a criação da aplicação e da API REST.
- **Spring Data JPA**: Para persistência de dados.
- **H2 Database**: Banco de dados relacional em memória.
- **Maven**: Gerenciador de dependências e build do projeto.

## ⚙️ Como Executar

1. **Pré-requisitos**:
   - Tenha o **Java 21** ou superior instalado.
   - Tenha o **Maven** configurado em seu ambiente.

2. **Clone o repositório**:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```

3. **Execute a aplicação**:
   - Abra o projeto em sua IDE de preferência (IntelliJ, VSCode, etc.).
   - Execute a classe `br.ufma.Lp2Entrega3Application`.
   - A aplicação estará disponível em `http://localhost:8081`.

> **Nota**: A classe `DataInitializer` irá popular o banco com dados de exemplo na primeira inicialização para facilitar os testes.

## 🗄️ Banco de Dados

O projeto utiliza um banco de dados H2 em memória.

- **Console H2**: Acesse em `http://localhost:8081/h2-console`
- **JDBC URL**: `jdbc:h2:mem:extensaodb`
- **Usuário**: `sa`
- **Senha**: (deixe em branco)

## 🧪 Guia de API e Exemplos de Requisições

A URL base para todas as requisições é `http://localhost:8081`.

---

### 🎓 Cursos (`/api/cursos`)

#### Criar um novo curso
- **POST** `/api/cursos`
- **Corpo (JSON):**
  ```json
  {
    "nome": "Engenharia de Software",
    "codigo": 2026002,
    "cargaHoraria": 3400
  }
  ```

#### Listar todos os cursos
- **GET** `/api/cursos`

#### Buscar curso por ID
- **GET** `/api/cursos/1`

#### Listar versões de PPC de um curso
- **GET** `/api/cursos/1/versoes`

---

### 👤 Usuários (`/api/usuarios`)

#### Criar um novo usuário (Ex: Discente)
- **POST** `/api/usuarios`
- **Corpo (JSON):**
  ```json
  {
    "nome": "Novo Discente",
    "email": "novo.discente@ufma.br",
    "senha": "senha123",
    "matricula": "2024001002",
    "semestreAtual": 1,
    "curso": {
      "id": 1
    }
  }
  ```
> **Nota**: Para criar outros tipos de usuário (Docente, Coordenador), a estrutura do JSON deve ser ajustada de acordo com os atributos da respectiva classe.

#### Autenticar um usuário
- **POST** `/api/usuarios/login`
- **Corpo (JSON):**
  ```json
  {
    "email": "discente.exemplo@ufma.br",
    "senha": "senha123"
  }
  ```

#### Desativar um usuário
- **DELETE** `/api/usuarios/2`

#### Atribuir um papel a um usuário
- **POST** `/api/usuarios/2/papeis`
- **Corpo (JSON):**
  ```json
  {
    "nome": "MONITOR"
  }
  ```

---

### ✨ Oportunidades (`/api/oportunidades`)

#### Criar uma nova oportunidade
- **POST** `/api/oportunidades`
- **Corpo (JSON):**
  ```json
  {
    "idDocenteResponsavel": 1,
    "titulo": "Desenvolvimento de API com Spring Boot",
    "descricao": "Projeto de extensão para desenvolvimento de uma API REST.",
    "tipo": {
      "id": 1
    },
    "modalidade": "remoto",
    "cargaHoraria": 120,
    "vagas": 3,
    "inicio": "2024-08-01T10:00:00",
    "idAutor": 1
  }
  ```

#### Aprovar uma oportunidade
- **PUT** `/api/oportunidades/1/aprovar`
- **Corpo (JSON):**
  ```json
  {
    "idAvaliador": 1 
  }
  ```

#### Rejeitar uma oportunidade
- **PUT** `/api/oportunidades/1/rejeitar`
- **Corpo (JSON):**
  ```json
  {
    "idAvaliador": 1,
    "motivo": "Descrição da oportunidade está incompleta."
  }
  ```

---

### 📝 Inscrições (`/api/inscricoes`)

#### Criar uma nova inscrição
- **POST** `/api/inscricoes`
- **Corpo (JSON):**
  ```json
  {
    "idOportunidade": 1,
    "idDiscente": 1,
    "motivacao": "Tenho grande interesse na área e gostaria de aprofundar meus conhecimentos."
  }
  ```

#### Aprovar uma inscrição
- **PUT** `/api/inscricoes/1/aprovar`
- **Corpo (JSON):**
  ```json
  {
    "idAvaliador": 1
  }
  ```

#### Rejeitar uma inscrição
- **PUT** `/api/inscricoes/1/rejeitar`
- **Corpo (JSON):**
  ```json
  {
    "idAvaliador": 1,
    "motivo": "O discente não atende aos pré-requisitos."
  }
  ```

#### Abandonar uma inscrição
- **PUT** `/api/inscricoes/1/abandonar`

---

### 👥 Grupos (`/api/grupos`)

#### Cadastrar um novo grupo
- **POST** `/api/grupos`
- **Corpo (JSON):**
  ```json
  {
    "nome": "Grupo de Estudos de Cloud Computing",
    "tipo": "Estudo",
    "email": "cloud.ufma@exemplo.com",
    "descricao": "Grupo para discutir e aprender sobre tecnologias de nuvem.",
    "responsavelId": 1
  }
  ```

#### Adicionar membro a um grupo
- **POST** `/api/grupos/1/membros`
- **Corpo (JSON):**
  ```json
  {
    "usuarioId": 1
  }
  ```

#### Remover membro de um grupo
- **DELETE** `/api/grupos/1/membros/1`

---

### 📜 Aproveitamentos (`/api/aproveitamentos`)

#### Submeter um novo aproveitamento
- **POST** `/api/aproveitamentos`
- **Corpo (JSON):**
  ```json
  {
    "idDiscente": 1,
    "descricao": "Participação na Semana de Computação 2024",
    "instituicao": "UFMA",
    "horas": 40,
    "dataLimite": "2024-12-31"
  }
  ```

#### Aprovar um aproveitamento
- **PUT** `/api/aproveitamentos/1/aprovar`
- **Corpo (JSON):**
  ```json
  {
    "idAvaliador": 1
  }
  ```

---

### 🎓 PPC (Projeto Pedagógico de Curso) (`/api/cursos/{cursoId}/versoes`)

#### Cadastrar uma nova versão de PPC
- **POST** `/api/cursos/1/versoes`
- **Corpo (JSON):**
  ```json
  {
    "coordenadorId": 1,
    "versao": "2024.1",
    "cargaHoraria": 3250
  }
  ```

#### Atualizar uma versão de PPC
- **PUT** `/api/cursos/1/versoes`
- **Corpo (JSON):**
  ```json
  {
    "novaCargaHoraria": 3300,
    "novaVersao": "2024.2",
    "coordenadorId": 1
  }
  ```
