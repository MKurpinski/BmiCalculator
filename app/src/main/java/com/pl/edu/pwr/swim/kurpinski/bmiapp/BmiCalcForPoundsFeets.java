package com.pl.edu.pwr.swim.kurpinski.bmiapp;

/**
 * Created by michal on 21.03.2017.
 */

public class BmiCalcForPoundsFeets implements IBmiCalc {
    public static float minimalHeight = Utils.minimalHeight*Utils.feetsPerMeter;
    public static float maximalHeight = Utils.maximumHeight*Utils.feetsPerMeter;
    public static float minimalMass = Utils.minimalMass*Utils.poundsPerKilo;
    public static float maximalMass = Utils.maximumMass*Utils.poundsPerKilo;
    @Override
    public float countBmi(float mass, float height) {
        if(isValidHeight(height) && isValidMass(mass)){
            mass = mass/Utils.poundsPerKilo;
            height = height/Utils.feetsPerMeter;
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
