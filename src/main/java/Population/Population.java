package Population;

import Individual.Individual;

public class Population {
    private Individual[] individuals;
    private static int num = 100;   //种群数目
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
        for(int i = 0; i < num; i++) {
            assert individuals != null;
            individuals[i] = new Individual(x1Length,a1,b1,x2Length,a2,b2);
        }
    }



}
