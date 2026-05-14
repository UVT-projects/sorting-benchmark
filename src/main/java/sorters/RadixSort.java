package sorters;

import java.util.Arrays;

public class RadixSort extends AbstractSorter {
    @Override
    public String getName() {
        return "Radix Sort";
    }

    @Override
    protected void performSort(int[] arr) {
        if (arr.length == 0) return;

        // Find the maximum number to know number of digits
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            passes++;
            if (arr[i] > max) max = arr[i];
        }

        // Do counting sort for every digit. Note that instead
        // of passing digit number, exp is passed. exp is 10^i
        // where i is current digit number
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, exp);
        }
    }

    private void countSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (int i = 0; i < n; i++) {
            passes++;
            count[(arr[i] / exp) % 10]++;
        }

        // Change count[i] so that count[i] now contains actual
        // position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            passes++;
            count[i] += count[i - 1];
        }

        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            passes++;
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
            swaps++;
        }

        // Copy the output array to arr[]
        for (int i = 0; i < n; i++) {
            passes++;
            arr[i] = output[i];
            swaps++;
        }
    }
}
