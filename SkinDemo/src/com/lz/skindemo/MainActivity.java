package com.lz.skindemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener{

    private Button button1,button2,button3,btn_f,btn_g,btn_n;
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        // 对资源文件进行解压操作
        LZR.skinFileHandler(LZR.SDCARDROOT);
        
        ll = (LinearLayout) findViewById(R.id.ll_main);
        btn_f = (Button) findViewById(R.id.btn_f);
        btn_g = (Button) findViewById(R.id.btn_g);
        btn_n = (Button) findViewById(R.id.btn_n);
        
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        
        if(LZR.skinFils.size() == 0){
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
        }else if(LZR.skinFils.size() == 1){
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.GONE); 
            button1.setText(LZR.skinFils.get(0));
        }else if(LZR.skinFils.size() == 2){
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button1.setText(LZR.skinFils.get(0));
            button2.setText(LZR.skinFils.get(1));
        }
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        LZR.context = this;
        LZR.packageName = getPackageName();
        setSkin();
    }
    
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.button1:
            LZR.choosedSkinPath = LZR.skinFils.get(0);
            setSkin();
            break;
        case R.id.button2:
            LZR.choosedSkinPath = LZR.skinFils.get(1);
            setSkin();
            break;
        default:
            Intent it = new Intent(this, SecondActivity.class);
            startActivity(it);
            break;
        }
    }

    /**
     * 为UI控件设置背景资源
     */
    private void setSkin(){
        LZR.setViewBackGroundRes(ll,"bg.png",null,null);
        LZR.setViewBackGroundRes(btn_f,"f.xml","f_up.png","f_down.png");
        LZR.setViewBackGroundRes(btn_g,"g.xml","g_up.png","g_down.png");
        LZR.setViewBackGroundRes(btn_n,"n.xml","n_up.png","n_down.png");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LZR.clearData();
        clearCache(ll);
        clearCache(btn_f);
        clearCache(btn_g);
        clearCache(btn_n);
    }
    /**
     * 对View进行资源清空操作
     * @param v
     */
    private void clearCache(View v){
        Drawable dw =  v.getBackground();
        if(dw instanceof BitmapDrawable){
            BitmapDrawable bd = (BitmapDrawable) dw;
            v.setBackgroundResource(0);
            bd.setCallback(null);
            bd.getBitmap().recycle();
        }
    }
}
