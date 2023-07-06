package com.livestockshop.cartservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.livestockshop.cartservice.model.dto.ProductInCartForRead;
import com.livestockshop.cartservice.model.entity.ProductInCartEntity;

public interface ProductInCartRepository extends CrudRepository<ProductInCartEntity, Long> {

  /**
   * Finds products in a cart by user's id.
   * <p>
   * Products in a cart are fetched with products.
   * 
   * @param userId a {@code Long} representing user's id
   * @param userId a {@code Pageable} with the required page ordinal and size
   * @return a {@code Page} of products in a cart with the given user's id
   */
  @EntityGraph(ProductInCartEntity.ENTITY_GRAPH_WITH_PRODUCT)
  @Query(name = ProductInCartEntity.JPQL_FIND_PRODUCTS_IN_CART_BY_USER_ID)
  Page<ProductInCartForRead> findByUserIdWithPaging(
      @Param("userId") Long userId,
      Pageable pageable);
}
