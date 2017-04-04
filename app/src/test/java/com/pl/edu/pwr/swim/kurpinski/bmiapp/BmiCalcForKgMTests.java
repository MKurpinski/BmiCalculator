package com.pl.edu.pwr.swim.kurpinski.bmiapp;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by michal on 14.03.2017.
 */

public class BmiCalcForKgMTests {
    @Test
    public void massUnderZeroIsInvalid(){
        //GIVEN
        float testMass = -1.0f;
        //WHEN
        BmiCalcForKgM bmiCalculator = new BmiCalcForKgM();
        //THEN
        junit.framework.Assert.assertFalse(bmiCalculator.isValidMass(testMass));
    }
    @Test
    public void heightUnderZeroIsInvalid() {
        //GIVEN
        float testHeight = -1.0f;
        //WHEN
        BmiCalcForKgM bmiCalculator = new BmiCalcForKgM();
        //THEN
        junit.framework.Assert.assertFalse(bmiCalculator.isValidHeight(testHeight));
    }
    @Test
    public void massAboveMaximalMassIsInvalid(){
        //GIVEN
        float testMass = BmiCalcForKgM.maximalMass+1;
        //WHEN
        BmiCalcForKgM bmiCalculator = new BmiCalcForKgM();
        //THEN
        junit.framework.Assert.assertFalse(bmiCalculator.isValidMass(testMass));
    }
    @Test
    public void heightAboveMaximalHeightIsInvalid(){
        //GIVEN
        float testHeigth = BmiCalcForKgM.maximalHeight+1;
        //WHEN
        BmiCalcForKgM bmiCalculator = new BmiCalcForKgM();
        //THEN
        junit.framework.Assert.assertFalse(bmiCalculator.isValidHeight(testHeigth));
    }
    @Test
    public void massBelowMaximalMassIsValid(){
        //GIVEN
        float testMass = BmiCalcForKgM.maximalMass-1;
        //WHEN
        BmiCalcForKgM bmiCalculator = new BmiCalcForKgM();
        //THEN
        junit.framework.Assert.assertTrue(bmiCalculator.isValidMass(testMass));
    }
    @Test
    public void heightBelowMaximalHeightIsValid(){
        //GIVEN
        float testHeigth = BmiCalcForKgM.maximalHeight-1;
        //WHEN
        BmiCalcForKgM bmiCalculator = new BmiCalcForKgM();
        //THEN
        junit.framework.Assert.assertTrue(bmiCalculator.isValidHeight(testHeigth));
    }
    @Test
    public void CalcBmiWithValidDataShouldReturnCorrectBmi(){
        //GIVEN
        float testHeigth = 2;
        float testMass = 80;
        float expected = 20;
        //WHEN
        BmiCalcForKgM bmiCalculator = new BmiCalcForKgM();
        //THEN
        junit.framework.Assert.assertEquals(expected,bmiCalculator.countBmi(testMass,testHeigth));
    }
}
