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

 //konstruktor (inicjalizuje wspolczynniki i oblicza delte)
 public Trinomial(double a, double b, double c)
 {
  this.a = a;
  this.b = b;
  this.c = c;
  calculateDelta();  //delta obliczana na podstawie przekazanych wspolczynnikow
 }

 //obliczanie delty
 public void calculateDelta()
 {
  delta=b*b-4*a*c;
 }

 //obliczanie pierwiastkow
 public void calculateRoots()
 {
  if (delta<0)
  {
   System.out.println("Brak pierwiastków");
  }
  else if (delta==0)
  {
   x1=-b / (2*a);
   System.out.println("x1 = x2 = "+x1);
  }
  else
  {
   x1=(-b - Math.sqrt(delta)) / (2*a);
   x2=(-b + Math.sqrt(delta)) / (2*a);
   System.out.println("x1 = "+x1);
   System.out.println("x2 = "+x2);
  }
 }

 //---gettery (uzyskuje dane wartosci)---
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
  TextField t1, t2, t3;  //pola tekstowe do wprowadzania wspolczynnikow a, b, c
  //LineChart l;  random linia
  LineChart<Number, Number> l; // Wykres liniowy
  XYChart.Series<Number, Number> series;  //dane wykresu (seria punktow)
 
 @Override
  
  public void start(Stage primaryStage) throws Exception 
   {
    AnchorPane ap = new AnchorPane(); // Główny kontener aplikacji

    Label l1,l2,l3; // Etykiety dla współczynników a, b, c

    Button action = new Button("Display"); // Przycisk do wywołania wyświetlenia wykresu
    
    action.setOnAction(this::display); // Ustawienie akcji dla przycisku
        
    double a, b, c;
    
    VBox parameters = new VBox(); // Kontener do układania elementów w pionie
    parameters.setSpacing( 5.0d );  // Odstępy między elementami
    parameters.setAlignment(Pos.TOP_LEFT); // Wyrównanie do górnego lewego rogu
    
    HBox line_1, line_2, line_3; // Linie zawierające etykiety i pola tekstowe

    // Linie do wprowadzania wartości a, b, c
    line_1 = new HBox();
    line_1.setSpacing( 4.0d );
    
    l1 = new Label("a:");
    t1 = new TextField("0.0"); //domyslna wartosc a=1
    
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

    // Dodanie wszystkich linii i przycisku do kontenera
    parameters.getChildren().addAll(line_1, line_2, line_3, action);

    
    ap.getChildren().add( parameters );  // Dodanie parametrów do głównego kontenera

    // Tworzenie wykresu liniowego z odpowiednimi osiami
    l = new LineChart(new NumberAxis("X", -10.0, 10.0, 1.0), new NumberAxis("Y", -10.0, 10.0, 1.0));

    l.setLegendVisible(false); //ukrycie legendy
    l.setCreateSymbols(false);  //ukrycie symboli punktow na wykresie-tylko linie
    
    series = new XYChart.Series<>();  //utworzenie serii danych
    
    //series.getData().add( new XYChart.Data<>(0.0,0.0));
    //series.getData().add( new XYChart.Data<>(0.3,0.6));
    //series.getData().add( new XYChart.Data<>(0.7,0.8));

    l.getData().add(series); //dodanie serii do wykreu

    
    l.setTitle("Some function");
    l.setStyle("-fx-background-color: white"); // Styl wykresu
    ap.getChildren().add( l ); // Dodanie wykresu do kontenera
    AnchorPane.setTopAnchor( l, 120.0d ); // Ustawienia kotwiczenia (pozycjonowanie)
    AnchorPane.setBottomAnchor( l, 40.0d );
    AnchorPane.setRightAnchor( l, 10.0d );
    AnchorPane.setLeftAnchor( l, 10.0d );

    //Konfiguracja sceny i okna głównego
    Scene scene = new Scene(ap);
    primaryStage.setTitle("Wykres funkcji kwadratowej");
    primaryStage.setScene( scene );
    primaryStage.setWidth(600);
    primaryStage.setHeight(500);
    primaryStage.show();   // Wyświetlenie okna aplikacji
  }

  private void display(ActionEvent e)
   {
    double a,b,c;

    // Pobieranie wartości współczynników z pól tekstowych
    a=Double.valueOf(t1.getText());
    b=Double.valueOf(t2.getText());
    c=Double.valueOf(t3.getText());

    Trinomial t = new Trinomial(a,b,c);
    t.calculateRoots(); //obliczanie pierwiastkow

    System.out.println("Display pressed");

    System.out.println("a=" + t1.getText());
    System.out.println("b=" + t2.getText());
    System.out.println("c=" + t3.getText());

    System.out.println("Delta = " + t.getDelta());
    if (t.getDelta() >= 0) {
     System.out.println("x1 = " + t.getX1());
     if (t.getDelta() > 0) {
      System.out.println("x2 = " + t.getX2());
     }
    }

    // Aktualizacja danych wykresu - usuwamy stare punkty
    series.getData().clear();

    // Tworzenie punktów dla wykresu funkcji kwadratowej y = ax^2 + bx + c
    for (double x = -10; x <= 10; x += 0.1) {
     double y = a * x * x + b * x + c; // Obliczanie wartości y dla danego x
     series.getData().add(new XYChart.Data<>(x, y)); // Dodawanie punktu do serii danych
    }


    
    //series.getData().remove(0,2);

    //series.getData().add( new XYChart.Data<>(0.2,0.5));
    //series.getData().add( new XYChart.Data<>(0.3,0.5));
    //series.getData().add( new XYChart.Data<>(0.7,0.5));

    
   }  

   
  public static void main(String[] args) 
   {
    launch(args);
   }
 }
  