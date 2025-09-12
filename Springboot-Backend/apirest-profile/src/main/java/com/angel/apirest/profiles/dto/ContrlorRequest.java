package com.angel.apirest.profiles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContrlorRequest {


    @NotEmpty
    private DocumentoIdentidadDTO documentoIdentidad;
    
    @NotBlank
    private String correoElectronico;
    
    @NotBlank
    private String telefono;


}
