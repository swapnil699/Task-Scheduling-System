package org.example.taskscheduling.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class  PokemonService {
    private RestTemplate restTemplate;

    public PokemonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchPokemonData(String pokemonName) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + pokemonName.toLowerCase();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Pokémon not found in external API");
        }
    }
}

