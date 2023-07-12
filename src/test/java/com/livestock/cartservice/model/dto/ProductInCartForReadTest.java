package com.livestock.cartservice.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("ProductInCartForRead")
@Tag("dto")
@Tag("productInCart")
class ProductInCartForReadTest {

  @Test
  @DisplayName("equals(Object) - the same object")
  final void equals_sameObject() throws Exception {
    ProductInCartForRead productInCart = new ProductInCartForRead(1L, "name", "decription", 1, 1d,
        "RUB");
    assertEquals(productInCart, productInCart);
  }

  @Test
  @DisplayName("equals(Object) - matching object")
  final void equals_matchingObject() throws Exception {
    ProductInCartForRead productInCart1 = new ProductInCartForRead(1L, "name", "decription", 1, 1d,
        "RUB");
    ProductInCartForRead productInCart2 = new ProductInCartForRead(1L, "name", "decription", 1, 1d,
        "RUB");
    assertEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("equals(Object) - different product id")
  final void equals_differentProductId() throws Exception {
    ProductInCartForRead productInCart1 = new ProductInCartForRead(1L, "name", "decription", 1, 1d,
        "RUB");
    ProductInCartForRead productInCart2 = new ProductInCartForRead(2L, "name", "decription", 1, 1d,
        "RUB");
    assertNotEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("equals(Object) - different product name")
  final void equals_differentProductName() throws Exception {
    ProductInCartForRead productInCart1 = new ProductInCartForRead(1L, "name1", "decription", 1, 1d,
        "RUB");
    ProductInCartForRead productInCart2 = new ProductInCartForRead(1L, "name2", "decription", 1, 1d,
        "RUB");
    assertNotEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("equals(Object) - different description")
  final void equals_differentDescription() throws Exception {
    ProductInCartForRead productInCart1 = new ProductInCartForRead(1L, "name", "decription1", 1, 1d,
        "RUB");
    ProductInCartForRead productInCart2 = new ProductInCartForRead(1L, "name", "decription2", 1, 1d,
        "RUB");
    assertNotEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("equals(Object) - different quantity")
  final void equals_differentQuantity() throws Exception {
    ProductInCartForRead productInCart1 = new ProductInCartForRead(1L, "name", "decription", 1, 1d,
        "RUB");
    ProductInCartForRead productInCart2 = new ProductInCartForRead(1L, "name", "decription", 2, 1d,
        "RUB");
    assertNotEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("equals(Object) - different price")
  final void equals_differentPrice() throws Exception {
    ProductInCartForRead productInCart1 = new ProductInCartForRead(1L, "name", "decription", 1, 1d,
        "RUB");
    ProductInCartForRead productInCart2 = new ProductInCartForRead(1L, "name", "decription", 1, 2d,
        "RUB");
    assertNotEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("equals(Object) - different currency")
  final void equals_differentCurrency() throws Exception {
    ProductInCartForRead productInCart1 = new ProductInCartForRead(1L, "name", "decription", 1, 1d,
        "RUB");
    ProductInCartForRead productInCart2 = new ProductInCartForRead(1L, "name", "decription", 1, 1d,
        "USD");
    assertNotEquals(productInCart1, productInCart2);
  }

  @Test
  @DisplayName("hashCode() - matching object")
  final void hashCode_matchingObject() throws Exception {
    ProductInCartForRead productInCart1 = new ProductInCartForRead(1L, "name", "decription", 1, 1d,
        "RUB");
    ProductInCartForRead productInCart2 = new ProductInCartForRead(1L, "name", "decription", 1, 1d,
        "RUB");
    assertEquals(productInCart1.hashCode(), productInCart2.hashCode());
  }
}
