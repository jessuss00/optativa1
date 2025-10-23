package com.daw.pokedaw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.pokedaw.persistence.entities.Pokemon;
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
        // Reglas de negocio
        if (pokemon.getTipo1() == null) {
            throw new PokemonException("El tipo1 es obligatorio");
        }

        // Si solo hay un tipo -> tipo2 = NINGUNO
        if (pokemon.getTipo2() == null) {
            pokemon.setTipo2(Tipo.NINGUNO);
        }

        // No permitir tipo1 == tipo2
        if (pokemon.getTipo1().equals(pokemon.getTipo2()) && pokemon.getTipo2() != Tipo.NINGUNO) {
            throw new PokemonException("El tipo1 y tipo2 no pueden ser iguales");
        }


        // Si no se indica fecha de captura, no se modifica nada (puede ser null)
        pokemon.setId(0);
        return this.pokemonRepository.save(pokemon);
    }

    // Modificar (sin permitir cambiar tipoCaptura ni fechaCaptura)
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

        Pokemon pokemonBD = this.findById(idPokemon);

        // Solo se pueden modificar campos normales
        pokemonBD.setNombre(pokemon.getNombre());
        pokemonBD.setNumeroPokedex(pokemon.getNumeroPokedex());

        return this.pokemonRepository.save(pokemonBD);
    }

    // Eliminar
    public boolean delete(int idPokemon) {
        if (!this.pokemonRepository.existsById(idPokemon)) {
            throw new NotFoundExeptions("El Pokémon no existe");
        }

        this.pokemonRepository.deleteById(idPokemon);
        return this.pokemonRepository.existsById(idPokemon);
    }

    // Buscar por número de Pokédex
    public Pokemon buscarPorNumero(int numeroPokedex) {
        Optional<Pokemon> opt = this.pokemonRepository.findByNumeroPokedex(numeroPokedex);
        if (opt.isEmpty()) {
            throw new NotFoundExeptions("No existe ningún Pokémon con ese número de Pokédex");
        }
        return opt.get();
    }

    // Buscar capturados en un rango de fechas
    public List<Pokemon> capturadosEnRango(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) {
            throw new PokemonException("Debe indicar ambas fechas");
        }
        return this.pokemonRepository.findByFechaCapturaBetween(desde, hasta);
    }

    // Buscar por tipo (tipo1 o tipo2)
    public List<Pokemon> buscarPorTipo(Tipo tipo) {
        if (tipo == null) {
            throw new PokemonException("Debe indicar un tipo");
        }
        return this.pokemonRepository.findByTipo1OrTipo2(tipo, tipo);
    }

    // Cambiar tipos (endpoint específico)
    public Pokemon cambiarTipos(int idPokemon, Tipo nuevoTipo1, Tipo nuevoTipo2) {
        Pokemon pokemonBD = this.findById(idPokemon);

        if (nuevoTipo1 == null) {
            throw new PokemonException("El tipo1 es obligatorio");
        }
        if (nuevoTipo2 == null) {
            nuevoTipo2 = Tipo.NINGUNO;
        }
        if (nuevoTipo1.equals(nuevoTipo2) && nuevoTipo2 != Tipo.NINGUNO) {
            throw new PokemonException("El tipo1 y tipo2 no pueden ser iguales");
        }

        pokemonBD.setTipo1(nuevoTipo1);
        pokemonBD.setTipo2(nuevoTipo2);

        return this.pokemonRepository.save(pokemonBD);
    }
}
