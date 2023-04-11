package Functions;

public class Bits {
    private int allBits;
    private int intBits;
    private int floatBits;

    //获取整数部分
    public int getInt(float a,float b) {
        return (int)(b-a);
    }
    //获取小数部分
    public float getDouble(float a,float b) {
        return (b-a-getInt(a,b));
    }

    //寻找区间[a,b],精度为precision的总位数
    public int getBits(float a,float b,double precision) {
        int m = 1;
        int end = (int) ((b-a)/precision);
        while(Math.pow(2,m-1) < end + 1)
            m++;
        return --m;
    }
    //获取区间[a,b]整数所用的位数
    public int getIntBits(float a,float b){
        int m = getInt(a,b) + 1;
        int n = 1;
        while(Math.pow(2,n) < m)
            n++;
        return n;
    }
}
