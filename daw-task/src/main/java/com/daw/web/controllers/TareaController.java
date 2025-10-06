package com.daw.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public List<Tarea> list(){
		return this.tareaService.findAll();
	}
	@GetMapping("/{idTarea}")
	public ResponseEntity<?> findByid(@PathVariable int idTarea) {
		try {
			return ResponseEntity.ok(this.tareaService.findById(idTarea));
		} catch (TareaNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	public ResponseEntity<?> create(@RequestBody Tarea tarea){
		try {
			return ResponseEntity.ok(this.tareaService.create(tarea));

		} catch (TareaExceptions e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}
	}
	

}
