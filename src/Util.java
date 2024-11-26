import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    public static final Map<Character, Integer> LETTER_POINTS = Map.ofEntries(
            Map.entry('A', 1), Map.entry('Ą', 5), Map.entry('B', 3), Map.entry('C', 2),
            Map.entry('Ć', 6), Map.entry('D', 2), Map.entry('E', 1), Map.entry('Ę', 5),
            Map.entry('F', 5), Map.entry('G', 3), Map.entry('H', 3), Map.entry('I', 1),
            Map.entry('J', 3), Map.entry('K', 2), Map.entry('L', 2), Map.entry('Ł', 3),
            Map.entry('M', 2), Map.entry('N', 1), Map.entry('Ń', 7), Map.entry('O', 1),
            Map.entry('Ó', 5), Map.entry('P', 2), Map.entry('R', 1), Map.entry('S', 1),
            Map.entry('Ś', 5), Map.entry('T', 2), Map.entry('U', 3), Map.entry('W', 1),
            Map.entry('Y', 2), Map.entry('Z', 1), Map.entry('Ź', 9), Map.entry('Ż', 5)
    );
}
