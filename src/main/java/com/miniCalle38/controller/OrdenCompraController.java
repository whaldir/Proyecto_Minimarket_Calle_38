package com.miniCalle38.controller;

import java.sql.Date;
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

import com.miniCalle38.entity.DetalleCompra;
import com.miniCalle38.entity.OrdenCompra;
import com.miniCalle38.entity.Producto;
import com.miniCalle38.entity.Proveedor;
import com.miniCalle38.repository.DetalleCompraRepository;
import com.miniCalle38.repository.OrdenCompraRepository;
import com.miniCalle38.repository.ProductoRepository;
import com.miniCalle38.repository.ProveedorRepository;



@Controller
@RequestMapping("/ordenCompra")
public class OrdenCompraController {

	@Autowired
	OrdenCompraRepository ordenCompraRepository;
	
	@Autowired
	DetalleCompraRepository detalleCompraRepository;
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	ProveedorRepository proveedorRepository;
	
	@RequestMapping(value="/buscarOrdenCompra", method=RequestMethod.GET)
	public String buscarProveedor(HttpServletRequest request, @RequestParam(value="fechaInicio") Date fechaInicio,@RequestParam(value="fechaFin") Date fechaFin, Model model) {
		List<OrdenCompra> listaOrdenesCompra = ordenCompraRepository.findByFechaRegistroBetween(fechaInicio, fechaFin);
		model.addAttribute("listaOrdenesCompra",listaOrdenesCompra);
		return "gestionOrdenCompra";
	}
	
	@RequestMapping(value="/buscarNombre", method=RequestMethod.GET)
    public String buscarNombre(@RequestParam("idProveedor") int idProveedor, Model model) {
		Proveedor objProveedor = proveedorRepository.findById(idProveedor);
		List<OrdenCompra> listaOrdenesCompra = ordenCompraRepository.findByProveedor(objProveedor);
		model.addAttribute("listaOrdenesCompra",listaOrdenesCompra);
		List<Proveedor> listaProveedores = proveedorRepository.findAll();
		model.addAttribute("listaProveedores",listaProveedores);
		model.addAttribute("idProveedorSeleccionada", idProveedor);
		return "gestionOrdenCompra";
	}
	
	@GetMapping("/ver/{id}")
	public String ver(HttpServletRequest request, @PathVariable("id")int id, Model model) {
		OrdenCompra objOrdenCompraBD = ordenCompraRepository.findById(id);
		List<DetalleCompra> listaDetalleCompra = detalleCompraRepository.findByOrdenCompra(objOrdenCompraBD);
		List<Proveedor> listaProveedores = proveedorRepository.findAll();
		model.addAttribute("ordenCompra",objOrdenCompraBD);
		model.addAttribute("listaProveedores",listaProveedores);
		model.addAttribute("listaDetalleCompra",listaDetalleCompra);
		return "VerDetalleCompra";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(HttpServletRequest request, @PathVariable("id")int id) {
		OrdenCompra ordenCompra = ordenCompraRepository.findById(id);
		String mensaje;
		if (ordenCompra != null && "Pendiente de Aprobacion".equals(ordenCompra.getEstado())) {
			ordenCompra.setEstado("Aprobado");
			ordenCompraRepository.save(ordenCompra);
			mensaje = "La orden de compra fue aprobada con exito";
		}
		return "redirect:/home/mostrarGestionOrdenesCompra";
	}
	
	@PostMapping("/mostrarNuevo")
	public String mostrarNuevo(HttpServletRequest request,Model model) {
		OrdenCompra ordenCompra = new OrdenCompra();
		model.addAttribute("objOrdenCompra", ordenCompra);
		List<Proveedor> listaProveedores = proveedorRepository.findAll();
		model.addAttribute("listaProveedores",listaProveedores);
		return "nuevaOrdenCompra";
	}
	
	@RequestMapping(value="/guardar", method=RequestMethod.GET, params="grabarCabecera")
	public String guardarCabecera(HttpServletRequest request, @ModelAttribute("objOrdenCompra")OrdenCompra ordenCompra, Model model) {
		ordenCompra.setEstado("Pendiente de Aprobacion");
		ordenCompraRepository.save(ordenCompra);
		List<Proveedor> listaProveedores = proveedorRepository.findAll();
		model.addAttribute("listaProveedores",listaProveedores);
		model.addAttribute("objOrdenCompra", ordenCompra);
		List<Producto> listaProductos = productoRepository.findAll();
		model.addAttribute("listaProductos",listaProductos);
		return "nuevaOrdenCompra";
	}
	
	@RequestMapping(value="/guardar", method=RequestMethod.GET, params="grabarDetalle")
	public String guardarDetalle(HttpServletRequest request, @ModelAttribute("objOrdenCompra")OrdenCompra ordenCompra, @RequestParam("idProducto")int idProducto, @RequestParam("cantidad")int cantidad, @RequestParam("precioUnitario")double precioUnitario, Model model) {
		OrdenCompra ordenCompraBD = ordenCompraRepository.findById(ordenCompra.getId());
		Producto objProductoBD = productoRepository.findById(idProducto);
		DetalleCompra detalleCompra = new DetalleCompra();
		detalleCompra.setCantidad(cantidad);
		detalleCompra.setPrecioUnitario(precioUnitario);
		detalleCompra.setProducto(objProductoBD);
		detalleCompra.setOrdenCompra(ordenCompraBD);
		detalleCompraRepository.save(detalleCompra);
		List<DetalleCompra> listaDetallesCompra = detalleCompraRepository.findByOrdenCompra(ordenCompraBD);
		model.addAttribute("listaDetallesCompra",listaDetallesCompra);
		List<Proveedor> listaProveedores = proveedorRepository.findAll();
		model.addAttribute("listaProveedores",listaProveedores);
		model.addAttribute("objOrdenCompra",ordenCompraBD);
		List<Producto> listaProductos = productoRepository.findAll();
		model.addAttribute("listaProductos",listaProductos);
		return "nuevaOrdenCompra";
	}
}
