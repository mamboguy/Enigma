/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Mamboguy
 */
public class PlugboardDocumentFilter extends DocumentFilter {

    public PlugboardDocumentFilter() {
        super();
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        //Force uppercase
        text = text.toUpperCase();
        
        //Destroy all non-alphabetic characters
        text = text.replaceAll("[^a-zA-Z]", "");
        
        //Call super method
        fb.replace(offset, length, text, attrs);
    }

}
