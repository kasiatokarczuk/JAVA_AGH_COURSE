package org.example.test;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;


class Ball {
    double x0, y0;  // poczatkowa pozycja
    double x, y;    // obecna pozycja
    double v, alpha;  // predkosc i kąt
    final double g = 9.81;  // stała grawitacji

    Ball(double x0, double y0, double v, double alpha) {
        this.x0 = x0;
        this.y0 = y0;
        this.x = x0;
        this.y = y0;
        this.v = v;
        this.alpha = alpha;
    }

    // rysowanie pilki w ustawionej pozycji
    void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);  // kolor piłki
        gc.fillOval(x - 15, y - 15, 30, 30);  // kształt piłki
    }

    // update pozycji pilki (ustawiajac kat i predkosc)
    void updatePosition(double t) {
        double alphaRad = Math.toRadians(alpha);  // stopnie -> radiany
        x = x0 + v * Math.cos(alphaRad) * t;  // pozioma zmiana położenia pilki
        y = y0 - (v * Math.sin(alphaRad) * t - (g * t * t) / 2);  // pionowa zmiana położenia piłki
    }

    // powrót piłki do pozycji początkowej
    void reset() {
        x = x0;
        y = y0;
    }
}

public class Simple_gamev1 extends Application implements ChangeListener<Number> {
    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;

    GraphicsContext gc;
    Canvas canvas;
    Ball ball;
    Slider alphaSlider, vSlider;
    Timeline timeline;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        primaryStage.setTitle("Volleyball");

        canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        //inicjalizacja z początkowymi parametrami
        ball = new Ball(100, 300, 20, 45);
        drawVolleyballField(gc);  // rysowanie boiska
        ball.draw(gc);  // rysowanie piłki

        // poruszanie myszka -> zmiana pozycji piłki
        canvas.setOnMousePressed(this::handleMouse);

        root.getChildren().add(canvas);

        // rozpoczecie animacji po nacisnieciu przycisku play
        Button playButton = new Button("Play");
        playButton.setOnAction(this::playAnimation);
        root.getChildren().add(playButton);
        AnchorPane.setBottomAnchor(playButton, 5.0d);

        // slider do ustawiania kata
        alphaSlider = new Slider(0, 90, 45);
        alphaSlider.setShowTickMarks(true);
        alphaSlider.setShowTickLabels(true);
        alphaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ball.alpha = newValue.doubleValue();
            System.out.println("Alpha: " + ball.alpha);
        });
        root.getChildren().add(alphaSlider);
        AnchorPane.setBottomAnchor(alphaSlider, 5.0d);
        AnchorPane.setLeftAnchor(alphaSlider, 150.0d);

        // slider do ustawiania predkosci
        vSlider = new Slider(10, 100, 20);
        vSlider.setShowTickMarks(true);
        vSlider.setShowTickLabels(true);
        vSlider.valueProperty().addListener(this);
        root.getChildren().add(vSlider);
        AnchorPane.setBottomAnchor(vSlider, 5.0d);
        AnchorPane.setLeftAnchor(vSlider, 300.0d);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(FRAME_WIDTH + 10);
        primaryStage.setHeight(FRAME_HEIGHT + 100);
        primaryStage.show();
    }

    // Handle slider value changes
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        ball.v = newValue.doubleValue();
        System.out.println("Velocity: " + ball.v);
    }

    // Play animation by moving the ball
    private void playAnimation(ActionEvent e) {
        if (timeline != null) {
            timeline.stop();  // Stop existing timeline if any
        }

        ball.reset();  // Reset the ball to its initial position
        final double[] time = {0};  // Track elapsed time

        timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            step(time[0]);
            time[0] += 0.05;  // Increment time by 0.05 seconds
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // Animation step: update the ball's position and redraw
    private void step(double t) {
        gc.clearRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);  // czyszczenie canvas
        drawVolleyballField(gc);  // ponowne narysowanie pola do siatkowki

        ball.updatePosition(t);  // update pozycji pilki

        // koniec animacji jesli pilka wyjdzie poza pole
        if (ball.x > FRAME_WIDTH || ball.y > FRAME_HEIGHT || ball.y < 0) {
            timeline.stop();
            ball.reset();  // resetowanie pozycji pilki
        }

        ball.draw(gc);  // rysowanie pilki na nowej pozycji
    }

    // klikniecie myszki do zmiany pozycji pilki
    private void handleMouse(MouseEvent e) {
        ball.x0 = e.getX();
        ball.y0 = e.getY();
        ball.reset();

        gc.clearRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);  // czyszczenie canvas
        drawVolleyballField(gc);  // ponowne narysowanie pola do siatkowki
        ball.draw(gc);  // rysowanie pilki w nowej pozycji

        System.out.println("Ball repositioned to: (" + ball.x0 + ", " + ball.y0 + ")");
    }

    // rysowanie pola do siatkowki
    private void drawVolleyballField(GraphicsContext gc) {
        gc.setFill(Color.BLUEVIOLET);  // kolor tla
        gc.fillRect(0, 0, FRAME_WIDTH , FRAME_HEIGHT );  // rysowanie tla

        gc.setStroke(Color.WHITE);  // kolor siatki
        gc.setLineWidth(4);
        gc.strokeLine(FRAME_WIDTH / 2, 150, FRAME_WIDTH / 2, FRAME_HEIGHT );  // siatka
    }
}

