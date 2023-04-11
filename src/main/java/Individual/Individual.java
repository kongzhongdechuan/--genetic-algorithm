package Individual;

import Functions.ExperimentFuctions;

import java.awt.*;
import java.util.Random;


public class Individual {
    //gene中包含两个：X1，X2
    private int[] geneX1;
    private int geneX1IntLength;
    private int geneX1Length;
    private int[] geneX2;
    private int geneX2IntLength;
    private int geneX2Length;
    //fitness用于表现对于函数的适应性
    private double fitness;

    public Individual() {

    }

    public Individual(int x1IntLength,int x1Length,int x2IntLength,int x2Length) {
        geneX1IntLength = x1IntLength;
        geneX1Length = x1Length;
        geneX2IntLength = x2IntLength;
        geneX2Length = x2Length;
    }

    //对genX1初始化
    private void InitX1() {
        Random random = new Random(System.currentTimeMillis());
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
        Random random = new Random(System.currentTimeMillis());
        geneX2 = new int[geneX2Length];
        for(int i = 0; i < geneX2Length; i++) {
            if(random.nextFloat() < 0.5)
                geneX2[i] = 0;
            else
                geneX2[i] = 1;
        }
    }



    public void calculateFitness() {
        float X1 = deCodeX1();
        float X2 = deCodeX2();
        fitness = new ExperimentFuctions().valus(X1,X2);
    }

    //解码，将二进制代码转化为整形
    public float deCode(int[] gene, int geneIntLength, int geneLength){
        int numInt = 0;
        for(int i = 0; i < geneIntLength ; i++) {
            numInt = numInt * 2 + gene[i];
        }
        float numFloat = 0.0F;
        for(int i = geneIntLength; i < geneLength; i++) {
            numFloat += Math.pow(0.5,i - geneIntLength + 1);
        }
        return (numInt + numFloat);
    }

    public float deCodeX1() {
        return deCode(geneX1,geneX1IntLength,geneX1Length);
    }
    public float deCodeX2() {
        return deCode(geneX2,geneX2IntLength,geneX2Length);
    }


    public static void main(String[] args) {
        Individual individual = new Individual(5,10,5,10);
        individual.InitX1();
        System.out.println(individual.deCodeX1());
        individual.InitX2();
        System.out.println(individual.deCodeX2());
    }
}
