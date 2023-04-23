package Main;

import Population.Population;
import Individual.Individual;

import java.util.LinkedList;

public class BestIndividual {
    public static void main(String[] args) {
        //PPT代码输入之后得到结果
        int[] X1 = {1,1,1,1,0,1,1,1,1,1,1,1,0,1,0,0,1,0};
        int[] X2 = {1,1,1,1,0,1,0,0,1,0,1,1,0,1,0};

        Individual individual = new Individual(18,-3.0,12.1,15,4.1,5.8);
        individual.setGeneX1(X1);
        individual.setGeneX2(X2);
        individual.calculateFitness();
        System.out.println("The max is :"+individual.getFitness());
    }
}
