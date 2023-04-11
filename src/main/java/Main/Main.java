package Main;


import Functions.Bits;
import Individual.Individual;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        float a1,b1;
        System.out.println("请输入第一个区间：");
        a1 = input.nextFloat();
        b1 = input.nextFloat();

        float a2,b2;
        System.out.println("请输入第二个区间：");
        a2 = input.nextFloat();
        b2 = input.nextFloat();

        double precision;
        System.out.println("请输入精度：");
        precision = input.nextDouble();

        Bits bits = new Bits();
        //获取gene的编码位数
        int x1Length,x2Length;
        x1Length = bits.getBits(a1,b1,precision);
        x2Length = bits.getBits(a2,b2,precision);
        //获取x1,x2的整数位数
        int x1IntLength,x2IntLength;
        x1IntLength = bits.getIntBits(a1,b1);
        x2IntLength = bits.getIntBits(a2,b2);

        Individual individual = new Individual(x1IntLength,x1Length,x2IntLength,x2Length);
        System.out.println("X1:" + individual.deCodeX1());
        System.out.println("X2:" + individual.deCodeX2());

    }
}
