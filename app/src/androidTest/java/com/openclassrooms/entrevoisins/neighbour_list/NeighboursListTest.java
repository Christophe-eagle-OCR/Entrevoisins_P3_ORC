
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     * (soit : affichage du recyclerview avec la liste des neighbours)
     * Correction d'une erreur sur le test avec la methode Matchers.allOf() et isDisplayed()
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        //onView(ViewMatchers.withId(R.id.list_neighbours))  // code initial
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     * Correction d'une erreur sur le test avec la methode Matchers.allOf() et isDisplayed()
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2, and before we check how many neighbour is displayed
        //onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));   // code initial
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT));

        // When perform a click on a delete icon
        //onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));   // code initial
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        // Then : the number of element is 11
        //onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));     // code initial
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * When we select an item, the profil item is shown
     * On click neighbour, profil is displayed
     */
    @Test
    public void myNeighboursList_onSelectItem_shouldDisplayProfilScreen() {
        // Given : on start app
        // When perform a click on neighbour (item)
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        // Then : profil neighbour is displayed
        onView(ViewMatchers.withId(R.id.activity_profil_neighbour)).check(matches(isDisplayed()));
    }

    /**
     * When we select an item, the name off this item  is the same in the profil item shown
     * On click neighbour, profil is displayed
     */
    @Test
    public void myNeighboursList_onSelectItem_shouldNameOnDisplayProfilScreen() {
        // Given : on start app
        // When perform a click on neighbour (item)
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        // Then : profil neighbour is displayed
        onView(ViewMatchers.withId(R.id.activity_profil_neighbour)).check(matches(isDisplayed()));

        // then  : check that the good neighbourName is displayed
        onView(ViewMatchers.withId(R.id.info_card_name_txt)).check(matches(withText("Vincent")));
        //onView(ViewMatchers.withId(R.id.activity_profil_neighbour_toolbar_name_txt)).check(matches(withText("Vincent")));
    }

    /**
     * test vérifiant que l’onglet Favoris n’affiche que les voisins marqués comme
     * favoris.
     */
    @Test
    public void myNeighboursList_onSelectItemAndAddFavorite_shouldViewFavoriteIsNotBeEmpty() {

        // de la page d'accueil , click sur un voisin
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        // click sur le bouton favoris dans la vue du profil
        onView(ViewMatchers.withId(R.id.activity_profil_neighbour_favoris_button)).perform(click());

        // retour en arrière vers la page d'accueil
        //pressBack();
        onView(ViewMatchers.withId(R.id.activity_profil_neighbour_toolbar_returnButton)).perform(click());

        //click sur l'onglet Favorites ou faire défiler vers la gauche l'écran
        //onView(withContentDescription("Favorites")).perform(click());
        onView(withId(R.id.container)).perform(swipeLeft());

        //vérifier l'affichage des favoris
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed())).check(matches(isDisplayed()));
        //onView(Matchers.allOf(ViewMatchers.withId(R.id.item_list_name), isDisplayed())).check(matches(withText("Vincent")));
        //onView(allOf(withText("Vincent"),isDisplayed())).check(matches(isDisplayed()));
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(1));

        // click sur le voisin affiché dans l'onglet favorites puis on verifie qu'il a bien le meme nom que celui mis en favoris précédemment
        onView(Matchers.allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.activity_profil_neighbour_toolbar_name_txt)).check(matches(withText("Vincent")));


    }
}