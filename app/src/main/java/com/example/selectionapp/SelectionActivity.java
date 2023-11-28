package com.example.selectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class SelectionActivity extends AppCompatActivity {
    private Random rng = new Random();
    private int random1;
    private int random2;
    private int tag = -1;
    private boolean selectionFinished = false;
    private ArrayList<String> optionList = new ArrayList<>();
    private int[] results;
    private int[][] selectionWinners;
    private int optionTotal;
    private String resultOutput = "";
    private TextView titleView;
    private TextView resultView;
    private Button button1;
    private Button button2;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        optionList = extras.getStringArrayList("optionList");
        titleView = findViewById(R.id.titleView);
        resultView = findViewById(R.id.resultDisplay);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        resetButton = findViewById(R.id.resetButton);
        optionTotal = optionList.size();
        selectionWinners = new int[optionTotal][optionTotal - 1];
        results = new int[optionTotal];
        startSelection();
    }

    @SuppressLint("SetTextI18n")
    private void startSelection() {
        for (int i = 0; i < optionTotal; i++) {
            for (int j = 0; j < optionTotal - 1; j++) {
                if (i <= j) {
                    selectionWinners[i][j] = -1;
                } else {
                    selectionWinners[i][j] = -2;
                }
            }
        }
        do {
            random1 = rng.nextInt(optionTotal);
            random2 = rng.nextInt(optionTotal - 1);
        } while ((random1 == random2) || (selectionWinners[random1][random2] >= -1));
        button1.setText(optionList.get(random1));
        button1.setTag(random1);
        button2.setText(optionList.get(random2));
        button2.setTag(random2);
    }

    public void nextOption(View view) {

        tag = Integer.parseInt(view.getTag().toString());
        if (tag == random1) {
            selectionWinners[random1][random2] = random1;
        } else if (tag == random2) {
            selectionWinners[random1][random2] = random2;
        } else {
            Toast.makeText(this, "Something Broke", Toast.LENGTH_LONG).show();
        }
        selectionFinished = true;
        for (int i = 0; i < optionTotal; i++) {
            for (int j = 0; j < optionTotal - 1; j++) {
                if (selectionWinners[i][j] == -2) {
                    selectionFinished = false;
                }
            }
        }
        if (!selectionFinished) {
            do {
                random1 = rng.nextInt(optionTotal);
                random2 = rng.nextInt(optionTotal - 1);
            } while ((random1 == random2) || (selectionWinners[random1][random2] >= -1));
            button1.setText(optionList.get(random1));
            button1.setTag(random1);
            button2.setText(optionList.get(random2));
            button2.setTag(random2);
        } else {
            displayResults();
        }
    }

    public void newSelection(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    private void displayResults() {
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        titleView.setText("Results");
        resultView.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);
        for (int i = 0; i < optionTotal; i++) {
            for (int j = 0; j < optionTotal - 1; j++) {
                if (selectionWinners[i][j] == i) {
                    results[i]++;
                } else if (selectionWinners[i][j] == j) {
                    results[j]++;
                }
            }
        }
        for (int i = 0; i < optionTotal; i++) {
            for (int j = i + 1; j < optionTotal; j++) {
                int tempInt;
                String tempString;
                if (results[i] < results[j]) {
                    tempInt = results[i];
                    results[i] = results[j];
                    results[j] = tempInt;
                    tempString = optionList.get(i);
                    optionList.set(i, optionList.get(j));
                    optionList.set(j, tempString);
                }
            }
        }
        for (int i = 0; i < optionTotal; i++) {
            resultOutput += optionList.get(i) + ": " + Integer.toString(results[i]) + "\n";
        }
        resultView.setText(resultOutput);
    }
}