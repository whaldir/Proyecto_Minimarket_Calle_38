package com.miniCalle38.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniCalle38.entity.DetalleCompra;
import com.miniCalle38.entity.OrdenCompra;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Integer>{
	List<DetalleCompra> findByOrdenCompra(OrdenCompra ordenCompra);

}
