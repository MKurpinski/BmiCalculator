package com.pl.edu.pwr.swim.kurpinski.bmiapp;

/**
 * Created by michal on 14.03.2017.
 */

public interface IBmiCalc {
    float countBmi(float mass, float height);
    boolean isValidMass(float mass);
    boolean isValidHeight(float height);

}
