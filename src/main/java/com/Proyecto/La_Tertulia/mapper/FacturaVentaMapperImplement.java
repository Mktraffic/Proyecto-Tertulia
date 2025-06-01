package com.Proyecto.La_Tertulia.mapper;
import com.Proyecto.La_Tertulia.dto.FacturaVentaDTO;
import com.Proyecto.La_Tertulia.model.FacturaVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacturaVentaMapperImplement implements FacturaVentaMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private VentaMapper ventaMapper;

    @Override
    public FacturaVentaDTO toDTO(FacturaVenta factura) {
        if (factura == null) return null;
        FacturaVentaDTO dto = new FacturaVentaDTO();
        dto.setId(factura.getId());
        dto.setUsuario(usuarioMapper.toDTO(factura.getUsuario()));
        dto.setTipoDocumento(factura.getTipoDocumento());
        dto.setNumeroDocumento(factura.getNumeroDocumento());
        dto.setFechaFactura(factura.getFechaFactura());
        dto.setVenta(ventaMapper.toDTO(factura.getVenta()));
        dto.setSubTotal(factura.getSubTotal());
        dto.setIva(factura.getIva());
        dto.setTotalFactura(factura.getTotalFactura());
        return dto;
    }

    @Override
    public FacturaVenta toEntity(FacturaVentaDTO dto) {
        if (dto == null) return null;
        FacturaVenta factura = new FacturaVenta();
        factura.setId(dto.getId());
        factura.setUsuario(usuarioMapper.toEntity(dto.getUsuario()));
        factura.setTipoDocumento(dto.getTipoDocumento());
        factura.setNumeroDocumento(dto.getNumeroDocumento());
        factura.setFechaFactura(dto.getFechaFactura());
        factura.setVenta(ventaMapper.toEntity(dto.getVenta()));
        factura.setSubTotal(dto.getSubTotal());
        factura.setIva(dto.getIva());
        factura.setTotalFactura(dto.getTotalFactura());
        return factura;
    }
}
