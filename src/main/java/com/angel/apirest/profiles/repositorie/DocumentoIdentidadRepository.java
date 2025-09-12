package com.angel.apirest.profiles.repositorie;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.angel.apirest.profiles.models.DocumentoIdentidad;

public interface DocumentoIdentidadRepository extends JpaRepository<DocumentoIdentidad, Long> {

    Optional<DocumentoIdentidad> findByNumeroDocumento(String NumeroDocumento);

}
