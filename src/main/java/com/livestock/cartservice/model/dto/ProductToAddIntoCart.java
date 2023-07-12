package com.livestock.cartservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A product to add into the cart.
 * <p>
 * The {@code equals} method should be used for comparisons.
 * The {@code ProductToAddIntoCart} objects are compared by {@code userEmail},
 * {@code productId}, and {@code quantity}.
 * <p>
 * This class is immutable and thread-safe.
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public final class ProductToAddIntoCart {

  @NotNull(message = "The user's email is required")
  private final String userEmail;

  @NotNull(message = "The product's id is required")
  private final Long productId;

  @NotNull(message = "The quantity is required")
  private final Integer quantity;
}
