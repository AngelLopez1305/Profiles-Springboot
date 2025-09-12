package com.angel.apirest.profiles.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DocumentoIdentidad")
@Getter
@Setter
@NoArgsConstructor
@ToString

public class DocumentoIdentidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDocumentoIdentidad", nullable = false, unique = true)
    private Long IdDocumentoIdentidad;

    @Column(name = "TipoDocumentoIdentidad",nullable = false, length = 20)
    private String TipoDocumentoIdentidad;

    @Column(name = "NumeroDocumento",nullable = false, length = 100, unique = true)
    private String numeroDocumento;

    @Column(name = "NombreUsuario",nullable = false, length = 50)
    private String Nombre;

    @Column(name = "ApellidoUsuario",nullable = false, length = 50)
    private String Apellido;
    
    @Column(name = "Direccion",nullable = true, length = 100)
    private String Direccion;

    @Column(name = "Ciudad",nullable = true, length = 50)
    private String Ciudad;

}
