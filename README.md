# Netshoes Scraper API 🚀

Este projeto é uma API REST robusta desenvolvida com **Spring Boot**, projetada para realizar o web scraping automatizado de produtos do e-commerce Netshoes. Utilizando a biblioteca **Jsoup**, a aplicação extrai dados críticos como títulos, preços, cores e tamanhos, persistindo-os em um banco de dados **PostgreSQL** para análise e consulta posterior.

---

## 🛠️ Tecnologias Utilizadas

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

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| POST | /api/products/scrape | Inicia o scraping e salva no banco |
| GET | /api/products | Lista todos os produtos |
| GET | /api/products/melhorpreco | Retorna o produto mais barato |

</details>

---

## 🚀 Como Executar o Projeto

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
  "success": true,
  "totalProducts": 9,
  "products": [
    {
      "id": 1,
      "title": "Tênis Adidas Duramo 2.0 Masculino - Preto",
      "price": "R$ 294,49",
      "imageUrl": "https://static.netshoes.com.br/produtos/tenis-adidas-duramo-20-masculino/06/3ZP-5591-006/3ZP-5591-006_detalhe1.jpg?ts=1776654845",
      "description": "Saia para correr com liberdade e qualidade usando o Tênis Adidas Duramo 2.0 Masculino. É confeccionado em material macio ao toque da pele, com cabedal respirável e estrutura resistente para a sua corrida. Esse tênis traz amortecimento flexível lightmotion para movimentos em alta velocidade. Já o solado em borracha oferece tração e aderência ao solo para melhor performance. Peça já seu tênis masculino e dê seu melhor no esporte!",
      "colors": "Preto",
      "sizes": "Disponíveis: 39, 40, 42, 44 | Indisponíveis: 37, 38, 41, 43, 45",
      "collectedAt": "2026-04-20T14:51:34.8820733"
    }
  ]
}
```

Nota: `totalProducts` indica quantos itens foram processados na última execução.

</details>

---

## 📄 Estrutura do Projeto

- controller → endpoints REST  
- service → lógica de negócio  
- scraper → extração com Jsoup  
- entity → modelagem das tabelas  
- repository → acesso ao banco  

</details>

---

## 👩‍💻 Autora

Rebeca da Silva Vieira
