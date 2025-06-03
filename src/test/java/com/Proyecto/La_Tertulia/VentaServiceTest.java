package com.Proyecto.La_Tertulia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Proyecto.La_Tertulia.dto.DetalleVentaDTO;
import com.Proyecto.La_Tertulia.dto.PersonaDTO;
import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.dto.RolDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;
import com.Proyecto.La_Tertulia.dto.VentaDTO;
import com.Proyecto.La_Tertulia.mapper.VentaMapper;

import com.Proyecto.La_Tertulia.model.Persona;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.model.Rol;
import com.Proyecto.La_Tertulia.model.Usuario;
import com.Proyecto.La_Tertulia.model.Venta;
import com.Proyecto.La_Tertulia.repository.ProductRepository;

import com.Proyecto.La_Tertulia.repository.UsuarioRepository;
import com.Proyecto.La_Tertulia.repository.VentaRepository;
import com.Proyecto.La_Tertulia.service.VentaService;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private VentaMapper ventaMapper;

    @InjectMocks
    private VentaService ventaService;

    @Test
    void testRegistrarVentaCon() {

        Rol rolVen = new Rol(2L, "VENDEDOR");
        Persona persona = new Persona(2L, 1002525091L, "Cedula de ciudadania", "luis", "rincon", true, 320802341L,
                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
        Usuario usuario = new Usuario(2L, "user2", "password2", rolVen, persona);

        Product producto1 = new Product(1L, "Producto 1", "tipo 1", "descripcionx", "300ml", "5 grados de alcohol", 10,
                5);
        Product producto2 = new Product(2L, "Producto 2", "tipo 2", "descripciony", "500ml", "10 grados de alcohol", 20,
                10);

        RolDTO rolDTO = new RolDTO(2L, "VENDEDOR");
        PersonaDTO personaDTO = new PersonaDTO(2L, 1002525091L, "Cedula de ciudadania", "luis", "rincon", true,
                320802341L, "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
        UsuarioDTO usuarioDTO = new UsuarioDTO(2L, "user2", "password2", rolDTO, personaDTO);

        ProductDTO productDTO1 = new ProductDTO(1L, "Producto 1", "tipo 1", "descripcionx", "300ml",
                "5 grados de alcohol", 10, 5);
        ProductDTO productDTO2 = new ProductDTO(2L, "Producto 2", "tipo 2", "descripciony", "500ml",
                "10 grados de alcohol", 20, 10);

        DetalleVentaDTO detalleDTO1 = new DetalleVentaDTO(1L, null, productDTO1, "Producto 1", 2, 10, 50.0);
        DetalleVentaDTO detalleDTO2 = new DetalleVentaDTO(2L, null, productDTO2, "Producto 2", 3, 20, 150.0);

        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setVendedor(usuarioDTO);
        ventaDTO.setTipoDocumentoCliente("Cedula de ciudadania");
        ventaDTO.setNumeroDocumentoCliente(100325091L);
        ventaDTO.setFechaVenta(LocalDate.now());
        ventaDTO.setDetalles(List.of(detalleDTO1, detalleDTO2));

        Venta ventaGuardada = new Venta();
        ventaGuardada.setId(1L);
        ventaGuardada.setVendedor(usuario);
        ventaGuardada.setTipoDocumentoCliente("Cedula de ciudadania");
        ventaGuardada.setNumeroDocumentoCliente(100325091L);
        ventaGuardada.setFechaVenta(LocalDate.now());
        ventaGuardada.setTotalVenta(80.0); // 2*10 + 3*20

        VentaDTO ventaRetornada = new VentaDTO(
                1L,
                ventaDTO.getFechaVenta(),
                usuarioDTO,
                "Cedula de ciudadania",
                100325091L,
                80.0,
                List.of(detalleDTO1, detalleDTO2));

        // Mocks
        Mockito.when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(producto1));
        Mockito.when(productRepository.findById(2L)).thenReturn(Optional.of(producto2));
        Mockito.when(productRepository.save(Mockito.any())).thenAnswer(inv -> inv.getArgument(0));
        Mockito.when(ventaRepository.save(Mockito.any())).thenReturn(ventaGuardada);
        Mockito.when(ventaMapper.toDTO(Mockito.any())).thenReturn(ventaRetornada);

        VentaDTO resultado = ventaService.registrarVenta(ventaDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1L, resultado.getId());
        Assertions.assertEquals(80.0, resultado.getTotalVenta());
        Assertions.assertEquals(2, resultado.getDetalles().size());

        Mockito.verify(usuarioRepository).findById(2L);
        Mockito.verify(productRepository).findById(1L);
        Mockito.verify(productRepository).findById(2L);
        Mockito.verify(productRepository, Mockito.times(2)).save(Mockito.any());
        Mockito.verify(ventaRepository).save(Mockito.any());
        Mockito.verify(ventaMapper).toDTO(Mockito.any());
    }

    @Test
    void testObtenerVentasEntreFechas() {

        LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
        LocalDate fechaFin = LocalDate.of(2024, 12, 31);

        Rol rolVen = new Rol(2L, "VENDEDOR");
        Persona persona = new Persona(2L, 1002525091L, "Cedula", "luis", "rincon", true, 320802341L, "correo@gmail.com",
                LocalDate.of(1990, 1, 1));
        Usuario usuario = new Usuario(2L, "user2", "pass", rolVen, persona);

        Product producto1 = new Product(1L, "Producto A", "tipo", "desc", "300ml", "5%", 10, 5);
        Product producto2 = new Product(2L, "Producto B", "tipo", "desc", "500ml", "10%", 15, 8);

        // Ventas simuladas
        Venta venta1 = new Venta(1L, LocalDate.of(2024, 5, 10), usuario, "Cedula", 1001L, 50.0, new ArrayList<>());
        Venta venta2 = new Venta(2L, LocalDate.of(2024, 6, 15), usuario, "Cedula", 1001L, 50.0, new ArrayList<>());

        List<Venta> ventas = List.of(venta1, venta2);

        RolDTO rolDTO = new RolDTO(2L, "VENDEDOR");
        PersonaDTO personaDTO = new PersonaDTO(2L, 1002525091L, "Cedula", "luis", "rincon", true, 320802341L,
                "correo@gmail.com", LocalDate.of(1990, 1, 1));
        UsuarioDTO usuarioDTO = new UsuarioDTO(2L, "user2", "pass", rolDTO, personaDTO);

        VentaDTO ventaDTO1 = new VentaDTO(1L, venta1.getFechaVenta(), usuarioDTO, "Cedula", 1001L, 50.0,
                new ArrayList<>());
        VentaDTO ventaDTO2 = new VentaDTO(2L, venta2.getFechaVenta(), usuarioDTO, "Cedula", 1002L, 100.0,
                new ArrayList<>());

        Mockito.when(ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin)).thenReturn(ventas);
        Mockito.when(ventaMapper.toDTO(venta1)).thenReturn(ventaDTO1);
        Mockito.when(ventaMapper.toDTO(venta2)).thenReturn(ventaDTO2);

        List<VentaDTO> resultado = ventaService.obtenerVentasEntreFechas(fechaInicio, fechaFin);

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(1L, resultado.get(0).getId());
        Assertions.assertEquals(2L, resultado.get(1).getId());

        Mockito.verify(ventaRepository).findByFechaVentaBetween(fechaInicio, fechaFin);
        Mockito.verify(ventaMapper).toDTO(venta1);
        Mockito.verify(ventaMapper).toDTO(venta2);
    }

    @Test
    void testFindAllSales() {

        Rol rolVen = new Rol(2L, "VENDEDOR");
        Persona persona = new Persona(2L, 1002525091L, "Cedula", "luis", "rincon", true, 320802341L, "correo@gmail.com",
                LocalDate.of(1990, 1, 1));
        Usuario usuario = new Usuario(2L, "user2", "pass", rolVen, persona);

        Venta venta1 = new Venta(1L, LocalDate.of(2024, 5, 10), usuario, "Cedula", 1001L, 50.0, new ArrayList<>());
        Venta venta2 = new Venta(2L, LocalDate.of(2024, 6, 15), usuario, "Cedula", 1001L, 50.0, new ArrayList<>());
        List<Venta> ventas = List.of(venta1, venta2);

        RolDTO rolDTO = new RolDTO(2L, "VENDEDOR");
        PersonaDTO personaDTO = new PersonaDTO(2L, 1002525091L, "Cedula", "luis", "rincon", true, 320802341L,
                "correo@gmail.com", LocalDate.of(1990, 1, 1));
        UsuarioDTO usuarioDTO = new UsuarioDTO(2L, "user2", "pass", rolDTO, personaDTO);

        VentaDTO ventaDTO1 = new VentaDTO(1L, venta1.getFechaVenta(), usuarioDTO, "Cedula", 1001L, 60.0,
                new ArrayList<>());
        VentaDTO ventaDTO2 = new VentaDTO(2L, venta2.getFechaVenta(), usuarioDTO, "Cedula", 1002L, 120.0,
                new ArrayList<>());
        List<VentaDTO> expected = List.of(ventaDTO1, ventaDTO2);

        Mockito.when(ventaRepository.findAll()).thenReturn(ventas);
        Mockito.when(ventaMapper.toDTO(venta1)).thenReturn(ventaDTO1);
        Mockito.when(ventaMapper.toDTO(venta2)).thenReturn(ventaDTO2);

        List<VentaDTO> resultado = ventaService.findAllSales();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(expected.get(0).getId(), resultado.get(0).getId());
        Assertions.assertEquals(expected.get(1).getId(), resultado.get(1).getId());

        Mockito.verify(ventaRepository).findAll();
        Mockito.verify(ventaMapper).toDTO(venta1);
        Mockito.verify(ventaMapper).toDTO(venta2);
    }

@Test
void testObtainSaleId() {
    Long idBuscado = 2L;

   
    Rol rol = new Rol(2L, "VENDEDOR");
    Persona persona = new Persona(2L, 1002525091L, "Cedula", "Luis", "Rincon", true, 320802341L, "correo@gmail.com", LocalDate.of(1990, 1, 1));
    Usuario usuario = new Usuario(2L, "user2", "pass", rol, persona);

    Venta venta1 = new Venta(1L, LocalDate.of(2024, 5, 1), usuario, "Cedula", 1001L, 50.0, new ArrayList<>());
    Venta venta2 = new Venta(2L, LocalDate.of(2024, 6, 1), usuario, "Cedula", 1002L, 100.0, new ArrayList<>());

    List<Venta> ventas = List.of(venta1, venta2);

 
    VentaDTO ventaDTOEsperada = new VentaDTO(
        2L,
        LocalDate.of(2024, 6, 1),
        new UsuarioDTO(2L, "user2", "pass", new RolDTO(2L, "VENDEDOR"),
            new PersonaDTO(2L, 1002525091L, "Cedula", "Luis", "Rincon", true, 320802341L, "correo@gmail.com", LocalDate.of(1990, 1, 1))),
        "Cedula",
        1002L,
        100.0,
        new ArrayList<>()
    );

  
    Mockito.when(ventaRepository.findAll()).thenReturn(ventas);
    Mockito.when(ventaMapper.toDTO(venta1)).thenReturn(new VentaDTO(1L, venta1.getFechaVenta(), null, "Cedula", 1001L, 50.0, new ArrayList<>()));
    Mockito.when(ventaMapper.toDTO(venta2)).thenReturn(ventaDTOEsperada);


    VentaDTO resultado = ventaService.obtainSaleId(idBuscado);


    Assertions.assertNotNull(resultado);
    Assertions.assertEquals(idBuscado, resultado.getId());
    Assertions.assertEquals("Cedula", resultado.getTipoDocumentoCliente());
    Assertions.assertEquals(100.0, resultado.getTotalVenta());

    Mockito.verify(ventaRepository).findAll();
    Mockito.verify(ventaMapper).toDTO(venta1);
    Mockito.verify(ventaMapper).toDTO(venta2);
}



}
