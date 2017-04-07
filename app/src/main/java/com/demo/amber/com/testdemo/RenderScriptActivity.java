package com.demo.amber.com.testdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class RenderScriptActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;

    ImageView iv2;

    EditText et1, et2;

    Button bt;

    Bitmap bmp;

    int scaleRatio;// 图片缩放比例 (例如1/10) 数值越大速度越快，虚化细节越少

    int blurRadius; // 虚化程度 数值越大速度越慢，细节越好，cpu占用越大

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.iv);
        iv2 = (ImageView) findViewById(R.id.iv2);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(this);

        // 加载用来对比的原图
        Glide.with(this).load(R.drawable.yue).into(iv);
        // 生成用来虚化的bitmap
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.yue);

        /** RenderScript 解决方案 **/

        et1.setText("10");
        et2.setText("5");

        doBlur();

    }

    private void doBlur() {
        scaleRatio = Integer.parseInt(et1.getText().toString());
        blurRadius = Integer.parseInt(et2.getText().toString());

        // 计算图片缩小后的长宽
        int width = Math.round(bmp.getWidth() / scaleRatio);
        int height = Math.round(bmp.getHeight() / scaleRatio);
        // 创建一张缩小后的图片做为渲染的图片
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp, width, height, false);

        Bitmap blur = BlurBitmapUtil.blurBitmap(this, scaledBitmap, blurRadius);
        iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv2.setImageBitmap(blur);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.bt:
            if (scaleRatio > 1)
                doBlur();
            break;
        }
    }
}
