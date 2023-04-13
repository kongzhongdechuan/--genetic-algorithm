package Population;

import Individual.Individual;

import java.util.*;


public class newPopulation {
    private final Individual[] individuals;
    private static final int allnum = 50;   //种群最大容量
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

    public newPopulation(int x1Length,double a1,double b1,int x2Length,double a2,double b2) {
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

    //通过轮盘赌，从总数为n的种群中，选取num数量的个体
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


    public void sort() {

       for(int i = 0; i < N; i++) {
           for(int j = 0; j < N - i - 1; j++) {
               if(individuals[j].getFitness() < individuals[j+1].getFitness()) {
                   Individual tmp = individuals[j];
                   individuals[j] = individuals[j+1];
                   individuals[j+1] = tmp;
               }
           }
       }
    }

    public void updatenewPopulation() {

        //产生孩子
        generateChildren(0.6);


        //保留最好的5个,不能用list，有bug
        sort();
        Individual[] temp = new Individual[10];
        for(int i = 0; i < 10; i ++) {
            temp[i] = individuals[i];
        }

        //变异
        mutation(0.01);

        //获取剩下的allnum-10个对象
        selectIndividuals(N,allnum-10);

        //利用tmp存储对象
        Individual[] tmp = new Individual[allnum-10];
        for(int i = 0; i < allnum - 10; i++){
            tmp[i] = individuals[Order[i]];
        }

        int i = 0;      //记录individuals中的偏移量
        int j = 0;      //记录tmp中的偏移量

        while(i < 10) {

            System.out.println("最优选择：" + temp[i].getFitness());
            individuals[i] = temp[i];
            System.out.println("进入：" + individuals[i].getFitness());
            i++;
        }
        while(i < allnum) {
            individuals[i++] = tmp[j++];
        }

        //重置N
        N = allnum;
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

    public static void main(String[] args) {
        newPopulation newPopulation = new newPopulation(18,-3.2,12.1,15,4.1,5.8);
        for(int i = 0; i < 20; i++) {
            newPopulation.display();
            System.out.println("最佳：" + newPopulation.bestFitness());
            System.out.println("********************************");
            newPopulation.updatenewPopulation();
        }
    }



}
