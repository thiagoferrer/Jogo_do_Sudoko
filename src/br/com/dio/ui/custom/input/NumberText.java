package br.com.dio.ui.custom.input;

import br.com.dio.model.Space;
import br.com.dio.service.EventEnum;
import br.com.dio.service.EventListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import static br.com.dio.service.EventEnum.CLEAR_SPACE;

public class NumberText extends JTextField implements EventListener {

    private final Space space;

    public NumberText(final Space space) {
        this.space = space;
        var dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());
        if (space.isFixed()) {
            this.setText(space.getActual().toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void changeSpace() {
                if (getText().isEmpty()) {
                    space.clearSpace();
                    setBackground(Color.WHITE);      // limpo
                    setToolTipText(null);
                    return;
                }
                try {
                    int v = Integer.parseInt(getText());
                    space.setActual(v);
                    if (Integer.valueOf(v).equals(space.getExpected())) {
                        setBackground(new Color(198, 239, 206)); // verde suave
                        setToolTipText("Correto");
                    } else {
                        setBackground(new Color(255, 199, 206)); // rosa suave
                        setToolTipText("Valor diferente do esperado");
                    }
                } catch (NumberFormatException ex) {
                    // como você limita a 1 dígito [1..9], isso quase não acontece
                    setBackground(Color.WHITE);
                    setToolTipText(null);
                }
            }

        });
    }

    @Override
    public void update(EventEnum eventType) {
        if (eventType.equals(CLEAR_SPACE) && (this.isEnabled())) {
            this.setText("");
        }
    }
}