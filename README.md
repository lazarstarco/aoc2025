# Advent of Code 2025

This project contains solutions for Advent of Code 2025.

Each day has a corresponding directory under:

```
src/main/java/dayXX/
```

Puzzle inputs are expected to be placed in:

```
src/main/resources/dayXX/input.txt
```

Only `.gitkeep` files are committed so that the folder structure is preserved.  
Users should add their own `input.txt` files for each day.

---

## Folder Structure

```
src/
 └── main/
      ├── java/
      │    ├── day01/
      │    │    Day01.java
      │    ├── day02/
      │    │    Day02.java
      │    └── day03/
      │         Day03.java
      │        ...
      └── resources/
           ├── day01/
           │    .gitkeep
           │    input.txt
           ├── day02/
           │    .gitkeep
           │    input.txt
           └── day03/
                .gitkeep
                input.txt
                ...
```

---

## Adding Input Files

For each day:

1. Navigate to the corresponding folder in `src/main/resources/dayXX/`.
2. Create or replace the `input.txt` file.
3. Paste the puzzle input for that day into this file.

---

## Running Solutions

Each day includes its own runner class under `src/main/java/dayXX` (colloquially named `DayXX.java`).  
Running a runner class will automatically load the input from the matching `resources/dayXX/input.txt` file.
