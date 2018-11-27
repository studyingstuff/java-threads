package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*
Вариант 10 = 3/6 (ряд квадратів натуральних чисел/обчислення суми елементів послідовності)
Натуральные - 1,2,3...
 */
public class Test extends Application {

    private static boolean running1 = true;
    private static boolean running2 = true;
/*
The volatile keyword is used to say to the jvm "Warning, this variable may be modified in an other Thread".
 Without this keyword the JVM is free to make some optimizations,
  like never refreshing those local copies in some threads.
   The volatile force the thread to update the original variable for each variable.
 */
    private static volatile int DEFAULT_VALUE = 1; //add or remove volatile
    Label label1 = new Label("Value");
    static TextArea log1 = new TextArea();
    Button stop1 = new Button("Stop");

    Label label2 = new Label("Value");
    static TextArea log2 = new TextArea();
    Button stop2 = new Button("Stop");

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int local_value = DEFAULT_VALUE;
            while (running2){
                if( local_value!= DEFAULT_VALUE){
                    log2.appendText(Math.pow(DEFAULT_VALUE, 2)+"\n");
//                    LOGGER.log(Level.INFO,"Got Change for MY_INT : {0}", MY_INT);
                    local_value = DEFAULT_VALUE;
                }
            }
        }
    }

    static class ChangeMaker extends Thread{
        @Override
        public void run() {

            int local_value = DEFAULT_VALUE;
            while (running1){
                log1.appendText(DEFAULT_VALUE++ + "\n");
//                LOGGER.log(Level.INFO, "Incrementing MY_INT to {0}", local_value+1);
                DEFAULT_VALUE = ++local_value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

    Pane root_pane = new Pane();

//    Thread thread1 = new Thread(new Runnable() {
//        @Override
//        public void run() {
////            int local_value = DEFAULT_VALUE;
//            while(running1){
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        log1.appendText(DEFAULT_VALUE++ + "\n");
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
//    Thread thread2 = new Thread(new Runnable() {
//
//        @Override
//        public void run() {
//            int local_value = DEFAULT_VALUE;
//            while(running2){
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(local_value != DEFAULT_VALUE)
//                        log2.appendText((Math.pow(DEFAULT_VALUE, 2))+"\n");
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
        new ChangeMaker().start();
//        thread1Label.start();
        new ChangeListener().start();
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


        root_pane.getChildren().addAll(stop1, stop2, gp);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
