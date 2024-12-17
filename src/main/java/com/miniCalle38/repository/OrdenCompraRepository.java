package com.miniCalle38.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniCalle38.entity.OrdenCompra;
import com.miniCalle38.entity.Proveedor;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer>{
	List<OrdenCompra> findAll();
	List<OrdenCompra> findByFechaRegistroBetween(Date fechaInicio, Date fechaFin);
	OrdenCompra findById(int id);
	List<OrdenCompra> findByProveedor (Proveedor proveedor);
	List<OrdenCompra> findByEstado(String estado);

}
