package assessment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayersNumbersRepository {

    private final String fileName;
    private List<List<Byte>> numbers;

    public PlayersNumbersRepository(final String fileName) {
        this.fileName = fileName;
    }

    public boolean loadNumbers() throws IOException {
        numbers = Files.lines(Paths.get(fileName))
            .map(this::parseToList)
            .collect(Collectors.toList());
        return true;
    }

    public List<List<Byte>> getNumbers() {
        return numbers;
    }

    private List<Byte> parseToList(final String line) {
        return Stream.of(line.split(" "))
            .map(Byte::valueOf)
            .sorted()
            .collect(Collectors.toList());
    }
}
