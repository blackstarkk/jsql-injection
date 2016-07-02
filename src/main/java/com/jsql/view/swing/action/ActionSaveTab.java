/*******************************************************************************
 * Copyhacked (H) 2012-2014.
 * This program and the accompanying materials
 * are made available under no term at all, use it like
 * you want, but share and discuss about it
 * every time possible with every body.
 * 
 * Contributors:
 *      ron190 at ymail dot com - initial implementation
 ******************************************************************************/
package com.jsql.view.swing.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;

import com.jsql.util.ConfigurationUtil;
import com.jsql.view.swing.HelperGui;
import com.jsql.view.swing.MediatorGui;
import com.jsql.view.swing.dialog.ReplaceFileChooser;
import com.jsql.view.swing.scrollpane.LightScrollPane;
import com.jsql.view.swing.table.PanelTable;

/**
 * Save the content of tab in a file.
 */
@SuppressWarnings("serial")
public class ActionSaveTab extends AbstractAction {
    /**
     * Log4j logger sent to view.
     */
    private static final Logger LOGGER = Logger.getLogger(ActionSaveTab.class);
    
    final ReplaceFileChooser filechooser = new ReplaceFileChooser(ConfigurationUtil.prefPathFile);

    public ActionSaveTab() {
        super();
        
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        this.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
        this.putValue(Action.NAME, "Save Tab As...");
        this.putValue(Action.SMALL_ICON, HelperGui.EMPTY);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        filechooser.setDialogTitle("Save Tab As");

        if (MediatorGui.tabResults().getSelectedComponent() instanceof PanelTable) {
            saveTablePanel();
        } else if (MediatorGui.tabResults().getSelectedComponent() instanceof LightScrollPane) {
            if ((((LightScrollPane) MediatorGui.tabResults().getSelectedComponent()).scrollPane.getViewport()).getView() instanceof JTextComponent) {
                saveJTextComponent();
            }
        }
    }
    
    private void saveTablePanel() {
        JTable table = ((PanelTable) MediatorGui.tabResults().getSelectedComponent()).table;
        
        if (table == null) {
            return;
        }

        int returnVal = filechooser.showSaveDialog(MediatorGui.frame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = filechooser.getSelectedFile();
            
            ConfigurationUtil.setPath(filechooser.getCurrentDirectory().toString());

            try {
                TableModel model = table.getModel();
                FileWriter excel = new FileWriter(file);
                
                for (int i = 2; i < model.getColumnCount(); i++) {
                    excel.write(model.getColumnName(i) + "\t");
                }
                
                excel.write("\n");
                
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 2; j < model.getColumnCount(); j++) {
                        /*
                         * Cell empty when string was too long to be injected (columnTooLong|cellEmpty|cellEmpty).
                         */
                        if (model.getValueAt(i, j) == null) {
                            excel.write("\t");
                        /*
                         * Remove line break.
                         */
                        } else {
                            excel.write(model.getValueAt(i, j).toString().replaceAll("\n", "\\n").replaceAll("\t", "\\t") + "\t");
                        }
                    }
                    excel.write("\n");
                }
                
                excel.close();
                
            } catch (IOException err) {
                LOGGER.warn(err.getMessage(), err);
            }
        }
    }
    
    private void saveJTextComponent() {
        JTextComponent textArea = 
            (JTextComponent) (((LightScrollPane) MediatorGui.tabResults().getSelectedComponent()).scrollPane.getViewport()).getView();
        
        if (textArea == null) {
            return;
        }
        
        int returnVal = filechooser.showSaveDialog(MediatorGui.frame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = filechooser.getSelectedFile();
            
            ConfigurationUtil.setPath(filechooser.getCurrentDirectory().toString());
            
            try {
                BufferedWriter fileOut = new BufferedWriter(new FileWriter(file));
                textArea.write(fileOut);
                fileOut.close();
            } catch (IOException err) {
                LOGGER.warn(err.getMessage(), err);
            }
        }
    }
}