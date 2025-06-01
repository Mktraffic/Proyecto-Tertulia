package com.Proyecto.La_Tertulia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Proyecto.La_Tertulia.dto.CompraDTO;
import com.Proyecto.La_Tertulia.dto.DetalleCompraDTO;
import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;
import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.service.CompraService;
import com.Proyecto.La_Tertulia.service.ProductService;
import com.Proyecto.La_Tertulia.service.UsuarioService;
import com.Proyecto.La_Tertulia.service.VentaService;
import jakarta.servlet.http.HttpSession;

@Controller
public class SalesController {
    @Autowired
    private VentaService ventaService;
    @Autowired
    private CompraService compraService;
    @Autowired
    private ProductService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/manageSales")
    public String chargeSalesToManage(Model model, HttpSession session) {
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuario");
        if (usuarioDTO == null) {
            return "redirect:/login";
        }
        List<VentaDTO> ventas = ventaService.findAllSales();
        model.addAttribute("Ventas", ventas);
        model.addAttribute("usuario", usuarioDTO);
        model.addAttribute("rol", usuarioDTO.getRol().getNombreRol().trim());
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
        return "redirect:/manageSales";
    }

    @PostMapping("/deleteSale")
    public String deleteSale(@RequestParam("id") Long id) {
        // Logica para eliminar venta, deben de añadir la cantidad substraida del
        // producto

        return "redirect:/manageSales";
    }

    @GetMapping("/addSale")
    public String showFormAddSale(Model model) {
        List<String> categorias = productoService.productCategories();
        model.addAttribute("categorias", categorias);
        model.addAttribute("ventaDTO", new VentaDTO());
        model.addAttribute("productos", new ArrayList<>());
        model.addAttribute("presentaciones", new ArrayList<>());
        return "SaleRegistration";
    }

    @PostMapping("/SaleRegistration")
    public String saleRegistration(@ModelAttribute VentaDTO venta, @ModelAttribute("categoria") String tipoProd,
            @ModelAttribute("producto") String nombreProd, Model model) {
                venta();
                compra();
        // registrar la venta, crear una nueva ventaDTO y pasarle por parametros lo que
        // se optiene de "venta"
        //Revisar como van a agregar la venta de ese usuario a su arreglo
        model.addAttribute("ventaDTO", new VentaDTO());
        return "redirect:/SaleRegistration";
    }

    public void venta(){
        System.out.println("registrando venta");
        VentaDTO venta = new VentaDTO();
        venta.setId(null);
        venta.setFechaVenta(LocalDate.now());
        venta.setVendedor(usuarioService.findUserByName("mktraffic"));
        venta.setTipoDocumentoCliente("cedula de ciudadania");
        venta.setNumeroDocumentoCliente(1000000000L);
        System.out.println("vamos a la lista de detalles");
        List<DetalleVentaDTO> detalle = new ArrayList<>();
        ProductDTO productDTO = productoService.findById(1L).orElseThrow(null);
        ProductDTO productDTO1 = productoService.findById(2L).orElseThrow(null);
        detalle.add(new DetalleVentaDTO(null, venta, productDTO, productDTO.getName(), productDTO.getPrice(), 3 , productDTO.getPrice()*3));
        detalle.add(new DetalleVentaDTO(null, venta, productDTO1, productDTO1.getName(), productDTO1.getPrice(), 3 , productDTO1.getPrice()*3));
        venta.setDetalles(detalle);
        double total = detalle.stream().mapToDouble(DetalleVentaDTO::getSubtotal).sum();
        venta.setTotalVenta(total);
        System.out.println("total de la venta: " + total);
        ventaService.registrarVenta(venta);
        System.out.println("venta registrada exitosamente");
    }
      public void compra(){
        System.out.println("registrando compra");
        CompraDTO compra = new CompraDTO();
        compra.setId(null);
        compra.setFechaCompra(LocalDate.now());
        compra.setVendedor(usuarioService.findUserByName("mktraffic"));
        compra.setNombreProveedor("Pxdd");

        System.out.println("vamos a la lista de detalles");
        List<DetalleCompraDTO> detalle = new ArrayList<>();
        ProductDTO productDTO = productoService.findById(1L).orElseThrow(null);
        ProductDTO productDTO1 = productoService.findById(2L).orElseThrow(null);
        detalle.add(new DetalleCompraDTO(null, compra, productDTO, productDTO.getName(), productDTO.getPrice(), 2 , (double)productDTO.getPrice()*2));
        detalle.add(new DetalleCompraDTO(null, compra, productDTO1, productDTO1.getName(), productDTO1.getPrice(), 2 , (double)productDTO1.getPrice()*2));
        compra.setDetalles(detalle);
        double total = detalle.stream().mapToDouble(DetalleCompraDTO::getSubtotal).sum();
        compra.setTotalVenta(total);
        System.out.println("total de la venta: " + total);
        compraService.registrarCompra(compra);
        System.out.println("venta registrada exitosamente");
    }



    // Para cargar lo que se necesita para agregar venta @GetMapping("/productos")

    @GetMapping("/productos")
     @ResponseBody
    public List<String> obtainProducts(@RequestParam String categoria) {
        return productoService.productsByCategory(categoria);
    }

    @GetMapping("/presentaciones")
     @ResponseBody
    public List<String> obtainPresentations(@RequestParam String producto) {
        return productoService.presentationByProductName(producto);
    }

    @GetMapping("/precio")
     @ResponseBody
    public int obtainProductPrice(@RequestParam String presentacion, @RequestParam String producto) {
        return productoService.productPrice(producto, presentacion);
    }

}
