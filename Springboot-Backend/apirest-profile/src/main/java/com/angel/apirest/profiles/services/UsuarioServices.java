package com.angel.apirest.profiles.services;

import com.angel.apirest.profiles.AbstractServices.UsuarioAbstract;
import com.angel.apirest.profiles.dto.UsuarioDTO;
import com.angel.apirest.profiles.dto.DocumentoIdentidadDTO;
import com.angel.apirest.profiles.models.Usuario;
import com.angel.apirest.profiles.models.DocumentoIdentidad;
import com.angel.apirest.profiles.repositorie.UsuarioRepository;
import com.angel.apirest.profiles.repositorie.DocumentoIdentidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioServices extends UsuarioAbstract<Usuario, UsuarioDTO> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DocumentoIdentidadServices documentoIdentidadServices;

    @Autowired
    private DocumentoIdentidadRepository documentoIdentidadRepository;

    @Override
    public UsuarioDTO entityToDTO(Usuario entity) {
        if (entity == null)
            return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuarioDTO(entity.getIdUsuario());
        dto.setCorreoElectronicoDTO(entity.getCorreoElectronico());
        dto.setTelefonoDTO(entity.getTelefono());
        dto.setIdDocumentoIdentidad(
                entity.getDocumentoIdentidad() != null ? entity.getDocumentoIdentidad().getIdDocumentoIdentidad()
                        : null);
        return dto;
    }

    @Override
    public Usuario dtoToEntity(UsuarioDTO dto) {
        if (dto == null)
            return null;

        Usuario entity = new Usuario();
        entity.setIdUsuario(dto.getIdUsuarioDTO());
        entity.setCorreoElectronico(dto.getCorreoElectronicoDTO());
        entity.setTelefono(dto.getTelefonoDTO());

        if (dto.getIdDocumentoIdentidad() != null) {
            DocumentoIdentidad doc = documentoIdentidadRepository.findById(dto.getIdDocumentoIdentidad())
                    .orElseThrow(() -> new RuntimeException("Documento de identidad no encontrado"));
            entity.setDocumentoIdentidad(doc);
        }

        return entity;
    }

    @Override
    public Usuario updateEntity(Usuario existingEntity, UsuarioDTO dto) {
        if (existingEntity == null || dto == null)
            return existingEntity;
        existingEntity.setCorreoElectronico(dto.getCorreoElectronicoDTO());
        existingEntity.setTelefono(dto.getTelefonoDTO());
        return existingEntity;
    }

    // Crear usuario con documento
    public UsuarioDTO createUsuarioWithDoc(DocumentoIdentidadDTO docDTO, String correo, String telefono) {
        DocumentoIdentidadDTO savedDoc = documentoIdentidadServices.CreateDocIdent(docDTO);
        if (savedDoc == null)
            return null;

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCorreoElectronicoDTO(correo);
        usuarioDTO.setTelefonoDTO(telefono);
        usuarioDTO.setIdDocumentoIdentidad(savedDoc.getIdDocumentoIdentidadDTO());

        return create(usuarioDTO, usuarioRepository);
    }

    // Actualizar usuario y documento
    public UsuarioDTO updateUsuarioWithDoc(Long idUsuario, String correo, String telefono,
                                           DocumentoIdentidadDTO docDTO) {
        Usuario existing = usuarioRepository.findById(idUsuario).orElse(null);
        if (existing == null)
            return null;

        existing.setCorreoElectronico(correo);
        existing.setTelefono(telefono);

        DocumentoIdentidad doc = existing.getDocumentoIdentidad();
        if (doc == null)
            doc = new DocumentoIdentidad();

        doc.setTipoDocumentoIdentidad(docDTO.getTipoDocumentoIdentidadDTO());
        doc.setNumeroDocumento(docDTO.getNumeroDocumentoDTO());
        doc.setNombre(docDTO.getNombreDTO());
        doc.setApellido(docDTO.getApellidoDTO());
        doc.setDireccion(docDTO.getDireccionDTO());
        doc.setCiudad(docDTO.getCiudadDTO());
        doc.setNacionalidad(docDTO.getNacionalidadDTO());

        existing.setDocumentoIdentidad(doc);
        usuarioRepository.save(existing);

        return entityToDTO(existing);
    }

    // Obtener detalles completos de usuario
    public Map<String, Object> getUsuarioDetailsMap(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null)
            return null;

        DocumentoIdentidad doc = usuario.getDocumentoIdentidad();
        if (doc == null)
            return null;

        Map<String, Object> response = new HashMap<>();
        response.put("idUsuario", usuario.getIdUsuario());
        response.put("correoElectronico", usuario.getCorreoElectronico());
        response.put("telefono", usuario.getTelefono());
        response.put("idDocumentoIdentidad", doc.getIdDocumentoIdentidad());
        response.put("tipoDocumentoIdentidad", doc.getTipoDocumentoIdentidad());
        response.put("numeroDocumento", doc.getNumeroDocumento());
        response.put("nombre", doc.getNombre());
        response.put("apellido", doc.getApellido());
        response.put("direccion", doc.getDireccion());
        response.put("ciudad", doc.getCiudad());
        response.put("nacionalidad", doc.getNacionalidad());
        return response;
    }

    // 🔥 Eliminar usuario junto con su documento
    public boolean deleteUsuarioAndDoc(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return false;
        }

        DocumentoIdentidad doc = usuario.getDocumentoIdentidad();

        usuarioRepository.delete(usuario);

        if (doc != null && documentoIdentidadRepository.existsById(doc.getIdDocumentoIdentidad())) {
            documentoIdentidadRepository.delete(doc);
        }

        return true;
    }

    // Listar todos los usuarios
    public List<UsuarioDTO> listarUsuarios() {
        return getAll(usuarioRepository);
    }

    // Buscar usuario por ID
    public UsuarioDTO getUsuarioById(Long id) {
        return entityToDTO(usuarioRepository.findById(id).orElse(null));
    }
}
