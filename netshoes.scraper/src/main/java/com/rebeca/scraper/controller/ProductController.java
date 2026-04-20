package com.rebeca.scraper.controller;

import com.rebeca.scraper.entity.Product;
import com.rebeca.scraper.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /** Aciona o scraping da Netshoes e persiste os resultados.*/

    @PostMapping("/scrape")
    public ResponseEntity<Map<String, Object>> scrapeProducts() {
        try {
            List<Product> products = productService.scrapeAndSaveAll();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("totalProducts", products.size());
            response.put("products", products);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Erro no processamento: " + e.getMessage()
            ));
        }
    }

    /** Lista todos os produtos cadastrados.*/

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /** Busca o produto disponível (com estoque) de menor preço.*/

    @GetMapping("/melhorpreco")
    public ResponseEntity<Product> getCheapestProduct() {
        Product product = productService.getCheapestAvailableProduct();
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }
}