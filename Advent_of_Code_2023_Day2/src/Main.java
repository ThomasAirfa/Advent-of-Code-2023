import java.io.IOException;
import java.nio.file.Path;

public class Main {

  public static void main(String[] args) {
    var cubeConundrum = new CubeConundrum();
    try {
      cubeConundrum.readValuesInAllLines(Path.of("input.txt"));
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  } 
}