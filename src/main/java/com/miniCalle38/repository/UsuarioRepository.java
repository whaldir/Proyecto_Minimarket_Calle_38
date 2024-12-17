package com.miniCalle38.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniCalle38.entity.Usuario;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	Usuario findByCorreoAndPassword(String correo, String password);
	List<Usuario> findAll();
	Usuario findById(int id);
	
	List<Usuario> findByEstado(String estado);
	List<Usuario> findByNombreContains(String nombre);
}
