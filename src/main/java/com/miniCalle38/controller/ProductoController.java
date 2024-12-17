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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.miniCalle38.entity.CategoriaProducto;
import com.miniCalle38.entity.Producto;
import com.miniCalle38.entity.Proveedor;
import com.miniCalle38.repository.CategoriaProductoRepository;
import com.miniCalle38.repository.ProductoRepository;
import com.miniCalle38.repository.ProveedorRepository;

@Controller
@RequestMapping("/producto")
public class ProductoController {
	
	@Autowired
	CategoriaProductoRepository categoriaProductoRepository;
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	ProveedorRepository proveedorRepository;
	
	@GetMapping("/buscarNombre")
    public String buscarNombre(@RequestParam("nombre") String nombre, Model model) {
		List<Producto> listaProductos = productoRepository.findByNombreContains(nombre);
		model.addAttribute("listaProductos",listaProductos);
		return "gestionProductos";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(HttpServletRequest request, @PathVariable("id") int id, Model model) {
		Producto producto = productoRepository.findById(id);
		if(producto != null && "Activo".equals(producto.getEstado())) {
			producto.setEstado("Inactivo");
			productoRepository.save(producto);
		}
		List<Producto> listaProductos = productoRepository.findByEstado("Activo");
		model.addAttribute("listaProductos",listaProductos);
		return "gestionProductos";
	}
	
	@GetMapping("/mostrarEditar/{id}")
	public String mostrarEditar(HttpServletRequest request, @PathVariable("id") int id, Model model) {
		Producto objProducto = productoRepository.findById(id);
		model.addAttribute("objProducto",objProducto);
		List<CategoriaProducto> listaCategoriaProductos = categoriaProductoRepository.findAll();
		List<Proveedor> listaProveedores = proveedorRepository.findAll();
		model.addAttribute("listaCategoriaProductos",listaCategoriaProductos);
		model.addAttribute("listaProveedores", listaProveedores);
		return "editarProducto";
	}
	
	@PostMapping("/mostrarNuevo")
	public String mostrarNuevo(HttpServletRequest request, Model model) {
		Producto objProducto = new Producto();
		List<CategoriaProducto> listaCategoriaProductos = categoriaProductoRepository.findAll();
		List<Proveedor> listaProveedores = proveedorRepository.findAll();
		model.addAttribute("listaCategoriaProductos",listaCategoriaProductos);
		model.addAttribute("listaProveedores",listaProveedores);
		model.addAttribute("objProducto",objProducto);
		return "nuevoProducto";
	}
	
	@RequestMapping(value="/registrar", method=RequestMethod.POST, params="grabar")
	public String registrar(HttpServletRequest request, @ModelAttribute("objProducto")Producto objProducto, Model model) {
		Producto objProductoBD = productoRepository.findByNombre(objProducto.getNombre());
		if (objProductoBD!=null) {
			model.addAttribute("mensaje", "El Producto ya se encuentra registrado");
			model.addAttribute("objProducto",objProducto);
			//List<CategoriaProducto> listaCategoriaProductos = categoriaProductoRepository.findByNombre("Activo");
			List<CategoriaProducto> listaCategoriaProductos = categoriaProductoRepository.findAll();
			List<Proveedor> listaProveedores = proveedorRepository.findAll();
			model.addAttribute("listaCategoriaProductos",listaCategoriaProductos);
			model.addAttribute("listaProveedores",listaProveedores);
			return "nuevoProducto";
		}
		else {
			productoRepository.save(objProducto);
			return "redirect:/home/mostrarGestionProductos";
		}
	}
	
	@RequestMapping(value="/registrar", method=RequestMethod.POST, params="cancelar")
	public String cancelar(HttpServletRequest request, @ModelAttribute("objProducto")Producto objProducto, Model model) {
		return "redirect:/home/mostrarGestionProductos";
		
	}
	
	@PostMapping("/actualizar")
	public String actualizar(HttpServletRequest request, @ModelAttribute("objProducto")Producto objProducto, Model model) {
		Producto objProductoBD = productoRepository.findById(objProducto.getId());
		objProductoBD.setNombre(objProducto.getNombre());
		objProductoBD.setDescripcion(objProducto.getDescripcion());
		objProductoBD.setPrecioVenta(objProducto.getPrecioVenta());
		objProductoBD.setPrecioCompra(objProducto.getPrecioCompra());
		objProductoBD.setStock(objProducto.getStock());
		objProductoBD.setCategoria(objProducto.getCategoria());
		objProductoBD.setProveedor(objProducto.getProveedor());
		objProductoBD.setEstado(objProducto.getEstado());
		productoRepository.save(objProductoBD);
		return "redirect:/home/mostrarGestionProductos";
	}

}
