package com.pl.edu.pwr.swim.kurpinski.bmiapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.massET) EditText massET;
    @BindView(R.id.heightET) EditText heightET;
    @BindView(R.id.resultTV) TextView result;
    @BindView(R.id.my_toolbar) Toolbar toolbar;
    @BindView(R.id.spinnerMeasure) Spinner spinner;
    SharedPreferences sharedPreferences;
    IBmiCalc bmiCalculatorForKgM;
    IBmiCalc bmiCalculatorForPoundsM;
    Boolean canResetEditTexts = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(Utils.sharedPreferencesKey, Context.MODE_PRIVATE);
        bmiCalculatorForKgM = new BmiCalcForKgM();
        bmiCalculatorForPoundsM = new BmiCalcForPoundsFeets();
        ButterKnife.bind(MainActivity.this);
        readAndSetValuesFromMemory();
        setSupportActionBar(toolbar);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Utils.massKey, massET.getText().toString());
        savedInstanceState.putString(Utils.heightKey, heightET.getText().toString());
        savedInstanceState.putString(Utils.bmiKey, result.getText().toString());
        savedInstanceState.putInt(Utils.unitKey,spinner.getSelectedItemPosition());
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        massET.setText(savedInstanceState.getString(Utils.massKey));
        heightET.setText(savedInstanceState.getString(Utils.heightKey));
        String resultString = savedInstanceState.getString(Utils.bmiKey);
        if(!resultString.equals("")){
            presentBmi(Float.parseFloat(resultString));
        }
        spinner.setSelection(savedInstanceState.getInt(Utils.unitKey));
        canResetEditTexts = false;
    }
    @OnItemSelected(R.id.spinnerMeasure)
    void changeMeasure() {
            if (spinner.getSelectedItemPosition()==Utils.indexOfFtPs) {
                setHints(getString(R.string.massHintPn), getString(R.string.heightHintF));
            }
            else{
                setHints(getString(R.string.massHintKg), getString(R.string.heightHintM));
            }
        if(canResetEditTexts){
            clearTextOdEditTexts();
        }
        else{
            canResetEditTexts = true;
        }

    }

    @OnClick(R.id.confirmB)
     void calculateBmi() {
            IBmiCalc bmiCalc = spinner.getSelectedItem().toString().equals(getString(R.string.kgM))?bmiCalculatorForKgM:bmiCalculatorForPoundsM;
            try{
                closeKeyboard();
                Float bmi = bmiCalc.countBmi(Float.parseFloat(massET.getText().toString()),Float.parseFloat(heightET.getText().toString()));
                presentBmi(bmi);
            }
            catch (IllegalArgumentException ex){
                Toast.makeText(MainActivity.this, R.string.invalidInput, Toast.LENGTH_SHORT).show();
            }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        setEnabledFieldsInMenu(menu);
        return true;
    }
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(featureId == AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR && menu != null){
            setEnabledFieldsInMenu(menu);
        }
        return super.onMenuOpened(featureId, menu);
    }
    private void setEnabledFieldsInMenu(Menu menu){
        menu.getItem(Utils.indexOfSave).setEnabled(checkIfValidToSave());
        menu.getItem(Utils.indexOfShare).setEnabled(CheckIfBmiCalculated());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                handleShareClick();
                break;
            case R.id.save:
                handleSaveClick();
                break;
            case R.id.about:
                handleAboutClick();
                break;
        }
        return true;
    }

    private void handleAboutClick() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    private void readAndSetValuesFromMemory() {
        massET.setText(sharedPreferences.getString(Utils.massKey,""));
        heightET.setText(sharedPreferences.getString(Utils.heightKey,""));
        spinner.setSelection(sharedPreferences.getInt(Utils.unitKey,Utils.indexOfKgM),false);
    }
    private void handleSaveClick() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Utils.massKey, massET.getText().toString());
        editor.putString(Utils.heightKey, heightET.getText().toString());
        editor.putInt(Utils.unitKey, spinner.getSelectedItemPosition());
        editor.commit();
        Toast.makeText(MainActivity.this,Utils.savedSuccessfully,Toast.LENGTH_LONG).show();
    }

    private void handleShareClick(){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(Utils.typeOfIntent);
        share.putExtra(Intent.EXTRA_TEXT, chooseTextOfShare());

        startActivity(Intent.createChooser(share, Utils.titleOfShare));
    }
    private String chooseTextOfShare(){
        float bmi = Float.parseFloat(result.getText().toString());
        String message;
        if(bmi<Utils.toSmallBmi){
            message = Utils.toSmallBmiMessage;
        }
        else if(bmi>=Utils.toBigBmi){
            message = Utils.toBigBmiMessage;
        }
        else{
            message = Utils.correctBmiMessage;
        }
        message+= bmi;
        return message;
    }
    private boolean CheckIfBmiCalculated() {
        return !result.getText().toString().equals("");
    }
    private boolean checkIfValidToSave(){
        if(massET.getText().toString().equals("") || heightET.getText().toString().equals("")){
            return false;
        }
            IBmiCalc bmiCalc = spinner.getSelectedItem().toString().equals(getString(R.string.kgM)) ? bmiCalculatorForKgM : bmiCalculatorForPoundsM;
            return bmiCalc.isValidMass(Float.parseFloat(massET.getText().toString())) & bmiCalc.isValidHeight(Float.parseFloat(heightET.getText().toString()));
    }
    private void presentBmi(float bmi) {
        if(bmi<Utils.toSmallBmi){
            result.setTextColor(Color.BLUE);
        }
        else if(bmi>=Utils.toBigBmi){
            result.setTextColor(Color.RED);
        }
        else{
            result.setTextColor(Color.GREEN);
        }
        result.setText(String.format(Locale.ROOT, "%.2f", bmi));
    }
    private void setHints(String massH, String heightH){
        massET.setHint(massH);
        heightET.setHint(heightH);
    }
    private void clearTextOdEditTexts() {
        massET.setText("");
        heightET.setText("");
        result.setText("");
    }
    private void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(massET.getWindowToken(), 0);
    }
}
