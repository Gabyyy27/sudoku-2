/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_sudoku;

/**
 *
 * 
 */

import javax.swing.*;
import java.awt.Color;

public class pos {
    private int row;
    private int column;
    JLabel label;
    
    private boolean canChange = true;
    
    public pos(int row, int column) {
        this.row = row;
        this.column = column;
        this.label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setOpaque(true);
    }
    
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public JLabel getLabel() {
        return label;
    }
    
    public int getCurrentNumber() {
        if (label.getText().equals("")) return -1;
        
        return Integer.parseInt(label.getText());
    }
    
    public void setCurrentNumber(int num) {
        if (num == 0) {
            label.setText("");
            return;
        }
        
        label.setText("" + num);
    }
    
    public boolean canChange() {
        return canChange;
    }
    
    public void setChange(boolean canChange) {
        this.canChange = canChange;
    }
    
}
