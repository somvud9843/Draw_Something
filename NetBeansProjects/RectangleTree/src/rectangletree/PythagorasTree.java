package rectangletree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Created by milanbojovic on 21.11.15..
 */
public class PythagorasTree extends Application  {
    protected double p;
    protected double s;

    private int maxDepth;
    private int currentDepth;
    protected Canvas canvas;
    protected WebView webView;
    protected double canvasWidth, canvasHeight;
    protected GraphicsContext gContext;
    double x1, y1, x2, y2;
    double a, s2;
    double tanphi = 1.0;
    String mathMl;

    public PythagorasTree(int maxDepth, Canvas canvas) {
        a = Math.min(canvasHeight, canvasWidth) / 5;
        x1 = canvasWidth / 2 - a/2;
        y1 = canvasHeight - a/2;
        x2 = canvasWidth / 2 + a/2;
        y2 = y1;

        setCurrentDepth(10);
        p = 2;
        s = 1.41; //2/Sqrt(2)

        s = 2;  // two new figures
        s2 = 2; //1/sqrt(2) size of new figures

        initMathMl();
    }


    void drawLevel0() {}

    public void drawCurrentLevel() {
        drawPythagorasTree(getCurrentDepth(), x1, y1, x2, y2);
        updateFractalDimension(mathMl);
    }
    
    public void setCurrentDepth(int currentDepth) {
            this.currentDepth = currentDepth;
    }
     public int getCurrentDepth() {
        return currentDepth;
    }
    public void updateFractalDimension(String mathMl){
        webView.getEngine().loadContent(mathMl);
    }
    
    public void drawPythagorasTree(int n, double x1, double  y1, double x2, double y2){
        if(n > 0) {
                // (1) determine vertices
                double dx = x2 - x1;
                double dy = y1 - y2;
                double x3 = x1 - dy;
                double y3 = y1 - dx;
                double x4 = x2 - dy;
                double y4 = y2 - dx;

                //Drawing // (2) Square vollstndiges
                gContext.strokeLine((int)x1, (int)y1, (int)x2, (int)y2);
                gContext.strokeLine((int)x2, (int)y2, (int)x4, (int)y4);
                gContext.strokeLine((int)x4, (int)y4, (int)x3, (int)y3);
                gContext.strokeLine((int)x1, (int)y1, (int)x3, (int)y3);

                // Calculate (3) coordinates of the new vertex
                double v = (x3 + x4) / 2 - (dy / 2 * tanphi);
                double w = (y3 + y4) / 2 - (dx / 2 * tanphi);

                if  (dx * dx + dy * dy > 2) {
                    // (4) draw small Teilbume
                    drawPythagorasTree(n-1, x3, y3, v, w);
                    drawPythagorasTree(n-1, v, w, x4, y4);
                }
        }
    }



        private void initMathMl(){

            mathMl =    "      <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\t\n" +
                        "         <mrow>\n" +
                        "            <mi>d</mi>\n" +
                        "            <mtext> </mtext>\n" +
                        "            <mo>=</mo>\n" +
                        "            <mtext> </mtext>\n" +
                        "            <mfrac>\n" +
                        "               <mrow>\n" +
                        "                  <mi>log</mi>\n" +
                        "                     <mfenced>\n" +
                        "                           <mtext>P</mtext>\n" +
                        "                     </mfenced>\n" +
                        "               </mrow>\n" +
                        "               <mrow>\n" +
                        "                  <mi>log</mi>\n" +
                        "                  <mfenced>\n" +
                        "                     <mtext>S</mtext>\n" +
                        "                  </mfenced>\n" +
                        "               </mrow>\n" +
                        "            </mfrac>\n" +
                        "            <mtext> </mtext>\n" +
                        "            <mo>=</mo>\n" +
                        "            <mtext> </mtext>\n" +
                        "            <mfrac>\n" +
                        "               <mrow>\n" +
                        "                  <mi>log</mi>\n" +
                        "                  <mfenced>\n" +
                        "                     <mtext>" + (int)p + "</mtext>\n" +
                        "                  </mfenced>\n" +
                        "               </mrow>\n" +
                        "               <mrow>\n" +
                        "                  <mi>log</mi>\n" +
                        "                  <mfenced>\n" +
                        "                   <mfrac>\n" +
                        "                       <mrow>\n" +
                        "                           <mtext>" + (int)s + "</mtext>\n" +
                        "                       </mrow>\n" +
                        "                       <mrow>\n" +
                        "                           <msqrt>\n" +
                        "                               <mtext>" + (int)s2 + "</mtext>\n" +
                        "                           </msqrt>\n" +
                        "                       </mrow>\n" +
                        "                   </mfrac>\n" +
                        "                  </mfenced>\n" +
                        "               </mrow>\n" +
                        "            </mfrac>\n" +
                        "            <mtext> </mtext>\n" +
                        "            <mo>=</mo>\n" +
                        "            <mtext> </mtext>\n" +
                        "            <mn>" + Math.log(p) / Math.log(s/Math.sqrt(s2)) + "</mn>\n" +
                        "         </mrow>\n" +
                        "      </math>";
        }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Fractal Drawer");
        Canvas c = new Canvas();
        PythagorasTree pt = new PythagorasTree(10,c);
        c.getGraphicsContext2D();
        Pane pane = new Pane(c);
        Scene scene = new Scene(pane, 800, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
}
}