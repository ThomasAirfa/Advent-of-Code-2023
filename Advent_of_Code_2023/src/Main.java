import java.nio.file.Path;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    var trebuchet = new Trebuchet();
    try {
      trebuchet.readValuesInAllLines(Path.of("input.txt"));
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
    System.out.println(trebuchet.sum);
  }
}