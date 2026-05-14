package generator;

import sorters.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final int[] SIZES = {100, 1000, 10000, 100000, 1000000, 10000000};
    private static final int ITERATIONS = 5;
    private static final long SEED = 42;
    private static final int CONTROLLED_MAX = 1000;

    public static void main(String[] args) {
        List<Sorter> sorters = Arrays.asList(
                new BubbleSort(),
                new InsertionSort(),
                new SelectionSort(),
                new QuickSort(),
                new MergeSort(),
                new HeapSort(),
                new CountingSort(),
                new RadixSort(),
                new BucketSort()
        );

        System.out.println("=== Starting Sorting Benchmark ===");

        System.out.println("Starting Warm-up...");
        warmup(sorters);
        System.out.println("Warm-up complete.");

        try (PrintWriter writer = new PrintWriter(new FileWriter("benchmark_results.csv"))) {
            writer.println("Algorithm,DataCategory,Size,DataType,Comparisons,Swaps,Passes,TimeNano");

            for (int size : SIZES) {
                System.out.println("\nBenchmarking size: " + size);
                runIntTests(sorters, size, writer);
                runFloatTests(sorters, size, writer);
                runStringTests(sorters, size, writer);
                writer.flush();
            }

            System.out.println("\nBenchmark complete! Results saved to: benchmark_results.csv");

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void warmup(List<Sorter> sorters) {
        int[] intData = DataGenerator.generateRandom(1000, SEED);
        Float[] floatData = FloatDataGenerator.generateRandomFloat(1000, SEED);
        String[] stringData = StringDataGenerator.generateRandomStringLex(1000, SEED);
        for (Sorter sorter : sorters) {
            for (int i = 0; i < 10; i++) {
                sorter.sort(intData.clone());
            }
            if (!isIntOnly(sorter)) {
                for (int i = 0; i < 10; i++) {
                    sorter.sort(floatData.clone());
                    sorter.sort(stringData.clone());
                }
            }
        }
    }

    // ── int benchmark ──────────────────────────────────────────────────────────

    private static void runIntTests(List<Sorter> sorters, int size, PrintWriter writer) {
        String[] types = {"Random", "Sorted", "ReverseSorted", "NearlySorted", "Flat", "Controlled"};
        int iterations = (size >= 1000000) ? 2 : ITERATIONS;

        for (String type : types) {
            System.out.print("  [int] " + type + "... ");
            int[] originalData = generateIntData(type, size);

            for (Sorter sorter : sorters) {
                if (shouldSkipInt(sorter, type, size)) continue;

                long totalComp = 0, totalSwaps = 0, totalPasses = 0, totalTime = 0;
                for (int i = 0; i < iterations; i++) {
                    sorter.sort(originalData.clone());
                    totalComp   += sorter.getComparisons();
                    totalSwaps  += sorter.getSwaps();
                    totalPasses += sorter.getPasses();
                    totalTime   += sorter.getTimeNano();
                }

                writer.printf("%s,int,%d,%s,%d,%d,%d,%d\n",
                        sorter.getName(), size, type,
                        totalComp / iterations, totalSwaps / iterations,
                        totalPasses / iterations, totalTime / iterations);
            }
            System.out.println("Done.");
        }
    }

    private static int[] generateIntData(String type, int size) {
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

    private static boolean shouldSkipInt(Sorter sorter, String dataType, int size) {
        if (size > 100000 && isQuadratic(sorter)) return true;

        if (sorter.getName().equals("Bucket Sort")) {
            if (size > 1000000)                                 return true;
            if (dataType.equals("Flat")       && size > 10000)  return true;
            if (dataType.equals("Controlled") && size > 100000) return true;
        }
        return false;
    }

    // ── float benchmark ────────────────────────────────────────────────────────

    private static void runFloatTests(List<Sorter> sorters, int size, PrintWriter writer) {
        String[] types = {"Random", "Sorted", "ReverseSorted", "NearlySorted", "Flat"};
        int iterations = (size >= 1000000) ? 2 : ITERATIONS;

        for (String type : types) {
            System.out.print("  [float] " + type + "... ");
            Float[] originalData = generateFloatData(type, size);

            for (Sorter sorter : sorters) {
                if (isIntOnly(sorter)) continue;
                if (size > 100000 && isQuadratic(sorter)) continue;

                long totalComp = 0, totalSwaps = 0, totalPasses = 0, totalTime = 0;
                for (int i = 0; i < iterations; i++) {
                    sorter.sort(originalData.clone());
                    totalComp   += sorter.getComparisons();
                    totalSwaps  += sorter.getSwaps();
                    totalPasses += sorter.getPasses();
                    totalTime   += sorter.getTimeNano();
                }

                writer.printf("%s,float,%d,%s,%d,%d,%d,%d\n",
                        sorter.getName(), size, type,
                        totalComp / iterations, totalSwaps / iterations,
                        totalPasses / iterations, totalTime / iterations);
            }
            System.out.println("Done.");
        }
    }

    private static Float[] generateFloatData(String type, int size) {
        return switch (type) {
            case "Random"        -> FloatDataGenerator.generateRandomFloat(size, SEED);
            case "Sorted"        -> FloatDataGenerator.generateSortedFloat(size);
            case "ReverseSorted" -> FloatDataGenerator.generateReverseSortedFloat(size);
            case "NearlySorted"  -> FloatDataGenerator.generateNearlySortedFloat(size, SEED);
            case "Flat"          -> FloatDataGenerator.generateFlatFloat(size, SEED);
            default              -> new Float[size];
        };
    }

    // ── string benchmark ───────────────────────────────────────────────────────

    private static void runStringTests(List<Sorter> sorters, int size, PrintWriter writer) {
        if (size > 100000) {
            writer.printf("# String benchmarks skipped for size=%d — string comparison overhead makes full runs take days%n", size);
            System.out.println("  [string] Skipped (size > 100 000 — would take days).");
            return;
        }

        String[] types = {"Random", "Sorted", "ReverseSorted", "NearlySorted", "RandomNumeric"};
        int iterations = ITERATIONS;

        for (String type : types) {
            System.out.print("  [string] " + type + "... ");
            String[] originalData = generateStringData(type, size);

            for (Sorter sorter : sorters) {
                if (isIntOnly(sorter)) continue;

                long totalComp = 0, totalSwaps = 0, totalPasses = 0, totalTime = 0;
                for (int i = 0; i < iterations; i++) {
                    sorter.sort(originalData.clone());
                    totalComp   += sorter.getComparisons();
                    totalSwaps  += sorter.getSwaps();
                    totalPasses += sorter.getPasses();
                    totalTime   += sorter.getTimeNano();
                }

                writer.printf("%s,string,%d,%s,%d,%d,%d,%d\n",
                        sorter.getName(), size, type,
                        totalComp / iterations, totalSwaps / iterations,
                        totalPasses / iterations, totalTime / iterations);
            }
            System.out.println("Done.");
        }
    }

    private static String[] generateStringData(String type, int size) {
        return switch (type) {
            case "Random"        -> StringDataGenerator.generateRandomStringLex(size, SEED);
            case "Sorted"        -> StringDataGenerator.generateSortedStringLex(size);
            case "ReverseSorted" -> StringDataGenerator.generateReverseSortedStringLex(size);
            case "NearlySorted"  -> StringDataGenerator.generateNearlySortedStringLex(size, SEED);
            case "RandomNumeric" -> StringDataGenerator.generateRandomStringNumeric(size, SEED);
            default              -> new String[size];
        };
    }

    // ── helpers ────────────────────────────────────────────────────────────────

    private static boolean isQuadratic(Sorter sorter) {
        String name = sorter.getName();
        return name.contains("Bubble") || name.contains("Selection") || name.contains("Insertion");
    }

    private static boolean isIntOnly(Sorter sorter) {
        String name = sorter.getName();
        return name.contains("Counting") || name.contains("Radix") || name.contains("Bucket");
    }
}
