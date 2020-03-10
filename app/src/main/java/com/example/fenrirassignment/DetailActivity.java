package com.example.fenrirassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    private JSONObject restInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = this.getIntent();
        try {
            restInfo = new JSONObject(intent.getStringExtra("restInfo"));
        }catch (Exception e){
            e.printStackTrace();
        }
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(restInfo.toString());
    }
}
