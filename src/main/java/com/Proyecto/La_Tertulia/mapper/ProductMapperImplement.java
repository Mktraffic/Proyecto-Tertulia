package com.Proyecto.La_Tertulia.mapper;

import org.springframework.stereotype.Component;

import com.Proyecto.La_Tertulia.dto.*;
import com.Proyecto.La_Tertulia.model.*;

@Component
public class ProductMapperImplement implements ProductMapper {

    @Override
    public ProductDTO toDTO(Product product) {
        System.out.println("Convirtiendo producto a DTO: " + product);
        if (product instanceof Vodka vodka) {
            return new VodkaDTO(
                vodka.getId(),
                vodka.getName(),
                vodka.getDescription(),
                vodka.getPresentation(),
                vodka.getStock(),
                vodka.getPrice(),
                vodka.getGraduacionAlcoholica()
            );
        } else if (product instanceof Cerveza cerveza) {
            return new CervezaDTO(
                cerveza.getId(),
                cerveza.getName(),
                cerveza.getDescription(),
                cerveza.getPresentation(),
                cerveza.getStock(),
                cerveza.getPrice(),
                cerveza.getGraduacionAlcoholica()
            );
        } else if (product instanceof Vino vino) {
            return new VinoDTO(
                vino.getId(),
                vino.getName(),
                vino.getDescription(),
                vino.getPresentation(),
                vino.getStock(),
                vino.getPrice(),
                vino.getGraduacionAlcoholica(),
                vino.getRegionOrigen(),
                vino.getAnejamiento()
            );
        } else if (product instanceof Aguardiente aguardiente) {
            return new AguardienteDTO(
                aguardiente.getId(),
                aguardiente.getName(),
                aguardiente.getDescription(),
                aguardiente.getPresentation(),
                aguardiente.getStock(),
                aguardiente.getPrice(),
                aguardiente.getGraduacionAlcoholica(),
                aguardiente.getTipo()
            );
        } else {
            return null;
        }
    }

    @Override
    public Product toEntity(ProductDTO dto) {
        System.out.println("Convirtiendo DTO a entidad: " + dto);

        if (dto instanceof VodkaDTO vodkaDTO) {
            return new Vodka(
                vodkaDTO.getId(),
                vodkaDTO.getName(),
                vodkaDTO.getDescription(),
                vodkaDTO.getPresentation(),
                vodkaDTO.getStock(),
                vodkaDTO.getPrice(),
                vodkaDTO.getGraduacionAlcoholica()
            );
        } else if (dto instanceof CervezaDTO cervezaDTO) {
            return new Cerveza(
                cervezaDTO.getId(),
                cervezaDTO.getName(),
                cervezaDTO.getDescription(),
                cervezaDTO.getPresentation(),
                cervezaDTO.getStock(),
                cervezaDTO.getPrice(),
                cervezaDTO.getGraduacionAlcoholica()
            );
        } else if (dto instanceof VinoDTO vinoDTO) {
            System.out.println("llego al to entity de vino");
            return new Vino(
                vinoDTO.getId(),
                vinoDTO.getName(),
                vinoDTO.getDescription(),
                vinoDTO.getPresentation(),
                vinoDTO.getStock(),
                vinoDTO.getPrice(),
                vinoDTO.getGraduacionAlcoholica(),
                vinoDTO.getRegionOrigen(),
                vinoDTO.getAnejamiento()
            );
        } else if (dto instanceof AguardienteDTO aguardienteDTO) {
            return new Aguardiente(
                aguardienteDTO.getId(),
                aguardienteDTO.getName(),
                aguardienteDTO.getDescription(),
                aguardienteDTO.getPresentation(),
                aguardienteDTO.getStock(),
                aguardienteDTO.getPrice(),
                aguardienteDTO.getGraduacionAlcoholica(),
                aguardienteDTO.getTipo()
            );
        } else {
            System.out.println("Tipo de producto no reconocido: " + dto.getClass().getSimpleName());
            return null;
        }
    }

    @Override
    public Vino toEntityVino(ProductDTO productDTO) {
        System.out.println("Convirtiendo DTO a entidad Vino: " + productDTO);
        if (productDTO instanceof VinoDTO vinoDTO) {
            return new Vino(
                vinoDTO.getId(),
                vinoDTO.getName(),
                vinoDTO.getDescription(),
                vinoDTO.getPresentation(),
                vinoDTO.getStock(),
                vinoDTO.getPrice(),
                vinoDTO.getGraduacionAlcoholica(),
                vinoDTO.getRegionOrigen(),
                vinoDTO.getAnejamiento()
            );
        } else {
            System.out.println("El DTO no es una instancia de VinoDTO");
            return null;
        }
    }
}
