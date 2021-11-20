package com.example.ejericicio21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnActivityTakeVideo = (Button) findViewById(R.id.btnActivityTakeVideo);
        Button btnActivityShowVideo = (Button) findViewById(R.id.btnActivityShowVideo);

        btnActivityTakeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityVideo.class);
                startActivity(intent);
            }
        });
    }
}