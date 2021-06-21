package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     *
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     *
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     *
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);


    // methode ajoutée
    /**
     * Add a favorite neighbour
     *
     * @param neighbour
     */
    void addFavoriteNeighbour(Neighbour neighbour);


    // methode ajoutée
    /**
     * Get all favorites neighbours (pour les mettre dans l'onglet Favorites)
     */
    List<Neighbour> getFavoriteNeighbours();


    // methode ajoutée
    /**
     * Deletes a favorite neighbour
     *
     * @param neighbour
     */
    void deleteFavoriteNeighbour(Neighbour neighbour);

}
