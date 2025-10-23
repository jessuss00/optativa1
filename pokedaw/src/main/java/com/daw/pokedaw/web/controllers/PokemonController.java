package com.daw.pokedaw.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.pokedaw.service.PokemonService;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {
	@Autowired
	private PokemonService pokemonService;

}
