package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    // ajout de 3 CONSTANTES pour @Test sur methodes ajoutées
    private final int TEST_NEIGHBOUR1 = 0;
    private final int TEST_NEIGHBOUR2 = 3;
    private final int TEST_NEIGHBOUR3 = 8;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(TEST_NEIGHBOUR1);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    // Faire test pour les 3 methodes ajoutées
    // getFavoriteNeighbours
    // addFavoriteNeighbour
    // deleteFavoriteNeighbour

    //@Test
    // Given (quels sont les parametres de fonctions utilisés?)
    // When  (dans quel contexte?)
    // Then  (quel doit etre le résultat?)


    @Test
    public void getFavoriteNeighboursWithSuccess() {
        //Given
        Neighbour testNeighbour1 = service.getNeighbours().get(TEST_NEIGHBOUR1);
        testNeighbour1.setFavorite(true);

        Neighbour testNeighbour2 = service.getNeighbours().get(TEST_NEIGHBOUR2);
        testNeighbour2.setFavorite(true);

        Neighbour testNeighbour3 = service.getNeighbours().get(TEST_NEIGHBOUR3);
        testNeighbour3.setFavorite(true);

        // When
        List<Neighbour> expectedList = Arrays.asList(testNeighbour1, testNeighbour2, testNeighbour3);
        List<Neighbour> actualList = service.getFavoriteNeighbours();

        // Then
        assertThat(actualList, containsInAnyOrder(expectedList.toArray()));

        // Une autre façon de tester
        //service.getNeighbours().get(1).setFavorite(true);
        //service.getNeighbours().get(3).setFavorite(true);
        //service.getNeighbours().get(8).setFavorite(true);

        //List<Neighbour> expectedList = Arrays.asList(service.getNeighbours().get(1), service.getNeighbours().get(3), service.getNeighbours().get(8));
        //List<Neighbour> actualList = service.getFavoriteNeighbours();

        //assertEquals(expectedList, actualList);  à utiliser si l'ordre dans la liste est important (liste ordonnée)
    }


    @Test
    public void addFavoriteNeighbourWithSuccess() {
        //Given
        service.getNeighbours().forEach(neighbour -> neighbour.setFavorite(false));
        Neighbour neighbourToAddInFavorites = service.getNeighbours().get(TEST_NEIGHBOUR3);

        // When
        service.addFavoriteNeighbour(neighbourToAddInFavorites);

        // Then
        assertTrue(service.getFavoriteNeighbours().contains(neighbourToAddInFavorites));

        // Une autre façon de tester : possible de faire aussi ce test avec 2 neighbours , mis dans une list attendu puis assertThat ou assertEquals
        // Neighbour neighbourToAddInFavorites1 = service.getNeighbours().get(TEST_NEIGHBOUR1);
        // Neighbour neighbourToAddInFavorites2 = service.getNeighbours().get(TEST_NEIGHBOUR2);
        // service.addFavoriteNeighbour(neighbourToAddInFavorites1);
        // service.addFavoriteNeighbour(neighbourToAddInFavorites2);

        // List<Neighbour> expectedList = Arrays.asList(neighbourToAddInFavorites1, neighbourToAddInFavorites2);
        // List<Neighbour> actualList = service.getFavoriteNeighbours();

        // assertEquals(expectedList, actualList);
        // assertThat(actualList, containsInAnyOrder(expectedList.toArray()));
    }


    @Test
    public void deleteFavoriteNeighbourWithSuccess() {
        // Given
        Neighbour neighbourToDeleteFromFavorites = service.getNeighbours().get(TEST_NEIGHBOUR2);

        // When
        service.addFavoriteNeighbour(neighbourToDeleteFromFavorites);
        service.deleteFavoriteNeighbour(neighbourToDeleteFromFavorites);

        // Then
        assertFalse(service.getFavoriteNeighbours().contains(neighbourToDeleteFromFavorites));
    }
}
