package com.example.caculator;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    //tvNow,tvPast显示运算过程
    TextView tvPast;

    AutoScaleTextView tvNow;

    //保存，复制，清空tvPast中的运算过程
    Button btnSave;

    Button btnCopy;

    Button btnClear;

    //数字btns
    Button btn7;

    Button btn8;

    Button btn9;

    Button btn4;

    Button btn5;

    Button btn6;

    Button btn1;

    Button btn2;

    Button btn3;

    Button btn0;

    Button btnDot;

    //基本运算符: + - × / ( ) =
    Button btnAdd;

    Button btnSub;

    Button btnMul;

    Button btnDiv;

    Button btnBracket;

    Button btnEqual;

    //基本计算器功能：删除一个字符，清空当前math
    Button btnDel;

    Button btnClc;


    //存储过去和当前的math
    private String mathPast = "";
    private String mathNow = "";
    private int precision = 6;
    private int equal_flag = 0; //设置flag值判断是否需要清空mathNow进行新的运算
    private ScienceCalculator scienceCalculator = new ScienceCalculator(); //一个科学计算器


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.help_item:
                Toast.makeText(this,"这是一个帮助",Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_item:
                finish();
                break;
            case R.id.trans1:
                Intent intent = new Intent(MainActivity.this,TransActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.trans2:
                Intent intent1 = new Intent(MainActivity.this,TransformUnit.class);
                startActivity(intent1);
                finish();
                break;
                default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态栏 manifest的application中要设置appTheme为NoActionBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        //tvNow,tvPast显示运算过程
        tvPast= (TextView) findViewById(R.id.tv_past);

        tvNow=(AutoScaleTextView)findViewById(R.id.tv_now);

        //保存，复制，清空tvPast中的运算过程


        //数字btns
        btn7=(Button)findViewById(R.id.btn_7);

        btn8=(Button)findViewById(R.id.btn_8);

        btn9=(Button)findViewById(R.id.btn_9);

        btn4=(Button)findViewById(R.id.btn_4);

        btn5=(Button)findViewById(R.id.btn_5);

        btn6=(Button)findViewById(R.id.btn_6);

        btn1=(Button)findViewById(R.id.btn_1);

        btn2=(Button)findViewById(R.id.btn_2);

        btn3=(Button)findViewById(R.id.btn_3);

        btn0=(Button)findViewById(R.id.btn_0);

        btnDot=(Button)findViewById(R.id.btn_dot);

        //基本运算符: + - × / ( ) =
        btnAdd=(Button)findViewById(R.id.btn_add);

        btnSub=(Button)findViewById(R.id.btn_sub);

        btnMul=(Button)findViewById(R.id.btn_mul);

        btnDiv=(Button)findViewById(R.id.btn_div);

        btnBracket=(Button)findViewById(R.id.btn_bracket);

        btnEqual=(Button)findViewById(R.id.btn_equal);

        //基本计算器功能：删除一个字符，清空当前math
        btnDel=(Button)findViewById(R.id.btn_del);

        btnClc=(Button)findViewById(R.id.btn_clc);

        //设置控件属性
        initTvPast();
        initNumBtns();
        initBaseOpers();
        //initThreeFunctions();
        initBaseCalculatorFunction();
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(MainActivity.this, LandActivity.class);
            intent.putExtra("main", tvPast.getText().toString()); //把当前的tvPast中的运算过程传递给LandActivity
            startActivity(intent);
            finish(); //退出当前的activity
        }
    }

    //初始化tvPast
    public void initTvPast() {

        //设置tvPast一些属性
        tvPast.setMovementMethod(ScrollingMovementMethod.getInstance()); //内容自动滚动到最新的一行
        tvPast.setTextIsSelectable(true); //长按复制

        //获取界面切换的tvPast的内容
        Intent intent = this.getIntent();
        String tvPastContent = intent.getStringExtra("land");

        //如果当前的界面是启动界面，不是从MainActivity切换来的，上面的mathPast就为null了，要处理这种异常
        if (tvPastContent == null) {
            tvPast.setText("");
        } else {
            String[] maths = tvPastContent.split("\n");
            int i;
            for (i = 0; i < maths.length - 1; i++) {
                tvPast.append(maths[i] + "\n");
            }
            tvPast.append(maths[i]); //最后一个math不用加换行
        }

    }


    //初始化数字btns
    public void initNumBtns() {

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //如果flag=1，表示要输入新的运算式，清空mathNow并设置flag=0
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }

                if (mathNow.length() == 0) {                    //1.mathNow为空，+0
                    mathNow += "0";
                } else if (mathNow.length() == 1) {             //2.mathNow 长度为1

                    if (mathNow.charAt(0) == '0') {                 //2.1 如果该字符为0，不加
                        mathNow += "";
                    } else if (isNum(mathNow.charAt(0))) {          //2.2 如果该字符为1-9，+0
                        mathNow += "0";
                    }

                } else if (!isNum(mathNow.charAt(mathNow.length() - 2)) && mathNow.charAt(mathNow.length() - 1) == '0') {
                    mathNow += "";                              //3.属于2.1的一般情况，在math中间出现 比如：×0 +0
                } else {                                        //4.除此之外，+0
                    mathNow += "0";
                }
                tvNow.setText(mathNow);
            }
        });

        //btn 1-9 输入条件相同
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }

                if (mathNow.length() == 0) {
                    mathNow += "1";
                } else {

                    //math的最后一个字符是：1-9, oper, (, .
                    char ch = mathNow.charAt(mathNow.length() - 1);
                    if (isNum(ch) || isOper(ch) || ch == '(' || ch == '.')
                        mathNow += "1";
                }
                tvNow.setText(mathNow);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }
                if (mathNow.length() == 0) {
                    mathNow += "2";
                } else {
                    char ch = mathNow.charAt(mathNow.length() - 1);
                    if (isNum(ch) || isOper(ch) || ch == '(' || ch == '.')
                        mathNow += "2";
                }
                tvNow.setText(mathNow);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }
                if (mathNow.length() == 0) {
                    mathNow += "3";
                } else {
                    char ch = mathNow.charAt(mathNow.length() - 1);
                    if (isNum(ch) || isOper(ch) || ch == '(' || ch == '.')
                        mathNow += "3";
                }
                tvNow.setText(mathNow);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }
                if (mathNow.length() == 0) {
                    mathNow += "4";
                } else {
                    char ch = mathNow.charAt(mathNow.length() - 1);
                    if (isNum(ch) || isOper(ch) || ch == '(' || ch == '.')
                        mathNow += "4";
                }
                tvNow.setText(mathNow);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }
                if (mathNow.length() == 0) {
                    mathNow += "5";
                } else {
                    char ch = mathNow.charAt(mathNow.length() - 1);
                    if (isNum(ch) || isOper(ch) || ch == '(' || ch == '.')
                        mathNow += "5";
                }
                tvNow.setText(mathNow);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }
                if (mathNow.length() == 0) {
                    mathNow += "6";
                } else {
                    char ch = mathNow.charAt(mathNow.length() - 1);
                    if (isNum(ch) || isOper(ch) || ch == '(' || ch == '.')
                        mathNow += "6";
                }
                tvNow.setText(mathNow);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }
                if (mathNow.length() == 0) {
                    mathNow += "7";
                } else {
                    char ch = mathNow.charAt(mathNow.length() - 1);
                    if (isNum(ch) || isOper(ch) || ch == '(' || ch == '.')
                        mathNow += "7";
                }
                tvNow.setText(mathNow);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }
                if (mathNow.length() == 0) {
                    mathNow += "8";
                } else {
                    char ch = mathNow.charAt(mathNow.length() - 1);
                    if (isNum(ch) || isOper(ch) || ch == '(' || ch == '.')
                        mathNow += "8";
                }
                tvNow.setText(mathNow);
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }
                if (mathNow.length() == 0) {
                    mathNow += "9";
                } else {
                    char ch = mathNow.charAt(mathNow.length() - 1);
                    if (isNum(ch) || isOper(ch) || ch == '(' || ch == '.')
                        mathNow += "9";
                }
                tvNow.setText(mathNow);
            }
        });

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }
                if (mathNow.length() == 0) {                                //1.mathNow为空，+0.
                    mathNow += "0.";
                } else if (isOper(mathNow.charAt(mathNow.length() - 1))) {  //2.mathNow的最后一个字符为oper，+0.
                    mathNow += "0.";
                } else if (isNum(mathNow.charAt(mathNow.length() - 1))) {   //3.mathNow的最后一个字符为num，+.
                    mathNow += ".";
                } else {                                                    //4.除此之外，不加
                    mathNow += "";
                }
                tvNow.setText(mathNow);
            }
        });
    }

    //初始化基本的运算符
    public void initBaseOpers() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mathNow.length() == 0) {
                    mathNow += "+";
                } else {
                    if (isNum(mathNow.charAt(mathNow.length() - 1))
                            || mathNow.charAt(mathNow.length() - 1) == ')'
                            || mathNow.charAt(mathNow.length() - 1) == '('
                            || mathNow.charAt(mathNow.length() - 1) == 'π'
                            || mathNow.charAt(mathNow.length() - 1) == 'e')
                        mathNow += "+";
                }
                tvNow.setText(mathNow);
                equal_flag = 0; //可能用运算结果直接运算，flag直接设0
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mathNow.length() == 0) {
                    mathNow += "-";
                } else {
                    if (isNum(mathNow.charAt(mathNow.length() - 1))
                            || mathNow.charAt(mathNow.length() - 1) == ')'
                            || mathNow.charAt(mathNow.length() - 1) == '('
                            || mathNow.charAt(mathNow.length() - 1) == 'π'
                            || mathNow.charAt(mathNow.length() - 1) == 'e')
                        mathNow += "-";
                }
                tvNow.setText(mathNow);
                equal_flag = 0;
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mathNow.length() != 0) {
                    if (isNum(mathNow.charAt(mathNow.length() - 1))
                            || mathNow.charAt(mathNow.length() - 1) == ')'
                            || mathNow.charAt(mathNow.length() - 1) == 'π'
                            || mathNow.charAt(mathNow.length() - 1) == 'e')
                        mathNow += "×";
                }
                tvNow.setText(mathNow);
                equal_flag = 0;
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mathNow.length() != 0) {
                    if (isNum(mathNow.charAt(mathNow.length() - 1))
                            || mathNow.charAt(mathNow.length() - 1) == ')'
                            || mathNow.charAt(mathNow.length() - 1) == 'π'
                            || mathNow.charAt(mathNow.length() - 1) == 'e')
                        mathNow += "/";
                }
                tvNow.setText(mathNow);
                equal_flag = 0;
            }
        });


        btnBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag == 1) {
                    mathNow = "";
                    equal_flag = 0;
                }
                if (mathNow.length() == 0) {                                //1.mathNow为空，+(
                    mathNow += "(";
                } else if (isOper(mathNow.charAt(mathNow.length() - 1))) {  //2.mathNow最后一个字符是oper，+(
                    mathNow += "(";
                } else if (isNum(mathNow.charAt(mathNow.length() - 1))      //3.mathNow最后一个字符是num, π, e
                        || mathNow.charAt(mathNow.length() - 1) == 'π'
                        || mathNow.charAt(mathNow.length() - 1) == 'e') {
                    if (!hasLeftBracket(mathNow))                               //3.1 没有(, 加 ×(
                        mathNow += "×(";
                    else                                                        //3.2 已有(, 加 )
                        mathNow += ")";
                } else if (mathNow.charAt(mathNow.length() - 1) == ')') {   //4.mathNow最后一个字符是)，说明用户是在补全右括号，+)
                    mathNow += ')';
                }
                tvNow.setText(mathNow);
            }
        });


        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //右括号自动补全
                int leftNum = 0;
                int rightNum = 0;
                for (int i = 0; i < mathNow.length(); i++) {
                    if (mathNow.charAt(i) == '(')
                        leftNum++;
                    if (mathNow.charAt(i) == ')')
                        rightNum++;
                }
                int missingNum = leftNum - rightNum; //缺失的 ) 数量
                while (missingNum > 0) {
                    mathNow += ')';
                    missingNum--;
                }
                tvNow.setText(mathNow);

                mathPast = "\n" + mathNow; //使得呈现的mathPast自动换行

                double result = scienceCalculator.cal(mathNow, precision, 0); //调用科学计算器

                if (result == Double.MAX_VALUE)
                    mathNow = "Math Error";
                else {
                    mathNow = String.valueOf(result);
                    if (mathNow.charAt(mathNow.length() - 2) == '.' && mathNow.charAt(mathNow.length() - 1) == '0') {
                        mathNow = mathNow.substring(0, mathNow.length() - 2);
                    }
                }

                mathPast = mathPast + "=" + mathNow;

                //用tvPast.set(mathPast)不能实现自动滚动到最新运算过程
                tvPast.append(mathPast); //添加新的运算过程

                //tvPast滚动到最新的运算过程
                int offset = tvPast.getLineCount() * tvPast.getLineHeight();
                if (offset > tvPast.getHeight()) {
                    tvPast.scrollTo(0, offset - tvPast.getHeight());
                }
                tvNow.setText(mathNow);

                equal_flag = 1; //设置flag=1
            }
        });
    }



    //初始化计算器基本Button
    public void initBaseCalculatorFunction() {
        btnClc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mathNow = "";
                tvNow.setText(mathNow);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mathNow.length() != 0) {
                    mathNow = mathNow.substring(0, mathNow.length() - 1);
                    tvNow.setText(mathNow);
                }
            }
        });
    }

    //判断当前字符是否为数字
    private boolean isNum(char c) {
        char num[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int i = 0;
        for (; i < num.length; i++) {
            if (num[i] == c)
                break;
        }
        return i != num.length;
    }

    //判断当前字符是否为运算符
    private boolean isOper(char c) {
        char oper[] = {'+', '-', '×', '/'};
        int i = 0;
        for (; i < oper.length; i++) {
            if (oper[i] == c)
                break;
        }
        return i != oper.length;
    }

    //判断当前math是否有')'
    private boolean hasLeftBracket(String s) {
        int i = 0;
        for (; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                break;
        }
        return i != s.length();
    }
}
