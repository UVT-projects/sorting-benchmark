package sorters;

public class InsertionSort extends AbstractSorter {
    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void performSort(Comparable[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            passes++;
            Comparable key = arr[i];
            int j = i - 1;
            while (j >= 0) {
                comparisons++;
                if (arr[j].compareTo(key) > 0) {
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

    @Override
    protected void performSort(int[] sortArr) {
        int n = sortArr.length;
        for (int i = 1; i < n; i++) {
            passes++;
            int key = sortArr[i];
            int j = i - 1;
            while (j >= 0) {
                comparisons++;
                if (sortArr[j] > key) {
                    sortArr[j + 1] = sortArr[j];
                    swaps++;
                    j--;
                } else {
                    break;
                }
            }
            sortArr[j + 1] = key;
        }
    }
}
