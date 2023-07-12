package com.livestock.cartservice.service;

import org.springframework.data.domain.Page;

import com.livestock.cartservice.model.dto.ProductInCartForRead;
import com.livestock.cartservice.model.dto.ProductToAddIntoCart;

/**
 * The service for managing products in a cart.
 */
public interface ProductInCartService {

  /**
   * Finds products in a cart with the given user's email using paging.
   * 
   * @param page a {@code Integer} representing page ordinal
   * @param size a {@code Integer} representing page size
   * @param userEmail a {@code String} with the user's email
   * @return a {@code Page} of products in a cart with the given user's email
   *         found
   *         using paging
   */
  Page<ProductInCartForRead> getByUserEmailWithPaging(Integer page, Integer size, String userEmail);

  /**
   * Adds the given quantity of the given product into the user's cart.<br />
   * The quantity may be negative.<br />
   * If the result quantity is less than or equal to 0, the product is removed
   * from the cart.
   * 
   * @param product a {@code ProductToAddIntoCart} to add into the user's cart
   */
  void addProductToCart(ProductToAddIntoCart productToAdd);

  /**
   * Removes a product from the cart by product's id and user's email.
   * 
   * @param productId a {@code Long} representing id of the product
   * @param userEmail a {@code String} with the user's email
   */
  void removeProductFromCart(Long productId, String userEmail);

  /**
   * Clears a cart for the user with the given email.
   * 
   * @param userEmail a {@code String} with the user's email
   */
  void clearCartByUserEmail(String userEmail);
}
