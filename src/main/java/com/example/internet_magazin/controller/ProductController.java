package com.example.internet_magazin.controller;

import com.example.internet_magazin.dto.product.ProductDto;
import com.example.internet_magazin.dto.product.ProductFilterDto;
import com.example.internet_magazin.dto.product.ProductListRepository;
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

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDto dto) {
        ProductDto result = productService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Integer id) {
        ProductDto result = productService.get(id);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/filter")
    public ResponseEntity<?> getFilter(@RequestBody @Valid ProductFilterDto dto) {
        List<ProductDto> result = productService.filter(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/visible/{id}")
    public ResponseEntity<?> visibleProduct(@PathVariable("id")Integer id){
        return ResponseEntity.ok(productService.visible(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam("page") Integer page,
                                    @RequestParam("size") Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 15;
        }

        ProductListRepository productListRepository = new ProductListRepository();
        productListRepository.setDtoList(productService.getAll(page, size));
        productListRepository.setCount(productService.getProductCount());
        return ResponseEntity.ok(productListRepository);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id, @Valid ProductDto dto) {
        ProductDto result = productService.update(dto, id);
        return ResponseEntity.ok(result);
    }
            //------------SOFT DELETE-----------//
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id")Integer id){
        boolean result = productService.softDelete(id);
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
        Object result = productService.getAllAdmin();
        return ResponseEntity.ok(result);
    }

}
