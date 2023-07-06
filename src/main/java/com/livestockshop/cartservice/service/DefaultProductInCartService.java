package com.livestockshop.cartservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.livestockshop.cartservice.model.dto.ProductInCartForRead;
import com.livestockshop.cartservice.model.entity.ProductInCartEntity_;
import com.livestockshop.cartservice.repository.ProductInCartRepository;

import lombok.RequiredArgsConstructor;

/**
 * The default {@code ProductInCartService} implementation.
 */
@Service
@RequiredArgsConstructor
public class DefaultProductInCartService implements ProductInCartService {

  private final ProductInCartRepository productInCartRepository;

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
}
