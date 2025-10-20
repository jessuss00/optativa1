package com.daw.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Tarea;
import com.daw.servicies.TareaService;
import com.daw.servicies.exeptions.TareaExceptions;
import com.daw.servicies.exeptions.TareaNotFound;

@RestController
@RequestMapping("/tareas")
public class TareaController {
	@Autowired
	private TareaService tareaService;

	@GetMapping
	public ResponseEntity<?> list() {
		return ResponseEntity.ok(this.tareaService.findAll());
	}

	@GetMapping("/{idTarea}")
	public ResponseEntity<?> findByid(@PathVariable int idTarea) {
		try {
			return ResponseEntity.ok(this.tareaService.findById(idTarea));
		} catch (TareaNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Tarea tarea) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.tareaService.create(tarea));

		} catch (TareaExceptions e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}
	}

	@PutMapping("/{idTarea}")
	public ResponseEntity<?> update(@PathVariable int idTarea, @RequestBody Tarea tarea) {
		try {
			return ResponseEntity.ok(this.tareaService.update(tarea, idTarea));
		} catch (TareaNotFound ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (TareaExceptions ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@DeleteMapping("/{idTarea}")
	public ResponseEntity<?> delete(@PathVariable int idTarea) {
		try {
			this.tareaService.delete(idTarea);
			return ResponseEntity.ok().build();
		} catch (TareaNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/pendientes")
	public ResponseEntity<?> pendientes() {
		return ResponseEntity.ok(this.tareaService.Pendientes());
	}

	@GetMapping("/progreso")
	public ResponseEntity<?> progreso() {
		return ResponseEntity.ok(this.tareaService.Progreso());
	}

	@GetMapping("/completadas")
	public ResponseEntity<?> completadas() {
		return ResponseEntity.ok(this.tareaService.Completadas());
	}

	@PutMapping("/{idTarea}/iniciar")
	public ResponseEntity<?> iniciarTarea(@PathVariable int idTarea) {
		try {
			return ResponseEntity.ok(this.tareaService.marcarEnProgreso(idTarea));
		} catch (TareaNotFound ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (TareaExceptions ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@PutMapping("/{idTarea}/completar")
	public ResponseEntity<?> completarTarea(@PathVariable int idTarea) {
		try {
			return ResponseEntity.ok(this.tareaService.marcarComoCompletada(idTarea));
		} catch (TareaNotFound ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (TareaExceptions ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@GetMapping("/vencidas")
	public ResponseEntity<?> vencidas() {
	    return ResponseEntity.ok(this.tareaService.obtenerVencidas());
	}

	// Obtener tareas no vencidas
	@GetMapping("/no-vencidas")
	public ResponseEntity<?> noVencidas() {
	    return ResponseEntity.ok(this.tareaService.obtenerNoVencidas());
	}

	// Buscar tareas por título
	@GetMapping("/buscar/{titulo}")
	public ResponseEntity<?> buscarPorTitulo(@PathVariable String titulo) {
	    return ResponseEntity.ok(this.tareaService.buscarPorTitulo(titulo));
	}

}
