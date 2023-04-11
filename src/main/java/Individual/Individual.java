package Individual;

import Functions.ExperimentFuctions;

import java.util.Random;


public class Individual {
    //gene中包含两个：X1，X2
    private int[] geneX1;
    private final int geneX1Length;
    //区间[a1,b1]
    private final double a1;
    private final double b1;

    private int[] geneX2;
    private final int geneX2Length;
    //区间[a2,b2]
    private final double a2;
    private final double b2;

    //fitness用于表现对于函数的适应性
    private double fitness;

    private static double X1;
    private static double X2;

    //初始化，同时计算fitness
    public Individual(int x1Length,double a1,double b1,int x2Length,double a2,double b2) {
        geneX1Length = x1Length;
        this.a1 = a1;
        this.b1 = b1;
        geneX2Length = x2Length;
        this.a2 = a2;
        this.b2 = b2;

        //初始化X1，X2
        InitX1();
        InitX2();

        //将X1，X2转化为区间上的值
        deCodeX1();
        deCodeX2();

        //计算fitness
        calculateFitness();

    }

    //对genX1初始化
    private void InitX1() {
        Random random = new Random();
        geneX1 = new int[geneX1Length];
        for(int i = 0; i < geneX1Length; i++) {
            if(random.nextFloat() < 0.5)
                geneX1[i] = 0;
            else
                geneX1[i] = 1;
        }
    }
    //对genX2初始化
    private void InitX2() {
        Random random = new Random();
        geneX2 = new int[geneX2Length];
        for(int i = 0; i < geneX2Length; i++) {
            if(random.nextFloat() < 0.5)
                geneX2[i] = 0;
            else
                geneX2[i] = 1;
        }
    }


    //解码，将二进制代码转化为整形
    public int deCode(int[] gene,int geneLength){
        int num = 0;
        for(int i = 0; i < geneLength ; i++) {
            num = num * 2 + gene[i];
        }
        return num;
    }

    //将X1、X2转换到对应区间上
    public double deCodeX1() {
        return deCode(geneX1,geneX1Length) * (b1 - a1) / (Math.pow(2,geneX1Length) - 1) + a1;
    }
    public double deCodeX2() {
        return deCode(geneX2,geneX2Length) * (b2 - a2) / (Math.pow(2,geneX2Length) - 1) + a2;
    }


    public void calculateFitness() {
        X1 = deCodeX1();
        //System.out.println("******"+X1);
        X2 = deCodeX2();
        //System.out.println("******"+X2);
        this.fitness = new ExperimentFuctions().values(X1,X2);
    }


    public double getFitness(){
        return this.fitness;
    }

    public double getX1(){
        return X1;
    }
    public double getX2(){
        return X2;
    }

    public static void main(String[] args) {
        Individual individual = new Individual(10,-3.0,12.1,10,4.1,5.8);
        System.out.println("decodeX1:" + individual.deCodeX1());
        System.out.println("decodeX2:" + individual.deCodeX2());
        System.out.println(individual.getFitness());
    }
}
