package com.Proyecto.La_Tertulia.mapper;

import com.Proyecto.La_Tertulia.dto.FacturaDTO;
import com.Proyecto.La_Tertulia.dto.DetalleFacturaDTO;
import com.Proyecto.La_Tertulia.model.Factura;
import com.Proyecto.La_Tertulia.model.DetalleFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FacturaMapperImplement implements FacturaMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private DetalleFacturaMapperImplement detalleFacturaMapper;

    @Override
    public FacturaDTO toDTO(Factura factura) {
        if (factura == null) return null;
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setUsuario(usuarioMapper.toDTO(factura.getUsuario()));
        dto.setTipoDocumento(factura.getTipoDocumento());
        dto.setNumeroDocumento(factura.getNumeroDocumento());
        dto.setFechaFactura(factura.getFechaFactura());
        if (factura.getProductos() != null) {
            List<DetalleFacturaDTO> productosDTO = factura.getProductos().stream()
                    .map(detalleFacturaMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setProductos(productosDTO);
        }
        dto.setSubTotal(factura.getSubTotal());
        dto.setIva(factura.getIva());
        dto.setTotalFactura(factura.getTotalFactura());
        return dto;
    }

    @Override
    public Factura toEntity(FacturaDTO dto) {
        if (dto == null) return null;
        Factura factura = new Factura();
        factura.setId(dto.getId());
        factura.setUsuario(usuarioMapper.toEntity(dto.getUsuario()));
        factura.setTipoDocumento(dto.getTipoDocumento());
        factura.setNumeroDocumento(dto.getNumeroDocumento());
        factura.setFechaFactura(dto.getFechaFactura());
        if (dto.getProductos() != null) {
            List<DetalleFactura> productos = dto.getProductos().stream()
                    .map(detalleFacturaMapper::toEntity)
                    .collect(Collectors.toList());
            factura.setProductos(productos);
        }
        factura.setSubTotal(dto.getSubTotal());
        factura.setIva(dto.getIva());
        factura.setTotalFactura(dto.getTotalFactura());
        return factura;
    }
}
