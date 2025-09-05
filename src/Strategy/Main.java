package Strategy;

import java.util.Random;
import java.util.Arrays;



// -------- Strategy-rajapinta --------
interface SortStrategy {
    void sort(int[] arr);
    String name();
}

// -------- QuickSort --------
class QuickSortStrategy implements SortStrategy {
    @Override
    public String name() { return "QuickSort"; }

    @Override
    public void sort(int[] arr) {
        quicksort(arr, 0, arr.length - 1);
    }

    private void quicksort(int[] a, int low, int high) {
        if (low >= high) return;
        int p = partition(a, low, high);
        quicksort(a, low, p - 1);
        quicksort(a, p + 1, high);
    }

    private int partition(int[] a, int low, int high) {
        // valitaan keskipisteen arvo pivotiksi (simppelein versio)
        int pivot = a[(low + high) >>> 1];
        int i = low, j = high;
        while (i <= j) {
            while (a[i] < pivot) i++;
            while (a[j] > pivot) j--;
            if (i <= j) {
                swap(a, i, j);
                i++; j--;
            }
        }
        return i - 1; // palautetaan jakokohta (yksi mahdollinen tapa)
    }

    private void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}

// -------- MergeSort --------
class MergeSortStrategy implements SortStrategy {
    @Override
    public String name() { return "MergeSort"; }

    @Override
    public void sort(int[] arr) {
        int[] tmp = new int[arr.length];
        mergesort(arr, tmp, 0, arr.length - 1);
    }

    private void mergesort(int[] a, int[] tmp, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) >>> 1;
        mergesort(a, tmp, left, mid);
        mergesort(a, tmp, mid + 1, right);
        merge(a, tmp, left, mid, right);
    }

    private void merge(int[] a, int[] tmp, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            if (a[i] <= a[j]) tmp[k++] = a[i++];
            else              tmp[k++] = a[j++];
        }
        while (i <= mid)  tmp[k++] = a[i++];
        while (j <= right) tmp[k++] = a[j++];

        for (int t = left; t <= right; t++) a[t] = tmp[t];
    }
}

// -------- HeapSort --------
class HeapSortStrategy implements SortStrategy {
    @Override
    public String name() { return "HeapSort"; }

    @Override
    public void sort(int[] arr) {
        heapsort(arr);
    }

    private void heapsort(int[] a) {
        int n = a.length;
        // rakenna max-heap
        for (int i = n/2 - 1; i >= 0; i--) {
            heapify(a, n, i);
        }
        // nosta isoin loppuun ja kutista kekoa
        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end);
            heapify(a, end, 0);
        }
    }

    private void heapify(int[] a, int heapSize, int i) {
        while (true) {
            int largest = i;
            int l = 2*i + 1;
            int r = 2*i + 2;
            if (l < heapSize && a[l] > a[largest]) largest = l;
            if (r < heapSize && a[r] > a[largest]) largest = r;
            if (largest == i) break;
            swap(a, i, largest);
            i = largest;
        }
    }

    private void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}

// -------- Context (valitsee strategian) --------
class Sorter {
    private SortStrategy strategy;

    public Sorter(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(int[] arr) {
        strategy.sort(arr);
    }

    public String currentStrategyName() {
        return strategy.name();
    }
}

// -------- Pieni apuluokka mittauksiin --------
class Perf {
    public static long timeMillis(Runnable r) {
        long t0 = System.nanoTime();
        r.run();
        long t1 = System.nanoTime();
        return (t1 - t0) / 1_000_000; // ms
    }

    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i-1] > a[i]) return false;
        }
        return true;
    }
}

// -------- Main: luo datat, mittaa, tulostaa --------
public class Main {
    public static void main(String[] args) {
        final int SMALL = 30;
        final int LARGE = 100_000; // voit muuttaa jos kone hidas/nopea

        int[] smallData = randomArray(SMALL, 0, 1_000);
        int[] largeData = randomArray(LARGE, 0, 1_000_000);

        SortStrategy[] strategies = new SortStrategy[] {
                new QuickSortStrategy(),
                new MergeSortStrategy(),
                new HeapSortStrategy()
        };

        System.out.println("= = = SMALL DATASET (" + SMALL + " elements) = = =");
        runSuite(smallData, strategies, true);

        System.out.println();
        System.out.println("= = = LARGE DATASET (" + LARGE + " elements) = = =");
        runSuite(largeData, strategies, false);
    }

    private static void runSuite(int[] data, SortStrategy[] strategies, boolean showSample) {
        for (SortStrategy s : strategies) {
            int[] copy = data.clone(); // aina sama lähtö jokaiselle strategi alle
            Sorter sorter = new Sorter(s);

            long ms = Perf.timeMillis(() -> sorter.sort(copy));

            boolean ok = Perf.isSorted(copy);
            System.out.printf("%-12s : %6d ms  | sorted=%s%n",
                    sorter.currentStrategyName(), ms, ok ? "YES" : "NO");

            if (showSample) {
                System.out.println("  before -> " + Arrays.toString(data));
                System.out.println("  after  -> " + Arrays.toString(copy));
            }
        }
    }

    private static int[] randomArray(int n, int min, int maxExclusive) {
        Random rnd = new Random(42); // siemen → toistettavat tulokset
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = min + rnd.nextInt(Math.max(1, maxExclusive - min));
        }
        return a;
    }
}

