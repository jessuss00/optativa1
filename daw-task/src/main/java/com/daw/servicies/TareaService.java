package com.daw.servicies;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	//delete
	public void delete(int idTarea) {
		this.tareaRepository.deleteById(idTarea);
	}

}
