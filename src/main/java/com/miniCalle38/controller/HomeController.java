package com.miniCalle38.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.miniCalle38.entity.CategoriaProducto;
import com.miniCalle38.entity.Distrito;
import com.miniCalle38.entity.OrdenCompra;
import com.miniCalle38.entity.Producto;
import com.miniCalle38.entity.Proveedor;
import com.miniCalle38.entity.Usuario;
import com.miniCalle38.repository.CategoriaProductoRepository;
import com.miniCalle38.repository.DistritoRepository;
import com.miniCalle38.repository.OrdenCompraRepository;
import com.miniCalle38.repository.ProductoRepository;
import com.miniCalle38.repository.ProveedorRepository;
import com.miniCalle38.repository.UsuarioRepository;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	CategoriaProductoRepository categoriaProductoRepository;
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	ProveedorRepository proveedorRepository;
	
	@Autowired
	DistritoRepository distritoRepository;
	
	@Autowired
	OrdenCompraRepository ordenCompraRepository;
	
	@RequestMapping(value="/validarUsuario", method=RequestMethod.POST)
	public String validarUsuario(HttpServletRequest request, @RequestParam("correo") String correo, @RequestParam("password") String password) {
		Usuario objUsuario = usuarioRepository.findByCorreoAndPassword(correo, password);
		if (objUsuario!=null) {
			HttpSession sesion = request.getSession();
			return "principal";
		}
		else {
			return "index";
		}
	}
	
	@GetMapping("/mostrarGestionProductos")
	public String mostrarGestionProductos(HttpServletRequest request, Model model) {
		List<Producto> listaProductos = productoRepository.findByEstado("Activo");
		List<CategoriaProducto> listaCategoriaProductos = categoriaProductoRepository.findAll();
		List<Proveedor> listaProveedores = proveedorRepository.findAll();
		model.addAttribute("listaProductos", listaProductos);
		model.addAttribute("listaCategoriaProductos",listaCategoriaProductos);
		model.addAttribute("listaProveedores",listaProveedores);
		return "gestionProductos";
	}
	
	@GetMapping("/mostrarGestionUsuarios")
	public String mostrarGestionUsuarios(HttpServletRequest request, Model model) {
	    List<Usuario> listaUsuarios = usuarioRepository.findByEstado("Activo");
	    List<Distrito> listaDistritos = distritoRepository.findAll();
	    model.addAttribute("listaUsuarios", listaUsuarios);
	    model.addAttribute("listaDistritos",listaDistritos);
	    return "gestionUsuarios";
	}
	
	@GetMapping("/mostrarGestionProveedores")
	public String mostrarGestionProveedores(HttpServletRequest request, Model model) {
		List<Proveedor> listaProveedores = proveedorRepository.findByEstado("Activo");
		List<Distrito> listaDistritos = distritoRepository.findAll();
		model.addAttribute("listaProveedores", listaProveedores);
		model.addAttribute("listaDistritos",listaDistritos);
		return "gestionProveedores";
	}
	
	@GetMapping("/mostrarGestionOrdenesCompra")
	public String mostrarGestionOrdenesCompra(HttpServletRequest request, Model model) {
		List<OrdenCompra> listaOrdenesCompra = ordenCompraRepository.findAll();
		List<Proveedor> listaProveedores = proveedorRepository.findAll();
		model.addAttribute("listaProveedores",listaProveedores);
		model.addAttribute("listaOrdenesCompra", listaOrdenesCompra);
		return "gestionOrdenCompra";
	}
	
	@GetMapping(value="/cerrarSesion")
	public String cerrarSesion(HttpServletRequest request, Model model) {
		request.getSession(false).invalidate();
		return "index";
	}
	
	@GetMapping("/mostrarPrincipal") 
	public String mostrarPrincipal(HttpServletRequest request) {
		return "principal";
	}
}
