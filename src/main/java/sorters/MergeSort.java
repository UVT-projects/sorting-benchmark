package sorters;

public class MergeSort extends AbstractSorter {
    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    protected void performSort(Comparable[] arr) {
        mergeSortG(arr, 0, arr.length - 1);
    }

    private void mergeSortG(Comparable[] arr, int l, int r) {
        if (l < r) {
            passes++;
            int m = l + (r - l) / 2;
            mergeSortG(arr, l, m);
            mergeSortG(arr, m + 1, r);
            mergeG(arr, l, m, r);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void mergeG(Comparable[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        Comparable[] L = new Comparable[n1];
        Comparable[] R = new Comparable[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[l + i];
        for (int j = 0; j < n2; j++) R[j] = arr[m + 1 + j];
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            comparisons++;
            if (L[i].compareTo(R[j]) <= 0) {
                arr[k] = L[i++];
            } else {
                arr[k] = R[j++];
            }
            swaps++;
            k++;
        }
        while (i < n1) { arr[k++] = L[i++]; swaps++; }
        while (j < n2) { arr[k++] = R[j++]; swaps++; }
    }

    @Override
    protected void performSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            passes++;
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[m + 1 + j];

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            comparisons++;
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            swaps++; // Treating movement back to original array as a "swap"
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            swaps++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            swaps++;
        }
    }
}
