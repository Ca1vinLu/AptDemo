package me.lyz.aptdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import me.lyz.annotation.BindActivity;
import me.lyz.annotation.BindView;

@BindActivity
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
