package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.chart.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

class Trinomial
{
 double a,b,c;
 double x1,x2,delta;

 public Trinomial(double a, double b, double c)
 {
  this.a = a;
  this.b = b;
  this.c = c;
  delta(a,b,c);
 }

 public void delta(double a,double b,double c)
 {
  delta=b*b-4*a*c;
 }

 public void pierwiastki()
 {
  if (delta<0)
  {
   System.out.println("Brak pierwiastków");
  }
  else if (delta==0)
  {
   x1=-b/2*a;
   System.out.println("x1="+x1);
  }
  else
  {
   x1=(-b-Math.sqrt(delta))/(2*a);
   x2=(-b+Math.sqrt(delta))/(2*a);
   System.out.println("x1="+x1);
   System.out.println("x2="+x2);
  }
 }

 public double getDelta()
 {
  return delta;
 }

 public double getX1()
 {
  return x1;
 }

 public double getX2()
 {
  return x2;
 }

}


public class Chart_1 extends Application
 {
  TextField t1, t2, t3;
  LineChart l;
  XYChart.Series<Double, Double> series;
 
 @Override
  
  public void start(Stage primaryStage) throws Exception 
   {
    AnchorPane ap = new AnchorPane();

    Label l1,l2,l3;

    Button action = new Button("Display");
    
    action.setOnAction(this::display);
        
    double a, b, c;
    
    VBox parameters = new VBox();
    parameters.setSpacing( 5.0d );
    parameters.setAlignment(Pos.TOP_LEFT);
    
    HBox line_1, line_2, line_3;
    
    line_1 = new HBox();
    line_1.setSpacing( 4.0d );
    
    l1 = new Label("a:");
    t1 = new TextField("0.0");
    
    line_1.getChildren().addAll(l1, t1);


    line_2 = new HBox();
    line_2.setSpacing( 4.0d );    

    l2 = new Label("b:");
    t2 = new TextField("0.0");
    
    line_2.getChildren().addAll(l2, t2);


    line_3 = new HBox();
    line_3.setSpacing( 4.0d );    
    
    l3 = new Label("c:");
    t3 = new TextField("0.0");
    
    line_3.getChildren().addAll(l3, t3);


    parameters.getChildren().addAll(line_1, line_2, line_3, action);

    
    ap.getChildren().add( parameters );
    
    l = new LineChart(new NumberAxis("X", 0.0, 1.0, 0.1), new NumberAxis("Y", 0.0, 1.0, 0.1));

    l.setLegendVisible(false);
    l.setCreateSymbols(false);
    
    series = new XYChart.Series<>();
    
    series.getData().add( new XYChart.Data<>(0.0,0.0));
    series.getData().add( new XYChart.Data<>(0.3,0.6));    
    series.getData().add( new XYChart.Data<>(0.7,0.8));

    l.getData().add(series);

    
    l.setTitle("Some function");
    l.setStyle("-fx-background-color: white");
    ap.getChildren().add( l );
    AnchorPane.setTopAnchor( l, 120.0d );
    AnchorPane.setBottomAnchor( l, 40.0d );
    AnchorPane.setRightAnchor( l, 10.0d );
    AnchorPane.setLeftAnchor( l, 10.0d );
    Scene scene = new Scene(ap);
    primaryStage.setTitle("Simple chart plotting");
    primaryStage.setScene( scene );
    primaryStage.setWidth(568);
    primaryStage.setHeight(420);
    primaryStage.show();   
  }

  private void display(ActionEvent e)
   {
    double a,b,c;
    double x1,x2;

    a=Double.valueOf(t1.getText());
    b=Double.valueOf(t2.getText());
    c=Double.valueOf(t3.getText());

    Trinomial t = new Trinomial(a,b,c);

    System.out.println("Display pressed");

    System.out.println("a=" + t1.getText());
    System.out.println("b=" + t2.getText());
    System.out.println("c=" + t3.getText());
    System.out.println("x1=" + t.getX1());
    System.out.println("x2=" + t.getX2());
    

    
    series.getData().remove(0,2);

    series.getData().add( new XYChart.Data<>(0.2,0.5));
    series.getData().add( new XYChart.Data<>(0.3,0.5));    
    series.getData().add( new XYChart.Data<>(0.7,0.5));

    
   }  

   
  public static void main(String[] args) 
   {
    launch(args);
   }
 }  
  