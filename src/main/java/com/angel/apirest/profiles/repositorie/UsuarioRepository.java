package com.angel.apirest.profiles.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import com.angel.apirest.profiles.models.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {   

}
