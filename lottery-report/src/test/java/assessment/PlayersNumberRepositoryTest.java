package assessment;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static assessment.TestHelper.asList;
import static org.junit.Assert.assertEquals;

public class PlayersNumberRepositoryTest {

    @Test(expected = IOException.class)
    public void shouldThrowExceptionWhenLoadingNonExistentFile() throws IOException {
        PlayersNumbersRepository repository = new PlayersNumbersRepository("nonExistent.txt");

        repository.loadNumbers();
    }

    @Test
    public void shouldLoadAndGetOrderedNumbers() throws IOException {
        List<List<Byte>> expectedNumbers = new ArrayList<>();
        expectedNumbers.add(asList(1, 2, 3, 4, 5));
        expectedNumbers.add(asList(1, 2, 3, 4, 5));
        PlayersNumbersRepository repository = new PlayersNumbersRepository("src/test/resources/test_input.txt");

        repository.loadNumbers();
        List<List<Byte>> actualNumbers = repository.getNumbers();

        assertEquals(expectedNumbers, actualNumbers);
    }

}
