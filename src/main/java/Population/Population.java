package Population;

import Individual.Individual;

import java.util.*;


public class Population {
    private Individual[] individuals;
    private static int allnum = 100;   //种群最大
    private static int N = allnum;     //种群中目前数目
    private static double sumFitness = 0;      //总群的总的fitness和，用于轮盘赌

    private static int parentNum = 20;   //每次选择20个“父母”
    private int[] Order = new int[2*allnum];

    private double[] addFitness;           //第i位存储i位之前所有fitness/sumFitness占比

    private int x1Length;
    private double a1;
    private double b1;
    private int x2Length;
    private double a2;
    private double b2;

    public Population(int x1Length,double a1,double b1,int x2Length,double a2,double b2) {
        this.x1Length = x1Length;
        this.a1 = a1;
        this.b1 = b1;
        this.x2Length = x2Length;
        this.a2 = a2;
        this.b2 = b2;

        individuals = new Individual[2*allnum];
        for(int i = 0; i < allnum; i++) {
            assert individuals != null;
            individuals[i] = new Individual(x1Length,a1,b1,x2Length,a2,b2);
        }
    }

    //从总数为n的种群中，选取num数量的个体
    public void selectIndividuals(int n,int num) {

        sumFitness = 0;

        for(int i = 0; i < n; i++) {
            sumFitness += individuals[i].getFitness();
        }

        addFitness = new double[n];
        for(int i = 0; i < n; i++) {
            addFitness[i] = individuals[i].getFitness()/sumFitness;
        }

        //计算累计适应度
        for(int i = 1; i < n; i++) {
            addFitness[i] += addFitness[i-1];
        }

        //选择数目为parentNum的Parents
        choose(num);
    }
    //产生随机数，根据累计适应度来进行选择个体
    public void choose(int num) {
        Random random = new Random();

        for(int i = 0; i < num; i++) {
            double chooseNumber = random.nextDouble();
            int order;
            for(order = 0; order < N; order++) {
                if(chooseNumber < addFitness[order])
                    break;
            }
            Order[i] = order;

        }
    }

    //从获得的parents，根据概率pc后去选择是否后代
    public void generateChildren(double Pc) {
        //选择父母
        selectIndividuals(N,parentNum);
        //产生后代
        Random random = new Random();
        for(int i = 0; i < parentNum/2; i+=2) {
            if(random.nextDouble() <= Pc) {
                Individual father = individuals[Order[i]];
                Individual mother = individuals[Order[i+1]];

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

    //先保留max个个体
    public List<Individual> betterIndividual() {

        List<Individual> list = new ArrayList<Individual>();
        for(int i = 0; i < N; i++) {
            list.add(individuals[i]);
        }
        list.sort(Individual::compareTo);
        return list;
    }

    //淘汰一些个体，对种群进行更新
    public void updatePopulation() {


        //选择90个个体，保留10个优秀个体
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
        List<Individual> list = betterIndividual();
        int j = 1;
        for(int i = allnum-10; i < allnum; i++) {
            individuals[i] = list.get(i-allnum+10);
        }
        N = allnum;


    }

    public double bestFitness() {
        List<Individual> list = betterIndividual();
        return list.get(0).getFitness();
    }

    public static void main(String[] args) {
        Population population = new Population(18,-3.2,12.1,15,4.1,5.8);
        System.out.println("第一代：" + population.bestFitness());

        for(int i = 0; i < 1000; i++) {
            population.generateChildren(0.6);
            population.mutation(0.01);
            population.updatePopulation();
            System.out.println("第" + i +"代：" + population.bestFitness());
            if(population.bestFitness() > 38)
            {
                System.out.println("*******************************************");
            }
        }
    }



}
