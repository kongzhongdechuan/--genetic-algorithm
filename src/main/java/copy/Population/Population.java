package copy.Population;

import Individual.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Population {
    private final Individual[] individuals;
    private static final int allnum = 100;   //种群最大容量
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

    public Population(int x1Length, double a1, double b1, int x2Length, double a2, double b2) {
        this.x1Length = x1Length;
        this.a1 = a1;
        this.b1 = b1;
        this.x2Length = x2Length;
        this.a2 = a2;
        this.b2 = b2;

        //将种群数目初始化最大种群数目的两倍，防止出现数组越界情况
        individuals = new Individual[2*allnum];
        for(int i = 0; i < allnum; i++) {
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

       // System.out.println("最后一个的累计适应度为：****************" + addFitness[n-1]);

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
            //System.out.println("order:*************" + order);

        }
    }

    //从获得的parents，根据概率pc后去选择是否后代
    public void generateChildren(double Pc) {
        //从初始数目为allnum种群中，选择parentNum个父母
        selectIndividuals(allnum,parentNum);
        //产生后代
        Random random = new Random();
        for(int i = 0; i < parentNum-1; i+=2) {
            if(random.nextDouble() <= Pc) {
                Individual father = individuals[Order[i]];
                Individual mother = individuals[Order[i+1]];

                //种群数目加一
                individuals[N++] = father.marry(mother);
                //System.out.println("N:" + N + "individuals:**************" + individuals[N-1].getFitness());
            }
        }
    }


    //对种群进行概率为Pm的基因变异
    public void mutation(double Pm) {
        //对种群中的N个个体进行变异
        for(int i = 0; i < N; i++) {
            System.out.println("*************"+individuals[i].getFitness());
            individuals[i].mutation(Pm);
            System.out.println("****************" + individuals[i].getFitness());
        }
    }

    //先保留max个个体
    public List<Individual> betterIndividual() {

        List<Individual> list = new ArrayList<>();
        for(int i = 0; i < N; i++) {
            list.add(individuals[i]);
        }
        list.sort(Individual::compareTo);
        return list;
    }


    //淘汰一些个体，对种群进行更新
    public void updatePopulation() {


        //先保留最优秀的10个个体
        List<Individual> list = betterIndividual();


        /*
        System.out.println("最优秀的十个人：*************");
        //展示list信息
        for(Individual i:list) {
            System.out.println(i.getFitness());
        }
        System.out.println("end*********");

        */


        //从种群数目为N，选择allnum-10个个体
        //通过order记录偏移量
        selectIndividuals(N,allnum-10);
        Individual[] tmp = new Individual[allnum];
        for(int i = 0; i < allnum-10; i++) {
            tmp[i] = individuals[Order[i]];
        }
        for(int i = 0; i < allnum-10; i++) {
            individuals[i] = tmp[i];
        }

        //将最优秀的10个个体加入种群

        int i = allnum - 10;
        int j = 0;
        while(i < allnum) {
            individuals[i++] = list.get(j++);
        }
        //更新种群数目
        N = allnum;


    }

    public double bestFitness() {
        List<Individual> list = betterIndividual();
        return list.get(0).getFitness();
    }

    public void display(){
        List<Individual> list = betterIndividual();
        for(Individual i : list) {
            System.out.println(i.getFitness());
        }
    }

    public static void main(String[] args) {
        Population population = new Population(18,-3.2,12.1,15,4.1,5.8);

        for(int i = 0; i < 100; i++) {
            population.generateChildren(0.6);
            population.mutation(0.01);
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
