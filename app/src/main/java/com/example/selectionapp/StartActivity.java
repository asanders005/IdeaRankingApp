package com.example.selectionapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    private EditText optionInput;
    private TextView optionlist;
    private ArrayList<String> inputList = new ArrayList<>();
    private String input = "Options:\n";
    private boolean duplicate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        optionlist = findViewById(R.id.inputList);
        optionInput = findViewById(R.id.optionInput);
    }

    @SuppressLint("SetTextI18n")
    public void addOption(View view) {
        duplicate = false;
        for (int i = 0; i < inputList.size(); i++) {
            if (optionInput.getText().toString().toLowerCase().equals(inputList.get(i))) {
                duplicate = true;
            }
        }
        if (!optionInput.getText().toString().equals("") && !duplicate) {
            inputList.add(optionInput.getText().toString());
            input += optionInput.getText().toString() + "\n";
            optionInput.setText("");
            optionlist.setText(input);
        } else if (duplicate) {
            Toast.makeText(this, "You've already input that option", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeOption(View view) {
        if (inputList.size() > 0) {
            inputList.remove(inputList.size() - 1);
            input = "Options:\n";
            for (int i = 0; i < inputList.size(); i++) {
                input += inputList.get(i) + "\n";
            }
            optionlist.setText(input);
        }
    }

    public void startSelection(View view) {
        if (inputList.size() >=2) {
            Intent intent = new Intent(this, SelectionActivity.class);
            intent.putExtra("optionList", inputList);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please enter two or more options", Toast.LENGTH_SHORT).show();
        }
    }
}