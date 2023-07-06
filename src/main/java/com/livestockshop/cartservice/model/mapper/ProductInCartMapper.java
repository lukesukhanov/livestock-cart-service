package com.livestockshop.cartservice.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import com.livestockshop.cartservice.model.dto.ProductToAddIntoCart;
import com.livestockshop.cartservice.model.entity.ProductInCartEntity;

/**
 * The mappings between objects associated with products in a cart.
 */
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductInCartMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "product.id", source = "productId")
  @Mapping(target = "product.productName", ignore = true)
  @Mapping(target = "product.description", ignore = true)
  @Mapping(target = "product.price", ignore = true)
  @Mapping(target = "product.currency", ignore = true)
  ProductInCartEntity productToAddIntoCartToProductInCartEntity(
      ProductToAddIntoCart productToAddIntoCart);
}
