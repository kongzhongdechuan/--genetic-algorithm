package Main;

import Population.Population;
import Individual.Individual;

import java.util.LinkedList;

public class BestIndividual {
    public static void main(String[] args) {
        /*
        //测试通过，说明排序没问题
        Population population = new Population(18,-3.0,12.1,15,4.1,5.8);
        System.out.println("最好fitness：" + population.bestFitness());
        population.display();
        */
        Individual[] individuals = new Individual[10];
        for(int i = 0; i < 10; i ++) {
            individuals[i] = new Individual(18,-3.0,12.1,15,4.1,5.8);
        }
        LinkedList<Individual> list  = new LinkedList<>();
        for(int i = 0; i < 10; i++) {
            list.add(individuals[i]);
        }
        list.sort(Individual::compareTo);

        for(int i = 0; i <10; i ++) {
            System.out.println(individuals[i].getFitness());
        }
    }
}
