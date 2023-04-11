package Test;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;//变异
    private double crossoverRate;//交叉
    private int elitismCount;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    public Population initPopulation(int chromosomeLength) {
        return new Population(this.populationSize, chromosomeLength);
    }

    public double calcFitness(Individual individual) {
        double x = decodeChromosome(individual);
        return Math.sin(10 * Math.PI * x) / (2 * x) + Math.pow(x - 1, 4);
    }

    public double decodeChromosome(Individual individual) {
        int[] chromosome = individual.getChromosome();
        double x = 0;
        for (int i = 0; i < chromosome.length; i++) {
            x += chromosome[i] * Math.pow(2, i);
        }
        return x / (Math.pow(2, chromosome.length) - 1) * 1024 - 512;
    }

    public void evalPopulation(Population population) {
        double populationFitness = 0;
        for (Individual individual : population.getIndividuals()) {
            populationFitness += calcFitness(individual);
        }
        population.setPopulationFitness(populationFitness);
    }

    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
        return generationsCount >= maxGenerations;
    }

    public Individual selectParent(Population population) {
        Individual[] individuals = population.getIndividuals();
        double populationFitness = population.getPopulationFitness();
        double rouletteWheelPosition = new Random().nextDouble() * populationFitness;
        double spinWheel = 0;
        for (Individual individual : individuals) {
            spinWheel += calcFitness(individual);
            if (spinWheel >= rouletteWheelPosition) {
                return individual;
            }
        }
        return individuals[population.size() - 1];
    }

    public Population crossoverPopulation(Population population) {
        Population newPopulation = new Population(population.size());
        for (int i = 0; i < elitismCount; i++) {
            newPopulation.setIndividual(i, population.getFittest(i));
        }
        for (int i = elitismCount; i < population.size(); i++) {
            Individual parent1 = selectParent(population);
            Individual parent2 = selectParent(population);
            Individual child = crossover(parent1, parent2);
            newPopulation.setIndividual(i, child);
        }
        return newPopulation;
    }

    public Individual crossover(Individual parent1, Individual parent2) {
        Individual child = new Individual(parent1.getChromosome().length);
        int[] childChromosome = child.getChromosome();
        int[] parent1Chromosome = parent1.getChromosome();
        int[] parent2Chromosome = parent2.getChromosome();
        for (int i = 0; i < parent1Chromosome.length; i++) {
            if (new Random().nextDouble() < crossoverRate) {
                childChromosome[i] = parent1Chromosome[i];
            } else {
                childChromosome[i] = parent2Chromosome[i];
            }
        }
        return child;
    }

    public Population mutatePopulation(Population population) {
        Population newPopulation = new Population(population.size());
        for (int i = 0; i < elitismCount; i++) {
            newPopulation.setIndividual(i, population.getFittest(i));
        }
        for (int i = elitismCount; i < population.size(); i++) {
            Individual individual = population.getIndividual(i);
            mutate(individual);
            newPopulation.setIndividual(i, individual);
        }
        return newPopulation;
    }

    public void mutate(Individual individual) {
        int[] chromosome = individual.getChromosome();
        for (int i = 0; i < chromosome.length; i++) {
            if (new Random().nextDouble() < mutationRate) {
                chromosome[i] = chromosome[i] == 0 ? 1 : 0;
            }
        }
    }
}

class Individual {
    private int[] chromosome;
    private double fitness = -1;

    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
        for (int i = 0; i < chromosomeLength; i++) {
            this.chromosome[i] = new Random().nextInt(2);
        }
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public int size() {
        return chromosome.length;
    }

    public void setGene(int offset, int gene) {
        chromosome[offset] = gene;
    }

    public int getGene(int offset) {
        return chromosome[offset];
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}

class Population {
    private Individual[] individuals;
    private double populationFitness;

    public Population(int populationSize, int chromosomeLength) {
        individuals = new Individual[populationSize];
        for (int i = 0; i < populationSize; i++) {
            individuals[i] = new Individual(chromosomeLength);
        }
    }

    public Population(int populationSize) {
        individuals = new Individual[populationSize];
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public void setIndividual(int index, Individual individual) {
        individuals[index] = individual;
    }

    public int size() {
        return individuals.length;
    }

    public Individual getFittest(int offset) {
        Arrays.sort(individuals, Comparator.comparingDouble(Individual::getFitness));
        return individuals[offset];
    }

    public double getPopulationFitness() {
        double fitness = 0;
        for (Individual individual : individuals) {
            fitness += individual.getFitness();
        }
        return fitness;
    }

    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }
    // This is not the main function, but an example of how it could be implemented
// The main function would typically be in a separate file
    public static void main(String[] args) {
        // Set up GA parameters
        int populationSize = 100;
        int chromosomeLength = 10;
        double crossoverRate = 0.8;
        double mutationRate = 0.1;
        int elitismCount = 2;
        int maxGenerations = 100;

        // Initialize population
        Population population = new Population(populationSize, chromosomeLength);

        // Evaluate initial population
        GeneticAlgorithm ga = new GeneticAlgorithm(populationSize,crossoverRate, mutationRate, elitismCount);
        ga.evalPopulation(population);

        // Start evolution loop
        int generation = 1;
        while (!ga.isTerminationConditionMet(generation, maxGenerations)) {
            // Print fittest individual from population
            System.out.println("Best solution: " + population.getFittest(0).getFitness());

            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population);

            // Evaluate population
            ga.evalPopulation(population);

            // Increment the current generation
            generation++;
        }

        // Print final results
        System.out.println("Finished after " + maxGenerations + " generations.");
        System.out.println("Best solution: " + population.getFittest(0).getFitness());
    }
}


