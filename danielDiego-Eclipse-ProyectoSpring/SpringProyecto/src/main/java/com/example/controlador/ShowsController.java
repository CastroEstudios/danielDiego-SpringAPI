package com.example.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.modelo.Shows;
import com.example.repositorio.ShowsRepository;

@RestController
public class ShowsController {
	
	@Autowired
	private ShowsRepository repo;
	
	// GET + POST + DELETE + PUT
	@GetMapping("shows")
	List<Shows> getAllshows(@RequestParam(name = "titulo",required = false)
	String tituloParametro){
		if(tituloParametro == null) return repo.findAll();
		else {
			return repo.findBytipoStartingWith(tituloParametro);
		}
	}
	
	@GetMapping("shows/{titulo}")
	Shows getShowById(@PathVariable("titulo") String tituloRecibido) {
		return repo.findByTitulo(tituloRecibido);
	}
	
	@DeleteMapping("shows/{titulo}")
	ResponseEntity<?> borrarShowsTitulo(@PathVariable("titulo") String tituloRecibido){		
		this.repo.deleteByTitulo(tituloRecibido);
		return new ResponseEntity<String>("El show se ha borrado correctamente",HttpStatus.OK);
	}
	
	@PostMapping("shows")
	ResponseEntity<String> anadirShow(@RequestBody Shows nuevosShow){
			this.repo.save(nuevosShow);
			return new ResponseEntity<String>("El show se ha añadido correctamente",HttpStatus.OK);
	}
	
	@PutMapping("shows/{titulo}")
    public ResponseEntity<Shows> updateUser(@PathVariable("titulo") String tituloRecibido, @RequestBody Shows show) {
        Shows existingShow = this.repo.findByTitulo(tituloRecibido);
        if (existingShow != null) {
        	existingShow.setShowId(show.getShowId());
        	existingShow.setTitulo(show.getTitulo());
        	existingShow.setActores(show.getActores());
        	existingShow.setAnyoLanzamiento(show.getAnyoLanzamiento());
        	existingShow.setDescripcion(show.getDescripcion());
        	existingShow.setDirector(show.getDirector());
        	existingShow.setDuracion(show.getDuracion());
        	existingShow.setPais(show.getPais());
        	existingShow.setTags(show.getTags());
        	existingShow.setRating(show.getRating());
        	existingShow.setFecha(show.getFecha());
        	existingShow.setTipo(show.getTipo());
        	this.repo.save(existingShow);
            return new ResponseEntity<>(existingShow, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
}
