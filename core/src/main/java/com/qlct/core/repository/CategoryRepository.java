package com.qlct.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.qlct.core.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	@Query(value = "SELECT * FROM categories WHERE c_id = :categoryId", nativeQuery = true)
	List<Category> findBycategoryId(@Param("categoryId") Long categoryId);
	
	// Lấy thông tin của 1 record
	@Query(value = "SELECT * FROM categories WHERE c_id = :categoryId", nativeQuery = true)
	Category getcategoryById(@Param("categoryId") Long categoryId);
}
