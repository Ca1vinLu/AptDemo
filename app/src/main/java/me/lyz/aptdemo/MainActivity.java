package me.lyz.aptdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.lyz.annotation.CoolAnnotation;

public class MainActivity extends AppCompatActivity {

    @CoolAnnotation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
