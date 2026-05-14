package generator;

import java.util.Random;

public class FloatDataGenerator {

    public static Float[] generateRandomFloat(int size, long seed) {
        Random rand = new Random(seed);
        Float[] arr = new Float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextFloat() * 1000000;
        }
        return arr;
    }

    public static Float[] generateSortedFloat(int size) {
        Float[] arr = new Float[size];
        for (int i = 0; i < size; i++) arr[i] = (float) i;
        return arr;
    }

    public static Float[] generateReverseSortedFloat(int size) {
        Float[] arr = new Float[size];
        for (int i = 0; i < size; i++) arr[i] = (float) (size - i);
        return arr;
    }

    public static Float[] generateNearlySortedFloat(int size, long seed) {
        Float[] arr = generateSortedFloat(size);
        Random rand = new Random(seed);
        int swaps = size / 20;
        for (int i = 0; i < swaps; i++) {
            int a = rand.nextInt(size);
            int b = rand.nextInt(size);
            Float temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
        return arr;
    }

    public static Float[] generateFlatFloat(int size, long seed) {
        Random rand = new Random(seed);
        Float[] arr = new Float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (float) rand.nextInt(10);
        }
        return arr;
    }
}
