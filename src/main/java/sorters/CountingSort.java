package sorters;

public class CountingSort extends AbstractSorter {
    @Override
    public String getName() {
        return "Counting Sort";
    }

    @Override
    protected void performSort(int[] arr) {
        if (arr.length == 0) return;

        // Find min and max to determine range
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            passes++;
            if (arr[i] > max) max = arr[i];
            if (arr[i] < min) min = arr[i];
        }

        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[arr.length];

        // Store count of each element
        for (int i = 0; i < arr.length; i++) {
            passes++;
            count[arr[i] - min]++;
        }

        // Change count[i] so that count[i] now contains actual
        // position of this element in output array
        for (int i = 1; i < count.length; i++) {
            passes++;
            count[i] += count[i - 1];
        }

        // Build the output array
        for (int i = arr.length - 1; i >= 0; i--) {
            passes++;
            output[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
            swaps++; // Movement to output array
        }

        // Copy the output array to arr
        for (int i = 0; i < arr.length; i++) {
            passes++;
            arr[i] = output[i];
            swaps++; // Movement back to original array
        }
    }
}
