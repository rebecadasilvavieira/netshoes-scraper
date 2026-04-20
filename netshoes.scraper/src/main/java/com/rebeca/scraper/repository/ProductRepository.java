package com.rebeca.scraper.repository;

import com.rebeca.scraper.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**Interface de acesso a dados para a entidade Product.*/

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}