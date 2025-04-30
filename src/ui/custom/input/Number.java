package ui.custom.input;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Dimension;
import java.awt.Font;

import static java.awt.Font.PLAIN;

import model.Square;

public class Number extends JTextField {

    public Number(final Square square) {
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
}
