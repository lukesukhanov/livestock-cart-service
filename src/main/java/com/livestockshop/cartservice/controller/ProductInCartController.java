package com.livestockshop.cartservice.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestockshop.cartservice.exception.GeneralResponseEntityExceptionHandler;
import com.livestockshop.cartservice.model.dto.ProductInCartForRead;
import com.livestockshop.cartservice.model.dto.ProductToAddIntoCart;
import com.livestockshop.cartservice.service.ProductInCartService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;

/**
 * Provides the endpoints for accessing products in a cart.
 * <p>
 * The endpoints {@code /productsInCart/**} are used.
 * <p>
 * The JSON format is used for the response body.
 * 
 * @see ProductInCartService
 * @see GeneralResponseEntityExceptionHandler
 */
@RestController
@RequestMapping("/productsInCart")
@Validated
@RequiredArgsConstructor
public class ProductInCartController {

  private final ProductInCartService productInCartService;

  /**
   * Finds products in a cart by user's id using paging.
   * <p>
   * Serves the {@code GET} requests for the {@code /productsInCart} endpoint.
   * <p>
   * Request parameters:
   * <ul>
   * <li>page - the required page, type: integer, greater than or equal to 0,
   * not required</li>
   * <li>size - the page size, type: integer, positive, not required</li>
   * <li>userId - the user's id, type: long, required</li>
   * </ul>
   * <p>
   * <b>Usage example</b>
   * <p>
   * <i>Request</i>
   * <p>
   * GET /productsInCart?page=0&size=5&userId=1
   * <p>
   * <i>Normal response</i>
   * <p>
   * Status: 200<br />
   * Body: {numberOfElements: 5, first: true, last: false, totalElements: 10,
   * totalPages: 2, content: [{productName: "Овцы бараны", description: "Продаю
   * баранов и овец", quantity: 2, price: 9500, currency: "RUB"}, ...]}
   * <p>
   * <i>Response in case of invalid request parameters</i>
   * <p>
   * Status: 400<br />
   * Body: {type: "/probs/invalidRequestParameters", title: "Invalid request
   * parameters", status: 400, invalid-params: [{"name": "page", "reason": "Page
   * ordinal must be greater than or equal to 0"}]}
   * <p>
   * <i>Response in case request parameter has invalid type</i>
   * <p>
   * Status: 400<br />
   * Body: {type: "/probs/requestPropertyTypeMismatch", title: "Request property
   * type mismatch", status: 400, detail: "Failed to convert value ..."}
   * 
   * @param page a {@code Integer} representing page ordinal
   * @param size a {@code Integer} representing page size
   * @param userId a {@code Long} representing user's id
   * @return a {@code ResponseEntity} with the status {@code 200} and the body
   *         containing products from a cart with the given user's id found
   *         using paging
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getByUserIdWithPaging(
      @PositiveOrZero(message = "Page ordinal must be greater than or equal to 0")
      @RequestParam(name = "page", required = false) Integer page,
      @Positive(message = "Page size must be positive")
      @RequestParam(name = "size", required = false) Integer size,
      @RequestParam(name = "userId", required = true) Long userId) {

    Page<ProductInCartForRead> productsInCart = this.productInCartService
        .getByUserIdWithPaging(page, size, userId);
    Map<String, Object> responseBody = Map.of(
        "numberOfElements", productsInCart.getNumberOfElements(),
        "first", productsInCart.isFirst(),
        "last", productsInCart.isLast(),
        "totalElements", productsInCart.getTotalElements(),
        "totalPages", productsInCart.getTotalPages(),
        "content", productsInCart.getContent());
    return ResponseEntity.ok(responseBody);
  }

  /**
   * Adds the given quantity of the given product into the user's cart.<br />
   * The quantity may be negative.<br />
   * If the result quantity is less than or equal to 0, the product is removed
   * from the cart.
   * <p>
   * Serves the {@code POST} requests for the {@code /productsInCart} endpoint.
   * <p>
   * Request body:
   * <ul>
   * <li>userId - the user's id, type: long, required</li>
   * <li>productId - the product's id, type: long, required</li>
   * <li>quantity - how many products to add: integer, required</li>
   * </ul>
   * <p>
   * <b>Usage example</b>
   * <p>
   * <i>Request</i>
   * <p>
   * POST /productsInCart<br />
   * Body: {userId": 452, productId": 26, quantity: 2}
   * <p>
   * <i>Normal response</i>
   * <p>
   * Status: 204<br />
   * <p>
   * <i>Response in case of invalid request body fields</i>
   * <p>
   * Status: 400<br />
   * Body: {type: "/probs/invalidRequestBodyFields", title: "Invalid request
   * body fields", status: 400, invalid-fields: [{"name": "userId", "reason":
   * "The user's id is required"}]}
   * <p>
   * <i>Response in case of invalid request body</i>
   * <p>
   * Status: 400<br />
   * Body: {type: "/probs/invalidRequestBody", title: "Invalid request body",
   * status: 400, detail: "JSON parse error ..."}
   * 
   * @param productToAdd a {@code ProductInCartEntity} to add into the user's
   *        cart
   * @return a {@code ResponseEntity} with the status {@code 204}
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addProductToCart(@RequestBody @Valid ProductToAddIntoCart productToAdd) {
    this.productInCartService.addProductToCart(productToAdd);
    return ResponseEntity.noContent().build();
  }

  /**
   * Removes a product from the cart by id.
   * <p>
   * Serves the {@code DELETE} requests for the {@code /productsInCart/{id}}
   * endpoint.
   * <p>
   * Path variables:
   * <ul>
   * <li>id - the id of the product in the cart, type: long, required</li>
   * </ul>
   * <p>
   * <b>Usage example</b>
   * <p>
   * <i>Request</i>
   * <p>
   * DELETE /productsInCart/1<br />
   * <p>
   * <i>Normal response</i>
   * <p>
   * Status: 204<br />
   * <p>
   * <i>Response in case the path variable has invalid type</i>
   * <p>
   * Status: 400<br />
   * Body: {type: "/probs/requestPropertyTypeMismatch", title: "Request property
   * type mismatch", status: 400, detail: "Failed to convert value ..."}
   * 
   * @param productInCartId a {@code Long} representing id of the product in the
   *        cart
   * @return a {@code ResponseEntity} with the status {@code 204}
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> removeProductFromCart(@PathVariable("id") Long productInCartId) {
    this.productInCartService.removeProductFromCart(productInCartId);
    return ResponseEntity.noContent().build();
  }
}
