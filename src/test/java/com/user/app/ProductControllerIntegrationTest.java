package com.user.app;

import com.user.app.jpa.model.Product;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
class ProductControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAddProduct() {
        Product product = new Product(1, "Laptop", 25, 1000.0);
        ResponseEntity<Product> response = restTemplate.postForEntity("/products", product, Product.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Laptop", response.getBody().getName());
    }

    @Test
    void testFindAllProducts() {
        ResponseEntity<List> response = restTemplate.getForEntity("/products", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testFindProductById() {
        int productId = 1;
        ResponseEntity<Product> response = restTemplate.getForEntity("/products/" + productId, Product.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productId, response.getBody().getId());
    }

    @Test
    void testUpdateProduct() {
        int productId = 1;
        Product updatedProduct = new Product(productId, "Updated Laptop", 30, 1200.0);
        HttpEntity<Product> requestEntity = new HttpEntity<>(updatedProduct);

        ResponseEntity<Product> response = restTemplate.exchange(
                "/products/update/" + productId,
                HttpMethod.PUT,
                requestEntity,
                Product.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Laptop", response.getBody().getName());
    }

    @Test
    void testDeleteProduct() {
        int productId = 1;
        ResponseEntity<String> response = restTemplate.exchange(
                "/products/delete/" + productId,
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted successfully", response.getBody());
    }
}