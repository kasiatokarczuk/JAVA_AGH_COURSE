package org.example.zad4;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
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
import java.util.ArrayList;


//--------klasa abstrakcyjna Shape---------
abstract class Shape
{
 protected double x, y;
 protected Color color;

 public Shape(double x, double y, Color color)
 {
  this.x = x;
  this.y = y;
  this.color = color;
 }

 public abstract void calcArea();
 public abstract void draw(GraphicsContext gc);

 public void enlarge()
 {
  // Domyślnie: brak działania. Konkretne klasy nadpisują tę metodę.
 }
}




//------------klasa do rysowania okręgów-----------
class Circle1 extends Shape {
 double radius;

 public Circle1(double x, double y, Color color, double radius) {
  super(x, y, color);
  this.radius = radius;
 }

 @Override
 public void calcArea() {
  double area = Math.PI * radius * radius;
  System.out.println("Circle area: " + area);
 }

 @Override
 public void draw(GraphicsContext gc) {
  gc.setFill(color);
  gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
 }

 @Override
 public void enlarge() {
  this.radius *= 1.1; // powiększenie promienia o 10%
 }
}




//---------------klasa do rysowania prostokątów------------------
class Rectangle extends Shape {
 double width;
    double height;

 public Rectangle(double x, double y, Color color, double width, double height) {
  super(x, y, color);
  this.width = width;
  this.height = height;
 }

 @Override
 public void calcArea() {
  double area = width * height;
  System.out.println("Rectangle area: " + area);
 }

 @Override
 public void draw(GraphicsContext gc) {
  gc.setFill(color);
  gc.fillRect(x, y, width, height);
 }

 @Override
 public void enlarge() {
  this.width *= 1.1;
  this.height *= 1.1; // powiększanie rozmiaru o 10%
 }
}


//----------------klasa do rysowania elipsy------------------
class Ellipse extends Shape {
 double radiusX;
    double radiusY;

 public Ellipse(double x, double y, Color color, double radiusX, double radiusY) {
  super(x, y, color);
  this.radiusX = radiusX;
  this.radiusY = radiusY;
 }

 @Override
 public void calcArea() {
  double area = Math.PI * radiusX * radiusY;
  System.out.println("Ellipse area: " + area);
 }

 @Override
 public void draw(GraphicsContext gc) {
  gc.setFill(color);
  gc.fillOval(x - radiusX, y - radiusY, radiusX * 2, radiusY * 2);
 }

 @Override
 public void enlarge() {
  this.radiusX *= 1.1;
  this.radiusY *= 1.1; // powiększa o 10%
 }
}



public class SimpleGamePolymorphism extends Application {
 private static final int FRAME_WIDTH = 640;
 private static final int FRAME_HEIGHT = 480;

 private Canvas canvas;
 private GraphicsContext gc;

 // Dwie listy do zarządzania kształtami
 private ArrayList<Shape> allShapes;
 private ArrayList<Shape> visibleShapes;

 private boolean showingOnlyCircles = false;

 public static void main(String[] args) {
  launch(args);
 }

 @Override
 public void start(Stage primaryStage) {
  AnchorPane root = new AnchorPane();
  canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
  gc = canvas.getGraphicsContext2D();
  allShapes = new ArrayList<>();
  visibleShapes = new ArrayList<>();

  canvas.setOnMousePressed(this::handleMousePress);

  root.getChildren().add(canvas);

  Button toggleShapesButton = new Button("Toggle Shapes");
  toggleShapesButton.setOnAction(e -> toggleShapes());

  root.getChildren().add(toggleShapesButton);
  AnchorPane.setBottomAnchor(toggleShapesButton, 10.0);
  AnchorPane.setLeftAnchor(toggleShapesButton, 10.0);


  //-----------Dodanie losowych kształtów do listy--------------
  allShapes.add(new Circle1(100, 100, Color.BLUE, 30));
  allShapes.add(new Rectangle(200, 150, Color.RED, 50, 80));
  allShapes.add(new Circle1(300, 200, Color.GREEN, 40));
  allShapes.add(new Rectangle(400, 300, Color.YELLOW, 70, 40));
  allShapes.add(new Ellipse(150, 300, Color.PURPLE, 40, 20));
  allShapes.add(new Ellipse(350, 100, Color.ORANGE, 60, 30));

  //------------Początkowo widoczne wszystkie figury---------------
  visibleShapes.addAll(allShapes);

  redraw();

  Scene scene = new Scene(root, FRAME_WIDTH, FRAME_HEIGHT);
  primaryStage.setTitle("Polymorphism Shapes");
  primaryStage.setScene(scene);
  primaryStage.show();
 }


 //-------------powiększanie figur po nacisnieciu myszka---------------
 private void handleMousePress(MouseEvent event) {
  double mouseX = event.getX();
  double mouseY = event.getY();

  for (Shape shape : visibleShapes) {
   if (shape instanceof Circle1) {
    Circle1 circle = (Circle1) shape;
    double distance = Math.sqrt(Math.pow(mouseX - circle.x, 2) + Math.pow(mouseY - circle.y, 2));
    if (distance <= circle.radius) {
     shape.enlarge();
     break;
    }
   } else if (shape instanceof Rectangle) {
    Rectangle rectangle = (Rectangle) shape;
    if (mouseX >= rectangle.x && mouseX <= rectangle.x + rectangle.width &&
            mouseY >= rectangle.y && mouseY <= rectangle.y + rectangle.height) {
     shape.enlarge();
     break;
    }
   } else if (shape instanceof Ellipse) {
    Ellipse ellipse = (Ellipse) shape;
    double dx = (mouseX - ellipse.x) / ellipse.radiusX;
    double dy = (mouseY - ellipse.y) / ellipse.radiusY;
    if (dx * dx + dy * dy <= 1) {
     shape.enlarge();
     break;
    }
   }
  }
  redraw();
 }



//------------Wyswietlanie danych figur po nacisnieciu przycisku------------
 private int state = 0;
 private void toggleShapes() {
  switch (state) {
   case 0: // Wyświetl tylko prostokąty (znikają koła i elipsy)
    visibleShapes.clear();
    for (Shape shape : allShapes) {
     if (!(shape instanceof Circle1 || shape instanceof Ellipse)) {
      visibleShapes.add(shape);
     }
    }
    state = 1;
    break;

   case 1: // Wyświetl tylko elipsy (znikają prostokąty i koła)
    visibleShapes.clear();
    for (Shape shape : allShapes) {
     if (shape instanceof Ellipse) {
      visibleShapes.add(shape);
     }
    }
    state = 2;
    break;

   case 2: // Wyświetl tylko koła (znikają prostokąty i elipsy)
    visibleShapes.clear();
    for (Shape shape : allShapes) {
     if (shape instanceof Circle1) {
      visibleShapes.add(shape);
     }
    }
    state = 3;
    break;

   case 3: // Przywróć wszystkie figury
    visibleShapes.clear();
    visibleShapes.addAll(allShapes);
    state = 0;
    break;
  }
  redraw();
 }


 private void redraw() {
  gc.clearRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
  for (Shape shape : visibleShapes) {
   shape.draw(gc);
  }
 }
}

