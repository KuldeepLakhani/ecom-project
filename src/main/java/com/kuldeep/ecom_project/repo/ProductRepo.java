package com.kuldeep.ecom_project.repo;

import com.kuldeep.ecom_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo  extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE "+
            "LOWER(p.name) LIKE LOWER(CONCAT('%' ,:keyword, '%')) OR "+
            "LOWER(p.description) LIKE LOWER(CONCAT('%' ,:keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%' ,:keyword ,'%')) OR "+
            "LOWER(p.category) LIKE LOWER(CONCAT('%' ,:keyword, '%'))")

    List<Product> searchProducts(String keyword);

//  public List<Product> findByName(String name);
//  public  List<Product> findByNameLike(String name);
//we are not using it because by this we can only do specific queries
    //but if we want some customized querry we can use "JPQL"


    /*JPQL = J.P.A query language where we use class name for query
    rather than table column name like sql*/
//@Query()
//    List<Product> searchProducts(String keyword);
}
