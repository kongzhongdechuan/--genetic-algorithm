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

        double max = -9999;
        double X1 = 0;
        double X2 = 0;

        for(int i = 0; i < 10000000; i++) {
            Individual individual = new Individual(x1Length,a1,b1,x2Length,a2,b2);
            if(individual.getFitness() > max) {
                max = individual.getFitness();
                X1 = individual.getX1();
                X2 = individual.getX2();
            }
        }
        System.out.println("max:"+max);
        System.out.println("X1:" + X1);
        System.out.println("X2:" + X2);

    }
}
