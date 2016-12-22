/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BezierCurve;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import java.awt.geom.Point2D.Double;
import javafx.scene.canvas.GraphicsContext;

public class BezierCurve extends Application {
    int numOfPoints = 0;
    GraphicsContext g;
    Double[] coordlist;
    Color colorlist[]={Color.PALEGREEN, Color.GOLD,Color.GOLDENROD,Color.TOMATO};
    Pane pane ;
  public static void main(String[] args) throws Exception { launch(args); }
  
  @Override public void start(final Stage stage) throws Exception {
    coordlist = new Double[4];
    pane = new Pane();
    pane.setStyle("-fx-background-color: aliceblue;");
    pane.setPrefSize(400, 400);
    pane.setOnMouseClicked((MouseEvent event) -> {
        if (numOfPoints <4 ){
            
            pane.getChildren().add(new Points(colorlist[numOfPoints],event.getX(),event.getY()));
            coordlist[numOfPoints] = new Double(event.getX(),event.getY());
            if (numOfPoints ==3)paint();
            numOfPoints++;
        }else{
            
        }
    });
    

    stage.setTitle("Cubic Curve Manipulation Sample");
    stage.setScene(new Scene(pane,400,400,Color.ALICEBLUE));
    stage.show();    
   
  }  
  // a draggable anchor displayed around a point.
  class Points extends Circle { 
        Points(Color color, double x, double y) {
            
            setCenterX(x);
            setCenterY(y);
            setRadius(5);
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);
            Double points = new Double(x,y);
            enableDrag();
        }
      private void enableDrag() {
      final Delta dragDelta = new Delta();
      setOnMousePressed((MouseEvent mouseEvent) -> {
          // record a delta distance for the drag and drop operation.
          
          int te= checkColor( mouseEvent.getSource().toString());
          dragDelta.x = getCenterX() - mouseEvent.getX();
          dragDelta.y = getCenterY() - mouseEvent.getY();
          getScene().setCursor(Cursor.MOVE);
      });
      setOnMouseReleased((MouseEvent mouseEvent) -> {
          getScene().setCursor(Cursor.HAND);
      });
      setOnMouseDragged((MouseEvent mouseEvent) -> {
          pane.getChildren().remove(4, pane.getChildren().size());
          double newX = mouseEvent.getX() + dragDelta.x;
          if (newX > 0 && newX < getScene().getWidth()) {
              setCenterX(newX);
              coordlist[checkColor( mouseEvent.getSource().toString())].x=newX;
              
          }  
          double newY = mouseEvent.getY() + dragDelta.y;
          if (newY > 0 && newY < getScene().getHeight()) {
              setCenterY(newY);
              coordlist[checkColor( mouseEvent.getSource().toString())].y=newY;
          }
          paint(); 
      });
      setOnMouseEntered((MouseEvent mouseEvent) -> {
          if (!mouseEvent.isPrimaryButtonDown()) {
              getScene().setCursor(Cursor.HAND);
          }
      });
      setOnMouseExited((MouseEvent mouseEvent) -> {
          if (!mouseEvent.isPrimaryButtonDown()) {
              getScene().setCursor(Cursor.DEFAULT);
          }
      });
    }
      
    private class Delta { double x, y; }
   
    }
  
  public void paint(){
    double x1,x2=0,y1,y2=0;
    double t;           //the time interval
    double k = .025;	//time step value for drawing curve
    x1 = coordlist[0].x;
    y1 = coordlist[0].y;
    t=k;
    polynomial(t,k,x1,x2,y1,y2);
  };
  
  public int checkColor(String  f){
      String temp[] = f.split(",");
      System.out.println(temp[4].substring(8));      
      for (int i = 0; i < colorlist.length; i++) {
          if (temp[4].substring(8).equals(colorlist[i].toString())){
              return i;
          }
      }
      return 0;
  }
  public void polynomial(double t,double k,double x1,double x2,double y1,double y2){
        if (t >1+k )return;
        x2=(coordlist[0].x+t*(-coordlist[0].x*3+t*(3*coordlist[0].x-
        coordlist[0].x*t)))+t*(3*coordlist[1].x+t*(-6*coordlist[1].x+
        coordlist[1].x*3*t))+t*t*(coordlist[2].x*3-coordlist[2].x*3*t)+
        coordlist[3].x*t*t*t;
        y2=(coordlist[0].y+t*(-coordlist[0].y*3+t*(3*coordlist[0].y-
        coordlist[0].y*t)))+t*(3*coordlist[1].y+t*(-6*coordlist[1].y+
        coordlist[1].y*3*t))+t*t*(coordlist[2].y*3-coordlist[2].y*3*t)+
        coordlist[3].y*t*t*t;
        //draw curve
        Line line = new Line(x1, y1,x2, y2);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(5);
        x1 = x2;
        y1 = y2;
        pane.getChildren().add(line);
        t+=k;
        polynomial(t,k,x1,x2,y1,y2);
  }
}