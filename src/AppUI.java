import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ui.custom.screen.MainScreen;

public class AppUI {
    public static void main(String[] args) {
        final Map<String, String> positions = Stream.of(args)
                .collect(
                        Collectors.toMap(
                                key -> key.split(";")[0],
                                value -> value.split(";")[1]));

        MainScreen mainScreen = new MainScreen(positions);
        mainScreen.buildMainScreen();
    }
}
