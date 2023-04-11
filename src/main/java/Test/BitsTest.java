package Test;

import Functions.Bits;

import java.util.Scanner;

public class BitsTest {
    public static void main(String[] args) {
        float a,b;
        Scanner input = new Scanner(System.in);
        a = input.nextFloat();
        b = input.nextFloat();
        int m = new Bits().getInt(a,b);
        double n = new Bits().getDouble(a,b);
        int bits = new Bits().getBits(a,b,0.0001);
        int Bits = new Bits().getIntBits(a,b);
        System.out.println("总位数："+bits);
        System.out.println("整数位数："+Bits);
    }
}
