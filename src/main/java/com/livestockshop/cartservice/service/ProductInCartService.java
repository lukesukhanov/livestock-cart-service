package com.livestockshop.cartservice.service;

import org.springframework.data.domain.Page;

import com.livestockshop.cartservice.model.dto.ProductInCartForRead;
import com.livestockshop.cartservice.model.dto.ProductToAddIntoCart;

/**
 * The service for managing products in a cart.
 */
public interface ProductInCartService {

  /**
   * Finds products in a cart with the given user's id using paging.
   * 
   * @param page a {@code Integer} representing page ordinal
   * @param size a {@code Integer} representing page size
   * @param userId a {@code Long} representing user's id
   * @return a {@code Page} of products in a cart with the given user's id found
   *         using paging
   */
  Page<ProductInCartForRead> getByUserIdWithPaging(Integer page, Integer size, Long userId);

  /**
   * Adds the given quantity of the given product into the user's cart.<br />
   * The quantity may be negative.<br />
   * If the result quantity is less than or equal to 0, the product is removed
   * from the cart.
   * 
   * @param product a {@code ProductToAddIntoCart} to add into the user's cart
   */
  void addProductToCart(ProductToAddIntoCart productToAdd);
}
