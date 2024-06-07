import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class CubeConundrum {

  private int sum;
  private final int redCubes;
  private final int greenCubes;
  private final int blueCubes;

  public CubeConundrum() {
    this.sum = 0;
    this.redCubes = 12;
    this.greenCubes = 13;
    this.blueCubes = 14;
  }

  public void readValuesInAllLines(Path path) throws IOException {
    /*
     * Read all lines in given path and makes the sum of all valid games IDs.
     * */
    Objects.requireNonNull(path, "Path is null.");

//    try (var reader = Files.newBufferedReader(path)) {
//      String line;
//      while ((line = reader.readLine()) != null) {
//        if (gameIsValid(line)) {
//          this.sum += getGameID(line);
//        }
//      } // "Normal" version
//    }

    try (var lines = Files.lines(path)) {
      this.sum = lines.filter(this::gameIsValid)
          .mapToInt(this::getGameID)
          .sum(); // Stream version
      System.out.println(this.sum);
    }
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
     * */
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
     * */
    Objects.requireNonNull(line, "Line is null.");

    String lineSub = line.substring(line.indexOf(":") + 2); // + 2 to skip ": "
    String[] parsed = lineSub.split("; |, ");

    for (var s : parsed) {
      String[] data = s.split(" ");
      if (!subsetIsValid(data))
        return false;
    }
    return true;
  }
}