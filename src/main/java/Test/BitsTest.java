package Test;

import Functions.Bits;

import java.util.Scanner;

public class BitsTest {
    public static void main(String[] args) {
        float a,b;
        Scanner input = new Scanner(System.in);
        a = input.nextFloat();
        b = input.nextFloat();

        int bits = new Bits().getBits(a,b,0.0001);
        System.out.println("总位数："+bits);
    }
}
