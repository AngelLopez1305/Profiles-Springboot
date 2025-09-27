package com.angel.apirest.profiles.services;

import com.angel.apirest.profiles.AbstractServices.UsuarioAbstract;
import com.angel.apirest.profiles.dto.DocumentoIdentidadDTO;
import com.angel.apirest.profiles.models.DocumentoIdentidad;
import com.angel.apirest.profiles.repositorie.DocumentoIdentidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoIdentidadServices extends UsuarioAbstract<DocumentoIdentidad, DocumentoIdentidadDTO> {

    @Autowired
    public DocumentoIdentidadRepository documentoIdentidadRepository;

    @Override
    public DocumentoIdentidadDTO entityToDTO(DocumentoIdentidad entity) {
        if (entity == null)
            return null;
        DocumentoIdentidadDTO dto = new DocumentoIdentidadDTO();
        dto.setIdDocumentoIdentidadDTO(entity.getIdDocumentoIdentidad());
        dto.setTipoDocumentoIdentidadDTO(entity.getTipoDocumentoIdentidad());
        dto.setNumeroDocumentoDTO(entity.getNumeroDocumento());
        dto.setNombreDTO(entity.getNombre());
        dto.setApellidoDTO(entity.getApellido());
        dto.setDireccionDTO(entity.getDireccion());
        dto.setCiudadDTO(entity.getCiudad());
        dto.setNacionalidadDTO(entity.getNacionalidad());
        return dto;
    }

    @Override
    public DocumentoIdentidad dtoToEntity(DocumentoIdentidadDTO dto) {
        if (dto == null)
            return null;
        DocumentoIdentidad entity = new DocumentoIdentidad();
        entity.setIdDocumentoIdentidad(dto.getIdDocumentoIdentidadDTO());
        entity.setTipoDocumentoIdentidad(dto.getTipoDocumentoIdentidadDTO());
        entity.setNumeroDocumento(dto.getNumeroDocumentoDTO());
        entity.setNombre(dto.getNombreDTO());
        entity.setApellido(dto.getApellidoDTO());
        entity.setDireccion(dto.getDireccionDTO());
        entity.setCiudad(dto.getCiudadDTO());
        entity.setNacionalidad(dto.getNacionalidadDTO());
        return entity;
    }

    @Override
    public DocumentoIdentidad updateEntity(DocumentoIdentidad existingEntity, DocumentoIdentidadDTO dto) {
        if (existingEntity == null || dto == null)
            return null;
        existingEntity.setTipoDocumentoIdentidad(dto.getTipoDocumentoIdentidadDTO());
        existingEntity.setNumeroDocumento(dto.getNumeroDocumentoDTO());
        existingEntity.setNombre(dto.getNombreDTO());
        existingEntity.setApellido(dto.getApellidoDTO());
        existingEntity.setDireccion(dto.getDireccionDTO());
        existingEntity.setCiudad(dto.getCiudadDTO());
        existingEntity.setNacionalidad(dto.getNacionalidadDTO());
        return existingEntity;
    }

    // ----------------- MÉTODOS PÚBLICOS -----------------
    public List<DocumentoIdentidadDTO> listarDocumentos() {
        return getAll(documentoIdentidadRepository);
    }

    public DocumentoIdentidadDTO FindByIdDocument(Long id) {
        return getById(id, documentoIdentidadRepository);
    }

    public DocumentoIdentidadDTO CreateDocIdent(DocumentoIdentidadDTO dto) {
        if (dto == null)
            return null;
        // Convertir DTO a Entity
        DocumentoIdentidad entity = dtoToEntity(dto);
        // Guardar Entity
        DocumentoIdentidad saved = documentoIdentidadRepository.save(entity);
        // Devolver DTO
        return entityToDTO(saved);
    }

    public boolean DeleteDocIdent(Long id) {
        return delete(id, documentoIdentidadRepository);
    }

    public boolean UpdateDocIdent(DocumentoIdentidadDTO dto) {
        if (dto == null || dto.getIdDocumentoIdentidadDTO() == null)
            return false;
        DocumentoIdentidad updated = updateEntity(
                documentoIdentidadRepository.findById(dto.getIdDocumentoIdentidadDTO()).orElse(null), dto);
        if (updated == null)
            return false;
        documentoIdentidadRepository.save(updated);
        return true;
    }

    public Long FindbyNumIdentity(String numeroDocumento) {
        Optional<DocumentoIdentidad> docident = documentoIdentidadRepository.findByNumeroDocumento(numeroDocumento);
        return docident.map(DocumentoIdentidad::getIdDocumentoIdentidad).orElse(null);
    }
}
