package com.angel.apirest.profiles.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angel.apirest.profiles.dto.DocumentoIdentidadDTO;
import com.angel.apirest.profiles.models.DocumentoIdentidad;
import com.angel.apirest.profiles.repositorie.DocumentoIdentidadRepository;

@Service
public class DocumentoIdentidadServices {

    @Autowired
    private  DocumentoIdentidadRepository documentoIdentidadRepository;

    private DocumentoIdentidadDTO convertirDTO(DocumentoIdentidad docIdent) {
        try {

            if (docIdent == null) {
                return null;
            }

            DocumentoIdentidadDTO DocIdentiDTO = new DocumentoIdentidadDTO();
            DocIdentiDTO.setIdDocumentoIdentidadDTO(docIdent.getIdDocumentoIdentidad());
            DocIdentiDTO.setTipoDocumentoIdentidadDTO(docIdent.getTipoDocumentoIdentidad());
            DocIdentiDTO.setNumeroDocumentoDTO(docIdent.getNumeroDocumento());
            DocIdentiDTO.setNombreDTO(docIdent.getNombre());
            DocIdentiDTO.setApellidoDTO(docIdent.getApellido());
            DocIdentiDTO.setDireccionDTO(docIdent.getDireccion());
            DocIdentiDTO.setCiudadDTO(docIdent.getCiudad());

            return DocIdentiDTO;

        } catch (Exception e) {
            System.out.println("Error al convertir DocumentoIdentidad a DocumentoIdentidadDTO: " + e.getMessage());
            return null;
        }
    }

    private DocumentoIdentidad convertirEntidad(DocumentoIdentidadDTO docIdentDTO) {
        try {
            if (docIdentDTO == null) {
                return null;
            }

            DocumentoIdentidad docIdent = new DocumentoIdentidad();
            docIdent.setIdDocumentoIdentidad(docIdentDTO.getIdDocumentoIdentidadDTO());
            docIdent.setTipoDocumentoIdentidad(docIdentDTO.getTipoDocumentoIdentidadDTO());
            docIdent.setNumeroDocumento(docIdentDTO.getNumeroDocumentoDTO());
            docIdent.setNombre(docIdentDTO.getNombreDTO());
            docIdent.setApellido(docIdentDTO.getApellidoDTO());
            docIdent.setDireccion(docIdentDTO.getDireccionDTO());
            docIdent.setCiudad(docIdentDTO.getCiudadDTO());

            return docIdent;

        } catch (Exception e) {
            System.out.println("Error al convertir DocumentoIdentidadDTO a DocumentoIdentidad: " + e.getMessage());
            return null;
        }
    }

    public List<DocumentoIdentidadDTO> listarDocumentos() {
        return documentoIdentidadRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    public DocumentoIdentidadDTO FindByIdDocument(Long IdDocumentoIdentidad) {
        try {
            DocumentoIdentidad docuIdent = documentoIdentidadRepository.findById(IdDocumentoIdentidad).orElse(null);
            return convertirDTO(docuIdent);
        } catch (Exception e) {
            System.out.println("Error al buscar DocumentoIdentidad por ID: " + e.getMessage());
            return null;
        }
    }

    public Long FindbyNumIdentity(String NumeroDocumento) {
        try {
            Optional<DocumentoIdentidad> docident = documentoIdentidadRepository.findByNumeroDocumento(NumeroDocumento);
            return docident.map(DocumentoIdentidad::getIdDocumentoIdentidad).orElse(null);

        } catch (Exception e) {
            System.out.println("Error al buscar DocumentoIdentidad por atributos: " + e.getMessage());
            return null;
        }
    }

    public void CreateDocIdent(DocumentoIdentidadDTO docIdentDTO) {
        try {
            if (docIdentDTO == null) {
                throw new IllegalArgumentException("El DocumentoIdentidadDTO no puede ser nulo");
            }
            DocumentoIdentidad docIdent = convertirEntidad(docIdentDTO);
            documentoIdentidadRepository.save(docIdent);
            System.out.println("DocumentoIdentidad creado exitosamente");
        } catch (Exception e) {
            System.out.println("Error al crear DocumentoIdentidad: " + e.getMessage());
        }
    }

    public boolean DeleteDocIdent(Long IdDocumentoIdentidad) {
        boolean isDeleted = false;
        try {
            if (documentoIdentidadRepository.existsById(IdDocumentoIdentidad)) {
                documentoIdentidadRepository.deleteById(IdDocumentoIdentidad);
                isDeleted = true;
            } else {
                System.out.println("El DocumentoIdentidad con ID " + IdDocumentoIdentidad + " no existe.");
                isDeleted = false;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar DocumentoIdentidad: " + e.getMessage());
            isDeleted = false;
        }
        return isDeleted;
    }

    public boolean UpdateDocIdent(DocumentoIdentidadDTO docIdentDTO) {
        boolean isUpdated = false;
        try {
            if (docIdentDTO == null || docIdentDTO.getIdDocumentoIdentidadDTO() == null) {
                throw new IllegalArgumentException("El DocumentoIdentidadDTO o su ID no pueden ser nulos");
            }

            if (documentoIdentidadRepository.existsById(docIdentDTO.getIdDocumentoIdentidadDTO())) {
                DocumentoIdentidad docIdent = convertirEntidad(docIdentDTO);
                documentoIdentidadRepository.save(docIdent);
                isUpdated = true;
            } else {
                System.out.println(
                        "El DocumentoIdentidad con ID " + docIdentDTO.getIdDocumentoIdentidadDTO() + " no existe.");
                isUpdated = false;
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar DocumentoIdentidad: " + e.getMessage());
            isUpdated = false;
        }
        return isUpdated;
    }

}
