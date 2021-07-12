package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.openclassrooms.entrevoisins.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddFavoriteNeighbourAtListFavoritsTest {

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityTestRule = new ActivityTestRule<>(ListNeighbourActivity.class);

    @Test
    public void addFavoriteNeighbourAtListFavoritsTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.list_neighbours), isDisplayed(),
                        withParent(withId(R.id.container))));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.activity_profil_neighbour_favoris_button),
                        childAtPosition(
                                allOf(withId(R.id.activity_profil_neighbour),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.activity_profil_neighbour_toolbar_returnButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_profil_neighbour_toolbar),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction viewPager = onView(
                allOf(withId(R.id.container),
                        childAtPosition(
                                allOf(withId(R.id.main_content),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        viewPager.perform(swipeLeft());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.list_neighbours), isDisplayed(),
                        withParent(withId(R.id.container))));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        // Ajouté par Christophe le 08 juillet 2021
        //ici il est affiché le profil d'un voisin dans l'onglet Favorites
        //verifier si le bouton favoris est affiché
        onView(withId(R.id.activity_profil_neighbour_favoris_button)).check(matches(isDisplayed()));
        //et vérifier si le voisin  is favorite
        onView(allOf(withId(R.id.activity_profil_neighbour_favoris_button), withTagValue(is((Object) "isFavorite")))).check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
