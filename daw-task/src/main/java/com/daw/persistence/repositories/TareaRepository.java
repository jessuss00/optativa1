package com.daw.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Tarea;
import com.daw.persistence.entities.enums.Estado;


public interface TareaRepository  extends ListCrudRepository<Tarea, Integer>{
	
	//Select * from where ESTADO=?
	List<Tarea> findByEstado(Estado estado);

	
	
	
}
