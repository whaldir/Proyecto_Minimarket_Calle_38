package com.miniCalle38.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniCalle38.entity.CategoriaProducto;
import com.miniCalle38.entity.Producto;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	List<Producto> findAll();
	List<Producto> findByCategoria(CategoriaProducto categoria);
	Producto findByNombre(String nombre);
	Producto findById(int id);
	List<Producto> findByNombreContains (String nombre);
	List<Producto> findByEstado(String estado);
}
