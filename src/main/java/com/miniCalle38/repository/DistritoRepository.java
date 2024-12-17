package com.miniCalle38.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniCalle38.entity.Distrito;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, Integer> {
	List<Distrito> findByNombre(String nombre);
	Distrito findById(int id);
}
