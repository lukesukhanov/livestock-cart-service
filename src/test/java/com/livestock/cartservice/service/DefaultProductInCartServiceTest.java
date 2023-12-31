package com.livestock.cartservice.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import com.livestock.cartservice.Application;
import com.livestock.cartservice.model.dto.ProductInCartForRead;
import com.livestock.cartservice.model.dto.ProductToAddIntoCart;
import com.livestock.cartservice.model.entity.ProductInCartEntity_;
import com.livestock.cartservice.repository.ProductInCartRepository;

@SpringBootTest(classes = Application.class)
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
    ProductInCartForRead productInCart1 = new ProductInCartForRead(1L, "name", "description", 1, 1d,
        "RUB");
    Collections.addAll(productsInCart, productInCart1);
    this.existingProductsInCart = new PageImpl<ProductInCartForRead>(productsInCart);
  }

  @Test
  @DisplayName("getByUserEmailWithPaging(...) - normal return")
  @WithMockUser(username = "vasya@gmail.com")
  final void getByUserEmailWithPaging_normalReturn() throws Exception {
    String userEmail = "vasya@gmail.com";
    Integer page = 0;
    Integer size = 10;
    Pageable pageable = PageRequest.of(page, size, Sort.by(ProductInCartEntity_.ID));
    when(this.productInCartRepository.findByUserEmailWithPaging(userEmail, pageable))
        .thenReturn(this.existingProductsInCart);
    Page<ProductInCartForRead> result = this.productInCartService.getByUserEmailWithPaging(page,
        size,
        userEmail);
    assertEquals(result, this.existingProductsInCart);
  }

  @Test
  @DisplayName("getByUserEmailWithPaging(...) - not matching principal email")
  @WithMockUser(username = "petya@gmail.com")
  final void getByUserEmailWithPaging_notMatchingPrincipalEmail() throws Exception {
    String userEmail = "vasya@gmail.com";
    Integer page = 0;
    Integer size = 10;
    assertThrows(AccessDeniedException.class,
        () -> this.productInCartService.getByUserEmailWithPaging(page, size, userEmail));
  }

  @Test
  @DisplayName("addProductToCart(ProductInCartEntity) - normal return")
  @WithMockUser(username = "vasya@gmail.com")
  final void addProductToCart_normalReturn() throws Exception {
    ProductToAddIntoCart productToAdd = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    assertDoesNotThrow(() -> this.productInCartService.addProductToCart(productToAdd));
  }

  @Test
  @DisplayName("addProductToCart(ProductInCartEntity) - not matching principal email")
  @WithMockUser(username = "petya@gmail.com")
  final void addProductToCart_notMatchingPrincipalEmail() throws Exception {
    ProductToAddIntoCart productToAdd = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    assertThrows(AccessDeniedException.class,
        () -> this.productInCartService.addProductToCart(productToAdd));
  }

  @Test
  @DisplayName("removeProductFromCart(Long, String) - normal return")
  @WithMockUser(username = "vasya@gmail.com")
  final void removeProductFromCart_normalReturn() throws Exception {
    assertDoesNotThrow(
        () -> this.productInCartService.removeProductFromCart(1L, "vasya@gmail.com"));
  }

  @Test
  @DisplayName("removeProductFromCart(Long, String) - not matching principal email")
  @WithMockUser(username = "petya@gmail.com")
  final void removeProductFromCart_notMatchingPrincipalEmail() throws Exception {
    assertThrows(AccessDeniedException.class,
        () -> this.productInCartService.removeProductFromCart(1L, "vasya@gmail.com"));
  }

  @Test
  @DisplayName("clearCartByUserEmail(String) - normal return")
  @WithMockUser(username = "vasya@gmail.com")
  final void clearCartByUserEmail_normalReturn() throws Exception {
    assertDoesNotThrow(() -> this.productInCartService.clearCartByUserEmail("vasya@gmail.com"));
  }

  @Test
  @DisplayName("clearCartByUserEmail(String) - not matching principal email")
  @WithMockUser(username = "petya@gmail.com")
  final void clearCartByUserEmail_notMatchingPrincipalEmail() throws Exception {
    assertThrows(AccessDeniedException.class,
        () -> this.productInCartService.clearCartByUserEmail("vasya@gmail.com"));
  }
}
