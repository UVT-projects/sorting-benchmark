package sorters;

public class HeapSort extends AbstractSorter {
    @Override
    public String getName() {
        return "Heap Sort";
    }

    @Override
    protected void performSort(Comparable[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            passes++;
            heapifyG(arr, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            passes++;
            Comparable temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            swaps++;
            heapifyG(arr, i, 0);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void heapifyG(Comparable[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n) {
            comparisons++;
            if (arr[l].compareTo(arr[largest]) > 0) largest = l;
        }
        if (r < n) {
            comparisons++;
            if (arr[r].compareTo(arr[largest]) > 0) largest = r;
        }
        if (largest != i) {
            Comparable swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            swaps++;
            heapifyG(arr, n, largest);
        }
    }

    @Override
    protected void performSort(int[] arr) {
        int n = arr.length;

        // Build heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            passes++;
            heapify(arr, n, i);
        }

        // Extract elements
        for (int i = n - 1; i > 0; i--) {
            passes++;
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            swaps++;

            heapify(arr, i, 0);
        }
    }

    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n) {
            comparisons++;
            if (arr[l] > arr[largest]) {
                largest = l;
            }
        }

        if (r < n) {
            comparisons++;
            if (arr[r] > arr[largest]) {
                largest = r;
            }
        }

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            swaps++;

            heapify(arr, n, largest);
        }
    }
}
