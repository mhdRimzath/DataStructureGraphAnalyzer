package graphanalyzer;
import java.util.Arrays;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.function.IntConsumer;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    private static final IntArray array = new IntArray(100);
    private static final IntStack stack = new IntStack(100);
    private static final IntQueue queue = new IntQueue(100);
    private static final IntLinkedList list = new IntLinkedList();
    private static final Graph graph = new Graph();
    private static final PerformanceLog log = new PerformanceLog();

    // Shared input helpers

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid 32-bit whole number.");
            }
        }
    }

    private static String readText(String message) {
        while (true) {
            System.out.print(message);
            String value = input.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    private static int readChoice(int minimum, int maximum) {
        while (true) {
            int choice = readInt("Enter your choice: ");

            if (choice >= minimum && choice <= maximum) {
                return choice;
            }

            System.out.println(
                "Choose between " + minimum + " and " + maximum + "."
            );
        }
    }

    private static void submenu(
        String title,
        String options,
        int maximum,
        IntConsumer action
    ) {
        while (true) {
            System.out.println("\n===== " + title + " =====");
            System.out.println(options);
            System.out.println("0. Return to Main Menu");

            int choice = readChoice(0, maximum);

            if (choice == 0) {
                return;
            }

            try {
                action.accept(choice);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
            // ==================================================
    // MEMBER 1 — MAIN MENU, ARRAY AND SEARCHING
    // ==================================================

    public static void main(String[] args) {
        try {
            boolean running = true;

            while (running) {
                System.out.println(
                    "\n===== DATA STRUCTURE & GRAPH ANALYZER ====="
                );

                System.out.println("1. Array Operations");
                System.out.println("2. Stack Operations");
                System.out.println("3. Queue Operations");
                System.out.println("4. Linked List Operations");
                System.out.println("5. Searching Operations");
                System.out.println("6. Graph Operations");
                System.out.println("7. Performance Comparison");
                System.out.println("8. Display All Results");
                System.out.println("9. Exit");

                int choice = readChoice(1, 9);

                switch (choice) {
                    case 1:
                        arrayMenu();
                        break;
                    case 2:
                        stackMenu();
                        break;
                    case 3:
                        queueMenu();
                        break;
                    case 4:
                        linkedListMenu();
                        break;
                    case 5:
                        searchingMenu();
                        break;
                    case 6:
                        graphMenu();
                        break;
                    case 7:
                        performanceMenu();
                        break;
                    case 8:
                        displayAllResults();
                        break;
                    case 9:
                        running = false;
                        break;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nInput ended.");
        } finally {
            input.close();
        }

        System.out.println("Goodbye!");
    }

    private static void arrayMenu() {
        submenu(
            "ARRAY OPERATIONS",
            "1. Insert\n"
                + "2. Delete by index\n"
                + "3. Search\n"
                + "4. Display",
            4,
            choice -> {
                switch (choice) {
                    case 1:
                        int index = readInt(
                            "Insert index (0 to " + array.size() + "): "
                        );
                        int value = readInt("Value: ");

                        array.insert(index, value);
                        System.out.println("Inserted: " + array);
                        break;

                    case 2:
                        int deleteIndex = readInt("Delete index: ");
                        int removed = array.delete(deleteIndex);

                        System.out.println("Deleted value: " + removed);
                        break;

                    case 3:
                        searchArray(false);
                        break;

                    case 4:
                        System.out.println("Array: " + array);
                        break;
                }
            }
        );
    }

    private static void searchingMenu() {
        submenu(
            "SEARCHING OPERATIONS",
            "1. Linear Search\n"
                + "2. Binary Search\n"
                + "3. Compare Both",
            3,
            choice -> {
                switch (choice) {
                    case 1:
                        searchArray(false);
                        break;
                    case 2:
                        searchArray(true);
                        break;
                    case 3:
                        compareSearches();
                        break;
                }
            }
        );
    }

    private static void searchArray(boolean binary) {
        int[] values = array.toArray();

        if (values.length == 0) {
            System.out.println("Insert array values first.");
            return;
        }

        int target = readInt("Search value: ");

        if (binary) {
            Arrays.sort(values);

            System.out.println(
                "Sorted copy: " + Arrays.toString(values)
            );
            System.out.println(
                "Index refers to this copy. Sorting is excluded from search time."
            );
        }

        SearchResult result;

        if (binary) {
            result = SearchAlgorithms.binarySearch(values, target);
        } else {
            result = SearchAlgorithms.linearSearch(values, target);
        }

        String algorithm = binary ? "Binary Search" : "Linear Search";

        System.out.println(algorithm + ": " + result);

        log.record(
            algorithm
                + ", target=" + target
                + ", data=" + Arrays.toString(values),
            result
        );
    }

    private static void compareSearches() {
        int[] values = array.toArray();

        if (values.length == 0) {
            System.out.println("Insert array values first.");
            return;
        }

        int target = readInt("Search value: ");

        long sortStart = System.nanoTime();
        Arrays.sort(values);
        long sortTime = System.nanoTime() - sortStart;

        SearchResult linear =
            SearchAlgorithms.linearSearch(values, target);

        SearchResult binary =
            SearchAlgorithms.binarySearch(values, target);

        System.out.println(
            "Same sorted input: " + Arrays.toString(values)
        );

        System.out.println(
            "Sorting time, excluded from search timings: "
                + sortTime + " ns"
        );

        System.out.printf(
            "%-18s %8s %14s %8s%n",
            "Algorithm", "Steps", "Time (ns)", "Index"
        );

        System.out.printf(
            "%-18s %8d %14d %8d%n",
            "Linear Search",
            linear.steps,
            linear.nanoseconds,
            linear.index
        );

        System.out.printf(
            "%-18s %8d %14d %8d%n",
            "Binary Search",
            binary.steps,
            binary.nanoseconds,
            binary.index
        );

        System.out.println("Index -1 means not found.");
        System.out.println(
            "Indices refer to the sorted copy; original array is unchanged."
        );
        System.out.println(
            "Step = one inspected element. Duplicate values may give different matching indices."
        );
        System.out.println(
            "Worst case: Linear O(n), Binary O(log n) on sorted data."
        );
        System.out.println(
            "Single-run times fluctuate; fewer steps may not mean a lower measured time."
        );

        String description =
            ", target=" + target
                + ", data=" + Arrays.toString(values);

        log.record("Linear comparison" + description, linear);
        log.record("Binary comparison" + description, binary);
    }

    // Connects performance features from Members 1, 3 and 4.
    private static void performanceMenu() {
        submenu(
            "PERFORMANCE COMPARISON",
            "1. Compare Linear and Binary Search\n"
                + "2. Compare BFS and DFS\n"
                + "3. Display Recorded Results",
            3,
            choice -> {
                switch (choice) {
                    case 1:
                        compareSearches();
                        break;
                    case 2:
                        compareTraversals();
                        break;
                    case 3:
                        log.display();
                        break;
                }
            }
        );
    }
    }



    