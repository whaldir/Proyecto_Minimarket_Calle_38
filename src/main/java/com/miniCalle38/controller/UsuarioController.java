package com.miniCalle38.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miniCalle38.entity.Distrito;
import com.miniCalle38.entity.Usuario;
import com.miniCalle38.repository.DistritoRepository;
import com.miniCalle38.repository.UsuarioRepository;



@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	DistritoRepository distritoRepository;
	
	@GetMapping("/buscarNombre")
    public String buscarNombre(@RequestParam("nombre") String nombre, Model model) {
        List<Usuario> listaUsuarios = usuarioRepository.findByNombreContains(nombre);
        model.addAttribute("listaUsuarios", listaUsuarios);
        return "gestionUsuarios";
    }
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") int id, Model model) {
	    Usuario usuario = usuarioRepository.findById(id);
	    if (usuario != null && "Activo".equals(usuario.getEstado())) {
	        usuario.setEstado("Inactivo");
	        usuarioRepository.save(usuario);
	    }
	    
	    List<Usuario> listaUsuarios = usuarioRepository.findByEstado("Activo");
	    model.addAttribute("listaUsuarios", listaUsuarios);
	    return "gestionUsuarios";
	}
	
	@GetMapping("/mostrarEditar/{id}")
	public String mostrarEditar(HttpServletRequest request,@PathVariable("id") int id, Model model) {
		Usuario objUsuario = usuarioRepository.findById(id);
		model.addAttribute("objUsuario", objUsuario);
		List<Distrito> listaDistritos = distritoRepository.findAll();
		model.addAttribute("listaDistritos",listaDistritos);
		return "editarUsuario";
	}
	
	@GetMapping("/mostrarNuevo")
	public String mostrarNuevo(HttpServletRequest request, Model model) {
		Usuario objUsuario = new Usuario();
		List<Distrito> listaDistritos = distritoRepository.findAll();
		model.addAttribute("listaDistritos",listaDistritos);
		model.addAttribute("objUsuario",objUsuario);
		return "nuevoUsuario";
	}
	
	@PostMapping("/registrar")
	public String registrar(HttpServletRequest request, @ModelAttribute("objUsuario")Usuario objUsuario, Model model) {
		usuarioRepository.save(objUsuario);
		List<Usuario> listaUsuarios = usuarioRepository.findAll();
		model.addAttribute("listaUsuarios", listaUsuarios);
		List<Distrito> listaDistritos = distritoRepository.findAll();
		model.addAttribute("listaDistritos",listaDistritos);
		return "index";
	}
	
	@PostMapping("/actualizar")
	public String actualizar(HttpServletRequest request, @ModelAttribute("objUsuario")Usuario objUsuario, Model model) {
		Usuario objUsuarioBD = usuarioRepository.findById(objUsuario.getId());
		objUsuarioBD.setNombre(objUsuario.getNombre());
		objUsuarioBD.setApellidoPaterno(objUsuario.getApellidoPaterno());
		objUsuarioBD.setApellidoMaterno(objUsuario.getApellidoMaterno());
		objUsuarioBD.setDni(objUsuario.getDni());
		objUsuarioBD.setTelefono(objUsuario.getTelefono());
		objUsuarioBD.setDireccion(objUsuario.getDireccion());
		objUsuarioBD.setCorreo(objUsuario.getCorreo());
		objUsuarioBD.setPassword(objUsuario.getPassword());
		objUsuarioBD.setRol(objUsuario.getRol());
		objUsuarioBD.setEstado(objUsuario.getEstado());
		usuarioRepository.save(objUsuarioBD);
		return "redirect:/home/mostrarGestionUsuarios";
	}
}
