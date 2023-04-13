package Main;

import Individual.Individual;

public class Test {
    public static void main(String[] args) {
        Individual[] individuals = new Individual[2];
        individuals[0] = new Individual(10,1,4,10,2,5);
        individuals[1] = new Individual(individuals[0]);
        System.out.println(individuals[0].getFitness());
        System.out.println(individuals[1].getFitness());
    }
}
