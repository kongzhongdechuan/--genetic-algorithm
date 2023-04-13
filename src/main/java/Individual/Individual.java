package Individual;

import Functions.ExperimentFuctions;


import java.util.Random;


public class Individual implements Comparable {
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

    private  double X1;
    private  double X2;

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

    //Individual通过对象进行初始化

    public Individual(Individual other) {
        this.geneX1 = other.geneX1;
        this.geneX1Length = other.geneX1Length;
        this.a1 = other.a1;
        this.b1 = other.b1;

        this.geneX2 = other.geneX2;
        this.geneX2Length = other.geneX2Length;
        this.a2 = other.a2;
        this.b2 = other.b2;

        this.fitness = other.fitness;
        this.X1 = other.X1;
        this.X2 = other.X2;
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

    public Individual marry(Individual other) {
        //交换X1
        int[] newGeneX1 = cross(this.geneX1, other.geneX1, geneX1Length);
        //交换X2
        int[] newGeneX2 = cross(this.geneX2, other.geneX2, geneX2Length);

        Individual newIndividual = new Individual(other);
        newIndividual.setGeneX1(newGeneX1);
        newIndividual.setGeneX2(newGeneX2);
        newIndividual.calculateFitness();
        return newIndividual;
    }
    //通过两点交叉更新数组
    public int[] cross(int[] array,int[] array2,int length) {

        int[] tmp = copy(array,length);

        //随机获取两个交叉点进行交换
        Random random = new Random();
        int first = random.nextInt(length);
        int second = random.nextInt(length);
        int max = Math.max(first, second);
        int min = Math.min(first,second);

        for(int i = min; i < max ; i++){
            tmp[i] = array2[i];
        }

        return tmp;
    }

    public int[] copy(int[] array,int length) {
        int[] tmp = new int[length];
        for(int i = 0; i < length; i++) {
            tmp[i] = array[i];
        }
        return tmp;
    }

    //变异，每个基因位有Pm概率变异
    public void mutation(double Pm) {
        Random random = new Random();
        //X1可能会产生变异
        for(int i = 0; i < geneX1Length; i++) {
            if(random.nextDouble() <= Pm) {
               geneX1[i] = reverse(geneX1[i]);
            }
        }
        //X2产生变异
        for(int i = 0; i < geneX2Length; i++) {
            if(random.nextDouble() <= Pm) {
                geneX2[i] = reverse(geneX2[i]);
            }
        }
        //更新fitness
        calculateFitness();
    }
    //基因翻转
    public int reverse(int a) {
        if(a == 0)
            return 1;
        return 0;
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

    public void setGeneX1(int[] geneX1) {
        this.geneX1 = geneX1;
    }
    public void setGeneX2(int[] geneX2) {
        this.geneX2 = geneX2;
    }



    //List降序排列
    @Override
    public int compareTo(Object o) {
        if(this.fitness < ((Individual) o).getFitness())
            return 1;
        if(this.fitness > ((Individual)o).getFitness())
            return -1;
        return 0;
    }

    public static void main(String[] args) {
        Individual individual = new Individual(10,-3.0,12.1,10,4.1,5.8);
        System.out.println("decodeX1:" + individual.deCodeX1());
        System.out.println("decodeX2:" + individual.deCodeX2());
        System.out.println(individual.getFitness());
    }



}
