package com.livestockshop.cartservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A product entity.
 * <p>
 * The {@code equals} method should be used for comparisons.
 * The {@code ProductEntity} objects are compared by {@code id}.
 * The {@code ProductEntity} with {@code id = null} is equal only to itself.
 * <p>
 * The {@code hashCode} method always returns the same value.
 * <p>
 * This class is not immutable and is not supposed to be used concurrently.
 */
@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {

  @Id
  @GeneratedValue(generator = "common_id_seq")
  @SequenceGenerator(name = "common_id_seq", sequenceName = "COMMON_ID_SEQ", allocationSize = 5)
  @Column(name = "ID", updatable = false)
  private Long id;

  @Column(name = "PRODUCT_NAME")
  private String productName;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "PRICE")
  private Double price;

  @Column(name = "CURRENCY")
  private String currency;

  @Override
  public int hashCode() {
    return 31;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProductEntity)) {
      return false;
    }
    ProductEntity other = (ProductEntity) o;
    return this.id != null && this.id.equals(other.getId());
  }
}
