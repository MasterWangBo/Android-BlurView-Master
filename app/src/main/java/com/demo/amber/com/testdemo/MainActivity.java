package com.demo.amber.com.testdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt1, bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        case R.id.bt1:
            /** FastBlur 解决方案 **/
            startActivity(new Intent(this, FastBlurActivity.class));
            break;

        case R.id.bt2:
            /** RenderScript 解决方案 **/
            startActivity(new Intent(this, RenderScriptActivity.class));
            break;

        }

    }

}
