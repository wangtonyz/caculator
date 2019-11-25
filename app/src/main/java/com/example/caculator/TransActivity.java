package com.example.caculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;

public class TransActivity extends AppCompatActivity implements View.OnClickListener {

    //视窗
    private TextView tv1,tv2;
    private Spinner p1,p2;
    //按钮
    private Button bts[] = new Button[14];
    private int btIds[] = new int[]{R.id.beql,R.id.bpt,R.id.bt0,R.id.bt1,R.id.bt2,R.id.bt3,R.id.bt4
            ,R.id.bt5,R.id.bt6,R.id.bt7,R.id.bt8,R.id.bt9,R.id.btbk,R.id.btc};
    //等式
    private String equation = "";
    private CheckInput cheIn;

    private TransformNum trnum;

    public TransActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        init();
    }

    public void onClick(View view){
        int id = view.getId();
        Button button = (Button)findViewById(id);
        String text = button.getText().toString();
        switch (id){
            case R.id.bt0:
            case R.id.bt1:
            case R.id.bt2:
            case R.id.bt3:
            case R.id.bt4:
            case R.id.bt5:
            case R.id.bt6:
            case R.id.bt7:
            case R.id.bt8:
            case R.id.bt9:
                cheIn.setEquation(equation);
                if(cheIn.checkNumberInput()){
                    equation =  cheIn.getEquation();
                    equation += text;
                }
                break;

            case R.id.btbk:
                cheIn.setEquation(equation);
                cheIn.backSpace();
                equation = cheIn.getEquation();
                break;

            case R.id.btc:
                equation = "";
                tv2.setText("");
                break;

           /* case R.id.bpt:
                cheIn.setEquation(equation);
                if(cheIn.checkPointInput()){
                    equation = cheIn.getEquation();
                    equation += text;
                }
                break;*/
            case R.id.beql:
                //tv2.setText(new CounterByEquation(equation).solveEquation());
                Trans();
                break;

        }
        tv1.setText(equation);

    }
    public void Trans(){
        int i1 = p1.getSelectedItemPosition();
        int i2 = p2.getSelectedItemPosition();

        if(i1==i2){
            tv2.setText(equation);
        }
        if(i1==2 && i2 == 0){
            tv2.setText(trnum.trans10to2(Integer.parseInt(equation)));
        }
        if(i1==2 && i2 == 1){
            tv2.setText(trnum.trans10to8(Integer.parseInt(equation)));
        }
        if(i1==2 && i2 == 3){
            tv2.setText(trnum.trans10to16(Integer.parseInt(equation)));
        }
        if(i1==0 && i2 == 2){
            tv2.setText(trnum.trans2to10(Integer.parseInt(equation)));
        }
        if(i1==1 && i2 == 2){
            tv2.setText(trnum.trans8to10(Integer.parseInt(equation)));
        }
        if(i1==3 && i2 == 2){
            tv2.setText(trnum.trans16to10(Integer.parseInt(equation)));
        }

    }

    void init(){
        for(int i = 0 ;i<bts.length;i++){
            bts[i]= (Button)findViewById(btIds[i]);
            bts[i].setOnClickListener(this);
        }
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        p1 = (Spinner) findViewById(R.id.spin);
        p2 = (Spinner) findViewById(R.id.spin1);
        cheIn = new CheckInput();
        trnum = new TransformNum();


    }
    public void onBackPressed(){
        Intent intent = new Intent(TransActivity.this,MainActivity.class);
        startActivity(intent);
    }
}

