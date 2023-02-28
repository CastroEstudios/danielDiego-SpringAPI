package com.example.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modelo.Usuarios;
import com.example.repositorio.UsuariosRepository;

@RestController
public class UsuariosController {
	
	@Autowired
	private UsuariosRepository repo;
	
	// GET + POST + DELETE + PUT

	@GetMapping("usuarios/{correo}")
	Usuarios getUsuarioByCorreo(@PathVariable("correo") String correoRecibido) {
		return repo.findByCorreo(correoRecibido);
	}
	
	@DeleteMapping("usuarios/{correo}")
	ResponseEntity<?> borrarUsuarioCorreo(@PathVariable("correo") String correoRecibido){		
		this.repo.deleteByCorreo(correoRecibido);
		return new ResponseEntity<String>("El usuario se ha borrado correctamente",HttpStatus.OK);
	}
	
	@PostMapping("usuarios")
	ResponseEntity<String> anadirUsuario(@RequestBody Usuarios nuevosUsuario){
			this.repo.save(nuevosUsuario);
			return new ResponseEntity<String>("El usuario se ha añadido correctamente",HttpStatus.OK);
	}
	
	@PutMapping("usuarios/{correo}")
    public ResponseEntity<Usuarios> updateUser(@PathVariable("correo") String correoRecibido, @RequestBody Usuarios usuario) {
        Usuarios existingUsuario = this.repo.findByCorreo(correoRecibido);
        if (existingUsuario != null) {
        	existingUsuario.setContrasegna(usuario.getContrasegna());
        	this.repo.save(existingUsuario);
            return new ResponseEntity<>(existingUsuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
}
