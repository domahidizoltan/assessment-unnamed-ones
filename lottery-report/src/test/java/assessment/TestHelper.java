package assessment;

import java.util.ArrayList;
import java.util.List;

final public class TestHelper {

    private TestHelper() {}

    public static List<Byte> asList(int... numbers) {
        final List<Byte> result = new ArrayList<>();
        for (int number : numbers) {
            result.add(Integer.valueOf(number).byteValue());
        }
        return result;
    }

}
