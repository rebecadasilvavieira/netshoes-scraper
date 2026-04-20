package com.rebeca.scraper.scraper;

import com.rebeca.scraper.entity.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Motor de extração de dados especializado no site Netshoes. */
@Component
public class NetshoesScraper {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36";
    private static final String PRODUCT_URL = "https://www.netshoes.com.br/p/tenis-adidas-duramo-20-masculino-3ZP-5591-006";

    /** Inicia a captura global do produto e suas variações. */
    public List<Product> scrapeAll() {
        return scrapeProductWithVariants(PRODUCT_URL);
    }

    /** Percorre a página principal e as URLs de cada cor/variante encontrada. */
    private List<Product> scrapeProductWithVariants(String url) {
        List<Product> products = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).userAgent(USER_AGENT).timeout(15000).get();
            String baseName = extractBaseName(doc);

            Element descEl = doc.selectFirst(".features--description");
            String description = descEl != null ? descEl.text().trim() : doc.select("meta[name=description]").attr("content");

            Elements colorItems = doc.select("ul.color-list li.color-list__item");

            if (colorItems.isEmpty()) {
                Product p = scrapeCurrentVariant(doc, baseName, description);
                if (p != null) products.add(p);
            } else {
                for (Element colorItem : colorItems) {
                    Element link = colorItem.selectFirst("a");
                    if (link == null) continue;

                    String variantUrl = link.absUrl("href");

                    try {
                        Thread.sleep(500);
                        Document variantDoc = Jsoup.connect(variantUrl).userAgent(USER_AGENT).timeout(15000).get();
                        Product p = scrapeCurrentVariant(variantDoc, baseName, description);
                        if (p != null) products.add(p);
                    } catch (Exception ignored) {}
                }
            }
        } catch (Exception ignored) {}

        return products;
    }

    /** Limpa o título original para obter o nome base do modelo. */
    private String extractBaseName(Document doc) {
        String metaTitle = doc.select("meta[property=og:title]").attr("content");
        if (!metaTitle.isEmpty()) {
            metaTitle = metaTitle.replaceAll("\\s*\\|\\s*Netshoes\\s*$", "").trim();
            int lastDash = metaTitle.lastIndexOf(" - ");
            return (lastDash > 0) ? metaTitle.substring(0, lastDash).trim() : metaTitle;
        }
        return "Produto";
    }

    /** Extrai os dados específicos da variante carregada no momento. */
    private Product scrapeCurrentVariant(Document doc, String baseName, String description) {
        try {
            String colorName = extractColorName(doc);
            return new Product(
                    baseName + " - " + colorName,
                    extractPriceFromDOM(doc),
                    doc.select("meta[property=og:image]").attr("content"),
                    description,
                    colorName,
                    extractAvailableSizes(doc),
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            return null;
        }
    }

    /** Obtém o nome da cor através do H1 ou metadados de título. */
    private String extractColorName(Document doc) {
        Element h1 = doc.selectFirst("h1.short-title");
        String text = (h1 != null) ? h1.text() : doc.select("meta[property=og:title]").attr("content");
        String[] parts = text.split(" - ");
        return (parts.length >= 2) ? parts[parts.length - 1].replace("| Netshoes", "").trim() : "Cor única";
    }

    /** Mapeia a grade de tamanhos separando a disponibilidade de estoque. */
    private String extractAvailableSizes(Document doc) {
        List<String> available = new ArrayList<>();
        List<String> unavailable = new ArrayList<>();
        Elements sizeItems = doc.select("ul.size-list li.size-list__item, li[class*='size']");

        for (Element li : sizeItems) {
            Element button = li.selectFirst("button, a");
            String size = button != null ? button.text().trim() : li.text().trim();

            if (size.isBlank() || !size.matches(".*\\d+.*")) continue;

            boolean isOut = li.hasClass("unavailable") || li.hasClass("disabled") || (button != null && button.hasAttr("disabled"));
            if (isOut) unavailable.add(size); else available.add(size);
        }

        if (available.isEmpty() && unavailable.isEmpty()) return "Tamanho único";

        StringBuilder sb = new StringBuilder();
        if (!available.isEmpty()) sb.append("Disponíveis: ").append(String.join(", ", available));
        if (!unavailable.isEmpty()) {
            if (sb.length() > 0) sb.append(" | ");
            sb.append("Indisponíveis: ").append(String.join(", ", unavailable));
        }
        return sb.toString();
    }

    /** Tenta capturar o preço via seletores CSS ou Regex em scripts internos. */
    private String extractPriceFromDOM(Document doc) {
        String[] selectors = {
                ".default-price",
                ".price-box",
                "span.number",
                "[class*='price'] strong",
                "[class*='price'] span",
                ".product-price",
                "[data-testid*='price']",
                "div[class*='Price'] span",
                "span[class*='Price']",
                "[class*='ProductPrice']",
                "strong[class*='price']"
        };

        for (String s : selectors) {
            Element el = doc.selectFirst(s);
            if (el != null && !el.text().isEmpty()) {
                String text = el.text().trim();
                if (text.matches(".*\\d+.*")) {
                    String cleaned = text.replaceAll("[^\\d.,]", "");
                    if (!cleaned.isEmpty() && cleaned.matches("\\d+[.,]?\\d*")) {
                        return "R$ " + cleaned;
                    }
                }
            }
        }

        String html = doc.html();
        String[] patterns = {
                "\"bestPrice\"\\s*:\\s*([\\d.]+)",
                "\"price\"\\s*:\\s*([\\d.]+)",
                "\"value\"\\s*:\\s*([\\d.]+)",
                "\"selling_price\"\\s*:\\s*([\\d.]+)",
                "\"salesPrice\"\\s*:\\s*([\\d.]+)",
                "price[\"']?\\s*:\\s*[\"']?([\\d.,]+)",
                "R\\$\\s*([\\d.,]+)"
        };

        for (String p : patterns) {
            var matcher = java.util.regex.Pattern.compile(p).matcher(html);
            if (matcher.find()) {
                String priceValue = matcher.group(1).replace(".", "").replace(",", ".");
                try {
                    double price = Double.parseDouble(priceValue);
                    if (price > 0 && price < 100000) {
                        return "R$ " + formatPrice(matcher.group(1));
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        Elements allElements = doc.select("*");
        for (Element el : allElements) {
            String text = el.ownText().trim();
            if (text.matches("R\\$\\s*\\d+[.,]\\d{2}")) {
                return text;
            }
        }

        return "Preço indisponível";
    }

    /** Converte valores decimais de ponto para o formato de moeda brasileiro. */
    private String formatPrice(String price) {
        if (!price.contains(".")) return price + ",00";
        String[] parts = price.split("\\.");
        return parts[0] + "," + (parts[1].length() == 1 ? parts[1] + "0" : parts[1]);
    }
}