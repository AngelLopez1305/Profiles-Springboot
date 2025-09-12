package com.angel.apirest.profiles.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UsuarioDTO {

    @NotBlank
    private Long IdUsuarioDTO;

    @Email
    @NotBlank
    private String CorreoElectronicoDTO;

    @NotBlank
    @Size(max = 9)
    private String TelefonoDTO;

    @NotEmpty
    private Long IdDocumentoIdentidad;

}
