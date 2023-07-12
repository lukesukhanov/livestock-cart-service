package com.livestock.cartservice.model.entity;

import org.hibernate.annotations.DynamicUpdate;

import com.livestock.cartservice.repository.ProductInCartRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A product in cart entity.
 * <p>
 * The {@code equals} method should be used for comparisons.
 * The {@code ProductInCartEntity} objects are compared by {@code id}.
 * The {@code ProductInCartEntity} with {@code id = null} is equal only to
 * itself.
 * <p>
 * The {@code hashCode} method always returns the same value.
 * <p>
 * This class is not immutable and is not supposed to be used concurrently.
 * 
 * @see ProductInCartRepository
 */
@Entity
@Table(name = "PRODUCT_IN_CART")
@DynamicUpdate
@NamedEntityGraph(
    name = "productInCart.withProduct",
    attributeNodes = @NamedAttributeNode(value = ProductInCartEntity_.PRODUCT))
@NamedQuery(name = "find_products_in_cart_by_user_email",
    query = "select new com.livestock.cartservice.model.dto.ProductInCartForRead("
        + "product.id, product.productName, product.description, quantity, product.price, product.currency) "
        + "from ProductInCartEntity where userEmail = :userEmail")
@NamedQuery(name = "delete_product_in_cart_by_product_id_and_user_email",
    query = "delete from ProductInCartEntity where product.id = :productId and userEmail = :userEmail")
@NamedQuery(name = "delete_products_in_cart_by_user_email",
    query = "delete from ProductInCartEntity where userEmail = :userEmail")
@NoArgsConstructor
@Getter
@Setter
public class ProductInCartEntity {

  public static final String ENTITY_GRAPH_WITH_PRODUCT = "productInCart.withProduct";

  public static final String JPQL_FIND_PRODUCTS_IN_CART_BY_USER_EMAIL = "find_products_in_cart_by_user_email";
  public static final String JPQL_DELETE_PRODUCT_IN_CART_BY_PRODUCT_ID_AND_USER_EMAIL = "delete_product_in_cart_by_product_id_and_user_email";
  public static final String JPQL_DELETE_PRODUCTS_IN_CART_BY_USER_EMAIL = "delete_products_in_cart_by_user_email";

  @Id
  @GeneratedValue(generator = "common_id_seq")
  @SequenceGenerator(name = "common_id_seq", sequenceName = "COMMON_ID_SEQ", allocationSize = 5)
  @Column(name = "ID", updatable = false)
  private Long id;

  @Column(name = "QUANTITY")
  private Integer quantity;

  @Column(name = "USERS_EMAIL")
  private String userEmail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCT_ID")
  private ProductEntity product;

  @Override
  public int hashCode() {
    return 31;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProductInCartEntity)) {
      return false;
    }
    ProductInCartEntity other = (ProductInCartEntity) o;
    return this.id != null && this.id.equals(other.getId());
  }
}
