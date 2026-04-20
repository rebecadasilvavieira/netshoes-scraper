package com.rebeca.scraper.service;

import com.rebeca.scraper.entity.Product;
import com.rebeca.scraper.repository.ProductRepository;
import com.rebeca.scraper.scraper.NetshoesScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Classe que controla as regras de negócio dos produtos. */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private NetshoesScraper netshoesScraper;

    /** Pede ao robô para buscar os produtos e salva cada um no banco de dados. */
    @Transactional
    public List<Product> scrapeAndSaveAll() {
        List<Product> scrapedProducts = netshoesScraper.scrapeAll();
        List<Product> savedProducts = new ArrayList<>();

        for (Product product : scrapedProducts) {
            try {
                savedProducts.add(productRepository.save(product));
            } catch (Exception e) {
                /** Se um produto der erro, pula para o próximo para não parar tudo*/
            }
        }
        return savedProducts;
    }

    /** Puxa a lista de todos os produtos que já estão salvos. */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /** Encontra o produto em estoque que tem o menor preço de todos. */
    public Product getCheapestAvailableProduct() {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .filter(p -> p.getSizes() != null && p.getSizes().startsWith("Disponíveis:"))
                .min(Comparator.comparing(this::extractNumericPrice))
                .orElse(null);
    }

    /** Transforma o texto do preço (R$ 100,00) em um número que o Java consiga comparar. */
    private Double extractNumericPrice(Product product) {
        try {
            String price = product.getPrice();
            if (price == null || price.equals("Preço indisponível")) {
                return Double.MAX_VALUE; //
            }

            // Limpa os símbolos de moeda e pontos para sobrar só o número decimal
            String numericPrice = price.replace("R$ ", "").replace(".", "").replace(",", ".");
            return Double.parseDouble(numericPrice);
        } catch (Exception e) {
            return Double.MAX_VALUE;
        }
    }
}