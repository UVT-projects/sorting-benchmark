package sorters;

public class QuickSort extends AbstractSorter {

    @Override
    public String getName() {
        return "Quick Sort";
    }

    // ── Comparable[] ──────────────────────────────────────────────────────────

    @Override
    protected void performSort(Comparable[] arr) {
        quickSortG(arr, 0, arr.length - 1);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void quickSortG(Comparable[] arr, int low, int high) {
        while (low < high) {
            passes++;
            int[] b = threeWayPartitionG(arr, low, high);
            // arr[b[0]..b[1]] == pivot — done; recurse only on the two outer parts
            if (b[0] - low < high - b[1]) {
                quickSortG(arr, low, b[0] - 1);
                low = b[1] + 1;
            } else {
                quickSortG(arr, b[1] + 1, high);
                high = b[0] - 1;
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private int[] threeWayPartitionG(Comparable[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        comparisons++; if (arr[low].compareTo(arr[mid])  > 0) swapG(arr, low, mid);
        comparisons++; if (arr[low].compareTo(arr[high]) > 0) swapG(arr, low, high);
        comparisons++; if (arr[mid].compareTo(arr[high]) > 0) swapG(arr, mid, high);
        Comparable pivot = arr[mid];

        int lt = low, i = low, gt = high;
        while (i <= gt) {
            comparisons++;
            int cmp = arr[i].compareTo(pivot);
            if      (cmp < 0) swapG(arr, lt++, i++);
            else if (cmp > 0) swapG(arr, i, gt--);
            else              i++;
        }
        return new int[]{lt, gt};
    }

    private void swapG(Comparable[] arr, int i, int j) {
        if (i != j) {
            Comparable temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            swaps++;
        }
    }

    // ── int[] ─────────────────────────────────────────────────────────────────

    @Override
    protected void performSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            passes++;
            int[] b = threeWayPartition(arr, low, high);
            if (b[0] - low < high - b[1]) {
                quickSort(arr, low, b[0] - 1);
                low = b[1] + 1;
            } else {
                quickSort(arr, b[1] + 1, high);
                high = b[0] - 1;
            }
        }
    }

    private int[] threeWayPartition(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        comparisons++; if (arr[low] > arr[mid])  swap(arr, low, mid);
        comparisons++; if (arr[low] > arr[high]) swap(arr, low, high);
        comparisons++; if (arr[mid] > arr[high]) swap(arr, mid, high);
        int pivot = arr[mid];

        int lt = low, i = low, gt = high;
        while (i <= gt) {
            comparisons++;
            if      (arr[i] < pivot) swap(arr, lt++, i++);
            else if (arr[i] > pivot) swap(arr, i, gt--);
            else                     i++;
        }
        return new int[]{lt, gt};
    }

    private void swap(int[] arr, int i, int j) {
        if (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            swaps++;
        }
    }
}
