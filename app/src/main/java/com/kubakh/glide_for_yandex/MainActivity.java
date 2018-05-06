package com.kubakh.glide_for_yandex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                Intent galleryIntent = new Intent(MainActivity.this, SpaceGalleryActivity.class);
                startActivity(galleryIntent);

    }
}
