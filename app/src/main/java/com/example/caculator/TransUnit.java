package com.example.caculator;

public class TransUnit {
    public String intoCm(double number){
        return Double.toString(number*2.54);
    }

    public String cmtoIn(double number){
        return Double.toString(number/2.54);
    }

    public String ftoM(double number){
        return Double.toString(number*0.0929);
    }
    public String mtoF(double number){
        return Double.toString(number*10.7639);
    }

    public String KtoC(double number){
        return Double.toString(number-273.15);
    }

    public String CtoK(double number){
        return Double.toString(number+273.15);
    }

    public String lbtoKg(double number){
        return Double.toString(number*0.453592);
    }
    public String kgtoLb(double number){
        return Double.toString(number*2.204622);
    }

    public String LtoM3(double number){
        return Double.toString(number*0.001);
    }
    public String M3toL(double number){
        return Double.toString(number*1000);
    }
}
