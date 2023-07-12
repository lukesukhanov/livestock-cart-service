package com.livestock.cartservice.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("ProductInCartEntity")
@Tag("entity")
@Tag("productInCart")
class ProductInCartEntityTest {

  @Test
  @DisplayName("equals(Object) - the same object")
  final void equals_sameObject() throws Exception {
    ProductInCartEntity productInCart = new ProductInCartEntity();
    productInCart.setId(1L);
    productInCart.setUserEmail("vasya@gmail.com");
    productInCart.setProduct(new ProductEntity());
    assertEquals(productInCart, productInCart);
  }

  @Test
  @DisplayName("equals(Object) - the same id")
  final void equals_sameId() throws Exception {
    ProductInCartEntity productInCart1 = new ProductInCartEntity();
    productInCart1.setId(1L);
    productInCart1.setUserEmail("vasya1@gmail.com");
    productInCart1.setProduct(new ProductEntity());
    ProductInCartEntity productInCart2 = new ProductInCartEntity();
    productInCart2.setId(1L);
    productInCart2.setUserEmail("vasya2@gmail.com");
    productInCart2.setProduct(new ProductEntity());
    assertEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("equals(Object) - different id")
  final void equals_differentId() throws Exception {
    ProductEntity product = new ProductEntity();
    ProductInCartEntity productInCart1 = new ProductInCartEntity();
    productInCart1.setId(1L);
    productInCart1.setUserEmail("vasya@gmail.com");
    productInCart1.setProduct(product);
    ProductInCartEntity productInCart2 = new ProductInCartEntity();
    productInCart2.setId(2L);
    productInCart2.setUserEmail("vasya@gmail.com");
    productInCart2.setProduct(product);
    assertNotEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("equals(Object) - one of the ids is null")
  final void equals_oneOfIdsIsNull() throws Exception {
    ProductEntity product = new ProductEntity();
    ProductInCartEntity productInCart1 = new ProductInCartEntity();
    productInCart1.setId(1L);
    productInCart1.setUserEmail("vasya@gmail.com");
    productInCart1.setProduct(product);
    ProductInCartEntity productInCart2 = new ProductInCartEntity();
    productInCart2.setId(null);
    productInCart2.setUserEmail("vasya@gmail.com");
    productInCart2.setProduct(product);
    assertNotEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("equals(Object) - both ids are null")
  final void equals_bothIdsAreNull() throws Exception {
    ProductEntity product = new ProductEntity();
    ProductInCartEntity productInCart1 = new ProductInCartEntity();
    productInCart1.setId(null);
    productInCart1.setUserEmail("vasya@gmail.com");
    productInCart1.setProduct(product);
    ProductInCartEntity productInCart2 = new ProductInCartEntity();
    productInCart2.setId(null);
    productInCart2.setUserEmail("vasya@gmail.com");
    productInCart2.setProduct(product);
    assertNotEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("hashCode() - any other ProductInCartEntity")
  final void hashCode_anyOtherProductInCartEntity() throws Exception {
    ProductInCartEntity productInCart1 = new ProductInCartEntity();
    productInCart1.setId(1L);
    productInCart1.setUserEmail("vasya@gmail.com");
    productInCart1.setProduct(new ProductEntity());
    ProductInCartEntity productInCart2 = new ProductInCartEntity();
    productInCart2.setId(2L);
    productInCart2.setUserEmail("vasya@gmail.com");
    productInCart2.setProduct(new ProductEntity());
    assertEquals(productInCart1.hashCode(), productInCart2.hashCode());
  }
}
