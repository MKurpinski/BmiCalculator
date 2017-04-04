package com.pl.edu.pwr.swim.kurpinski.bmiapp;

/**
 * Created by michal on 14.03.2017.
 */

public class BmiCalcForKgM implements IBmiCalc {
    public static float minimalHeight = Utils.minimalHeight;
    public static float maximalHeight = Utils.maximumHeight;
    public static float minimalMass = Utils.minimalMass;
    public static float maximalMass = Utils.maximumMass;
    @Override
    public float countBmi(float mass, float height) {
        if(isValidHeight(height) && isValidMass(mass)){
            return mass/(height*height);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isValidMass(float mass) {
        return mass<=maximalMass && mass>=minimalMass;
    }

    @Override
    public boolean isValidHeight(float height) {
        return height<=maximalHeight && height>=minimalHeight;
    }
}
