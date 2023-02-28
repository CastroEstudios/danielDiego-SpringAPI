package com.example.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.modelo.Usuarios;

import jakarta.transaction.Transactional;

public interface UsuariosRepository extends JpaRepository<Usuarios,String> {
		
	@Transactional
    long deleteByCorreo(String correo);
	
	@Transactional
	Usuarios findByCorreo(String correo);
}
