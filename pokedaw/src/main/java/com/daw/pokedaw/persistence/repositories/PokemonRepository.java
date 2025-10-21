package com.daw.pokedaw.persistence.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.pokedaw.persistence.entities.Pokemon;

public interface PokemonRepository extends ListCrudRepository<Pokemon, Integer> {
	
	

}
