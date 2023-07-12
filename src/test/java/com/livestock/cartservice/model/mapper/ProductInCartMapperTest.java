package com.livestock.cartservice.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.livestock.cartservice.LivestockShopCartServiceApplication;
import com.livestock.cartservice.model.dto.ProductToAddIntoCart;
import com.livestock.cartservice.model.entity.ProductInCartEntity;

@SpringBootTest(classes = LivestockShopCartServiceApplication.class)
@DisplayName("ProductInCartMapper")
@Tag("mapper")
@Tag("productInCart")
class ProductInCartMapperTest {

  @Autowired
  private ProductInCartMapper productInCartMapper;

  @Test
  @DisplayName("productToAddIntoCartToProductInCartEntity(...) - the same object")
  final void productToAddIntoCartToProductInCartEntity_normalReturn() throws Exception {
    ProductToAddIntoCart productToAddIntoCart = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    ProductInCartEntity productInCartEntity = this.productInCartMapper
        .productToAddIntoCartToProductInCartEntity(productToAddIntoCart);
    assertEquals(productInCartEntity.getUserEmail(), productToAddIntoCart.getUserEmail());
    assertEquals(productInCartEntity.getProduct().getId(), productToAddIntoCart.getProductId());
    assertEquals(productInCartEntity.getQuantity(), productToAddIntoCart.getQuantity());
    assertNull(productInCartEntity.getProduct().getProductName());
    assertNull(productInCartEntity.getProduct().getDescription());
    assertNull(productInCartEntity.getProduct().getPrice());
    assertNull(productInCartEntity.getProduct().getCurrency());
  }
}
