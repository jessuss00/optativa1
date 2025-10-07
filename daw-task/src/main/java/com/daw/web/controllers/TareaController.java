package com.daw.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> list(){
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
	public ResponseEntity<?> create(@RequestBody Tarea tarea){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.tareaService.create(tarea));

		} catch (TareaExceptions e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}
	}
	
	@PutMapping("/{idTarea}")
	public ResponseEntity<?> update(@PathVariable int idTarea, @RequestBody Tarea tarea){
		try {
			return ResponseEntity.ok(this.tareaService.update(tarea, idTarea));
		} catch (TareaNotFound ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch(TareaExceptions ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	

}
