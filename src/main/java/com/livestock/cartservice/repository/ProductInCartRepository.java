package com.livestock.cartservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.livestock.cartservice.model.dto.ProductInCartForRead;
import com.livestock.cartservice.model.entity.ProductInCartEntity;

import jakarta.persistence.LockModeType;

public interface ProductInCartRepository extends CrudRepository<ProductInCartEntity, Long> {

  /**
   * Finds products in a cart by user's email.
   * <p>
   * Products in a cart are fetched with products.
   * 
   * @param userId a {@code String} with the user's email
   * @param pageable a {@code Pageable} with the required page ordinal and size
   * @return a {@code Page} of products in a cart with the given user's email
   */
  @EntityGraph(ProductInCartEntity.ENTITY_GRAPH_WITH_PRODUCT)
  @Query(name = ProductInCartEntity.JPQL_FIND_PRODUCTS_IN_CART_BY_USER_EMAIL)
  Page<ProductInCartForRead> findByUserEmailWithPaging(
      @Param("userEmail") String userEmail,
      Pageable pageable);

  /**
   * Finds a product in the cart by user's email and product's id.
   * <p>
   * The lock for write is acquired.
   * 
   * @param userEmail a {@code String} with the user's email
   * @param productId a {@code Long} representing product's id
   * @return an {@code Optional} with {@code ProductInCartEntity}
   */
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<ProductInCartEntity> findByUserEmailAndProductId(String userEmail, Long productId);

  /**
   * Removes a product from the cart by product's id and user's email.
   * 
   * @param productId a {@code Long} representing product's id
   * @param userEmail a {@code String} with the user's email
   */
  @Query(name = ProductInCartEntity.JPQL_DELETE_PRODUCT_IN_CART_BY_PRODUCT_ID_AND_USER_EMAIL)
  @Modifying
  void deleteByProductIdAndUserEmail(
      @Param("productId") Long productId,
      @Param("userEmail") String userEmail);

  /**
   * Removes a products from the cart by user's email.
   * 
   * @param userEmail a {@code String} with the user's email
   */
  @Query(name = ProductInCartEntity.JPQL_DELETE_PRODUCTS_IN_CART_BY_USER_EMAIL)
  @Modifying
  void deleteByUserEmail(@Param("userEmail") String userEmail);
}
