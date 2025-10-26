package com.daw.pokedaw.web.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daw.pokedaw.persistence.entities.Pokemon;
import com.daw.pokedaw.persistence.entities.enums.Tipo;
import com.daw.pokedaw.service.PokemonService;
import com.daw.pokedaw.service.exeptions.NotFoundExeptions;
import com.daw.pokedaw.service.exeptions.PokemonException;
 
@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    // Obtener todos los Pokémon
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(this.pokemonService.findAll());
    }

    // Obtener un Pokémon por id
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(this.pokemonService.findById(id));
        } catch (NotFoundExeptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Crear un nuevo Pokémon
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Pokemon p) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.pokemonService.create(p));
        } catch (PokemonException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Actualizar un Pokémon
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Pokemon p) {
        try {
            return ResponseEntity.ok(this.pokemonService.update(id, p));
        } catch (NotFoundExeptions ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (PokemonException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Eliminar un Pokémon
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            this.pokemonService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundExeptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Buscar por número de Pokédex
    @GetMapping("/buscar/numero/{numero}")
    public ResponseEntity<?> buscarPorNumero(@PathVariable int numero) {
        try {
            return ResponseEntity.ok(this.pokemonService.findByNumeroPokedex(numero));
        } catch (NotFoundExeptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Buscar por tipo principal o secundario
    @GetMapping("/buscar/tipo/{tipo}")
    public ResponseEntity<?> buscarPorTipo(@PathVariable String tipo) {
        try {
            Tipo tipoEnum = Tipo.valueOf(tipo.toUpperCase());
            return ResponseEntity.ok(this.pokemonService.findByTipo(tipoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tipo inválido. Revisa los valores del enum TipoPokemon.");
        } catch (PokemonException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Buscar Pokémon capturados entre dos fechas
    @GetMapping("/capturados")
    public ResponseEntity<?> capturadosEnRango(@RequestParam String desde, @RequestParam String hasta) {
        try {
            LocalDate fechaDesde = LocalDate.parse(desde);
            LocalDate fechaHasta = LocalDate.parse(hasta);
            List<Pokemon> lista = this.pokemonService.findByFechaCaptura(fechaDesde, fechaHasta);
            return ResponseEntity.ok(lista);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Formato de fecha inválido. Usa el formato YYYY-MM-DD.");
        } catch (PokemonException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Cambiar tipos de un Pokémon
    @PutMapping("/{id}/cambiar-tipos")
    public ResponseEntity<?> cambiarTipos(
            @PathVariable int id,
            @RequestParam String tipo1,
            @RequestParam(required = false) String tipo2) {
        try {
            Tipo t1 = Tipo.valueOf(tipo1.toUpperCase());
            Tipo t2 = (tipo2 == null) ? Tipo.NINGUNO : Tipo.valueOf(tipo2.toUpperCase());
            return ResponseEntity.ok(this.pokemonService.cambiarTipo(id, t1, t2));
        } catch (NotFoundExeptions ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tipo inválido. Revisa los valores del enum TipoPokemon.");
        } catch (PokemonException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
