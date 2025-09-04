package br.com.dio.ui.custom.button;

import javax.swing.*;

public class CheckGameStatusButton extends JButton {

    public CheckGameStatusButton() {
        this.setText("Verificar jogo");
        this.addActionListener(actionListener);
    }

}
