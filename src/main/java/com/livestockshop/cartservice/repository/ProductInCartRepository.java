package com.livestockshop.cartservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.livestockshop.cartservice.model.dto.ProductInCartForRead;
import com.livestockshop.cartservice.model.entity.ProductInCartEntity;

import jakarta.persistence.LockModeType;

public interface ProductInCartRepository extends CrudRepository<ProductInCartEntity, Long> {

  /**
   * Finds products in a cart by user's id.
   * <p>
   * Products in a cart are fetched with products.
   * 
   * @param userId a {@code Long} representing user's id
   * @param pageable a {@code Pageable} with the required page ordinal and size
   * @return a {@code Page} of products in a cart with the given user's id
   */
  @EntityGraph(ProductInCartEntity.ENTITY_GRAPH_WITH_PRODUCT)
  @Query(name = ProductInCartEntity.JPQL_FIND_PRODUCTS_IN_CART_BY_USER_ID)
  Page<ProductInCartForRead> findByUserIdWithPaging(
      @Param("userId") Long userId,
      Pageable pageable);

  /**
   * Finds a product in the cart by user's id and product's id.
   * <p>
   * The lock for write is acquired.
   * 
   * @param userId a {@code Long} representing user's id
   * @param product a {@code Long} representing product's id
   * @return a {@code Optional} with {@code ProductInCartEntity} with the given
   *         user's id and product's id
   */
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<ProductInCartEntity> findByUserIdAndProductId(Long userId, Long productId);
}
