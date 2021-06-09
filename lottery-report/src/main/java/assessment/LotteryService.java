package assessment;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LotteryService {

    private final PlayersNumbersRepository repository;

    public LotteryService(final PlayersNumbersRepository repository) {
        this.repository = repository;
    }

    public Map<Integer, Long> countMatches(final int... winningNumbers) throws InvalidLotteryNumbersInputException {
        validate(winningNumbers);
        final List<Byte> numbers = numbersToSortedList(winningNumbers);
        final Map<Integer, Long> matchingNumbers = countMatchingNumbers(numbers);
        return matchingNumbers;
    }

    private List<Byte> numbersToSortedList(final int[] winningNumbers) {
        final List<Byte> numbers = new ArrayList<>();
        for (int number : winningNumbers) {
            numbers.add(Integer.valueOf(number).byteValue());
        }
        numbers.sort(Comparator.naturalOrder());
        return numbers;
    }

    private Map<Integer, Long> countMatchingNumbers(final List<Byte> winningNumbers) {
        return repository.getNumbers().parallelStream()
            .map(playerNumbers -> countMatches(winningNumbers, playerNumbers))
            .filter(count -> count > 1)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private Integer countMatches(final List<Byte> winningNumbers, final List<Byte> playerNumbers) {
        List<Byte> playerNumbersCopy = new ArrayList<>(playerNumbers);
        playerNumbersCopy.retainAll(winningNumbers);
        return playerNumbersCopy.size();
    }

    private void validate(final int[] numbers) {
        validateLength(numbers);

        final List<Integer> verifiedNumbers = new ArrayList<>();
        for (int number: numbers) {
            validateRange(number);
            validateDuplicates(number, verifiedNumbers);

            verifiedNumbers.add(number);
        }
    }

    private void validateLength(final int[] numbers) {
        if (numbers == null || numbers.length != 5) {
            String length = numbers == null ? null : String.valueOf(numbers.length);
            String message = "Exactly 5 numbers are expected: actual length was " + length;
            throw new InvalidLotteryNumbersInputException(message);
        }
    }

    private void validateRange(final int number) {
        if (number < 1 || number > 90) {
            throw new InvalidLotteryNumbersInputException("Numbers must be between 1 and 90. Found " + number);
        }
    }

    private void validateDuplicates(final int number, final List<Integer> verifiedNumbers) {
        if (verifiedNumbers.contains(number)) {
            throw new InvalidLotteryNumbersInputException("Duplicate number found: " + number);
        }
    }

}
