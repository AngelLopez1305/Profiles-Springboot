package com.angel.apirest.profiles.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContrlorRequest {

    private Long IdUsuarioDTO;

    @NotNull
    @Valid
    private DocumentoIdentidadDTO documentoIdentidad;

    @NotBlank
    private String correoElectronico;

    @NotBlank
    private String telefono;
}
