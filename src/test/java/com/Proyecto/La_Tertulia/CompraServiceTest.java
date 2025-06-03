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

import com.Proyecto.La_Tertulia.dto.CompraDTO;
import com.Proyecto.La_Tertulia.dto.DetalleCompraDTO;

import com.Proyecto.La_Tertulia.dto.PersonaDTO;
import com.Proyecto.La_Tertulia.dto.ProductDTO;
import com.Proyecto.La_Tertulia.dto.RolDTO;
import com.Proyecto.La_Tertulia.dto.UsuarioDTO;

import com.Proyecto.La_Tertulia.mapper.CompraMapper;

import com.Proyecto.La_Tertulia.model.Compra;
import com.Proyecto.La_Tertulia.model.Persona;
import com.Proyecto.La_Tertulia.model.Product;
import com.Proyecto.La_Tertulia.model.Rol;
import com.Proyecto.La_Tertulia.model.Usuario;

import com.Proyecto.La_Tertulia.repository.CompraRepository;
import com.Proyecto.La_Tertulia.repository.ProductRepository;

import com.Proyecto.La_Tertulia.repository.UsuarioRepository;

import com.Proyecto.La_Tertulia.service.CompraService;


@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

        @Mock
        private CompraRepository compraRepository;

        @Mock
        private UsuarioRepository usuarioRepository;
        @Mock
        private ProductRepository productRepository;
        @Mock
        private CompraMapper compraMapper;

        @InjectMocks
        private CompraService compraService;

        @Test
        void testRegistrarVentaCon() {

                Rol rolVen = new Rol(2L, "VENDEDOR");
                Persona persona = new Persona(2L, 1002525091L, "Cedula de ciudadania", "luis", "rincon", true,
                                320802341L,
                                "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
                Usuario usuario = new Usuario(2L, "user2", "password2", rolVen, persona);

                Product producto1 = new Product(1L, "Producto 1", "tipo 1", "descripcionx", "300ml",
                                "5 grados de alcohol", 10,
                                5);
                Product producto2 = new Product(2L, "Producto 2", "tipo 2", "descripciony", "500ml",
                                "10 grados de alcohol", 20,
                                10);

                RolDTO rolDTO = new RolDTO(2L, "VENDEDOR");
                PersonaDTO personaDTO = new PersonaDTO(2L, 1002525091L, "Cedula de ciudadania", "luis", "rincon", true,
                                320802341L, "luis3050@gmail.com", LocalDate.of(1990, 1, 1));
                UsuarioDTO usuarioDTO = new UsuarioDTO(2L, "user2", "password2", rolDTO, personaDTO);

                ProductDTO productDTO1 = new ProductDTO(1L, "Producto 1", "tipo 1", "descripcionx", "300ml",
                                "5 grados de alcohol", 10, 5);
                ProductDTO productDTO2 = new ProductDTO(2L, "Producto 2", "tipo 2", "descripciony", "500ml",
                                "10 grados de alcohol", 20, 10);

                DetalleCompraDTO detalleDTO1 = new DetalleCompraDTO(1L, null, productDTO1, "Producto 1", 2, 10, 50.0);
                DetalleCompraDTO detalleDTO2 = new DetalleCompraDTO(2L, null, productDTO2, "Producto 2", 3, 20, 150.0);

                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setFechaCompra(LocalDate.now());
                compraDTO.setVendedor(usuarioDTO);
                compraDTO.setNombreProveedor("luis");

                compraDTO.setDetalles(List.of(detalleDTO1, detalleDTO2));

                Compra compraGuardada = new Compra();
                compraGuardada.setFechaCompra(compraDTO.getFechaCompra());
                compraGuardada.setId(1L);
                compraGuardada.setVendedor(usuario);
                compraGuardada.setNombreProveedor("luis");
                

                CompraDTO compraRetornada = new CompraDTO(
                                1L,
                                compraDTO.getFechaCompra(),
                                usuarioDTO,
                                "luis",
                                100.0,
                                List.of(detalleDTO1, detalleDTO2));

                // Mocks
                Mockito.when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));
                Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(producto1));
                Mockito.when(productRepository.findById(2L)).thenReturn(Optional.of(producto2));
                Mockito.when(productRepository.save(Mockito.any())).thenAnswer(inv -> inv.getArgument(0));
                Mockito.when(compraRepository.save(Mockito.any())).thenReturn(compraGuardada);
                Mockito.when(compraMapper.toDTO(Mockito.any())).thenReturn(compraRetornada);

                CompraDTO resultado = compraService.registrarCompra(compraDTO);

                Assertions.assertNotNull(resultado);
                Assertions.assertEquals(1L, resultado.getId());
                Assertions.assertEquals(100.0, resultado.getTotalVenta());
                Assertions.assertEquals(2, resultado.getDetalles().size());

                Mockito.verify(usuarioRepository).findById(2L);
                Mockito.verify(productRepository).findById(1L);
                Mockito.verify(productRepository).findById(2L);
                Mockito.verify(productRepository, Mockito.times(2)).save(Mockito.any());
                Mockito.verify(compraRepository).save(Mockito.any());
                Mockito.verify(compraMapper).toDTO(Mockito.any());
        }

        @Test
        void testObtenerComprasEntreFechas() {

                LocalDate fechaInicio = LocalDate.of(2023, 1, 1);
                LocalDate fechaFin = LocalDate.of(2023, 12, 31);

                Rol rolVen = new Rol(2L, "VENDEDOR");
                Persona persona = new Persona(1L, 1001234567L, "Cedula", "Juan", "Pérez", true, 320802341L,
                                "juan@mail.com", LocalDate.of(1990, 1, 1));
                Usuario usuario = new Usuario(1L, "juan123", "pass", rolVen, persona);

                Product producto1 = new Product(1L, "Producto A", "Tipo A", "Desc", "500ml", "10%", 10, 20);
                Product producto2 = new Product(2L, "Producto B", "Tipo B", "Desc", "750ml", "5%", 15, 30);

                Compra compra1 = new Compra(1L, LocalDate.of(2023, 5, 10), usuario, "Proveedor A", 100.0,
                                new ArrayList<>());
                Compra compra2 = new Compra(2L, LocalDate.of(2023, 6, 15), usuario, "Proveedor B", 150.0,
                                new ArrayList<>());

                List<Compra> compras = List.of(compra1, compra2);

                RolDTO rolDTO = new RolDTO(2L, "VENDEDOR");
                PersonaDTO personaDTO = new PersonaDTO(1L, 1001234567L, "Cedula", "Juan", "Pérez", true, 320802341L,
                                "juan@mail.com", LocalDate.of(1990, 1, 1));
                UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "juan123", "pass", rolDTO, personaDTO);

                CompraDTO compraDTO1 = new CompraDTO(1L, compra1.getFechaCompra(), usuarioDTO, "Proveedor A", 100.0,
                                new ArrayList<>());
                CompraDTO compraDTO2 = new CompraDTO(2L, compra2.getFechaCompra(), usuarioDTO, "Proveedor B", 150.0,
                                new ArrayList<>());

                Mockito.when(compraRepository.findByFechaCompraBetween(fechaInicio, fechaFin)).thenReturn(compras);
                Mockito.when(compraMapper.toDTO(compra1)).thenReturn(compraDTO1);
                Mockito.when(compraMapper.toDTO(compra2)).thenReturn(compraDTO2);

                List<CompraDTO> resultado = compraService.obtenerComprasEntreFechas(fechaInicio, fechaFin);

                Assertions.assertEquals(2, resultado.size());
                Assertions.assertEquals(1L, resultado.get(0).getId());
                Assertions.assertEquals("Proveedor A", resultado.get(0).getNombreProveedor());
                Assertions.assertEquals(2L, resultado.get(1).getId());
                Assertions.assertEquals("Proveedor B", resultado.get(1).getNombreProveedor());

                Mockito.verify(compraRepository).findByFechaCompraBetween(fechaInicio, fechaFin);
                Mockito.verify(compraMapper).toDTO(compra1);
                Mockito.verify(compraMapper).toDTO(compra2);
        }

        @Test
        void testFindAllBuys() {

                Rol rolVen = new Rol(2L, "VENDEDOR");
                Persona persona = new Persona(1L, 1001234567L, "Cedula", "Juan", "Pérez", true, 320802341L,
                                "juan@mail.com", LocalDate.of(1990, 1, 1));
                Usuario usuario = new Usuario(1L, "juan123", "pass", rolVen, persona);

                Compra compra1 = new Compra(1L, LocalDate.of(2023, 5, 10), usuario, "Proveedor A", 100.0,
                                new ArrayList<>());
                Compra compra2 = new Compra(2L, LocalDate.of(2023, 6, 15), usuario, "Proveedor B", 150.0,
                                new ArrayList<>());

                List<Compra> compras = List.of(compra1, compra2);

                RolDTO rolDTO = new RolDTO(2L, "VENDEDOR");
                PersonaDTO personaDTO = new PersonaDTO(1L, 1001234567L, "Cedula", "Juan", "Pérez", true, 320802341L,
                                "juan@mail.com", LocalDate.of(1990, 1, 1));
                UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "juan123", "pass", rolDTO, personaDTO);

                CompraDTO compraDTO1 = new CompraDTO(1L, compra1.getFechaCompra(), usuarioDTO, "Proveedor A", 100.0,
                                new ArrayList<>());
                CompraDTO compraDTO2 = new CompraDTO(2L, compra2.getFechaCompra(), usuarioDTO, "Proveedor B", 150.0,
                                new ArrayList<>());

                Mockito.when(compraRepository.findAll()).thenReturn(compras);
                Mockito.when(compraMapper.toDTO(compra1)).thenReturn(compraDTO1);
                Mockito.when(compraMapper.toDTO(compra2)).thenReturn(compraDTO2);

                List<CompraDTO> resultado = compraService.findAllBuys();

                Assertions.assertEquals(2, resultado.size());
                Assertions.assertEquals(1L, resultado.get(0).getId());
                Assertions.assertEquals("Proveedor A", resultado.get(0).getNombreProveedor());
                Assertions.assertEquals(2L, resultado.get(1).getId());
                Assertions.assertEquals("Proveedor B", resultado.get(1).getNombreProveedor());

                Mockito.verify(compraRepository).findAll();
                Mockito.verify(compraMapper).toDTO(compra1);
                Mockito.verify(compraMapper).toDTO(compra2);
        }

        @Test
        void testObtainBuyId() {
                Long idBuscado = 2L;

         
                Rol rol = new Rol(2L, "VENDEDOR");
                Persona persona = new Persona(2L, 1002525091L, "Cedula", "Luis", "Rincon", true, 320802341L,
                                "correo@gmail.com", LocalDate.of(1990, 1, 1));
                Usuario usuario = new Usuario(2L, "user2", "pass", rol, persona);

                Compra compra1 = new Compra(1L, LocalDate.of(2024, 5, 1), usuario, "Proveedor A", 150.0,
                                new ArrayList<>());
                Compra compra2 = new Compra(2L, LocalDate.of(2024, 6, 1), usuario, "Proveedor B", 300.0,
                                new ArrayList<>());

                List<Compra> compras = List.of(compra1, compra2);

         
                CompraDTO compraDTOEsperada = new CompraDTO(
                                2L,
                                LocalDate.of(2024, 6, 1),
                                new UsuarioDTO(2L, "user2", "pass",
                                                new RolDTO(2L, "VENDEDOR"),
                                                new PersonaDTO(2L, 1002525091L, "Cedula", "Luis", "Rincon", true,
                                                                320802341L, "correo@gmail.com",
                                                                LocalDate.of(1990, 1, 1))),
                                "Proveedor B",
                                300.0,
                                new ArrayList<>());

             
                Mockito.when(compraRepository.findAll()).thenReturn(compras);
                Mockito.when(compraMapper.toDTO(compra1)).thenReturn(
                                new CompraDTO(1L, compra1.getFechaCompra(), null, "Proveedor A", 150.0,
                                                new ArrayList<>()));
                Mockito.when(compraMapper.toDTO(compra2)).thenReturn(compraDTOEsperada);

                CompraDTO resultado = compraService.obtainBuyId(idBuscado);

               
                Assertions.assertNotNull(resultado);
                Assertions.assertEquals(idBuscado, resultado.getId());
                Assertions.assertEquals("Proveedor B", resultado.getNombreProveedor());
                Assertions.assertEquals(300.0, resultado.getTotalVenta());

                Mockito.verify(compraRepository).findAll();
                Mockito.verify(compraMapper).toDTO(compra1);
                Mockito.verify(compraMapper).toDTO(compra2);
        }

}
