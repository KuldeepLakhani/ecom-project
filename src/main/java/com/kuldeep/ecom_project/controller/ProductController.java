package com.kuldeep.ecom_project.controller;


import com.kuldeep.ecom_project.model.Product;
import com.kuldeep.ecom_project.repo.ProductRepo;
import com.kuldeep.ecom_project.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepo repo;


@GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){

    return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
}

@GetMapping("product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
    Product product = service.getProductById(id);

    if (product == null){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    else  {
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}

//@PostMapping("/Product")
//    public Product addProduct(@RequestBody Product product){
//    return service.addProduct(product);
//}

//@PutMapping("product")
//    public Product updateProduct(@RequestBody Product product){
//    return service.updateProduct(product);
//}

@DeleteMapping("product/{id}")
    public void deleteProduct(@PathVariable int id){
    service.deleteProductById(id);
}

//    @PostMapping("/product")
//    public ResponseEntity<?> addProduct(
//            @RequestPart("product") Product product,
//            @RequestPart("imageFile") MultipartFile imageFile
//    ) {
//        try {
//            System.out.println("ok");
//            Product product1 = service.addProduct(product, imageFile);
//            return new ResponseEntity<>(product1, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
@PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> addProduct(
        @RequestPart("product") Product product,
        @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
) throws IOException {

    System.out.println("controller hit");

    if (imageFile != null && !imageFile.isEmpty()) {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
    }

    return ResponseEntity.ok(repo.save(product));
}




    @GetMapping("/produt/{productId}/image")
public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){

    Product product = service.getProductById(productId);
    byte[] imageFile = product.getImageDate();
    return  ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
}
}
