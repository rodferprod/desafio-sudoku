package ui.custom.input;

import java.util.List;
import static java.util.Objects.isNull;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumberLimit extends PlainDocument {
    private final List<String> NUMBERS = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");

    // Método que atribui um valor ao input
    @Override
    public void insertString(final int offs, final String str, final AttributeSet attr) throws BadLocationException {
        if (isNull(str) || (!NUMBERS.contains(str)))
            return;

        if (getLength() + str.length() <= 1) {
            super.insertString(offs, str, attr);
        }
    }
}
