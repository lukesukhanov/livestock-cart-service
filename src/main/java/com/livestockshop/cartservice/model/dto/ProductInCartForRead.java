package com.livestockshop.cartservice.model.dto;

import com.livestockshop.cartservice.repository.ProductInCartRepository;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A product in the cart.
 * <p>
 * The {@code equals} method should be used for comparisons.
 * The {@code ProductInCartForRead} objects are compared by {@code productName},
 * {@code description}, {@code quantity}, {@code price} and {@code currency}.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see ProductInCartRepository
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public final class ProductInCartForRead {

  private final String productName;

  private final String description;

  private final Integer quantity;

  private final Double price;

  private final String currency;
}
