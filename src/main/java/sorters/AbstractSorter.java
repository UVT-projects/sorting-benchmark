package sorters;

public abstract class AbstractSorter implements Sorter {
    protected long comparisons = 0;
    protected long swaps = 0;
    protected long passes = 0;
    protected long timeNano = 0;

    @Override
    public void reset() {
        comparisons = 0;
        swaps = 0;
        passes = 0;
        timeNano = 0;
    }

    @Override
    public final void sort(int[] arr) {
        reset();
        long start = System.nanoTime();
        performSort(arr);
        timeNano = System.nanoTime() - start;
    }

    @Override
    public final void sort(Comparable[] arr) {
        reset();
        long start = System.nanoTime();
        performSort(arr);
        timeNano = System.nanoTime() - start;
    }

    protected abstract void performSort(int[] arr);

    protected void performSort(Comparable[] arr) {
        throw new UnsupportedOperationException(getName() + " only supports int[]");
    }

    @Override public long getComparisons() { return comparisons; }
    @Override public long getSwaps() { return swaps; }
    @Override public long getPasses() { return passes; }
    @Override public long getTimeNano() { return timeNano; }
}
