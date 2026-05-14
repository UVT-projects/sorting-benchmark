package generator;

import java.util.Random;

public class StringDataGenerator {
    public static String[] generateRandomStringNumeric(int size, long seed) {
        Random rand = new Random(seed);
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = String.valueOf(rand.nextInt(1000000));
        }
        return arr;
    }

    public static String[] generateSortedStringNumeric(int size) {
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) arr[i] = String.valueOf(i);
        return arr;
    }

    public static String[] generateReverseSortedStringNumeric(int size) {
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) arr[i] = String.valueOf(size - i);
        return arr;
    }

    public static String[] generateRandomStringLex(int size, long seed) {
        Random rand = new Random(seed);
        String[] arr = new String[size];
        String[] words = {"apple", "banana", "cherry", "date", "elderberry",
                "fig", "grape", "honeydew", "kiwi", "lemon"};
        for (int i = 0; i < size; i++) {
            arr[i] = words[rand.nextInt(words.length)] + rand.nextInt(1000);
        }
        return arr;
    }

    public static String[] generateSortedStringLex(int size) {
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) arr[i] = String.format("%010d", i);
        return arr;
    }

    public static String[] generateReverseSortedStringLex(int size) {
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) arr[i] = String.format("%010d", size - i);
        return arr;
    }

    public static String[] generateNearlySortedStringLex(int size, long seed) {
        String[] arr = generateSortedStringLex(size);
        Random rand = new Random(seed);
        int swaps = size / 20;
        for (int i = 0; i < swaps; i++) {
            int a = rand.nextInt(size);
            int b = rand.nextInt(size);
            String temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
        return arr;
    }
}
