package AliakseiKTest;

public class RandomValue {
    public static int getRandomValue(int min, int max){
        int randomValue = (int) (Math.random() * (max - min + 1)) + min;
        return randomValue;
    }
}
