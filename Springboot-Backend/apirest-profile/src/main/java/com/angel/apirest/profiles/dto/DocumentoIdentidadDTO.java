package com.angel.apirest.profiles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DocumentoIdentidadDTO {

    private Long IdDocumentoIdentidadDTO;

    @NotBlank
    private String TipoDocumentoIdentidadDTO;

    @NotBlank
    private String NumeroDocumentoDTO;

    @NotBlank
    private String NombreDTO;

    @NotBlank
    private String ApellidoDTO;

    @NotBlank
    @Size(max = 150) // Se quitó el min exagerado
    private String DireccionDTO;

    @NotBlank
    @Size(max = 100)
    private String CiudadDTO;

    @NotBlank
    @Size(max = 100)
    private String NacionalidadDTO;
}
