package com.angel.apirest.profiles.services;

import com.angel.apirest.profiles.models.*;
import com.angel.apirest.profiles.dto.*;
import com.angel.apirest.profiles.repositorie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DocumentoIdentidadServices documentoIdentidadServices;

    @Autowired
    private DocumentoIdentidadRepository docuId;

    private List<UsuarioDTO> EntitytoDTO() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            List<UsuarioDTO> usuarioDTOList = new ArrayList<>();

            for (Usuario u : usuarios) {
                UsuarioDTO dto = new UsuarioDTO();
                dto.setIdUsuarioDTO(u.getIdUsuario());
                dto.setCorreoElectronicoDTO(u.getCorreoElectronico());
                dto.setTelefonoDTO(u.getTelefono());
                dto.setIdDocumentoIdentidad(u.getDocumentoIdentidad().getIdDocumentoIdentidad());
                usuarioDTOList.add(dto);
            }

            return usuarioDTOList;
        } catch (Exception e) {
            System.out.println("Error en la conversión de Entity a DTO: " + e);
            return null;
        }
    }

    private Usuario DTOtoEntity(UsuarioDTO usuarioDTO) {
        try {
            if (usuarioDTO == null)
                return null;

            Usuario usu = new Usuario();
            usu.setIdUsuario(usuarioDTO.getIdUsuarioDTO());
            usu.setCorreoElectronico(usuarioDTO.getCorreoElectronicoDTO());
            usu.setTelefono(usuarioDTO.getTelefonoDTO());

            DocumentoIdentidad doc = docuId.findById(usuarioDTO.getIdDocumentoIdentidad())
                    .orElseThrow(() -> new RuntimeException("Documento de identidad no encontrado"));

            usu.setDocumentoIdentidad(doc);
            return usu;
        } catch (Exception e) {
            System.out.println("Error en la conversión de DTO a Entity: " + e.getMessage());
            return null;
        }
    }

    public List<UsuarioDTO> listarUsuarios() {
        return EntitytoDTO();
    }

    public UsuarioDTO GetUsuarioById(Long idUsuario) {
        try {
            if (idUsuario == null || idUsuario <= 0)
                return null;

            Usuario usu = usuarioRepository.findById(idUsuario).orElse(null);
            if (usu == null)
                return null;

            UsuarioDTO dto = new UsuarioDTO();
            dto.setIdUsuarioDTO(usu.getIdUsuario());
            dto.setCorreoElectronicoDTO(usu.getCorreoElectronico());
            dto.setTelefonoDTO(usu.getTelefono());
            dto.setIdDocumentoIdentidad(usu.getDocumentoIdentidad().getIdDocumentoIdentidad());
            return dto;

        } catch (Exception e) {
            System.out.println("Error al obtener el usuario por ID: " + e);
            return null;
        }
    }

    public Map<String, Object> getUsuarioDetailsMap(Long idUsuario) {
        UsuarioDTO usuarioDTO = GetUsuarioById(idUsuario);
        if (usuarioDTO == null)
            return null;

        DocumentoIdentidadDTO docDTO = documentoIdentidadServices
                .FindByIdDocument(usuarioDTO.getIdDocumentoIdentidad());
        if (docDTO == null)
            return null;

        Map<String, Object> response = new HashMap<>();
        response.put("idUsuario", usuarioDTO.getIdUsuarioDTO());
        response.put("correoElectronico", usuarioDTO.getCorreoElectronicoDTO());
        response.put("telefono", usuarioDTO.getTelefonoDTO());
        response.put("idDocumentoIdentidad", docDTO.getIdDocumentoIdentidadDTO());
        response.put("tipoDocumentoIdentidad", docDTO.getTipoDocumentoIdentidadDTO());
        response.put("numeroDocumento", docDTO.getNumeroDocumentoDTO());
        response.put("nombre", docDTO.getNombreDTO());
        response.put("apellido", docDTO.getApellidoDTO());
        response.put("direccion", docDTO.getDireccionDTO());
        response.put("ciudad", docDTO.getCiudadDTO());

        return response;
    }

    public UsuarioDTO CreateDoctIdent(DocumentoIdentidadDTO docuIdDTO,
            String correoElectronicoDTO, String telefonoDTO) {
        try {
            if (docuIdDTO == null)
                return null;

            documentoIdentidadServices.CreateDocIdent(docuIdDTO);
            Long idDocuId = documentoIdentidadServices.FindbyNumIdentity(docuIdDTO.getNumeroDocumentoDTO());

            UsuarioDTO usuDTO = new UsuarioDTO();
            usuDTO.setCorreoElectronicoDTO(correoElectronicoDTO);
            usuDTO.setTelefonoDTO(telefonoDTO);
            usuDTO.setIdDocumentoIdentidad(idDocuId);

            return CreateUsuario(usuDTO);

        } catch (Exception e) {
            System.out.println("Error al crear el usuario con documento de identidad: " + e);
            return null;
        }
    }

    public UsuarioDTO CreateUsuario(UsuarioDTO usuDTO) {
        try {
            if (usuDTO == null)
                return null;

            Usuario usu = DTOtoEntity(usuDTO);
            Usuario savedUsuario = usuarioRepository.save(usu);

            UsuarioDTO savedDTO = new UsuarioDTO();
            savedDTO.setIdUsuarioDTO(savedUsuario.getIdUsuario());
            savedDTO.setCorreoElectronicoDTO(savedUsuario.getCorreoElectronico());
            savedDTO.setTelefonoDTO(savedUsuario.getTelefono());
            savedDTO.setIdDocumentoIdentidad(savedUsuario.getDocumentoIdentidad().getIdDocumentoIdentidad());

            return savedDTO;
        } catch (Exception e) {
            System.out.println("Error al crear el usuario: " + e);
            return null;
        }
    }

    public UsuarioDTO UpdateUsuario(UsuarioDTO usuDTO) {
        try {
            if (usuDTO == null || usuDTO.getIdUsuarioDTO() == null || usuDTO.getIdUsuarioDTO() <= 0)
                return null;

            if (!usuarioRepository.existsById(usuDTO.getIdUsuarioDTO()))
                return null;

            Usuario usu = DTOtoEntity(usuDTO);
            Usuario updatedUsuario = usuarioRepository.save(usu);

            UsuarioDTO updatedDTO = new UsuarioDTO();
            updatedDTO.setIdUsuarioDTO(updatedUsuario.getIdUsuario());
            updatedDTO.setCorreoElectronicoDTO(updatedUsuario.getCorreoElectronico());
            updatedDTO.setTelefonoDTO(updatedUsuario.getTelefono());
            updatedDTO.setIdDocumentoIdentidad(updatedUsuario.getDocumentoIdentidad().getIdDocumentoIdentidad());

            return updatedDTO;
        } catch (Exception e) {
            System.out.println("Error al actualizar el usuario: " + e);
            return null;
        }
    }

    public boolean DeleteUsuario(Long idUsuario) {
        try {
            if (idUsuario == null || idUsuario <= 0)
                return false;

            if (!usuarioRepository.existsById(idUsuario))
                return false;

            usuarioRepository.deleteById(idUsuario);
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar el usuario: " + e);
            return false;
        }
    }
}
