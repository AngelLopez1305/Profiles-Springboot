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

    @NotBlank
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
    @Size(min = 120 ,max = 150)
    private String DireccionDTO;

    @NotBlank
    @Size(min = 50 ,max = 100)
    private String CiudadDTO;

}
