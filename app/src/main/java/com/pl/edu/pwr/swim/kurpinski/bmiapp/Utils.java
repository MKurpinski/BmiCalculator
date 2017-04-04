package com.pl.edu.pwr.swim.kurpinski.bmiapp;

/**
 * Created by michal on 21.03.2017.
 */

public class Utils {
    public static final float toSmallBmi = 18.5f;
    public static final float toBigBmi = 25f;
    public static final float feetsPerMeter = 3.280839895f;
    public static final float poundsPerKilo = 2.205071664f;
    public static final int indexOfSave = 0;
    public static final int indexOfShare = 1;
    public static final int indexOfKgM = 0;
    public static final int indexOfFtPs = 1;
    public static final float minimalMass = 10f;
    public static final float minimalHeight = 0.5f;
    public static final float maximumMass = 200f;
    public static final float maximumHeight = 2.5f;
    public static final String typeOfIntent = "text/plain";
    public static final String sharedPreferencesKey = "bmiApp";
    public static final String toSmallBmiMessage = "Muszę więcej jeść :D ! Moje BMI to: ";
    public static final String correctBmiMessage = "Jestem super! <3 Moje BMI to: ";
    public static final String toBigBmiMessage = "Muszę troszkę zrzucić :( ! Moje BMI to: ";
    public static final String titleOfShare = "Podziel się swoim BMI ze znajomymi!";
    public static final String massKey = "mass";
    public static final String heightKey = "heightKey";
    public static final String unitKey = "unitKey";
    public static final String bmiKey = "bmiKey";
    public static final String savedSuccessfully = "Saved successfully!";
    public static final String titleAbout = "About author";
    public static final String githubAccount = "https://github.com/MKurpinski";
}
