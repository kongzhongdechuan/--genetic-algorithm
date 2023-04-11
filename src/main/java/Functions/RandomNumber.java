package Functions;

import java.util.Random;

public class RandomNumber {
    public void RandomInit(int[] a,int length) {
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            if(random.nextFloat() < 0.5)
                a[i] = 0;
            else
                a[i] = 1;
        }
    }
    public int toInt(int[] a) {
        int num = 0;
        for(int i = 0; i < 5; i++) {
            num += num*2 + a[i];
        }
        return num;
    }

    public static void main(String[] args) {
        int[] a = new int[5];
        RandomNumber randomNumber = new RandomNumber();
        randomNumber.RandomInit(a,5);
        System.out.println(randomNumber.toInt(a));
    }
}
