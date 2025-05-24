package com.Proyecto.La_Tertulia.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.service.ProductService;
import com.Proyecto.La_Tertulia.service.VentaService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SalesController {
    @Autowired
    private VentaService ventaService;
    @Autowired
    private ProductService productoService;

    @GetMapping("/manageSales")
    public String chargeSales(Model model, HttpSession session) {
        List<VentaDTO> ventas = ventaService.findAllSales();
        model.addAttribute("Ventas", ventas);
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuarioDTO");
        model.addAttribute("usuarioDTO", usuarioDTO);
        return "SalesManagement";
    }

    @PostMapping("/searchSale")
    public String searchSale(@RequestParam("fechaInicial") LocalDate initialDate,
            @RequestParam("fechaFinal") LocalDate finaldate, Model model) {
        List<VentaDTO> ventas = ventaService.obtenerVentasEntreFechas(initialDate, finaldate);
        if (ventas.isEmpty()) {
            ventas = ventaService.findAllSales();
            model.addAttribute("error", "No hay ventas registradas en ese rango de fechas");
        } else {
            model.addAttribute("success", "Ventas encontradas");
        }
        model.addAttribute("Ventas", ventas);
        return "SalesManagement";
    }

    @PostMapping("/deleteSale")
    public String deleteSale(@RequestParam("id") Long id) {
        // Logica para eliminar venta, deben de añadir la cantidad substraida del
        // producto

        return "redirect:/manageSales";
    }

    @GetMapping("/addSale")
    public String addPerson(Model model) {
        List<String> categorias = productoService.productCategories();
        model.addAttribute("categorias", categorias);
        model.addAttribute("VentaDTO", new VentaDTO());
        return "SaleRegistration";
    }

    @PostMapping("/SaleRegistration")
    public String saleRegistration(@ModelAttribute VentaDTO venta, @ModelAttribute("categoria") String tipoProd,
            @ModelAttribute("producto") String nombreProd, Model model) {
        // registrar la venta, crear una nueva ventaDTO y pasarle por parametros lo que
        // se optiene de "venta"
        model.addAttribute("VentaDTO", new VentaDTO());
        return "redirect:/SaleRegistration";
    }



//Para cargar lo que se necesita para agregar venta   @GetMapping("/productos")

    @GetMapping("/productos")
    public List<String> obtenerProductos(@RequestParam String categoria) {
        return productoService.productsByCategory(categoria);
    }

    @GetMapping("/presentaciones")
    public List<String> obtenerPresentaciones(@RequestParam String producto) {
        return productoService.presentationByProductName(producto);
    }

    @GetMapping("/precio")
    public Double obtainProductPrice(@RequestParam String presentacion, @RequestParam String producto) {
        return productoService.productPrice(producto, presentacion);
    }

}
