package com.example.fenrirassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = this.getIntent();
        String result = intent.getStringExtra("result");
        Log.d("test", result);
    }

    public void backActivity(View view){
        finish();
    }
}
