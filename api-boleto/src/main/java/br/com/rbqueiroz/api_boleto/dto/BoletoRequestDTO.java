package br.com.rbqueiroz.api_boleto.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoletoRequestDTO {
    @NotNull(message = "Código de barras não pode ser Nulo")
    @NotEmpty(message = "Código de barras não pode ser Vazio")
    private String codigoBarras;
}
