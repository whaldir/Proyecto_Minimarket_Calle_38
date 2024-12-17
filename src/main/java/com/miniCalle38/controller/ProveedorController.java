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
import com.miniCalle38.entity.Proveedor;
import com.miniCalle38.repository.DistritoRepository;
import com.miniCalle38.repository.ProveedorRepository;

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {
	
	@Autowired
	ProveedorRepository proveedorRepository;
	
	@Autowired
	DistritoRepository distritoRepository;
	
	@GetMapping("/buscarNombre")
    public String buscarNombre(@RequestParam("nombre") String nombre, Model model) {
		List<Proveedor> listaProveedores = proveedorRepository.findByNombreContains(nombre);
		model.addAttribute("listaProveedores",listaProveedores);
		return "gestionProveedores";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(HttpServletRequest request, @PathVariable("id") int id, Model model) {
		Proveedor proveedor = proveedorRepository.findById(id);
		if (proveedor != null && "Activo".equals(proveedor.getEstado())) {
			proveedor.setEstado("Inactivo");
			proveedorRepository.save(proveedor);
		}
		List<Proveedor> listaProveedores = proveedorRepository.findByEstado("Activo");
		model.addAttribute("listaProveedores", listaProveedores);
		return "gestionProveedores";
	}
	
	@GetMapping("/mostrarEditar/{id}")
	public String mostrarEditar(HttpServletRequest request,@PathVariable("id") int id, Model model) {
		Proveedor objProveedor = proveedorRepository.findById(id);
		model.addAttribute("objProveedor", objProveedor);
		List<Distrito> listaDistritos = distritoRepository.findAll();
		model.addAttribute("listaDistritos",listaDistritos);
		return "editarProveedor";
	}
	
	@PostMapping("/mostrarNuevo")
	public String mostrarNuevo(HttpServletRequest request, Model model) {
		Proveedor objProveedor = new Proveedor();
		model.addAttribute("objProveedor",objProveedor);
		List<Distrito> listaDistritos = distritoRepository.findAll();
		model.addAttribute("listaDistritos",listaDistritos);
		return "nuevoProveedor";
	}
	
	@PostMapping("/registrar")
	public String registrar(HttpServletRequest request, @ModelAttribute("objProveedor")Proveedor objProveedor, Model model) {
		proveedorRepository.save(objProveedor);
		List<Proveedor> listaProveedores = proveedorRepository.findAll();
		model.addAttribute("listaProveedores", listaProveedores);
		List<Distrito> listaDistritos = distritoRepository.findAll();
		model.addAttribute("listaDistritos",listaDistritos);
		return "gestionProveedores";
	}
	
	@PostMapping("/actualizar")
	public String actualizar(HttpServletRequest request, @ModelAttribute("objProveedor")Proveedor objProveedor, Model model) {
		Proveedor objProveedorBD = proveedorRepository.findById(objProveedor.getId());
		objProveedorBD.setNombre(objProveedor.getNombre());
		objProveedorBD.setRuc(objProveedor.getRuc());
		objProveedorBD.setEncargado(objProveedor.getEncargado());
		objProveedorBD.setCelularEncargado(objProveedor.getCelularEncargado());
		objProveedorBD.setDireccion(objProveedor.getDireccion());
		objProveedorBD.setTelefono(objProveedor.getTelefono());
		objProveedorBD.setCorreo(objProveedor.getCorreo());
		objProveedorBD.setEstado(objProveedor.getEstado());
		proveedorRepository.save(objProveedorBD);
		return "redirect:/home/mostrarGestionProveedores";
	}

}
