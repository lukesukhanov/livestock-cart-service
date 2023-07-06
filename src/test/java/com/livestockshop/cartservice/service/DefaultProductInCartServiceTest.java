package com.livestockshop.cartservice.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.livestockshop.cartservice.LivestockShopCartServiceApplication;
import com.livestockshop.cartservice.model.dto.ProductInCartForRead;
import com.livestockshop.cartservice.model.dto.ProductToAddIntoCart;
import com.livestockshop.cartservice.model.entity.ProductInCartEntity_;
import com.livestockshop.cartservice.repository.ProductInCartRepository;

@SpringBootTest(classes = LivestockShopCartServiceApplication.class)
@DisplayName("DefaultProductInCartService")
@Tag("service")
@Tag("productInCart")
class DefaultProductInCartServiceTest {

  @MockBean
  private ProductInCartRepository productInCartRepository;

  @Autowired
  private DefaultProductInCartService productInCartService;

  private final Page<ProductInCartForRead> existingProductsInCart;

  {
    List<ProductInCartForRead> productsInCart = new ArrayList<>();
    ProductInCartForRead productInCart1 = new ProductInCartForRead("name", "description", 1, 1d,
        "RUB");
    Collections.addAll(productsInCart, productInCart1);
    this.existingProductsInCart = new PageImpl<ProductInCartForRead>(productsInCart);
  }

  @Test
  @DisplayName("getByUserIdWithPaging(...) - normal return")
  final void getByUserIdWithPaging_normalReturn() throws Exception {
    Long userId = 1L;
    Integer page = 0;
    Integer size = 10;
    Pageable pageable = PageRequest.of(page, size, Sort.by(ProductInCartEntity_.ID));
    when(this.productInCartRepository.findByUserIdWithPaging(userId, pageable))
        .thenReturn(this.existingProductsInCart);
    Page<ProductInCartForRead> result = this.productInCartService.getByUserIdWithPaging(page, size,
        userId);
    assertEquals(result, this.existingProductsInCart);
  }

  @Test
  @DisplayName("addProductToCart(ProductInCartEntity) - normal return")
  final void addProductToCart_normalReturn() throws Exception {
    ProductToAddIntoCart productToAdd = new ProductToAddIntoCart(1L, 1L, 1);
    assertDoesNotThrow(() -> this.productInCartService.addProductToCart(productToAdd));
  }
}
