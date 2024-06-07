import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class CubeConundrum {

  private int sum;
  private int powerSum;
  private final int redCubes;
  private final int greenCubes;
  private final int blueCubes;
  private int maxRedCubes;
  private int maxGreenCubes;
  private int maxBlueCubes;

  public CubeConundrum() {
    this.sum = 0;
    this.powerSum = 0;
    this.redCubes = 12;
    this.greenCubes = 13;
    this.blueCubes = 14;
    this.maxRedCubes = 0;
    this.maxGreenCubes = 0;
    this.maxBlueCubes = 0;
  }

  public void readValuesInAllLines(Path path) throws IOException {
    /*
     * Read all lines in given path and makes the sum of all valid games IDs.
     */
    Objects.requireNonNull(path, "Path is null.");

//    try (var reader = Files.newBufferedReader(path)) {
//      String line;
//      while ((line = reader.readLine()) != null) {
//        if (gameIsValid(line)) {
//          this.sum += getGameID(line);
//        }
//      } // "Normal" version
//    }

    // Part 1
    try (var lines = Files.lines(path)) {
      this.sum = lines.filter(this::gameIsValid).mapToInt(this::getGameID).sum(); // Stream version
      System.out.println(this.sum);
    }

    // Part 2
    try (var lines2 = Files.lines(path)) {
      this.powerSum = lines2.map(l -> getGameCubesPowerSet(l)).mapToInt(Integer::intValue).sum();
      System.out.println(this.powerSum);
    }
  }

  // PART 1
  private String[] parsedLine(String line) {
    /*
     * Parses the input String into a String[].
     */
    Objects.requireNonNull(line, "lLne is null.");

    String lineSub = line.substring(line.indexOf(":") + 2); // + 2 to skip ": "
    return lineSub.split("; |, ");
  }

  private int getGameID(String line) {
    /*
     * Gets a line and returns the game ID.
     */
    String[] parsed = line.replace("Game ", "").split(":");
    return Integer.parseInt(parsed[0]);
  }

  private boolean subsetIsValid(String[] data) {
    /*
     * Takes a subset and returns if he's valid or not.
     */
    Objects.requireNonNull(data, "Data is null");

    switch (data[1]) {
    case "red":
      return Integer.parseInt(data[0]) <= this.redCubes;
    case "green":
      return Integer.parseInt(data[0]) <= this.greenCubes;
    case "blue":
      return Integer.parseInt(data[0]) <= this.blueCubes;
    default:
      return false;
    }
  }

  private boolean gameIsValid(String line) {
    /*
     * Takes a game (input line), parse it, and returns if he's valid or not.
     */
    Objects.requireNonNull(line, "Line is null.");

    for (var s : parsedLine(line)) {
      String[] data = s.split(" ");
      if (!subsetIsValid(data))
        return false;
    }
    return true;
  }

  // PART 2
  private void setMaxCubesToZero() {
    /*
     * Used to reset max cubes values between each iteration of method getGamesCubesPowerSet.
     */
    this.maxRedCubes = 0;
    this.maxBlueCubes = 0;
    this.maxGreenCubes = 0;
  }

  private void updateMaxCubes(int value, String color) {
    /*
     * Takes a value of cubes, their color, returns the max between : the max cubes values for this color and the current value.
     */
    switch (color) {
    case "red":
      maxRedCubes = Math.max(value, maxRedCubes);
      break;
    case "green":
      maxGreenCubes = Math.max(value, maxGreenCubes);
      break;
    case "blue":
      maxBlueCubes = Math.max(value, maxBlueCubes);
      break;
    }
  }

  private int getGameCubesPowerSet(String line) {
    /*
     * Takes a String, parse it and returns the power of a set of cubes.
     */
    Objects.requireNonNull(line, "Line is null.");

    setMaxCubesToZero();
    for (var s : parsedLine(line)) {
      String[] data = s.split(" ");
      updateMaxCubes(Integer.parseInt(data[0]), data[1]);
    }
    return maxRedCubes * maxGreenCubes * maxBlueCubes;
  }
}