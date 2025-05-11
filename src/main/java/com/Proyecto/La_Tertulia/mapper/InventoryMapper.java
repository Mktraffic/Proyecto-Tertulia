package com.Proyecto.La_Tertulia.mapper;

import org.mapstruct.Mapper;
import com.Proyecto.La_Tertulia.dto.InventoryDTO;
import com.Proyecto.La_Tertulia.model.Inventory;


@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryDTO toDTO(Inventory inventory);
    Inventory toEntity(InventoryDTO inventoryDTO);
}