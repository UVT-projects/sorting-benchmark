package sorters;

public class BubbleSort extends AbstractSorter {
    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void performSort(Comparable[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            passes++;
            boolean swapped = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                comparisons++;
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    Comparable temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    @Override
    protected void performSort(int[] sortArr) {
        for (int i = 0; i < sortArr.length - 1; i++) {
            passes++;
            boolean swapped = false;
            for (int j = 0; j < sortArr.length - i - 1; j++) {
                comparisons++;
                if (sortArr[j] > sortArr[j + 1]) {
                    int temp = sortArr[j];
                    sortArr[j] = sortArr[j + 1];
                    sortArr[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
