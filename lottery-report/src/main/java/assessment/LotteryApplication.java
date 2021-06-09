package assessment;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class LotteryApplication {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("First argument (input file) is mandatory. Aborting application.");
			System.exit(1);
		}

		PlayersNumbersRepository repository = new PlayersNumbersRepository(args[0]);
		LotteryService service = new LotteryService(repository);

		boolean isFileLoaded = loadInputFile(repository);
		if (isFileLoaded) {
			doLotteryDraws(service);
		}

		// System.out.println("terminating application");
	}

	private static boolean loadInputFile(final PlayersNumbersRepository repository) {
		boolean isLoaded = false;
		try {
			// System.out.println("loading input file...");
			isLoaded = repository.loadNumbers();
			System.out.println("READY");
		} catch (IOException ex) {
			System.err.println("Could not load file");
			ex.printStackTrace();
		}
		return isLoaded;
	}

	private static void doLotteryDraws(final LotteryService service) {
		Scanner scan = new Scanner(System.in);
		String line;
		do {
			// System.out.println("type space separated numbers:");
			line = scan.nextLine();
			if (line.trim().length() > 0) {
				int[] numbers = lineToNumbers(line);
				countMatchingNumbers(service, numbers);
			}
		} while (line.trim().length() != 0);
	}

	private static int[] lineToNumbers(final String line) {
		String[] tokens = line.trim().split(" ");
		int[] numbers = new int[tokens.length];
		for (int idx = 0; idx < tokens.length; idx++) {
			numbers[idx] = Integer.valueOf(tokens[idx]);
		}
		return numbers;
	}

	private static void countMatchingNumbers(final LotteryService service, int[] numbers) {
		try {
			Map<Integer, Long> matches = service.countMatches(numbers);
			String[] result = new String[4];
			for (int number = 2, index = 0; number <= 5; number++, index++) {
				result[index] = matches.getOrDefault(number, 0L).toString();
			}
			String resultLine = String.join(" ", result);
			System.out.println(resultLine);
		} catch (InvalidLotteryNumbersInputException ex) {
			ex.printStackTrace();
		}
	}
}

