package com.livestock.cartservice.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("ProductToAddIntoCart")
@Tag("dto")
@Tag("productInCart")
class ProductToAddIntoCartTest {

  @Test
  @DisplayName("equals(Object) - the same object")
  final void equals_sameObject() throws Exception {
    ProductToAddIntoCart product = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    assertEquals(product, product);
  }

  @Test
  @DisplayName("equals(Object) - matching object")
  final void equals_matchingObject() throws Exception {
    ProductToAddIntoCart product1 = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    ProductToAddIntoCart product2 = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    assertEquals(product1, product2);
  }

  @Test
  @DisplayName("equals(Object) - different user's email")
  final void equals_differentUserEmail() throws Exception {
    ProductToAddIntoCart product1 = new ProductToAddIntoCart("vasya1@gmail.com", 1L, 1);
    ProductToAddIntoCart product2 = new ProductToAddIntoCart("vasya2@gmail.com", 1L, 1);
    assertNotEquals(product1, product2);
  }

  @Test
  @DisplayName("equals(Object) - different product's id")
  final void equals_differentProductId() throws Exception {
    ProductToAddIntoCart product1 = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    ProductToAddIntoCart product2 = new ProductToAddIntoCart("vasya@gmail.com", 2L, 1);
    assertNotEquals(product1, product2);
  }

  @Test
  @DisplayName("equals(Object) - different quantity")
  final void equals_differentQuantity() throws Exception {
    ProductToAddIntoCart product1 = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    ProductToAddIntoCart product2 = new ProductToAddIntoCart("vasya@gmail.com", 1L, 2);
    assertNotEquals(product1, product2);
  }

  @Test
  @DisplayName("hashCode() - matching object")
  final void hashCode_matchingObject() throws Exception {
    ProductToAddIntoCart product1 = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    ProductToAddIntoCart product2 = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    assertEquals(product1.hashCode(), product2.hashCode());
  }
}
