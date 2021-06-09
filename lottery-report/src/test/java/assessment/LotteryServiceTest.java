package assessment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static assessment.TestHelper.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

public class LotteryServiceTest {

    private static final List<List<Byte>> NUMBERS = new ArrayList<>();
    static {
        NUMBERS.add(asList(1, 12, 13, 14, 15));
        NUMBERS.add(asList(1,  2, 23, 24, 25));
        NUMBERS.add(asList(1,  2,  3, 34, 35));
        NUMBERS.add(asList(1,  2,  3,  4, 45));
        NUMBERS.add(asList(1,  2,  3,  4, 5));
    }

    @Mock
    private PlayersNumbersRepository repository;

    private LotteryService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new LotteryService(repository);
        given(repository.getNumbers()).willReturn(NUMBERS);
    }

    @Test(expected = InvalidLotteryNumbersInputException.class)
    public void shouldThrowExceptionOnNullWinnerNumber() {
        underTest.countMatches(null);
    }

    @Test(expected = InvalidLotteryNumbersInputException.class)
    public void shouldThrowExceptionOnNoWinnerNumber() {
        underTest.countMatches();
    }

    @Test(expected = InvalidLotteryNumbersInputException.class)
    public void shouldThrowExceptionOnLessThanFiveWinnerNumbers() {
        underTest.countMatches(1, 2, 3, 4);
    }

    @Test(expected = InvalidLotteryNumbersInputException.class)
    public void shouldThrowExceptionOnMoreThanFiveWinnerNumbers() {
        underTest.countMatches(1, 2, 3, 4, 5, 6);
    }

    @Test(expected = InvalidLotteryNumbersInputException.class)
    public void shouldThrowExceptionOnDuplicateNumber() {
        underTest.countMatches(1, 2, 3, 3, 5);
    }

    @Test(expected = InvalidLotteryNumbersInputException.class)
    public void shouldThrowExceptionOnNumberLessThan1() {
        underTest.countMatches(1, 2, 0, 4, 5);
    }

    @Test(expected = InvalidLotteryNumbersInputException.class)
    public void shouldThrowExceptionOnNumberGreaterThan90() {
        underTest.countMatches(1, 2, 91, 4, 5);
    }


    @Test
    public void shouldReturnAWinnerWith2MatchingNumbers() {
        Map<Integer, Long> countMatches = underTest.countMatches(1, 2, 3, 4, 5);

        long with2Matches = countMatches.get(2).longValue();
        assertEquals(1, with2Matches);
    }

    @Test
    public void shouldReturnAWinnerWith3MatchingNumbers() {
        Map<Integer, Long> countMatches = underTest.countMatches(1, 2, 3, 4, 5);

        long with3Matches = countMatches.get(3).longValue();
        assertEquals(1, with3Matches);
    }

    @Test
    public void shouldReturnAWinnerWith4MatchingNumbers() {
        Map<Integer, Long> countMatches = underTest.countMatches(1, 2, 3, 4, 5);

        long with4Matches = countMatches.get(4).longValue();
        assertEquals(1, with4Matches);
    }

    @Test
    public void shouldReturnAWinnerWith5MatchingNumbers() {
        Map<Integer, Long> countMatches = underTest.countMatches(1, 2, 3, 4, 5);

        long with5Matches = countMatches.get(5).longValue();
        assertEquals(1, with5Matches);
    }

    @Test
    public void shouldReturn2WinnersForAllWinnerMatchCount() {
        List<List<Byte>> numbers = make2WinnersForAllMatchCounts();
        given(repository.getNumbers()).willReturn(numbers);

        Map<Integer, Long> countMatches = underTest.countMatches(1, 2, 3, 4, 5);

        assertEquals(2, countMatches.get(2).longValue());
        assertEquals(2, countMatches.get(3).longValue());
        assertEquals(2, countMatches.get(4).longValue());
        assertEquals(2, countMatches.get(5).longValue());
    }

    private List<List<Byte>> make2WinnersForAllMatchCounts() {
        List<List<Byte>> numbers = new ArrayList<>();
        numbers.add(asList(1,  2, 23, 24, 25));
        numbers.add(asList(1,  2,  3, 34, 35));
        numbers.add(asList(1,  2,  3,  4, 45));
        numbers.add(asList(1,  2,  3,  4, 5));
        numbers.add(asList(1,  2, 23, 24, 25));
        numbers.add(asList(1,  2,  3, 34, 35));
        numbers.add(asList(1,  2,  3,  4, 45));
        numbers.add(asList(1,  2,  3,  4, 5));
        return numbers;
    }

}
