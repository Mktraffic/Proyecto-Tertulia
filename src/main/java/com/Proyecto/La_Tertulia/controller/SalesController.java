package com.Proyecto.La_Tertulia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Proyecto.La_Tertulia.dto.CompraDTO;
import com.Proyecto.La_Tertulia.dto.DetalleCompraDTO;
import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;
import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
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

    private ArrayList<DetalleVentaDTO> saleDetailsList;

    public SalesController() {
        saleDetailsList = new ArrayList<>();
    }

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

    @GetMapping("/addSale")
    public String showFormAddSale(Model model) {
        List<String> categorias = productoService.productCategories();
        model.addAttribute("categorias", categorias);
        model.addAttribute("detalleVentaDTO", new DetalleVentaDTO());
        model.addAttribute("productos", new ArrayList<>());
        model.addAttribute("presentaciones", new ArrayList<>());
        return "DetailsSaleRegistration";
    }

    @PostMapping("/detailSale")
    public String saveDetailSale(@ModelAttribute DetalleVentaDTO detalle, RedirectAttributes redirectAttributes,
            Model model) {
        ProductDTO product = productoService.findById(detalle.getProducto().getId()).orElse(null);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
            model.addAttribute("detalleVentaDTO", detalle);
            return "redirect:/addSale";
        }
        detalle.setNombreProducto(product.getName());
        detalle.setPrecioUnitario(product.getPrice());
        detalle.setSubtotal(detalle.getCantidad() * product.getPrice());
        detalle.setVenta(new VentaDTO());
        for (DetalleVentaDTO detalleVentaDTO : saleDetailsList) {
            if (detalleVentaDTO.getProducto().getId() == detalle.getProducto().getId()) {
                if (detalleVentaDTO.getCantidad() + detalle.getCantidad() > product.getStock()) {
                    redirectAttributes.addFlashAttribute("error", "No hay suficiente stock para este producto");
                    model.addAttribute("detalleVentaDTO", detalle);
                } else {
                    detalleVentaDTO.setCantidad(detalleVentaDTO.getCantidad() + detalle.getCantidad());
                    detalleVentaDTO.setSubtotal(detalleVentaDTO.getPrecioUnitario() * detalleVentaDTO.getCantidad());
                    redirectAttributes.addFlashAttribute("success", "Producto actualizado en la venta");
                }
                return "redirect:/addSale";
            } else if (detalle.getCantidad() > product.getStock()) {
                redirectAttributes.addFlashAttribute("error", "No hay suficiente stock para este producto");
                model.addAttribute("detalleVentaDTO", detalle);
                return "redirect:/addSale";
            }
        }
        saleDetailsList.add(detalle);
        redirectAttributes.addFlashAttribute("success", "Producto agregado a la venta");
        return "redirect:/addSale";
    }

    @GetMapping("/finishSale")
    private String showFinishSaleForm(Model model) {
        model.addAttribute("registroCompra", saleDetailsList);
        return "SaleRegistration";
    }

    @PostMapping("/removeDetailSale")
    public String eliminarProducto(@RequestParam("index") int index, Model model) {
        saleDetailsList.remove(index - 1);
        return "redirect:/finishSale";
    }

    public void compra() {
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
        detalle.add(new DetalleCompraDTO(null, compra, productDTO, productDTO.getName(), productDTO.getPrice(), 2,
                (double) productDTO.getPrice() * 2));
        detalle.add(new DetalleCompraDTO(null, compra, productDTO1, productDTO1.getName(), productDTO1.getPrice(), 2,
                (double) productDTO1.getPrice() * 2));
        compra.setDetalles(detalle);
        double total = detalle.stream().mapToDouble(DetalleCompraDTO::getSubtotal).sum();
        compra.setTotalVenta(total);
        System.out.println("total de la venta: " + total);
        compraService.registrarCompra(compra);
        System.out.println("venta registrada exitosamente");
    }

    @PostMapping("/saleRegistration")
    public String saleRegistration(@ModelAttribute("tipoDocCliente") String tipoDocCli,
            @ModelAttribute("numDocCliente") String numDoc, RedirectAttributes redirectAttributes,
            HttpSession session) {

        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuario");
        if (!saleDetailsList.isEmpty()) {

            VentaDTO venta = new VentaDTO(null, LocalDate.now(),
                    usuarioDTO, tipoDocCli, Long.parseLong(numDoc), this.obtainTotalSale(),
                    new ArrayList<DetalleVentaDTO>());
            for (DetalleVentaDTO detalleVentaDTO : saleDetailsList) {
                detalleVentaDTO.setVenta(venta);
            }
            venta.setDetalles(saleDetailsList);
            ventaService.registrarVenta(venta);
            saleDetailsList.clear();
            redirectAttributes.addFlashAttribute("success", "Venta realizada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "No hay productos en la venta");
        }
        return "redirect:/SaleManagement";
    }

    private double obtainTotalSale() {
        double totalSale = 0;
        for (DetalleVentaDTO detalleVentaDTO : saleDetailsList) {
            totalSale += detalleVentaDTO.getSubtotal();
        }
        return totalSale;
    }

    // Para cargar lo que se necesita para agregar venta @GetMapping("/productos")
    @GetMapping("/productos")
    @ResponseBody
    public ResponseEntity<List<ProductDTO>> getProductosByCategoria(@RequestParam String categoria) {
        try {
            List<ProductDTO> productos = productoService.productsByCategoryParam(categoria);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/presentaciones")
    @ResponseBody
    public ResponseEntity<List<String>> getPresentacionesByProducto(@RequestParam Long productoId) {
        try {
            List<String> presentaciones = productoService.presentationByProductid(productoId);
            return ResponseEntity.ok(presentaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/precio")
    @ResponseBody
    public ResponseEntity<Double> getPrecioByProductoAndPresentacion(
            @RequestParam Long productoId,
            @RequestParam String presentacion) {
        try {
            Double precio = productoService.productPrice(productoId, presentacion);
            if (precio != null) {
                return ResponseEntity.ok(precio);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
