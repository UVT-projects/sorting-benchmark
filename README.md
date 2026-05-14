# Sorting Algorithm Benchmark

Experimental benchmark framework for the academic paper:
**"Analiză comparativă a algoritmilor de sortare cuadratici, logaritmici și liniari"**
— Florian Răzvan Dumitrescu, Facultatea de Informatică, UVT, 2026

---

## Algorithms

| Algorithm | Complexity (avg) | Type |
|---|---|---|
| Bubble Sort | O(n²) | Quadratic |
| Insertion Sort | O(n²) | Quadratic |
| Selection Sort | O(n²) | Quadratic |
| Quick Sort | O(n log n) | Log-linear |
| Merge Sort | O(n log n) | Log-linear |
| Heap Sort | O(n log n) | Log-linear |
| Counting Sort | O(n + k) | Linear / int only |
| Radix Sort | O(n + k) | Linear / int only |
| Bucket Sort | O(n) avg | Linear / int only |

QuickSort uses **median-of-three pivot** + **Dijkstra's 3-way partition (DNF)** to handle duplicate-heavy data efficiently.

---

## Data types & distributions

| Data type | Distributions |
|---|---|
| `int` | Random, Sorted, ReverseSorted, NearlySorted, Flat, Controlled |
| `float` | Random, Sorted, ReverseSorted, NearlySorted, Flat |
| `string` | Random, Sorted, ReverseSorted, NearlySorted, RandomNumeric |

Sizes benchmarked: `100, 1 000, 10 000, 100 000, 1 000 000, 10 000 000`

Skip rules applied automatically:
- Quadratic algorithms skipped for n > 100 000
- BucketSort skipped for n > 1 000 000, Flat n > 10 000, Controlled n > 100 000
- CountingSort / RadixSort / BucketSort not run on float or string
- String benchmarks skipped entirely for n > 100 000

---

## Requirements

- Java 17+
- Maven 3.x

---

## Build & run

```bash
mvn compile
mvn exec:java -Dexec.mainClass="generator.Main"
```

Output: `benchmark_results.csv` in the project root.

The benchmark runs a **JVM warm-up phase** (10 iterations per sorter on n=1000) before recording results. Each configuration is averaged over 5 iterations (2 for n ≥ 1 000 000).

---

## CSV output format

```
Algorithm,DataCategory,Size,DataType,Comparisons,Swaps,Passes,TimeNano
```

- `DataCategory` — `int`, `float`, or `string`
- `DataType` — distribution name (e.g. `Random`, `Flat`)
- All numeric columns are averages over iterations
- Lines starting with `#` are skip comments

---

## Project structure

```
src/main/java/
  sorters/
    Sorter.java           — interface (sort overloads, counters, reset)
    AbstractSorter.java   — Template Method: timing + counter reset
    BubbleSort.java
    InsertionSort.java
    SelectionSort.java
    QuickSort.java        — median-of-three + 3-way DNF partition
    MergeSort.java
    HeapSort.java
    CountingSort.java
    RadixSort.java
    BucketSort.java
  generator/
    DataGenerator.java        — int distributions
    FloatDataGenerator.java   — float distributions
    StringDataGenerator.java  — string distributions
    Main.java                 — benchmark runner
    DataShowcase.java         — manual inspection helper
```

---

## Hardware (benchmark run)

| Component | Spec |
|---|---|
| CPU | AMD Ryzen 5 3550H, 4C/8T, 2.1 GHz base / 3.7 GHz boost |
| RAM | 14 GB (shared with Radeon Vega Mobile iGPU) |
| OS | Windows 11 Pro (Build 26200) |
| JDK | Java SE 17.0.7, HotSpot 64-bit Server VM |
