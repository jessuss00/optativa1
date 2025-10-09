package com.daw.servicies;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.daw.web.controllers.TareaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.daw.persistence.entities.Tarea;
import com.daw.persistence.entities.enums.Estado;
import com.daw.persistence.repositories.TareaRepository;
import com.daw.servicies.exeptions.TareaExceptions;
import com.daw.servicies.exeptions.TareaNotFound;

@Service
public class TareaService {

    
	@Autowired
	private TareaRepository tareaRepository;

	
	//findAll
	@GetMapping
	public List<Tarea> findAll(){
		return this.tareaRepository.findAll();
	}
	//findByID
	@GetMapping("/{idTarea}")
	public Tarea findById(@PathVariable int idTarea) {
		if (!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFound("El id de la tarea no existe");
		}
		
		return this.tareaRepository.findById(idTarea).get();
	}
	
	//create
	public Tarea create(Tarea tarea) {
		if (tarea.getFechaVencimiento().isBefore(LocalDate.now())) {
			throw new TareaExceptions("La fecha de vencimiento debe ser posterior");

		}
		tarea.setId(0);
		tarea.setEstado(Estado.PENDIENTE);
		tarea.setFechaCreacion(LocalDate.now());
		return this.tareaRepository.save(tarea);
	}
	//update
	public Tarea update(Tarea tarea, int idTarea) {
		if(tarea.getId()!=idTarea) {
			throw new TareaExceptions("El id del body y del path no coinciden");
		}
		if (!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFound("El id de la tarea no existe");
		}
		if (tarea.getEstado()!=null) {
			throw new TareaExceptions("No se puede modificar el estado");
		}
		if (tarea.getFechaCreacion()!=null) {
			throw new TareaExceptions("No se puede modificar la fecha de creacion");
		}
		Tarea tareaBD = this.findById(idTarea);
		tareaBD.setTitulo(tarea.getTitulo());
		tareaBD.setDescripcion(tarea.getDescripcion());
		tareaBD.setFechaVencimiento(tarea.getFechaVencimiento());
		
		return this.tareaRepository.save(tarea);	
		}
	
	//delete
	public boolean delete(int idTarea) {
		
		if (this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFound("La tarea no existe");
		}
			this.tareaRepository.deleteById(idTarea);
		
		
		return this.tareaRepository.existsById(idTarea);
		
	}
	
	public Tarea marcarEnProgreso(int idTarea) {
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaExceptions("El id del body y del path no coinciden");
		}
		if (this.tareaRepository.findById(idTarea).get().getEstado().equals(Estado.PENDIENTE)) {
			throw new TareaExceptions("La tarea ya esta completada o ya esta en pogreso");
			
		}
		 Tarea tarea = this.findById(idTarea);
		 tarea.setEstado(Estado.EN_PROGRESO);
		 
		 return this.tareaRepository.save(tarea);
	}

}
