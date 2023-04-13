package Population;

import Individual.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class testPopulation {
    private final Individual[] individuals;
    private static final int allnum = 20;   //种群最大容量
    private static int N = allnum;     //种群中目前数目

    private static final int parentNum = 20;   //每次选择20个“父母”
    private int[] Order = new int[2*allnum];

    private double[] addFitness;           //第i位存储i位之前所有fitness/sumFitness占比


    private int x1Length;
    private double a1;
    private double b1;
    private int x2Length;
    private double a2;
    private double b2;

    public testPopulation(int x1Length, double a1, double b1, int x2Length, double a2, double b2) {
        this.x1Length = x1Length;
        this.a1 = a1;
        this.b1 = b1;
        this.x2Length = x2Length;
        this.a2 = a2;
        this.b2 = b2;

        //将种群数目初始化最大种群数目的两倍，防止出现数组越界情况
        individuals = new Individual[2*allnum];
        for(int i = 0; i < allnum; i++) {
            assert individuals != null;
            individuals[i] = new Individual(x1Length,a1,b1,x2Length,a2,b2);
        }
    }

    //从总数为n的种群中，选取num数量的个体
    public void selectIndividuals(int n,int num) {

        //总群的总的fitness和，用于轮盘赌
        double sumFitness = 0;

        for(int i = 0; i < n; i++) {
            sumFitness += individuals[i].getFitness();
        }

        addFitness = new double[n];
        for(int i = 0; i < n; i++) {
            addFitness[i] = individuals[i].getFitness()/ sumFitness;
        }

        //计算累计适应度
        for(int i = 1; i < n; i++) {
            addFitness[i] += addFitness[i-1];
        }

        //选择数目为parentNum的Parents
        choose(num);
    }

    //从N个个体中选择num个个体
    //产生随机数，根据累计适应度来进行选择个体
    public void choose(int num) {
        Random random = new Random();
        Order = new int[2*allnum];

        for(int i = 0; i < num; i++) {
            double chooseNumber = random.nextDouble();
            int order;
            for(order = 0; order < N; order++) {
                if(chooseNumber < addFitness[order])
                    break;
            }
            //通过Order数组来记录第i个数组偏移量
            Order[i] = order;

        }
    }

    //从获得的parents，根据概率pc后去选择是否后代
    public void generateChildren(double Pc) {
        //从初始数目为allnum种群中，选择parentNum个父母
        Order = new int[2*allnum];
        selectIndividuals(allnum,parentNum);
        //产生后代
        Random random = new Random();
        for(int i = 0; i < parentNum-1; i+=2) {
            if(random.nextDouble() <= Pc) {
                Individual father = individuals[Order[i]];
                Individual mother = individuals[Order[i+1]];

                //种群数目加一
                individuals[N++] = father.marry(mother);
            }
        }
    }


    //对种群进行概率为Pm的基因变异
    public void mutation(double Pm) {
        //对种群中的N个个体进行变异
        for(int i = 0; i < N; i++) {
            individuals[i].mutation(Pm);
        }
    }


    public void display() {
        for(int i = 0; i < allnum; i++) {
            System.out.println(individuals[i].getFitness());
        }
    }
    public double bestFitness() {
        double max = individuals[0].getFitness();

        for(int i = 1; i < allnum; i++) {
            if(individuals[i].getFitness() > max) {
                max = individuals[i].getFitness();
            }
        }
        return max;
    }


    public void updatePopulation() {

    }














    public static void main(String[] args) {
        testPopulation population = new testPopulation(18,-3.2,12.1,15,4.1,5.8);

        for(int i = 0; i < 5; i++) {
            population.generateChildren(0.6);
           // population.mutation(0.01);
            population.updatePopulation();
            System.out.println("第" + i +"代：" + population.bestFitness());
            //population.display();
            if(population.bestFitness() > 38)
            {
                System.out.println("*******************************************");
            }
        }
    }



}
