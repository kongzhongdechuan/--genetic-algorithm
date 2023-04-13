package GeneticAlgorithm;

import Individual.Individual;
import Population.Population;

public class GeneticAlgorithm {
    private Population population;
    private Individual[] betterIndividual;

    private double Pm;

    public GeneticAlgorithm() {
        population = new Population(18,-3.0,12.1,15,4.1,5.8);
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
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

        for(int i = 0; i < 5000; i++) {
            geneticAlgorithm.setPm(0.01);
            System.out.println(geneticAlgorithm.population.bestFitness().getFitness() + "  X1:   " + geneticAlgorithm.population.bestFitness().getX1()+"  X2:  "+geneticAlgorithm.population.bestFitness().getX2());
            geneticAlgorithm.updatePopulation();
        }

        for(int i = 0; i < 10000; i++) {
            geneticAlgorithm.setPm(0.0033);
            System.out.println(geneticAlgorithm.population.bestFitness().getFitness() + "  X1:   " + geneticAlgorithm.population.bestFitness().getX1()+"  X2:  "+geneticAlgorithm.population.bestFitness().getX2());
            geneticAlgorithm.updatePopulation();
        }


    }
}
