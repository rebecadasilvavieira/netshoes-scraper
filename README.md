# Netshoes Scraper API 🚀

Este projeto é uma API REST robusta desenvolvida com **Spring Boot**, projetada para realizar o web scraping automatizado de produtos do e-commerce Netshoes. Utilizando a biblioteca **Jsoup**, a aplicação extrai dados críticos como títulos, preços, cores e tamanhos, persistindo-os em um banco de dados **PostgreSQL** para análise e consulta posterior.

---

## 🛠️ Tecnologias Utilizadas

<details>
<summary>📂 Ver tecnologias</summary>

| Tecnologia | Descrição |
| :--- | :--- |
| Java 21 | Linguagem de programação principal, aproveitando recursos modernos de performance |
| Spring Boot 3.2.5 | Framework base para construção da API REST |
| Spring Data JPA | Abstração para persistência de dados |
| PostgreSQL | Banco de dados relacional |
| Jsoup | Parsing de HTML e scraping |
| Maven | Gerenciamento de dependências |
| Postman | Testes da API |

</details>

---

## 📋 Funcionalidades da API

<details>
<summary>📂 Ver funcionalidades</summary>

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| POST | /api/products/scrape | Inicia o scraping e salva no banco |
| GET | /api/products | Lista todos os produtos |
| GET | /api/products/melhorpreco | Retorna o produto mais barato |

</details>

---

## 🚀 Como Executar o Projeto

<details>
<summary>📂 Ver instruções</summary>

### Pré-requisitos
- JDK 21 ou superior
- PostgreSQL ativo
- Postman (opcional)

### Configuração do banco

Edite:
`src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco_de_dados
spring.datasource.username=seu_usuario_do_banco
spring.datasource.password=sua_senha_do_banco
spring.jpa.hibernate.ddl-auto=update
```

### Execução

No diretório do projeto:

```bash
./mvnw spring-boot:run
```

A API estará disponível em:
```
http://localhost:8080
```

</details>

---

## 🧪 Testes

<details>
<summary>📂 Abrir aba de testes</summary>

### Guia rápido

1. POST → http://localhost:8080/api/products/scrape  
2. GET → http://localhost:8080/api/products  
3. GET → http://localhost:8080/api/products/melhorpreco  

### Exemplo de resposta

```json
{
  "status": "success",
  "totalProducts": 10,
  "data": [
    {
      "id": 1,
      "title": "Tênis Nike Revolution 7",
      "price": 299.90,
      "colors": ["Preto", "Azul"],
      "sizes": [38, 39, 40]
    }
  ]
}
```

Nota: `totalProducts` indica quantos itens foram processados na última execução.

</details>

---

## 📄 Estrutura do Projeto

<details>
<summary>📂 Ver estrutura</summary>

- controller → endpoints REST  
- service → lógica de negócio  
- scraper → extração com Jsoup  
- entity → modelagem das tabelas  
- repository → acesso ao banco  

</details>

---

## 👩‍💻 Autora

Rebeca da Silva Vieira
