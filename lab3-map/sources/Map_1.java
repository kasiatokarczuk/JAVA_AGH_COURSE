import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.shape.Polygon;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;



public class Map_1 extends Application
 {
  private static final int FRAME_WIDTH  = 640;
  private static final int FRAME_HEIGHT = 480;  

  int x, y;

  GraphicsContext gc;
  Canvas canvas;

      
  public static void main(String[] args) 
   {
    launch(args);
   }


    
    @Override
    public void start(Stage primaryStage) 
     {
      double x_p[] = new double[4];
      double y_p[] = new double[4];
      
      AnchorPane root = new AnchorPane();
      primaryStage.setTitle("Map");
	
      canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
      canvas.setOnMousePressed(this::mouse);
      
      gc = canvas.getGraphicsContext2D();

      
      // A
      Image image = new Image("map.jpg");
      
      gc.drawImage(image, 0, 0, FRAME_WIDTH, FRAME_HEIGHT);      
      
      // B     
      x_p[0] = 100;
      y_p[0] = 100;
      x_p[1] = 400;
      y_p[1] = 90;      
      x_p[2] = 300;
      y_p[2] = 200;      
      x_p[3] = 80;
      y_p[3] = 180;      
      
      gc.setFill(Color.GRAY);
      gc.fillPolygon(x_p, y_p, 4);
      
      gc.setLineWidth(2);
      
      gc.strokePolygon(x_p, y_p, 4);     
      
      	
      root.getChildren().add(canvas);	
      
      RadioButton rbtn1 = new RadioButton();
      rbtn1.setText("Woods");
      rbtn1.setSelected(true);      
      rbtn1.setOnAction(this::woods);	

      root.getChildren().add(rbtn1);
      AnchorPane.setBottomAnchor( rbtn1, 5.0d );
      AnchorPane.setLeftAnchor( rbtn1, 50.0d );      


      RadioButton rbtn2 = new RadioButton();
      rbtn2.setText("Rocks");
      rbtn2.setSelected(true);            
      rbtn2.setOnAction(this::rocks);	

      root.getChildren().add(rbtn2);
      AnchorPane.setBottomAnchor( rbtn2, 5.0d );
      AnchorPane.setLeftAnchor( rbtn2, 200.0d );      


     

      Scene scene = new Scene(root);
      primaryStage.setTitle("Dolina Bedkowska");
      primaryStage.setScene( scene );
      primaryStage.setWidth(FRAME_WIDTH + 10);
      primaryStage.setHeight(FRAME_HEIGHT+ 80);
      primaryStage.show();   
    }

   private void woods(ActionEvent e)    
    {
     System.out.println("woods");
    }

   private void rocks(ActionEvent e)    
    {
     System.out.println("rocks");
    }


   
   private void mouse(MouseEvent e)
    {
     System.out.println("X=" + e.getX());
     System.out.println("Y=" + e.getY());         
    }   
    
  
    
}	
