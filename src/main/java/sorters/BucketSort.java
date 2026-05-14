package sorters;

public class BucketSort extends AbstractSorter {

    @Override
    public String getName() {
        return "Bucket Sort";
    }

    @Override
    protected void performSort(int[] arr) {
        if (arr.length <= 1) return;

        int min = arr[0], max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            passes++;
            if (arr[i] < min) min = arr[i];
            if (arr[i] > max) max = arr[i];
        }

        if (min == max) return;

        int n = arr.length;
        double range = (double)(max - min + 1);

        // Pass 1: count how many elements go into each bucket
        int[] counts = new int[n];
        for (int val : arr) {
            passes++;
            counts[bucketIndex(val, min, range, n)]++;
        }

        // Compute start position of each bucket in the temp array
        int[] starts = new int[n];
        for (int i = 1; i < n; i++) {
            starts[i] = starts[i - 1] + counts[i - 1];
        }

        // Pass 2: distribute elements into their buckets in temp[]
        int[] temp = new int[n];
        int[] pos = starts.clone();
        for (int val : arr) {
            passes++;
            temp[pos[bucketIndex(val, min, range, n)]++] = val;
            swaps++;
        }

        // Sort each non-trivial bucket with insertion sort
        for (int i = 0; i < n; i++) {
            if (counts[i] <= 1) continue;
            passes++;
            insertionSort(temp, starts[i], starts[i] + counts[i] - 1);
        }

        // Copy sorted temp back to arr
        System.arraycopy(temp, 0, arr, 0, n);
    }

    private int bucketIndex(int val, int min, double range, int bucketCount) {
        int idx = (int)((val - min) / range * bucketCount);
        return idx < bucketCount ? idx : bucketCount - 1;
    }

    private void insertionSort(int[] arr, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= lo) {
                comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    swaps++;
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }
}
