package ITETransTrees.View;


import javax.swing.*;

import ITETransTrees.Module.NodeGeneric;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;


class DrawTreeUI extends JPanel {
    NodeGeneric tree;
    double zoomFactor = 1.0;
    int nodeRadius = 20;
    int verticalSpacing = 50;
    int horizontalSpacing = 20;
    Map<NodeGeneric, Point> nodeLocations;

    public DrawTreeUI(NodeGeneric root) {
        this.tree = root;
        setOpaque(false);
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getPreciseWheelRotation() < 0) {
                    zoomFactor *= 1.1;
                } else {
                    zoomFactor /= 1.1;
                }
                revalidate();
                repaint();
            }
        });
    }

    public void updateTree(NodeGeneric newRoot) {
        this.tree = newRoot;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform originalTransform = g2d.getTransform();
        g2d.scale(zoomFactor, zoomFactor);

        nodeLocations = new HashMap<>();
        int totalTreeWidth = getSubtreeWidth(tree);
        calculateNodeLocations(tree, getWidth() / 2, 50, nodeLocations, totalTreeWidth / 2);
        drawTree(g2d, tree);

        g2d.setTransform(originalTransform);
    }

    private void calculateNodeLocations(NodeGeneric node, int x, int y, Map<NodeGeneric, Point> nodeLocations, int offsetX) {
        if (node == null) {
            return;
        }

        nodeLocations.put(node, new Point(x, y));

        int totalChildrenWidth = node.child.stream().mapToInt(this::getSubtreeWidth).sum();
        int currentX = x - totalChildrenWidth / 2;

        for (NodeGeneric child : node.child) {
            int childWidth = getSubtreeWidth(child);
            calculateNodeLocations(child, currentX + childWidth / 2, y + verticalSpacing, nodeLocations, offsetX);
            currentX += childWidth + horizontalSpacing;
        }
    }

    private int getSubtreeWidth(NodeGeneric node) {
        if (node == null) {
            return 0;
        }
        int childrenWidth = node.child.stream().mapToInt(this::getSubtreeWidth).sum();
        return Math.max(childrenWidth, nodeRadius * 2 + horizontalSpacing * (node.child.size() - 1));
    }

    private void drawTree(Graphics2D g2d, NodeGeneric node) {
        if (node == null || node.value == null) {
            return;
        }

        Point point = nodeLocations.get(node);
        int x = point.x;
        int y = point.y;

        g2d.setColor(Color.WHITE);
        g2d.fillOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        g2d.drawString(node.value, x - 6, y + 4);

        for (NodeGeneric child : node.child) {
            Point childPoint = nodeLocations.get(child);
            g2d.setColor(Color.WHITE);
            g2d.drawLine(x, y, childPoint.x, childPoint.y);
            drawTree(g2d, child);
        }
    }
}
public class TreeExample {
    public static void main(String[] args) {
        // Create the root node
        NodeGeneric root = new NodeGeneric("Root");

        // Create child nodes
        NodeGeneric child1 = new NodeGeneric("Child 1");
        NodeGeneric child2 = new NodeGeneric("Child 2");
        NodeGeneric child3 = new NodeGeneric("Child 3");

        // Add children to root
        root.child.add(child1);
        root.child.add(child2);
        root.child.add(child3);

        // Create grandchildren
        child1.child.add(new NodeGeneric("Grandchild 1.1"));
        child1.child.add(new NodeGeneric("Grandchild 1.2"));

        child2.child.add(new NodeGeneric("Grandchild 2.1"));

        child3.child.add(new NodeGeneric("Grandchild 3.1"));
        child3.child.add(new NodeGeneric("Grandchild 3.2"));
        child3.child.add(new NodeGeneric("Grandchild 3.3"));

        // Create the UI and set up the frame
        JFrame frame = new JFrame("Generic Tree Example");
        DrawTreeUI treeUI = new DrawTreeUI(root);
        frame.add(new JScrollPane(treeUI));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
