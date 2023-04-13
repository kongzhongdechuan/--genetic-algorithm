package GeneticAlgorithm;

import Individual.Individual;
import Population.Population;

public class GeneticAlgorithm {
    private Population population;
    private Individual[] betterIndividual;

    public GeneticAlgorithm() {
        population = new Population(18,-3.0,12.1,15,4.1,5.8);
    }

    public void updatePopulation() {
        population.generateChildren(1);
        betterIndividual = population.betterIndividual();
        population.mutation(0.01);
        population.update();
        population.setIndividuals(betterIndividual,10,0);
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

    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        for(int i = 0; i < 100000; i++) {
            System.out.println(geneticAlgorithm.population.bestFitness());
            geneticAlgorithm.updatePopulation();
        }
    }
}
