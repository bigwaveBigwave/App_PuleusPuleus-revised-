package com.sungshin.Puleuspuleus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Other_plant_info extends AppCompatActivity {

    private Intent intent;
    private  ImageView imageView;
    private TextView titleRes;
    private TextView contentRes;
    private TextView contentRes2;
    private TextView contentRes3;
    private TextView contentRes4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_plant_info);

        Intent intent = getIntent();

        imageView = (ImageView) findViewById(R.id.imageView_other);
        titleRes = (TextView)findViewById(R.id.text_title_other);
        contentRes = (TextView)findViewById(R.id.text_content_other);
        contentRes2 = (TextView)findViewById(R.id.text_content_other2);
        contentRes3 = (TextView)findViewById(R.id.text_content_other3);
        contentRes4 = (TextView)findViewById(R.id.text_content_other4);


        imageView.setImageResource(intent.getIntExtra("imgRes",0));
        titleRes.setText(intent.getStringExtra("titleRes"));
        contentRes.setText(intent.getStringExtra("contentRes"));
        contentRes2.setText(intent.getStringExtra("contentRes2"));
        contentRes3.setText(intent.getStringExtra("contentRes3"));
        contentRes4.setText(intent.getStringExtra("contentRes4"));
        //String titleRes = intent.getStringExtra("titleRes");
        //String contentRes = intent.getStringExtra("contentRes");
    }


}




