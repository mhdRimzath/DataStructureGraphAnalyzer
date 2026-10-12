# Data Structure and Graph Performance Analyzer

A Java console application developed for **CIT300 – Data Structures and Algorithms, Graded Practical Assignment 2**.

The application demonstrates data structure operations, searching algorithms, graph traversal, input validation, and performance comparison through one integrated menu.

## Team Members

| Member | Name | Student ID | Assigned Responsibility |
|---|---|---|---|
| 1 | ABM. Rimzath | 23da2-0687 | Array, searching, and main menu integration |
| 2 | MFM. Usama | 23da2-0971 | Stack and circular queue |
| 3 | AGM. Sujath | 23da2-1133 | Linked list, performance history, and testing |
| 4 | ANM. Imthath | 23da2-0882 | Graph, BFS, and DFS |

## Individual Contributions

### ABM. Rimzath — 23da2-0687

- Array insertion, deletion, display, and validation in `IntArray.java`.
- Linear and binary searching in `SearchAlgorithms.java`.
- Search index, step count, and elapsed-time results in `SearchResult.java`.
- Array/search menus, search comparison, and main menu integration in `Main.java`.

### MFM. Usama — 23da2-0971

- Stack push, pop, peek, and display in `IntStack.java`.
- Circular queue enqueue, dequeue, front inspection, and display in `IntQueue.java`.
- Empty/full condition handling for the stack and queue.
- Stack and queue menu integration in `Main.java`.

### AGM. Sujath — 23da2-1133

- Linked list insertion, deletion, search, and display in `IntLinkedList.java`.
- Search and traversal history in `PerformanceLog.java`.
- Automated checks in `SelfTest.java`.
- Linked list menu and consolidated results display in `Main.java`.

### ANM. Imthath — 23da2-0882

- Adjacency-list graph representation and vertex/edge operations in `Graph.java`.
- Breadth-First Search (BFS) and Depth-First Search (DFS).
- Graph validation and traversal step/time measurements.
- Graph menu and BFS/DFS comparison integration in `Main.java`.

## Technologies Used

- Java — JDK 17 or later
- Visual Studio Code
- Git and GitHub
- Console input and output

No external Java libraries or database are required.

## Project Structure

```text
DataStructureGraphAnalyzer/
├── src/
│   └── graphanalyzer/
│       ├── Main.java
│       ├── IntArray.java
│       ├── IntStack.java
│       ├── IntQueue.java
│       ├── IntLinkedList.java
│       ├── SearchAlgorithms.java
│       ├── SearchResult.java
│       ├── Graph.java
│       ├── PerformanceLog.java
│       └── SelfTest.java
└── README.md
```

VS Code may display the nested folders together as `src/graphanalyzer` when Compact Folders is enabled.

Every Java source file belongs to the same package and begins with:

```java
package graphanalyzer;
```

`Main.java` contains the integrated menu and clearly separated member sections. Supporting classes implement the data structures and algorithms.

## Features

| Component | Functionality |
|---|---|
| Array | Insert by index, delete by index, search, and display |
| Stack | Push, pop, peek, and display using LIFO |
| Circular queue | Enqueue, dequeue, inspect front, and display using FIFO |
| Linked list | Insert at end, delete first matching value, search, and display |
| Searching | Linear search and binary search using a sorted copy |
| Graph | Add vertices/edges, display adjacency lists, and perform BFS/DFS |
| Performance | Compare operation counts and elapsed execution times |
| Results | Display current structures and recorded measurement history |

## How to Run

### 1. Check Java

Install JDK 17 or later and verify that both commands work:

```bash
java -version
javac -version
```

### 2. Open the Project

In VS Code, select **File > Open Folder** and open `DataStructureGraphAnalyzer`.

Open **Terminal > New Terminal**. Run the following commands from the project root, where `README.md` is located.

### 3. Compile

```bash
javac src/graphanalyzer/*.java
```

The compiler creates `.class` files alongside the source files in `src/graphanalyzer`. Recompile after changing the source code.

### 4. Run the Application

```bash
java -cp src graphanalyzer.Main
```

### 5. Run the Tests

```bash
java -cp src graphanalyzer.SelfTest
```

## Main Menu

```text
1. Array Operations
2. Stack Operations
3. Queue Operations
4. Linked List Operations
5. Searching Operations
6. Graph Operations
7. Performance Comparison
8. Display All Results
9. Exit
```

Enter `0` to return from a submenu to the main menu.

## Example Demonstrations

### Search Comparison

Insert `10, 20, 30, 40, 50` into the array and search for `50` using the comparison menu.

| Algorithm | Inspected Elements | Result Index |
|---|---:|---:|
| Linear search | 5 | 4 |
| Binary search | 3 | 4 |

Indices start at zero. Both algorithms search the same sorted copy in the comparison. The original array order is preserved.

### Stack and Queue

| Structure | Operations | First Removed Value |
|---|---|---:|
| Stack | Push 10, push 20, pop | 20 |
| Queue | Enqueue 10, enqueue 20, dequeue | 10 |

### Graph Traversal

Add vertices `A`, `B`, `C`, `D`, and `E`. Add edges in this order:

```text
A — B
A — C
B — D
C — E
```

Starting from `A`, the expected traversal orders are:

```text
BFS: A, B, C, D, E
DFS: A, B, D, C, E
```

Traversal order depends on edge insertion order. Only vertices reachable from the starting vertex are visited.

## Performance Measurement

### Search Steps

One search step represents one inspected array element or linked-list node. A search returns index `-1` when its target is absent.

Binary search requires sorted data. The program sorts a copy first. Sorting time is excluded from search measurements and displayed separately in the comparison. A binary-search index refers to the sorted copy.

### Graph Steps

Graph steps are calculated as:

```text
Visited vertices + examined adjacency entries
```

For the example graph, each traversal records five vertex visits and eight adjacency checks, giving **13 steps**. Every undirected edge has two adjacency entries.

BFS and DFS have equal counts for the same reachable component under this definition, even when their traversal orders differ.

### Execution Time

Elapsed time is measured using `System.nanoTime()`. Single-run measurements vary with JVM warm-up, runtime overhead, and system activity. Use operation counts and theoretical complexity alongside timings.

Search steps and graph steps measure different kinds of work and should not be treated as directly comparable speed scores.

## Time Complexity

| Operation | Time Complexity |
|---|---|
| Array insertion/deletion at an arbitrary index | O(n) worst case |
| Stack push, pop, and peek | O(1) |
| Circular queue enqueue, dequeue, and peek | O(1) |
| Linked list insertion at end with a tail reference | O(1) |
| Linked list search/delete by value | O(n) worst case |
| Linear search | O(n) worst case |
| Binary search on sorted data | O(log n) worst case |
| BFS using adjacency lists | O(V + E) overall |
| DFS using adjacency lists | O(V + E) overall |

`n` is the number of stored elements, `V` the number of vertices, and `E` the number of graph edges. The binary-search complexity excludes copying and sorting performed by the menu.

## Input Validation

The application handles:

- Non-numeric and out-of-range menu input.
- Values outside the Java integer range.
- Invalid array indices.
- Full arrays, stacks, and queues.
- Invalid operations on empty structures.
- Missing search values.
- Duplicate vertices and edges.
- Edges with unknown endpoints.
- Invalid traversal starting vertices.
- Self-loops, which are unsupported by this simple-graph design.

## Testing

`SelfTest.java` checks array operations, stack LIFO behaviour, queue FIFO behaviour and wraparound, linked-list deletion cases, successful/unsuccessful searches, integer boundaries, graph validation, isolated vertices, cycles, traversal order, and step counts.

Run the test command above to see the result for the checked-out code.

## GitHub Collaboration

Each member works on their component branch and records meaningful changes. Contributions are reviewed and integrated into `main`. Commit history and merge records document development and integration; pull requests provide additional evidence where used.

Before merging, compile the code, test the affected operations, and resolve any conflicts in the shared `Main.java` file.

## Design Notes and Limitations

- Array, stack, and queue capacities are 100 in the main application.
- Integer duplicates are allowed.
- Linear search returns the first matching index; binary search may return any matching index among duplicates.
- Linked list deletion removes the first matching value.
- Graph labels are case-sensitive, with a maximum length of 40 characters.
- The graph supports up to 200 vertices.
- BFS uses the custom `IntQueue` class; DFS uses recursion and Java's call stack.
- Java collections support graph storage and result history.
- All data and measurement history are held in memory and reset when the application exits.
