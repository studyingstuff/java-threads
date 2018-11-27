package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
/*
Вариант 10 = 3/6 (ряд квадратів натуральних чисел/обчислення суми елементів послідовності)
Натуральные - 1,2,3...
 */
public class Main extends Application {


    private boolean running1 = true;
    private boolean running2 = true;

    private static int DEFAULT_VALUE = 1;
    Label label1 = new Label("Value");
    TextArea log1 = new TextArea();
    Button stop1 = new Button("Stop");

    Label label2 = new Label("Value");
    TextArea log2 = new TextArea();
    Button stop2 = new Button("Stop");


    Pane root_pane = new Pane();

    Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            while(running1){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        log1.appendText(DEFAULT_VALUE++ + "\n");
                    }
                });
                try {
                    Thread.sleep(50);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    });
    //
//    Thread thread1Label = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            while(running1){
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        label1.setText(DEFAULT_RESULT+"");
//                    }
//                });
//                try {
//                    Thread.sleep(50);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    });
    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            while(running2){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        log2.appendText((Math.pow(DEFAULT_VALUE, 2))+"\n");
                    }
                });
                try {
                    Thread.sleep(50);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    });

    //    Thread thread2Label = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            while(running2){
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        label2.setText(VOLATILE_RESULT+"");
//                    }
//                });
//                try {
//                    Thread.sleep(50);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    });
    @Override
    public void start(Stage stage) throws Exception{
        setupScene();
        setupAction();

        stage.setTitle("Лабораторна робота #7 | Колесник С.Ю. | ТП-413");
        stage.setResizable(false);


        javafx.scene.Scene scene = new Scene(root_pane, 500, 250, Color.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
        thread1.start();
//        thread1Label.start();
        thread2.start();
//        thread2Label.start();
    }

    private void setupAction() {
        stop1.setOnAction(__ -> {
            running1 = false;
        });
        stop2.setOnAction(__ -> {
            running2 = false;
        });
    }


    private void setupScene() {
        GridPane gp = new GridPane();
        log1.setMaxHeight(150);
        log1.setMaxWidth(150);
        log1.setWrapText(true);

        stop1.setLayoutX(25);
        stop1.setLayoutY(5);

        log2.setMaxHeight(150);
        log2.setMaxWidth(150);
        log2.setWrapText(true);

        stop2.setLayoutX(250);
        stop2.setLayoutY(5);

        gp.add(log1,0,1);
        gp.add(label1,0,0);
        gp.add(log2,1,1);
        gp.add(label2,1,0);

        gp.setVgap(10);
        gp.setHgap(50);

        gp.setLayoutX(25);
        gp.setLayoutY(50);


        root_pane.getChildren().addAll(gp, stop1, stop2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
