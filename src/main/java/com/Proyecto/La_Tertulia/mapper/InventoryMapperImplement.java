package com.Proyecto.La_Tertulia.mapper;

import com.Proyecto.La_Tertulia.dto.InventoryDTO;
import com.Proyecto.La_Tertulia.model.Inventory;

public class InventoryMapperImplement implements InventoryMapper {

    @Override
    public InventoryDTO toDTO(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId(inventory.getId());
        ProductMapperImplement productMapper = new ProductMapperImplement();
        inventoryDTO.setProductDTO(productMapper.toDTO(inventory.getProduct()));
        inventoryDTO.setCantidad_producto(inventory.getCantidad_producto());
        return inventoryDTO;
    }

    @Override
    public Inventory toEntity(InventoryDTO inventoryDTO) {
        if (inventoryDTO == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        inventory.setId(inventoryDTO.getId());
        ProductMapperImplement productMapper = new ProductMapperImplement();
        inventory.setProduct(productMapper.toEntity(inventoryDTO.getProductDTO()));
        inventory.setCantidad_producto(inventoryDTO.getCantidad_producto());
        return inventory;
    }

}
