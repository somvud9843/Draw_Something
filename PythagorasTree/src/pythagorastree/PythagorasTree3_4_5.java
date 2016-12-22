/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pythagorastree;


import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.Path2D;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javax.swing.*;
 
public class PythagorasTree3_4_5 extends JPanel {
    int depthLimit = 6;
    float hue = 0.15f;
 
    public PythagorasTree3_4_5() {
        setPreferredSize(new Dimension(640, 640));
        setBackground(Color.white);
        
    }
    

 
    private void drawTree(Graphics2D g, float x1, float y1, float x2, float y2,
            int depth) {
 
        if (depth == depthLimit)
            return;
 
        float dx = x2 - x1; 
        float dy = y1 - y2; 
 
        float x3 = x2 - dy;
        float y3 = y2 - dx;
        float x4 = x1 - dy;
        float y4 = y1 - dx;
        float x5 = x4 + 4F/5F * (dx - dy);
        float y5 = y4 - 3F/5F * (dx + dy);
 
        Path2D square = new Path2D.Float();
        square.moveTo(x1, y1);
        square.lineTo(x2, y2);
        square.lineTo(x3, y3);
        square.lineTo(x4, y4);
        square.closePath();

        
        g.setColor(Color.getHSBColor(hue + depth * 0.02f, 1, 1));
        g.fill(square);
        g.setColor(Color.lightGray);
        g.draw(square);
   
 /*
        Path2D triangle = new Path2D.Float();
        triangle.moveTo(x3, y3);
        triangle.lineTo(x4, y4);
        triangle.lineTo(x5, y5);
        triangle.closePath();
 
        g.setColor(Color.getHSBColor(hue + depth * 0.035f, 1, 1));
        g.fill(triangle);
        g.setColor(Color.RED);
        g.draw(triangle);
 */
        drawTree(g, x4, y4, x5, y5, depth + 1);
        drawTree(g, x5, y5, x3, y3, depth + 1);
        
    }
 
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.lightGray);
        drawTree((Graphics2D) g, 275, 600, 375, 600, 0);
    }
    
    public void callRepaint(){
        repaint();
    }
    
    public static void main(String[] args) {
        PythagorasTree3_4_5 pt= new PythagorasTree3_4_5();
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.addMouseListener(new java.awt.event.MouseAdapter(){
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (SwingUtilities.isLeftMouseButton(evt)){
                        System.out.println("Left Clicked");
                        pt.changeDepth(true);
                        
                    }
                    if (SwingUtilities.isRightMouseButton(evt)){
                        System.out.println("Right Clicked");
                        pt.changeDepth(false);
                    }
                    
                }
            } );
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Pythagoras Tree");
            f.setResizable(false);
            f.add(new PythagorasTree3_4_5(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
    
        public int changeDepth(boolean mouse){
        if (mouse && depthLimit>0){
            depthLimit++;
            
        }else{
            depthLimit--;
        }
        //System.out.println(depthLimit);
        return depthLimit;
    }
}