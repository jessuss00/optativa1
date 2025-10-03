package com.daw.servicies;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.daw.persistence.entities.Tarea;
import com.daw.persistence.repositories.TareaRepository;

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
		return this.tareaRepository.findById(idTarea).get();
	}
	
	//create
	public Tarea create(Tarea tarea) {
		return this.tareaRepository.save(tarea);
	}
	//update
	//delete
	public void delete(int idTarea) {
		this.tareaRepository.deleteById(idTarea);
	}

}
