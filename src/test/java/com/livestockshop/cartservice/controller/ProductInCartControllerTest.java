package com.livestockshop.cartservice.controller;

import static org.mockito.Mockito.when;
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
import com.livestockshop.cartservice.LivestockShopCartServiceApplication;
import com.livestockshop.cartservice.model.dto.ProductInCartForRead;
import com.livestockshop.cartservice.model.dto.ProductToAddIntoCart;
import com.livestockshop.cartservice.model.entity.ProductInCartEntity_;
import com.livestockshop.cartservice.service.ProductInCartService;

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
    ProductInCartForRead product1 = new ProductInCartForRead("name", "description", 1, 1d, "RUB");
    this.existingProductsInCart = List.of(product1);
  }

  @Test
  @DisplayName("getByUserIdWithPaging(...) - normal return")
  final void getByUserIdWithPaging_normalReturn() throws Exception {
    Integer page = 0;
    Integer size = 10;
    Long userId = 1L;
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
    when(this.productInCartService.getByUserIdWithPaging(page, size, userId))
        .thenReturn(productsInCart);
    this.mockMvc.perform(get("/productsInCart")
        .accept(MediaType.APPLICATION_JSON)
        .param("page", page.toString())
        .param("size", size.toString())
        .param("userId", userId.toString()))
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().bytes(this.objectMapper.writeValueAsBytes(responseBody)));
  }

  @Test
  @DisplayName("getByUserIdWithPaging(ProductToAddIntoCart) - normal return")
  final void addProductToCart_normalReturn() throws Exception {
    ProductToAddIntoCart productToAdd = new ProductToAddIntoCart(1L, 1L, 1);
    this.mockMvc.perform(post("/productsInCart")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsBytes(productToAdd)))
        .andExpectAll(
            status().isNoContent());
  }
}
