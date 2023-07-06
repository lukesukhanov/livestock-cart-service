package com.livestockshop.cartservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.livestockshop.cartservice.model.dto.ProductInCartForRead;
import com.livestockshop.cartservice.model.dto.ProductToAddIntoCart;
import com.livestockshop.cartservice.model.entity.ProductInCartEntity;
import com.livestockshop.cartservice.model.entity.ProductInCartEntity_;
import com.livestockshop.cartservice.model.mapper.ProductInCartMapper;
import com.livestockshop.cartservice.repository.ProductInCartRepository;

import lombok.RequiredArgsConstructor;

/**
 * The default {@code ProductInCartService} implementation.
 */
@Service
@RequiredArgsConstructor
public class DefaultProductInCartService implements ProductInCartService {

  private final ProductInCartRepository productInCartRepository;

  private final ProductInCartMapper productInCartMapper;

  @Transactional(readOnly = true)
  @Override
  public Page<ProductInCartForRead> getByUserIdWithPaging(Integer page, Integer size, Long userId) {
    Pageable pageable;
    if (page != null && size != null) {
      pageable = PageRequest.of(page, size, Sort.by(ProductInCartEntity_.ID));
    } else {
      pageable = Pageable.unpaged();
    }
    return this.productInCartRepository.findByUserIdWithPaging(userId, pageable);
  }

  @Transactional
  @Override
  public void addProductToCart(ProductToAddIntoCart productToAdd) {
    if (productToAdd.getQuantity().equals(0)) {
      return;
    }
    ProductInCartEntity productInCart = this.productInCartRepository
        .findByUserIdAndProductId(productToAdd.getUserId(), productToAdd.getProductId())
        .orElseGet(
            () -> this.productInCartMapper.productToAddIntoCartToProductInCartEntity(productToAdd));
    if (productInCart.getId() != null) {
      // The product is already in the cart
      productInCart.setQuantity(productInCart.getQuantity() + productToAdd.getQuantity());
      if (productInCart.getQuantity() > 0) {
        // The final quantity > 0
        this.productInCartRepository.save(productInCart);
      } else {
        // The final quantity <= 0
        this.productInCartRepository.deleteById(productInCart.getId());
      }
    } else if (productInCart.getQuantity() > 0) {
      // There is no such product in the cart yet and the required quantity > 0
      this.productInCartRepository.save(productInCart);
    }
  }
}
