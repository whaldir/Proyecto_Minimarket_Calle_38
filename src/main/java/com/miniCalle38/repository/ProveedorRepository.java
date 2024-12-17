package com.miniCalle38.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniCalle38.entity.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor,Integer>{
	List<Proveedor> findAll();
	Proveedor findById(int id);
	//void deleteById(int id);
	List<Proveedor> findByEstado(String estado);
	List<Proveedor> findByNombreContains (String nombre);
	List<Proveedor> findByNombre(String nombre);
}
