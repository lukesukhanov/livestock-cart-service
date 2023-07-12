package com.livestock.cartservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.livestock.cartservice.model.dto.ProductInCartForRead;
import com.livestock.cartservice.model.dto.ProductToAddIntoCart;
import com.livestock.cartservice.model.entity.ProductInCartEntity;
import com.livestock.cartservice.model.entity.ProductInCartEntity_;
import com.livestock.cartservice.model.mapper.ProductInCartMapper;
import com.livestock.cartservice.repository.ProductInCartRepository;

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
  public Page<ProductInCartForRead> getByUserEmailWithPaging(Integer page, Integer size,
      String userEmail) {
    Pageable pageable;
    if (page != null && size != null) {
      pageable = PageRequest.of(page, size, Sort.by(ProductInCartEntity_.ID));
    } else {
      pageable = Pageable.unpaged();
    }
    return this.productInCartRepository.findByUserEmailWithPaging(userEmail, pageable);
  }

  @Transactional
  @Override
  public void addProductToCart(ProductToAddIntoCart productToAdd) {
    if (productToAdd.getQuantity().equals(0)) {
      return;
    }
    ProductInCartEntity productInCart = this.productInCartRepository
        .findByUserEmailAndProductId(productToAdd.getUserEmail(), productToAdd.getProductId())
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

  @Transactional
  @Override
  public void removeProductFromCart(Long productId, String userEmail) {
    this.productInCartRepository.deleteByProductIdAndUserEmail(productId, userEmail);
  }

  @Transactional
  @Override
  public void clearCartByUserEmail(String userEmail) {
    this.productInCartRepository.deleteByUserEmail(userEmail);
  }
}
