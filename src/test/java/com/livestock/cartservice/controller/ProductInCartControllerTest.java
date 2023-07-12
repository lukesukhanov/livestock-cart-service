package com.livestock.cartservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.livestock.cartservice.LivestockShopCartServiceApplication;
import com.livestock.cartservice.model.dto.ProductInCartForRead;
import com.livestock.cartservice.model.dto.ProductToAddIntoCart;
import com.livestock.cartservice.model.entity.ProductInCartEntity_;
import com.livestock.cartservice.service.ProductInCartService;

@SpringBootTest(classes = LivestockShopCartServiceApplication.class)
@DisplayName("ProductInCartController")
@Tag("controller")
@Tag("productInCart")
@AutoConfigureMockMvc
class ProductInCartControllerTest {

  @MockBean
  private ProductInCartService productInCartService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private final List<ProductInCartForRead> existingProductsInCart;

  {
    ProductInCartForRead product1 = new ProductInCartForRead(1L, "name", "description", 1, 1d, "RUB");
    this.existingProductsInCart = List.of(product1);
  }

  @Test
  @DisplayName("getByUserEmailWithPaging(...) - normal return")
  final void getByUserEmailWithPaging_normalReturn() throws Exception {
    Integer page = 0;
    Integer size = 10;
    String userEMail = "vasya@gmail.com";
    Pageable pageable = PageRequest.of(page, size, Sort.by(ProductInCartEntity_.ID));
    Page<ProductInCartForRead> productsInCart = new PageImpl<>(this.existingProductsInCart,
        pageable,
        this.existingProductsInCart.size());
    Map<String, Object> responseBody = Map.of(
        "content", productsInCart.getContent(),
        "numberOfElements", productsInCart.getNumberOfElements(),
        "first", productsInCart.isFirst(),
        "last", productsInCart.isLast(),
        "totalElements", productsInCart.getTotalElements(),
        "totalPages", productsInCart.getTotalPages());
    when(this.productInCartService.getByUserEmailWithPaging(page, size, userEMail))
        .thenReturn(productsInCart);
    this.mockMvc.perform(get("/productsInCart")
        .accept(MediaType.APPLICATION_JSON)
        .param("page", page.toString())
        .param("size", size.toString())
        .param("userEmail", userEMail.toString()))
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().bytes(this.objectMapper.writeValueAsBytes(responseBody)));
  }

  @Test
  @DisplayName("addProductToCart(ProductToAddIntoCart) - normal return")
  final void addProductToCart_normalReturn() throws Exception {
    ProductToAddIntoCart productToAdd = new ProductToAddIntoCart("vasya@gmail.com", 1L, 1);
    this.mockMvc.perform(post("/productsInCart")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsBytes(productToAdd)))
        .andExpectAll(
            status().isNoContent());
  }

  @Test
  @DisplayName("removeProductFromCart(Long, String) - normal return")
  final void removeProductFromCart_normalReturn() throws Exception {
    this.mockMvc.perform(delete("/productsInCart/1")
        .param("userEmail", "vasya@gmail.com"))
        .andExpectAll(
            status().isNoContent());
  }

  @Test
  @DisplayName("clearCartByUserEmail(String) - normal return")
  final void clearCartByUserEmail_normalReturn() throws Exception {
    this.mockMvc.perform(delete("/productsInCart")
        .param("userEmail", "vasya@gmail.com"))
        .andExpectAll(
            status().isNoContent());
  }
}
