package com.example.internet_magazin.controller;

import com.example.internet_magazin.dto.product.ProductCreateDto;
import com.example.internet_magazin.dto.product.ProductDto;
import com.example.internet_magazin.dto.product.ProductFilterDto;
import com.example.internet_magazin.dto.product.ProductListRepository;
import com.example.internet_magazin.entity.Product;
import com.example.internet_magazin.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/secured/create")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductCreateDto dto) {
        ProductDto result = productService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Integer id) {
        ProductDto result = productService.get(id);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/filter")
    public ResponseEntity<?> getFilter(@RequestBody ProductFilterDto dto) {
        List<ProductDto> result = productService.filter(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/visible/{id}")
    public ResponseEntity<?> visibleProduct(@PathVariable("id")Integer id, Product product){
        return ResponseEntity.ok(productService.visible(id, product));
    }
    @PutMapping("/secured/block/{id}")
    public ResponseEntity<?> hiddenProduct(@PathVariable("id") Integer id){
        boolean result = productService.block(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam("page") Integer page,
                                    @RequestParam("size") Integer size) {
        ProductListRepository productListRepository = new ProductListRepository();
        productListRepository.setDtoList(productService.getAll(page, size));
        productListRepository.setCount(productService.getProductCount());
        return ResponseEntity.ok(productListRepository);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id,
                                           @RequestBody ProductDto dto) {
        String result = productService.update(id, dto);
        return ResponseEntity.ok(result);
    }
            //------------SOFT DELETE-----------//
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id")Integer id){
        boolean result = productService.    softDelete(id);
        return ResponseEntity.ok(result);
    }
            // -------------HARD DELETE----------//
    @DeleteMapping("/deleteEntity/{id}")
    public ResponseEntity<?> hardDelete(@PathVariable("id") Integer id){
        String result = productService.hardDelete(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/secured/getAll")
    public ResponseEntity<?>getById(){
        String result = productService.getAllAdmin();
        return ResponseEntity.ok(result);
    }

}
