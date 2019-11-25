package com.example.caculator;

public class TransformNum {

     public String trans10to2(int number){
        return Integer.toBinaryString(number);
     }

    public String trans10to8(int number){
        return Integer.toOctalString(number);
    }

    public String trans10to16(int number){
        return Integer.toHexString(number);
    }

    public String trans2to10(int number){
         String num = String.valueOf(number);
        return Integer.valueOf(num,2).toString();
    }

    public String trans8to10(int number){
        String num = String.valueOf(number);
        return Integer.valueOf(num,8).toString();
    }

    public String trans16to10(int number){
        String num = String.valueOf(number);
        return Integer.valueOf(num,16).toString();
    }


}
