package com.daw.persistence.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Tarea;
import com.daw.persistence.entities.enums.Estado;


public interface TareaRepository  extends ListCrudRepository<Tarea, Integer>{
	
	//Select * from where ESTADO=?
	List<Tarea> findByEstado(Estado estado);
	
	 // Tareas vencidas -> fechaVencimiento < hoy
    List<Tarea> findByFechaVencimientoBefore(LocalDate fecha);

    // Tareas no vencidas -> fechaVencimiento > hoy
    List<Tarea> findByFechaVencimientoAfter(LocalDate fecha);

    // Buscar por título que contenga un texto (ignorando mayúsculas/minúsculas)
    List<Tarea> findByTituloContainingIgnoreCase(String titulo);

	
	
	
}
