package sorters;

public class SelectionSort extends AbstractSorter {
    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void performSort(Comparable[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            passes++;
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Comparable temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
                swaps++;
            }
        }
    }

    @Override
    protected void performSort(int[] sortArr) {
        int n = sortArr.length;
        for (int i = 0; i < n - 1; i++) {
            passes++;
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (sortArr[j] < sortArr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = sortArr[minIndex];
                sortArr[minIndex] = sortArr[i];
                sortArr[i] = temp;
                swaps++;
            }
        }
    }
}
