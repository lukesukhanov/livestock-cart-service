package com.livestockshop.cartservice.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestockshop.cartservice.model.dto.ProductInCartForRead;
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
   * type mismatch", status: 400, detail: "Failed to convert value of type
   * 'java.lang.String' to required type 'java.lang.Integer'; For input string:
   * \"1.1\"", property: "page", requiredType: "java.lang.Integer", value:
   * "1.1"}
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
      @RequestParam(name = "page", required = false)
      @Valid
      @PositiveOrZero(message = "Page ordinal must be greater than or equal to 0") Integer page,
      @RequestParam(name = "size", required = false)
      @Valid
      @Positive(message = "Page size must be positive") Integer size,
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
}
