package com.samuel.store.product.controller;

import com.samuel.store.product.entity.Category;
import com.samuel.store.product.entity.Product;
import com.samuel.store.product.service.ProductService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping(value = "/kari")
    public String getKari(){
        return "Hola Kari y Pablito =)";
    }
    /*@GetMapping
    public ResponseEntity<List<Product>> listProducts(){
        List<Product> products = productService.listAllProduct();
        if (products.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }*/
    @GetMapping
    public ResponseEntity<List<Product>> listProductsByCat(@RequestParam(name="categoryId", required = false) Long categoryId){
        List<Product> products = new ArrayList<>();
        if (null == categoryId) {
            products = productService.listAllProduct();
            if (products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if(products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long idProduct){
        Product product = productService.getProduct(idProduct);

        if (product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product productCreated = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        product.setId(id);
        Product productUpdated = productService.updateProduct(product);
        if (productUpdated==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productUpdated);
    }
    @DeleteMapping (value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product deletedProduct = productService.deleteProduct(id);

        if (deletedProduct==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedProduct);
    }

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable("id") Long id,
                                               @RequestParam(value = "quantity", required = true) Double quantity){
        Product updatedProduct = productService.updateStock(id, quantity);
        if (updatedProduct==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedProduct);


    }
}
