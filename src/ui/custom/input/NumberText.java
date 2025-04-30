package ui.custom.input;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Dimension;
import java.awt.Font;

import static java.awt.Font.PLAIN;
import static service.ReverseEvent.CLEAR_TEXT_FIELD;

import service.EventListener;
import model.Square;
import service.ReverseEvent;

public class NumberText extends JTextField implements EventListener {

    private final Square square;

    public NumberText(final Square square) {
        this.square = square;
        Dimension dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberLimit());
        this.setEnabled(!square.isFixed());

        if (square.isFixed()) {
            this.setText(square.getActual().toString());
        }

        // Atribuindo um evento ao text field
        this.getDocument().addDocumentListener(new DocumentListener() {

            // Alterando os valores armazenados (squares)
            // de acordo com o valor do text field
            private void changeSquare() {
                // Se o conteúdo do text field for apagado...
                if (getText().isEmpty()) {
                    // ... limpar a posição correspondente no square
                    square.clearSquare();
                    return;
                }
                // caso contrário atribuir o valor do text field ao square
                square.setActual(Integer.parseInt(getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeSquare();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSquare();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeSquare();
            }

        });
    }

    @Override
    public void update(final ReverseEvent eventType) {
        if (eventType.equals(CLEAR_TEXT_FIELD) && this.isEnabled()) {
            this.setText("");
        }
    }
}
