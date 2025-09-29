package com.daw.servicies;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Tarea;
import com.daw.persistence.repositories.TareaRepository;

@Service
public class TareaService {
	@Autowired
	private TareaRepository TareaRepository;
	
	//findAll
	public List<Tarea> findAll(){
		return this.TareaRepository.findAll();
	}
	//findByID
	public Tarea findById(int idTarea) {
		return this.TareaRepository.findById(idTarea).get();
	}
	
	//create
	
	//update
	//delete

}
