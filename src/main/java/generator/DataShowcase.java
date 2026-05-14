package generator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataShowcase {

    private static final int[] SIZES = {100, 1000, 10000, 100000, 1000000, 10000000};
    private static final long SEED = 42;
    private static final int CONTROLLED_MAX = 1000;
    private static final int SAMPLE_THRESHOLD = 1000;
    private static final int SAMPLE_SIZE = 20;

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("showcase_data.txt"))) {
            writer.println("Data Showcase — SortingBenchmark");
            writer.println("Seed: 42  |  Full data for n <= " + SAMPLE_THRESHOLD + ", first " + SAMPLE_SIZE + " values for larger sizes");
            writer.println("=".repeat(72));

            writeIntSection(writer);
            writeFloatSection(writer);
            writeStringSection(writer);

            System.out.println("Showcase written to showcase_data.txt");

        } catch (IOException e) {
            System.err.println("Error writing showcase: " + e.getMessage());
        }
    }

    // ── int ───────────────────────────────────────────────────────────────────

    private static void writeIntSection(PrintWriter writer) {
        writer.println("\n### INT");

        String[][] datasets = {
                {"Random",        null},
                {"Sorted",        null},
                {"ReverseSorted", null},
                {"NearlySorted",  null},
                {"Flat",          null},
                {"Controlled",    null},
        };

        for (String[] ds : datasets) {
            String type = ds[0];
            for (int size : SIZES) {
                int[] data = generateInt(type, size);
                writeBlock(writer, "int", type, size, data);
            }
        }
    }

    private static int[] generateInt(String type, int size) {
        return switch (type) {
            case "Random"        -> DataGenerator.generateRandom(size, SEED);
            case "Sorted"        -> DataGenerator.generateSorted(size);
            case "ReverseSorted" -> DataGenerator.generateRevSorted(size);
            case "NearlySorted"  -> DataGenerator.generateNearlySorted(size, SEED);
            case "Flat"          -> DataGenerator.generateFlat(size, SEED);
            case "Controlled"    -> DataGenerator.generateControlled(size, CONTROLLED_MAX, SEED);
            default              -> new int[size];
        };
    }

    private static void writeBlock(PrintWriter writer, String category, String type, int size, int[] data) {
        boolean full = size <= SAMPLE_THRESHOLD;
        int limit = full ? data.length : SAMPLE_SIZE;
        writer.printf("%n--- %s | %s | n=%,d (%s) ---%n", category, type, size, full ? "complete" : "first " + SAMPLE_SIZE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            if (i > 0) sb.append(", ");
            sb.append(data[i]);
        }
        if (!full) sb.append(", ...");
        writer.println(sb);
    }

    // ── float ─────────────────────────────────────────────────────────────────

    private static void writeFloatSection(PrintWriter writer) {
        writer.println("\n\n### FLOAT");

        String[] types = {"Random", "Sorted", "ReverseSorted", "NearlySorted", "Flat"};

        for (String type : types) {
            for (int size : SIZES) {
                Float[] data = generateFloat(type, size);
                writeBlockF(writer, type, size, data);
            }
        }
    }

    private static Float[] generateFloat(String type, int size) {
        return switch (type) {
            case "Random"        -> FloatDataGenerator.generateRandomFloat(size, SEED);
            case "Sorted"        -> FloatDataGenerator.generateSortedFloat(size);
            case "ReverseSorted" -> FloatDataGenerator.generateReverseSortedFloat(size);
            case "NearlySorted"  -> FloatDataGenerator.generateNearlySortedFloat(size, SEED);
            case "Flat"          -> FloatDataGenerator.generateFlatFloat(size, SEED);
            default              -> new Float[size];
        };
    }

    private static void writeBlockF(PrintWriter writer, String type, int size, Float[] data) {
        boolean full = size <= SAMPLE_THRESHOLD;
        int limit = full ? data.length : SAMPLE_SIZE;
        writer.printf("%n--- float | %s | n=%,d (%s) ---%n", type, size, full ? "complete" : "first " + SAMPLE_SIZE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            if (i > 0) sb.append(", ");
            sb.append(String.format("%.2f", data[i]));
        }
        if (!full) sb.append(", ...");
        writer.println(sb);
    }

    // ── string ────────────────────────────────────────────────────────────────

    private static void writeStringSection(PrintWriter writer) {
        writer.println("\n\n### STRING");

        String[] types = {"Random", "Sorted", "ReverseSorted", "NearlySorted", "RandomNumeric"};
        int[] stringSizes = {100, 1000, 10000, 100000};

        for (String type : types) {
            for (int size : stringSizes) {
                String[] data = generateString(type, size);
                writeBlockS(writer, type, size, data);
            }
        }
    }

    private static String[] generateString(String type, int size) {
        return switch (type) {
            case "Random"        -> StringDataGenerator.generateRandomStringLex(size, SEED);
            case "Sorted"        -> StringDataGenerator.generateSortedStringLex(size);
            case "ReverseSorted" -> StringDataGenerator.generateReverseSortedStringLex(size);
            case "NearlySorted"  -> StringDataGenerator.generateNearlySortedStringLex(size, SEED);
            case "RandomNumeric" -> StringDataGenerator.generateRandomStringNumeric(size, SEED);
            default              -> new String[size];
        };
    }

    private static void writeBlockS(PrintWriter writer, String type, int size, String[] data) {
        boolean full = size <= SAMPLE_THRESHOLD;
        int limit = full ? data.length : SAMPLE_SIZE;
        writer.printf("%n--- string | %s | n=%,d (%s) ---%n", type, size, full ? "complete" : "first " + SAMPLE_SIZE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            if (i > 0) sb.append(", ");
            sb.append(data[i]);
        }
        if (!full) sb.append(", ...");
        writer.println(sb);
    }
}
