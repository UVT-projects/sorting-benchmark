package generator;

import java.util.Random;

public class DataGenerator {

    public static int[] generateRandom(int size, long seed){
        Random rand  = new Random(seed);
        int[] randArr = new int[size];
        for (int i = 0; i < size; i++) {
            randArr[i] = rand.nextInt(1000000);
        }
        return randArr;
    }

    public static int[] generateSorted(int size){
        int[] sortedArr = new int[size];
        for (int i = 0; i < size; i++) {
            sortedArr[i] = i;
        }
        return sortedArr;
    }

    public static int[] generateRevSorted(int size){
        int[] revSortArr = new int[size];
        for (int i = 0; i < size; i++) {
            revSortArr[i] = size - i;
        }
        return revSortArr;
    }

    public static int[] generateNearlySorted(int size, long seed) {
        int[] arr = generateSorted(size);
        Random rand = new Random(seed);
        int swaps = size / 20;
        for (int i = 0; i < swaps; i++) {
            int a = rand.nextInt(size);
            int b = rand.nextInt(size);
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
        return arr;
    }

    public static int[] generateFlat(int size, long seed) {
        Random rand = new Random(seed);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(10);
        }
        return arr;
    }

    public static int[] generateControlled(int size, int maxValue, long seed) {
        Random rand = new Random(seed);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(maxValue);
        }
        return arr;
    }
}
