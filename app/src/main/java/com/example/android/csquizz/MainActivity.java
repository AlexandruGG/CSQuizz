package com.example.android.csquizz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Views used to manage scrolling and focus
    ScrollView quizView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates a ScrollView object
        quizView = (ScrollView) findViewById(R.id.quiz_scroll);

    }

    /***
     * This method scrolls down and focuses on a dummy view when "Begin" button is pressed
     ***/
    public void scrollDown(View v) {
        findViewById(R.id.user_name).clearFocus();
        findViewById(R.id.focus).requestFocus();
        quizView.smoothScrollTo(0, findViewById(R.id.question2).getTop());
    }

    /***
     * This method generates a toast on the screen
     ***/
    public void makeToastText(int scoreResId, String finalMessage) {
        Toast.makeText(this, getString(scoreResId) + finalMessage, Toast.LENGTH_LONG).show();
    }

    /***
     * This method calculates the final score & displays a toast using the makeToastText method
     ***/
    public void submitAnswers(View v) {

        int scoreTotal = answerQ1() + answerQ2() + answerQ3() + answerQ4() + answerQ5();

        //Custom messages & the user's name are used to generate the toast message
        String youScored = getString(R.string.you_scored);
        String userName = getUserName();
        String nameMessage = getString(R.string.name_input, userName);
        String finalMessage = " " + nameMessage + youScored + " " + scoreTotal + "/5.";

        if (scoreTotal == 5 || scoreTotal == 4)
            makeToastText(R.string.high_score, finalMessage);
        else if (scoreTotal == 0 || scoreTotal == 1 || scoreTotal == 2)
            makeToastText(R.string.low_score, finalMessage);
        else
            makeToastText(R.string.med_score, finalMessage);

        //Disables input for name field
        findViewById(R.id.user_name).setEnabled(false);

        //Disables input for question 1
        findViewById(R.id.q1_answer).setEnabled(false);

        //Disables input for question 2
        for (int i = 0; i < ((RadioGroup) findViewById(R.id.q2_radioGroup)).getChildCount(); i++) {
            ((RadioGroup) findViewById(R.id.q2_radioGroup)).getChildAt(i).setEnabled(false);
        }
        //Disables input for question 3
        findViewById(R.id.q3_answer).setEnabled(false);

        //Disables input for question 4
        ((CheckBox) findViewById(R.id.q4_answer1)).setEnabled(false);
        ((CheckBox) findViewById(R.id.q4_answer2)).setEnabled(false);
        ((CheckBox) findViewById(R.id.q4_answer3)).setEnabled(false);
        ((CheckBox) findViewById(R.id.q4_answer4)).setEnabled(false);

        //Disables input for question 5
        findViewById(R.id.q5_answer).setEnabled(false);
    }

    /***
     * This method gets the user's name (introduced in the first EditText object)
     ***/
    public String getUserName() {
        return ((EditText) findViewById(R.id.user_name)).getText().toString();
    }

    /***
     * This method scrolls the screen back to the top and resets all answers
     ***/
    public void resetAnswers(View v) {
        String userName = getUserName();
        String nameMessage = getString(R.string.name_input, userName);

        //Scroll to beginning of quiz
        quizView.smoothScrollTo(0, findViewById(R.id.title).getTop());

        //Reset Name Field
        EditText nameField = findViewById(R.id.user_name);
        nameField.setEnabled(true);

        //Reset Question 1
        String q1Null = "";
        EditText q1Input = findViewById(R.id.q1_answer);
        q1Input.setText(q1Null);
        q1Input.setEnabled(true);
        q1Input.clearFocus();

        //Reset Question 2
        RadioGroup q2RadioGroup = findViewById(R.id.q2_radioGroup);
        q2RadioGroup.clearCheck();
        for (int i = 0; i < q2RadioGroup.getChildCount(); i++) {
            q2RadioGroup.getChildAt(i).setEnabled(true);
        }

        //Reset Question 3
        String q3Null = "";
        EditText q3Input = findViewById(R.id.q3_answer);
        q3Input.setText(q3Null);
        q3Input.setEnabled(true);
        q3Input.clearFocus();

        //Reset Question 4
        ((CheckBox) findViewById(R.id.q4_answer1)).setEnabled(true);
        ((CheckBox) findViewById(R.id.q4_answer1)).setChecked(false);

        ((CheckBox) findViewById(R.id.q4_answer2)).setEnabled(true);
        ((CheckBox) findViewById(R.id.q4_answer2)).setChecked(false);

        ((CheckBox) findViewById(R.id.q4_answer3)).setEnabled(true);
        ((CheckBox) findViewById(R.id.q4_answer3)).setChecked(false);

        ((CheckBox) findViewById(R.id.q4_answer4)).setEnabled(true);
        ((CheckBox) findViewById(R.id.q4_answer4)).setChecked(false);

        //Reset Question 5
        EditText q5Input = findViewById(R.id.q5_answer);
        q5Input.setText("");
        q5Input.setEnabled(true);
        q5Input.clearFocus();

        //Set focus on dummy view
        findViewById(R.id.focus).requestFocus();

        //Show Toast
        Toast.makeText(this, "Give it another go, " + nameMessage + "!", Toast.LENGTH_LONG).show();

    }

    /***
     * The methods below get the user's answers and compare them to the correct answers
     ***/
    public int answerQ1() {
        String q1Answer = ((EditText) findViewById(R.id.q1_answer)).getText().toString();
        return q1Answer.compareToIgnoreCase(getString(R.string.q1_answer)) == 0 ? 1 : 0;
    }

    public int answerQ2() {
        return ((RadioButton) findViewById(R.id.a2_radio)).isChecked() ? 1 : 0;
    }

    public int answerQ3() {
        String q3Answer = ((EditText) findViewById(R.id.q3_answer)).getText().toString();
        return q3Answer.compareToIgnoreCase(getString(R.string.q3_answer)) == 0 ? 1 : 0;
    }

    public int answerQ4() {
        boolean q4Answer1IsChecked = ((CheckBox) findViewById(R.id.q4_answer1)).isChecked();
        boolean q4Answer2IsChecked = ((CheckBox) findViewById(R.id.q4_answer2)).isChecked();
        boolean q4Answer3IsChecked = ((CheckBox) findViewById(R.id.q4_answer3)).isChecked();
        boolean q4Answer4IsChecked = ((CheckBox) findViewById(R.id.q4_answer4)).isChecked();

        if (q4Answer1IsChecked && q4Answer2IsChecked && q4Answer3IsChecked && !q4Answer4IsChecked)
            return 1;
        else
            return 0;
    }

    public int answerQ5() {
        String q5Answer = ((EditText) findViewById(R.id.q5_answer)).getText().toString();
        return q5Answer.compareToIgnoreCase(getString(R.string.q5_answer)) == 0 ? 1 : 0;
    }

}
