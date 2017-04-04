package com.pl.edu.pwr.swim.kurpinski.bmiapp;

import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by michal on 26.03.2017.
 */

public class MainActivityInstrumentationTest {

        private String massToBeTypedInKilos = "80";
        private String massToBeTypedInPounds = "180";
        private String poundFeet = "ft/ps";
        private String heightToBeTypedInMetres = "2.0";
        private String heightToBeTypedInFeets = "6.0";
        private String expectedResultKgM = "20.00";
        private String expectedResultFtP = "24.41";

        // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
        @Rule
        public ActivityTestRule<MainActivity> activityTestRule =
                new ActivityTestRule<>(MainActivity.class);

        @Test
        public void validateMassEditText() {
            onView(withId(R.id.massET)).perform(typeText(massToBeTypedInKilos)).check(matches(withText(massToBeTypedInKilos)));
        }
        @Test
        public void validateHeightEditText() {
                onView(withId(R.id.heightET)).perform(typeText(heightToBeTypedInMetres)).check(matches(withText(heightToBeTypedInMetres)));
        }
        @Test
        public void ValidateResultInKgM(){
                onView(withId(R.id.massET)).perform(typeText(massToBeTypedInKilos));
                onView(withId(R.id.heightET)).perform(typeText(heightToBeTypedInMetres));
                onView(withId(R.id.confirmB)).perform(click());
                onView(withId(R.id.resultTV)).check(matches(withText(expectedResultKgM)));
        }
        @Test
        public void ValidateChangeSpinnerOnPtF(){
            onView(withId(R.id.spinnerMeasure)).perform(click());
            onData(allOf(is(instanceOf(String.class)), is(poundFeet))).perform(click());
            onView(withId(R.id.massET)).check(matches(withHint(R.string.massHintPn)));
            onView(withId(R.id.heightET)).check(matches(withHint(R.string.heightHintF)));
        }
        @Test
        public void ValidateResultInPtF(){
            onView(withId(R.id.spinnerMeasure)).perform(click());
            onData(allOf(is(instanceOf(String.class)), is(poundFeet))).perform(click());
            onView(withId(R.id.massET)).perform(typeText(massToBeTypedInPounds));
            onView(withId(R.id.heightET)).perform(typeText(heightToBeTypedInFeets));
            onView(withId(R.id.confirmB)).perform(click());
            onView(withId(R.id.resultTV)).check(matches(withText(expectedResultFtP)));
        }
        @Test
        public void ValidateDisplayingToastWithIncorrectData(){
            onView(withId(R.id.massET)).perform(typeText(massToBeTypedInKilos));
            onView(withId(R.id.heightET)).perform(typeText(heightToBeTypedInFeets));
            onView(withId(R.id.confirmB)).perform(click());
            onView(withText(R.string.invalidInput)).inRoot(new ToastMatcher())
                    .check(matches(isDisplayed()));
        }

}
