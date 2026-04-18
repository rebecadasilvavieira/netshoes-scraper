package com.rebeca.scraper.entity;

import java.time.LocalDateTime;

public class Product {

    private Long id;
    private String title;
    private String price;
    private String imageUrl;
    private String description;
    private LocalDateTime collectedAt;

    public Product() {
    }

    public Product(Long id, String title, String price, String imageUrl, String description, LocalDateTime collectedAt) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.collectedAt = collectedAt;
    }

    public Product(String title, String price, String imageUrl, String description, LocalDateTime collectedAt) {
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.collectedAt = collectedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCollectedAt() {
        return collectedAt;
    }

    public void setCollectedAt(LocalDateTime collectedAt) {
        this.collectedAt = collectedAt;
    }
}