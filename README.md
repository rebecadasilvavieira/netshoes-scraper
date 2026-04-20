# Netshoes Scraper API 

O desafio consiste em desenvolver um web scraper capaz de realizar a requisição de uma página de produto, obter o HTML e extrair as informações de **título, preço, imagem e descrição**.

Este projeto atende a esse requisito e, além disso, foi expandido com recursos complementares para melhor organização e demonstração técnica, como:

- **API REST** com Spring Boot

- Utilização de banco de dados **PostgreSQL**

- Endpoint para consulta dos produtos extraídos

- Armazenamento de informações adicionais, como **cores e tamanhos** disponíveis/indisponíveis

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Descrição |
| --- | --- |
| **Java 21** | Linguagem de programação principal, aproveitando recursos modernos de performance. |
| **Spring Boot 3.2.5** | Framework base para a construção da API REST. |
| **Spring Data JPA** | Abstração para persistência de dados e interação com o banco. |
| **PostgreSQL** | Banco de dados relacional para armazenamento dos produtos. |
| **Jsoup 1.22.1** | Biblioteca especializada para parsing de HTML e scraping. |
| **Maven Wrapper** | Gerenciamento de dependências (não requer instalação manual do Maven). |
| **Postman** | Ferramenta utilizada para validação e testes dos endpoints. |

---

## 📋 Funcionalidades da API

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `POST` | `/api/products/scrape` | Inicia o processo de scraping na Netshoes e salva os resultados no banco de dados postgresql. |
| `GET` | `/api/products` | Retorna a lista completa de todos os produtos armazenados. |
| `GET` | `/api/products/melhorpreco` | Identifica e retorna o produto com o menor preço disponível em estoque. |

---

## 🚀 Como Executar o Projeto (Windows)

### Pré-requisitos

- **Java 21** ou superior - [Download aqui](https://adoptium.net/)

- **PostgreSQL 15+** - [Download aqui](https://www.postgresql.org/download/)

- [](https://www.postgresql.org/download/)**Maven 4.0.0** - [Download aqui](https://www.postgresql.org/download/)

- **Git** (opcional, para clonar o repositório)

- **Postman** (opcional, para testar a API)

### 1. Clonar o Repositório

```
git clone https://github.com/rebecadasilvavieira/netshoes-scraper.git
cd netshoes-scraper
```

### 2. Configurar o Banco de Dados

**Criar o banco PostgreSQL:**

```sql
-- Conectar no PostgreSQL (via SQL Shell ou CMD )
psql -U postgres

-- Criar o banco
CREATE DATABASE netshoesdb;

-- Sair
\q
```

**Configurar as credenciais:** Edite o arquivo `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/netshoesdb
spring.datasource.username=postgres
spring.datasource.password=sua_senha_aqui
spring.jpa.hibernate.ddl-auto=update
```

### 3. Executar o Projeto

**Windows (PowerShell/CMD):**

```
.\mvnw.cmd spring-boot:run
```

---

<details>
<summary><strong>🧪 Testes na API (Clique para expandir)</strong></summary>

### Endpoints e Comandos

Você pode testar via Postman ou terminal:

1. **Executar Scraping:**

   ```
   curl -X POST http://localhost:8080/api/products/scrape
   ```

1. **Listar todos os produtos:**

   ```
   curl http://localhost:8080/api/products
   ```

1. **Buscar produto mais barato:**

   ```
   curl http://localhost:8080/api/products/melhorpreco
   ```

### 📸 Exemplo de Resposta - Api/Products

```json
{
  "success": true,
  "totalProducts": 9,
  "products": [
    {
      "id": 1,
      "title": "Tênis Adidas Duramo 2.0 Masculino - Preto",
      "price": "R$ 294,49",
      "imageUrl": "https://static.netshoes.com.br/produtos/tenis-adidas-duramo-20-masculino/06/3ZP-5591-006/3ZP-5591-006_detalhe1.jpg",
      "description": "Saia para correr com liberdade e qualidade usando o Tênis Adidas Duramo 2.0 Masculino...",
      "colors": "Preto",
      "sizes": "Disponíveis: 39, 40, 42, 44 | Indisponíveis: 37, 38, 41, 43, 45",
      "collectedAt": "2026-04-20T14:51:34.8820733"
    }
  ]
}
```

> **Nota:** Além das informações solicitadas no desafio, o projeto armazena dados complementares como cores e tamanhos.

</details>

---

## 🛠️ Solução de Problemas Comuns

| Erro | Solução |
| --- | --- |
| **"java: command not found"** | Instale o Java 21 e verifique com `java -version`. |
| **"connection refused"** | Verifique se o serviço do PostgreSQL está rodando no Windows (services.msc ). |
| **Porta 8080 em uso** | Altere `server.port=8090` no `application.properties`. |
| **"./mvnw" não reconhecido** | No Windows, use sempre `.\mvnw.cmd`. |

---

## 📄 Estrutura do Projeto

```
netshoes.scraper/
├── src/
│   ├── main/
│   │   ├── java/com/rebeca/scraper/
│   │   │   ├── controller/       # Endpoints REST
│   │   │   ├── service/          # Lógica de negócio
│   │   │   ├── scraper/          # Extração com Jsoup
│   │   │   ├── entity/           # Modelagem das tabelas
│   │   │   ├── repository/       # Acesso ao banco
│   │   │   └── Application.java  # Classe principal
│   │   └── resources/
│   │       └── application.properties  # Configurações
│   └── test/                     # Testes unitários
├── mvnw                          # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                      # Maven Wrapper (Windows)
├── pom.xml                       # Dependências Maven
└── README.md                     # Este arquivo
```

---

