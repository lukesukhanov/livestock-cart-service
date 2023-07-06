package com.livestockshop.cartservice.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("ProductEntity")
@Tag("entity")
@Tag("product")
class ProductEntityTest {

  @Test
  @DisplayName("equals(Object) - the same object")
  final void equals_sameObject() throws Exception {
    ProductEntity product = new ProductEntity();
    product.setId(1L);
    product.setProductName("name");
    product.setDescription("description");
    product.setPrice(1d);
    product.setCurrency("RUB");
    assertEquals(product, product);
  }

  @Test
  @DisplayName("equals(Object) - the same id")
  final void equals_sameId() throws Exception {
    ProductEntity product1 = new ProductEntity();
    product1.setId(1L);
    product1.setProductName("name1");
    product1.setDescription("description1");
    product1.setPrice(1d);
    product1.setCurrency("RUB");
    ProductEntity product2 = new ProductEntity();
    product2.setId(1L);
    product2.setProductName("name2");
    product2.setDescription("description2");
    product2.setPrice(2d);
    product2.setCurrency("USD");
    assertEquals(product1, product2);
  }

  @Test
  @DisplayName("equals(Object) - different id")
  final void equals_differentId() throws Exception {
    ProductEntity product1 = new ProductEntity();
    product1.setId(1L);
    product1.setProductName("name");
    product1.setDescription("description");
    product1.setPrice(1d);
    product1.setCurrency("RUB");
    ProductEntity product2 = new ProductEntity();
    product2.setId(2L);
    product2.setProductName("name");
    product2.setDescription("description");
    product2.setPrice(1d);
    product2.setCurrency("RUB");
    assertNotEquals(product1, product2);
  }

  @Test
  @DisplayName("equals(Object) - one of the ids is null")
  final void equals_oneOfIdsIsNull() throws Exception {
    ProductEntity product1 = new ProductEntity();
    product1.setId(1L);
    product1.setProductName("name");
    product1.setDescription("description");
    product1.setPrice(1d);
    product1.setCurrency("RUB");
    ProductEntity product2 = new ProductEntity();
    product2.setId(null);
    product2.setProductName("name");
    product2.setDescription("description");
    product2.setPrice(1d);
    product2.setCurrency("RUB");
    assertNotEquals(product1, product2);
  }

  @Test
  @DisplayName("equals(Object) - both ids are null")
  final void equals_bothIdsAreNull() throws Exception {
    ProductEntity product1 = new ProductEntity();
    product1.setId(null);
    product1.setProductName("name");
    product1.setDescription("description");
    product1.setPrice(1d);
    product1.setCurrency("RUB");
    ProductEntity product2 = new ProductEntity();
    product2.setId(null);
    product2.setProductName("name");
    product2.setDescription("description");
    product2.setPrice(1d);
    product2.setCurrency("RUB");
    assertNotEquals(product1, product2);
  }

  @Test
  @DisplayName("hashCode() - any other ProductEntity")
  final void hashCode_anyOtherProductEntity() throws Exception {
    ProductEntity product1 = new ProductEntity();
    product1.setId(1L);
    product1.setProductName("name1");
    product1.setDescription("description1");
    product1.setPrice(1d);
    product1.setCurrency("RUB");
    ProductEntity product2 = new ProductEntity();
    product2.setId(2L);
    product2.setProductName("name2");
    product2.setDescription("description2");
    product2.setPrice(2d);
    product2.setCurrency("USD");
    assertEquals(product1.hashCode(), product2.hashCode());
  }
}
