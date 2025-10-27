package com.daw.pokedaw.persistence.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.pokedaw.persistence.entities.Pokemon;
import com.daw.pokedaw.persistence.entities.enums.Tipo;


public interface PokemonRepository extends ListCrudRepository<Pokemon, Integer> {

    List<Pokemon> findByNumeroPokedex(Integer numeroPokedex);

    List<Pokemon> findByFechaCapturaBetween(LocalDate desde, LocalDate hasta);

    List<Pokemon> findByTipo1OrTipo2(Tipo tipo1, Tipo tipo2);;

}
