package com.Proyecto.La_Tertulia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeededForBuyDTO {
    private ProductDTO productDTO;
  //  private BuyDTO buyDTO;
    private UsuarioDTO usuarioDTO;
}
