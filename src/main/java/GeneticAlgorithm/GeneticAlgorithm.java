package GeneticAlgorithm;

import Functions.Bits;
import Individual.Individual;
import Population.Population;


import java.util.Scanner;

public class GeneticAlgorithm {
    private Population population;
    private Individual[] betterIndividual;

    private double Pm;

    public GeneticAlgorithm() {
        population = new Population(18,-3.0,12.1,15,4.1,5.8);
    }

    public GeneticAlgorithm(int l1, double a1,double b1,int l2,double a2,double b2)
    {
        population = new Population(l1,a1,b1,l2,a2,b2);
    }

    public void updatePopulation() {
        population.generateChildren(1);
        betterIndividual = population.betterIndividual();
        population.mutation(Pm);
        population.update();
        population.setIndividuals(betterIndividual,20,0);
        //展示betterIndividual内容
        //displayBetter();
        //展示population内容
        //population.display();
        //System.out.println("end***************");
    }

    public void displayBetter() {
        for(int i = 0; i < 10; i++) {
            System.out.println("Better:" + betterIndividual[i].getFitness());
        }
    }

    public void setPm(double pm) {
        Pm = pm;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double a1,b1,a2,b2;
        int l1,l2;
        double precision;
        System.out.println("Input the range of x1:");
        a1 = scanner.nextDouble();
        b1 = scanner.nextDouble();
        System.out.println("Input the range of x2:");
        a2 = scanner.nextDouble();
        b2 = scanner.nextDouble();
        System.out.println("Input the precision:");
        precision = scanner.nextDouble();


        l1 = new Bits().getBits(a1,b1,precision);
        l2 = new Bits().getBits(a2,b2,precision);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(l1,a1,b1,l2,a2,b2);

        //GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < 50; i++) {
            geneticAlgorithm.setPm(0.01);
            //System.out.println(geneticAlgorithm.population.bestFitness().getFitness() + "  X1:   " + geneticAlgorithm.population.bestFitness().getX1()+"  X2:  "+geneticAlgorithm.population.bestFitness().getX2());
            geneticAlgorithm.updatePopulation();
        }

        for(int i = 0; i < 1000; i++) {
            geneticAlgorithm.setPm(0.0033);
            //System.out.println(geneticAlgorithm.population.bestFitness().getFitness() + "  X1:   " + geneticAlgorithm.population.bestFitness().getX1()+"  X2:  "+geneticAlgorithm.population.bestFitness().getX2());
            geneticAlgorithm.updatePopulation();
        }


        System.out.println();
        System.out.println();
        long endTime = System.currentTimeMillis();
        System.out.println("The running time is :" + (endTime - startTime) + "ms");
        Individual best = new Individual(geneticAlgorithm.population.bestFitness());
        System.out.println("The best fitness is:    " + best.getFitness());
        System.out.print("X1: " + best.getX1() + "   ");
        best.displayX1();
        System.out.print("X2: " + best.getX2() + "    ");
        best.displayX2();



    }
}
