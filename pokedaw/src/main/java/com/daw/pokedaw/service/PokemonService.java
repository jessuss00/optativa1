package com.daw.pokedaw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.pokedaw.persistence.entities.Pokemon;
import com.daw.pokedaw.persistence.entities.enums.Pokeball;
import com.daw.pokedaw.persistence.entities.enums.Tipo;
import com.daw.pokedaw.persistence.repositories.PokemonRepository;

import com.daw.pokedaw.service.exeptions.NotFoundExeptions;
import com.daw.pokedaw.service.exeptions.PokemonException;

@Service
public class PokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;

    // Obtener todos
    public List<Pokemon> findAll() {
        return this.pokemonRepository.findAll();
    }

    // Obtener por ID
    public Pokemon findById(int idPokemon) {
        if (!this.pokemonRepository.existsById(idPokemon)) {
            throw new NotFoundExeptions("El id del Pokémon no existe");
        }
        return this.pokemonRepository.findById(idPokemon).get();
    }

    // Crear
    public Pokemon create(Pokemon pokemon) {
        if (pokemon.getTipo1() == null) {
            throw new PokemonException("El tipo1 es obligatorio");
        }

        if (pokemon.getTipo2() == null) {
            pokemon.setTipo2(Tipo.NINGUNO);
        }

        if (pokemon.getTipo1().equals(pokemon.getTipo2()) && pokemon.getTipo2() != Tipo.NINGUNO) {
            throw new PokemonException("El tipo1 y tipo2 no pueden ser iguales");
        }


        pokemon.setId(0);
        return this.pokemonRepository.save(pokemon);
    }

    // Update
    public Pokemon update(Pokemon pokemon, int idPokemon) {
        if (pokemon.getId() != idPokemon) {
            throw new PokemonException("El id del body y del path no coinciden");
        }

        if (!this.pokemonRepository.existsById(idPokemon)) {
            throw new NotFoundExeptions("El Pokémon no existe");
        }
      

        if (pokemon.getFechaCaptura() != null) {
            throw new PokemonException("No se puede modificar la fecha de captura");
        }
        if (pokemon.getTipo1() != null || pokemon.getTipo2() !=null) {
            throw new PokemonException("Solo se puede modificar el tipo con el endpoint");
        }

        Pokemon pokemonBD = this.findById(idPokemon);

        pokemonBD.setNombre(pokemon.getNombre());
        pokemonBD.setNumeroPokedex(pokemon.getNumeroPokedex());

        return this.pokemonRepository.save(pokemonBD);
    }

    // Delete
    public boolean delete(int idPokemon) {
        if (!this.pokemonRepository.existsById(idPokemon)) {
            throw new NotFoundExeptions("El Pokémon no existe");
        }

        this.pokemonRepository.deleteById(idPokemon);
        return this.pokemonRepository.existsById(idPokemon);
    }

    // Buscar por número de Pokédex
    public List<Pokemon> findByNumeroPokedex(int numeroPokedex) {
       return this.pokemonRepository.findByNumeroPokedex(numeroPokedex);
        
    }

    // Buscar capturados en un rango de fechas
    public List<Pokemon> findByFechaCaptura(LocalDate desde, LocalDate hasta) {
        return this.pokemonRepository.findByFechaCapturaBetween(desde, hasta);
    }

    // Buscar por tipo (tipo1 o tipo2)
 
    public List<Pokemon> findByTipo(Tipo tipo) {
        return this.pokemonRepository.findByTipo1OrTipo2(tipo, tipo);
    }

    // Cambiar tipos 
    public Pokemon cambiarTipos(int idPokemon, Tipo nuevoTipo1, Tipo nuevoTipo2) {
        Pokemon pokemonBD = this.findById(idPokemon);
        if (nuevoTipo1 != null && pokemonBD.getTipo1() != nuevoTipo1) {
            pokemonBD.setTipo1(nuevoTipo1);
        }
        if (nuevoTipo2 != null && pokemonBD.getTipo2() != nuevoTipo2) {
            pokemonBD.setTipo2(nuevoTipo2);
        }

        if (pokemonBD.getTipo1() == pokemonBD.getTipo2() && pokemonBD.getTipo1() != Tipo.NINGUNO) {
            throw new PokemonException("El tipo1 y tipo2 no pueden ser iguales");
        }

        return this.pokemonRepository.save(pokemonBD);
    }
}
