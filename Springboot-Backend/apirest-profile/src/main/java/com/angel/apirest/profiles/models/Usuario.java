package com.angel.apirest.profiles.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@ToString

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdUsuario;

    @Column(name = "CorreoElectronico", nullable = false, length = 100, unique = true)
    private String CorreoElectronico;

    @Column(name = "Telefono", nullable = true, length = 15)
    private String Telefono;

    @OneToOne
    @JoinColumn(name = "IdDocumentoIdentidad", referencedColumnName = "IdDocumentoIdentidad")
    private DocumentoIdentidad documentoIdentidad;

}
