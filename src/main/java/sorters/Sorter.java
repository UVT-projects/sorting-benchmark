package sorters;

public interface Sorter {
    void sort(int[] arr);
    void sort(Comparable[] arr);
    String getName();

    // Metrics
    long getComparisons();
    long getSwaps();
    long getPasses();
    long getTimeNano();

    void reset();
}
