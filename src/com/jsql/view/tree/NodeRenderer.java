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
package com.jsql.view.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Render a tree node based on node model.
 * Can render default tree node, or node for database, table or column.
 */
@SuppressWarnings("serial")
public class NodeRenderer extends DefaultTreeCellRenderer {
    /**
     * Create tree node based on the node model.
     */
    public NodeRenderer() {
        super();
    }
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object nodeRenderer,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {

        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) nodeRenderer;
        Object userObject = currentNode.getUserObject();
        NodeModel dataModel = (NodeModel) userObject;
        return dataModel.getComponent(tree, nodeRenderer, selected, expanded, leaf, row, hasFocus);
    }
}