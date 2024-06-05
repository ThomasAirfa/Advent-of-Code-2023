import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trebuchet {

  private int sum;

  public Trebuchet() {
    this.sum = 0;
  }

  public void readValuesInAllLines(Path path) throws IOException {
    /*
     * Decipher each line in the given path.
     */
    Objects.requireNonNull(path, "Path to file is null.");

    try (var reader = Files.newBufferedReader(path)) {
      String line;
      while ((line = reader.readLine()) != null) {
        this.sum += combineFirstAndLastInt(extractFirstInt(line), extractFirstInt(reverse(line)));
      }
    }
  }

  private String extractFirstInt(String line) {
    /*
     * Takes a String and returns the first digit in it using pattern-matching.
     */
    Objects.requireNonNull(line, "Line is null.");

    Pattern p = Pattern.compile("\\d");
    Matcher m = p.matcher(line);

    if (m.find()) {
      String s = m.group();
      return s;
    }
    return null;
  }

  private int combineFirstAndLastInt(String firstInt, String lastInt) {
    /*
     * Takes two Strings containing a digit and concatenates them to form a number.
     */
    Objects.requireNonNull(firstInt, "First int is null.");
    Objects.requireNonNull(lastInt, "Second int is null.");

    return Integer.parseInt(extractFirstInt(firstInt).concat(extractFirstInt(lastInt)));
  }

  private String reverse(String line) {
    /*
     * Takes a String and returns it backwards.
     */
    Objects.requireNonNull(line, "Line is null.");

    return new StringBuilder(line).reverse().toString();
  }

  public int sum() {
    /*
     * Getter for field sum.
     */
    return this.sum;
  }
}