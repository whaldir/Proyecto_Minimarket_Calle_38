package com.miniCalle38.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miniCalle38.entity.CategoriaProducto;

@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Integer> {
	List<CategoriaProducto> findByNombre(String nombre);
	CategoriaProducto findById(int id);
}
