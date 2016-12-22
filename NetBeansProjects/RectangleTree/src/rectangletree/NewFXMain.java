/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectangletree;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author YEN-WEN WANG
 */
public class NewFXMain extends Application {
    int depth=0;
    @Override
    public void start(Stage primaryStage) {
        //int depth =3;
        HashMap map = new HashMap();
        ArrayList<Rect> rl = new ArrayList<Rect>();
        Pane root = new Pane();
        Rect r = new Rect(275, 600, 375, 600);
        rl.add(r);
        map.put(1,rl);
        //root.getChildren().addAll(map.get(1));
        for (int i = 2; i <= depth; i++) {
            for (int j = 1; j <= i-1; j+=2) {
                
            }
            map.put(i, rl);
        }
        
        
        
        root.setOnMouseClicked((MouseEvent t) -> {
            MouseButton button = t.getButton();
                if(button==MouseButton.PRIMARY){
                     System.out.println("深度"+depth+"結點:"+ (depth + Math.pow(2, depth-1))+"增加:"+Math.pow(2, depth-1));
                     depth++;
                }else if(button==MouseButton.SECONDARY){
                     System.out.println("深度"+depth+"結點:"+ (depth-Math.pow(2, depth-1)));
                     depth--;
                }
        });
        Scene scene = new Scene(root, 640, 640);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    class Rect extends Path {
        float[] left;
        float[] right;
        Rect(float x1, float y1, float x2, float y2){ 
        float dx = x2 - x1; 
        float dy = y1 - y2; 
        float x3 = x2 - dy;
        float y3 = y2 - dx;
        float x4 = x1 - dy;
        float y4 = y1 - dx;
        float x5 = x4 + 4F/5F * (dx - dy);
        float y5 = y4 - 3F/5F * (dx + dy);
        left = new float[]{x4, y4, x5, y5};
        right= new float[]{x5, y5, x3, y3};
        
        setStrokeWidth(3);  
        
        MoveTo mt = new MoveTo();
        mt.setX(x1);
        mt.setY(y1);
        
        LineTo lt = new LineTo();
        lt.setX(x2);
        lt.setY(y2);
        
        LineTo lt2 = new LineTo();
        lt2.setX(x3);
        lt2.setY(y3);
        
        LineTo lt3 = new LineTo();
        lt3.setX(x4);
        lt3.setY(y4);
        
        ClosePath cp = new ClosePath();
        getElements().addAll(mt,lt,lt2,lt3,cp);
        setFill(Color.LIGHTGREEN);
        }
    }
    
 
  
    
}
