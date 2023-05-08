package Functions;

public class ExperimentFuctions {
    public double values(double x1, double x2) {
        return 21.5+x1*Math.sin(4.0*Math.PI*x1) + x2*Math.sin(20*Math.PI*x2);
    }

    public double values1(double x1,double x2) {
        return x2-x1;
    }

    public double values2(double x1,double x2) { return x2+x1 ;}


    public static void main(String[] args) {
        System.out.println(new ExperimentFuctions().values(11.6255,5.7250));
    }
}
