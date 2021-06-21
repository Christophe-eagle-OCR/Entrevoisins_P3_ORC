package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }


    /* methode ajoutée :
    va chercher le position de la position du neighbour dans la liste des neighbours
      et passe le neighbour en favorite avec .setFavorite */
    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void addFavoriteNeighbour(Neighbour neighbour) {
        int position = neighbours.indexOf(neighbour);
        neighbours.get(position).setFavorite(true);
    }

    /* methode ajoutée */
    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void deleteFavoriteNeighbour(Neighbour neighbour) {
        int position = neighbours.indexOf(neighbour);
        neighbours.get(position).setFavorite(false);
    }


    /*methode ajoutée pour créer la liste des favoris
     boucle for  if
     for : qui pour chaque itération (i) associé au neighbour courant de la liste des neighbours
       on recupère sa position dans la liste des neighbours par .get(i)
     if : et si le neighbour courant est favoris ,
       on l'ajoute à la liste favoriteNeighbours dans une ArrayListe qui a été créer dans cette methode*/
    /**
     * add getFavoriteNeighbours
     *
     * @return favoriteNeighbours , the list off favorites
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {

        List<Neighbour> favoriteNeighbours = new ArrayList<>();

        for (int i = 0; i < neighbours.size(); i++) {
            Neighbour currentIteratedNeighbour = neighbours.get(i);
            if (currentIteratedNeighbour.isFavorite()) {
                favoriteNeighbours.add(currentIteratedNeighbour);
            }
        }
        return favoriteNeighbours;
    }
}
