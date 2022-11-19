package com.example.internet_magazin.service;

import com.example.internet_magazin.dto.product.ProductDto;
import com.example.internet_magazin.dto.product.ProductFilterDto;
import com.example.internet_magazin.entity.Product;
import com.example.internet_magazin.entity.Profile;
import com.example.internet_magazin.exception.BadRequest;
import com.example.internet_magazin.repository.ProductRepository;
import com.example.internet_magazin.type.ProductStatus;
import com.example.internet_magazin.type.Role;
import com.example.internet_magazin.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProfileService profileService;

    public ProductService(ProductRepository productRepository, ProfileService profileService) {
        this.productRepository = productRepository;
        this.profileService = profileService;
    }

    public Product getEntity(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BadRequest("Product not found!!");
        }
        return optional.get();
    }

    public ProductDto create(ProductDto dto) {
        Product product = new Product();
        product.setCreatedAt(LocalDateTime.now());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setId(dto.getId());
        product.setStatus(ProductStatus.CREATED);
        productRepository.save(product);
        return convertToDto(product, new ProductDto());
    }

    public ProductDto get(Integer id) {
        Product product = getEntity(id);
        if (!product.getVisible()) {
            throw new BadRequest("Product has been deleted");
        }
        return convertToDto(product, new ProductDto());
    }

    public List<ProductDto> filter(ProductFilterDto dto) {
        String sortBy = ("created_at");
        if (dto.getSortBy() != null) {
            sortBy = dto.getSortBy();
        }
        PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirection(), sortBy);
        List<Predicate> predicates = new ArrayList<>();
        Specification<Product> specification = ((root, query, criteriaBuilder) -> {
            if (dto.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + dto.getName() + "%"));
            }
            if (dto.getDescription() != null) {
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + dto.getDescription() + "%"));
            }
            if (dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() != null) {
                predicates.add(criteriaBuilder.between(root.get("createdAt"),
                        dto.getMinCreatedDate(), dto.getMaxCreatedDate()));
            }
            if (dto.getMinCreatedDate() == null && dto.getMaxCreatedDate() != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("createdAt"),
                        dto.getMinCreatedDate()));
            }
            if (dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() == null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("createdAt"),
                        dto.getMaxCreatedDate()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });
        Page<Product> page = productRepository.findAll(specification, pageRequest);

        return page.stream().map(product -> convertToDto(product, new ProductDto())).collect(Collectors.toList());
    }

    public List<ProductDto> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> products = productRepository.page(pageRequest);
        List<ProductDto> productList = new ArrayList<>();
        for (Product product : products) {
            if (product.getStatus().equals(ProductStatus.PUBLISHED)) {
                productList.add(convertToDto(product, new ProductDto()));
            }
        }
        return productList;
    }

    public Long getProductCount() {
        return productRepository.productCount();
    }


    public ProductDto convertToDto(Product product, ProductDto dto) {
        dto.setRate(product.getRate());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setVisible(product.getVisible());
        return dto;
    }


    public ProductDto update(ProductDto dto, Integer id) {
        Product product = getEntity(id);
        product.setPrice(dto.getPrice());
        product.setUpdatedAt(LocalDateTime.now());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setId(dto.getId());
        productRepository.save(product);
        return convertToDto(product, new ProductDto());
    }


    public boolean softDelete(Integer id) {
        Product product = getEntity(id);
        product.setDeletedAt(LocalDateTime.now());
        product.setStatus(ProductStatus.BLOCKED);
        productRepository.save(product);
        return true;
    }


    public String hardDelete(Integer id) {
        Product product = getEntity(id);
        product.setDeletedAt(LocalDateTime.now());
        product.setStatus(ProductStatus.BLOCKED);
        productRepository.delete(product);
        return "Product deleted !";
    }

    public Object visible(Integer id) {
        if (!profileService.isAdmin()) {
            throw new BadRequest("Not found :(");
        }
        Product product = getEntity(id);
        product.setStatus(ProductStatus.PUBLISHED);
        product.setVisible(true);
        productRepository.save(product);
        return product;
    }

    public Object getAllAdmin() {
        if (profileService.isAdmin()) {
            return productRepository.findAll();
        }
        throw new BadRequest("Not found");
    }
}
