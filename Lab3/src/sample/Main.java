package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

/*
Вариант 10 = 0 : length = 110px, width = 20px, color = red
 */
public class Main extends Application {

    Thread thread1 = new Thread(){
        @Override
        public void run() {
            while (running1) {
//                    System.out.println("Test");
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pulseRectangle(rectangle1);
                            tickPixels(rectangle1, label1);
                        }
                    });
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
//                    try {
//                        Thread.sleep(50);
//                        pulseRectangle();
//                        calculatePixels();
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
            }
        }

    };
    Thread thread2 = new Thread(){
        @Override
        public void run() {
            while (running2) {
//                    System.out.println("Test");
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pulseRectangle(rectangle2);
                            tickPixels(rectangle2, label2);
                        }
                    });
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
//                    try {
//                        Thread.sleep(50);
//                        pulseRectangle();
//                        calculatePixels();
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
            }
        }

    };
    Thread thread3 = new Thread(){
        @Override
        public void run() {
            while (running3) {
//                    System.out.println("Test");
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pulseRectangle(rectangle3);
                            tickPixels(rectangle3, label3);
                        }
                    });
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
//                    try {
//                        Thread.sleep(50);
//                        pulseRectangle();
//                        calculatePixels();
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
            }
        }

    };

    Thread thread4 = new Thread(){
        @Override
        public void run() {
            while (running4) {
//                    System.out.println("Test");
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pulseRectangle(rectangle4);
                            tickPixels(rectangle4, label4);
                        }
                    });
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
//                    try {
//                        Thread.sleep(50);
//                        pulseRectangle();
//                        calculatePixels();
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
            }
        }

    };

    private final double length = 110;
    private final double width = 20;
    private final Color color = Color.RED;

    private final double MAX_LENGHT = 110;
    private boolean flag_direction = true;
    private boolean flag_direction1 = true;
    private boolean flag_direction2 = true;
    private boolean flag_direction3 = true;
    private boolean flag_direction4 = true;

    Rectangle rectangle1 = createRectangle(10, 50, 0, width, color);
    Label label1 = new Label();

    Rectangle rectangle2 = createRectangle(110, 50, 0, width, color);
    Label label2 = new Label();


    Rectangle rectangle3 = createRectangle(210, 50, 0, width, color);
    Label label3 = new Label();


    Rectangle rectangle4 = createRectangle(310, 50, 0, width, color);
    Label label4 = new Label();

    Label totalS = new Label();

    ToggleGroup tg1 = new ToggleGroup();
    RadioButton radioButton1_1 = new RadioButton("Start");
    RadioButton radioButton1_2 = new RadioButton("Cancel");

    ToggleGroup tg2 = new ToggleGroup();
    RadioButton radiButton2_1 = new RadioButton("Start");
    RadioButton radioButton2_2 = new RadioButton("Cancel");

    ToggleGroup tg3 = new ToggleGroup();
    RadioButton radiButton3_1 = new RadioButton("Start");
    RadioButton radioButton3_2 = new RadioButton("Cancel");

    ToggleGroup tg4 = new ToggleGroup();
    RadioButton radiButton4_1 = new RadioButton("Start");
    RadioButton radioButton4_2 = new RadioButton("Cancel");
    private boolean running1 = true;
    private boolean running4 = true;
    private boolean running2 = true;
    private boolean running3 = true;

    private void setupScene() {
        radioButton1_1.setToggleGroup(tg1);
        radioButton1_2.setToggleGroup(tg1);
        radiButton2_1.setToggleGroup(tg2);
        radioButton2_2.setToggleGroup(tg2);
        radiButton3_1.setToggleGroup(tg3);
        radioButton3_2.setToggleGroup(tg3);
        radiButton4_1.setToggleGroup(tg4);
        radioButton4_2.setToggleGroup(tg4);

        GridPane gp = new GridPane();
        gp.setHgap(30);
        gp.setVgap(10);
        gp.setPadding(new javafx.geometry.Insets(200, 0, 0, 10));
        gp.add(radioButton1_1, 0, 0);
        gp.add(radioButton1_2,0,1);
        gp.add(radiButton2_1, 1,0);
        gp.add(radioButton2_2,1,1);
        gp.add(radiButton3_1,2,0);
        gp.add(radioButton3_2,2,1);
        gp.add(radiButton4_1,3,0);
        gp.add(radioButton4_2,3,1);

        root_pane.getChildren().addAll(gp);
    }

    private void setupAction() {
        radioButton1_1.setOnAction(__ -> {
            thread1.start();
        });
        radiButton2_1.setOnAction(__ -> {
            thread2.start();

        });
        radiButton3_1.setOnAction(__ -> {
            thread3.start();
        });
        radiButton4_1.setOnAction(__ -> {
            thread4.start();
        });
        radioButton1_2.setOnAction(__ -> {
            running1 = false;
        });
        radioButton2_2.setOnAction(__ -> {
            running2 = false;
        });
        radioButton3_2.setOnAction(__ -> {
            running3 = false;
        });
        radioButton4_2.setOnAction(__ -> {
            running4 = false;
        });
    }

    private Pane root_pane = new Pane();

    public void pulseRectangle(Rectangle rectangle1){
        if(rectangle1 == this.rectangle1)
            flag_direction = flag_direction1;
        else if (rectangle1 == rectangle2)
            flag_direction = flag_direction2;
        else if (rectangle1 == rectangle3)
            flag_direction = flag_direction3;
        else if (rectangle1 == rectangle4)
            flag_direction = flag_direction4;
        if(flag_direction == true && rectangle1.getHeight() < MAX_LENGHT){
            rectangle1.setHeight(rectangle1.getHeight() + 1);
        }
//        else if (rectangle1.getHeight() !=) {
//            rectangle1.setHeight(rectangle1.getHeight() - 1);
//        }

        if(rectangle1.getHeight() == 1){
            flag_direction = true;
        }
        if(rectangle1.getHeight() == MAX_LENGHT) {
            flag_direction = false;
        }
    }

    private Rectangle createRectangle(double startX, double startY, double height,
                                      double width, Color color){
        Rectangle r = new Rectangle(startX, startY, width, height);
        r.setFill(color);
        return r;
    }
    @Override
    public void start(Stage stage) throws Exception{
        setupScene();
        setupAction();

        stage.setTitle("Лабораторна робота #3 | Колесник С.Ю. | ТП-413");
        stage.setResizable(false);

        label1.setLayoutY(10);
        label1.setLayoutX(10);
        label1.setWrapText(true);
        label1.setPrefSize(200, 20);
        label1.setText("0px");

        label2.setLayoutY(10);
        label2.setLayoutX(110);
        label2.setWrapText(true);
        label2.setPrefSize(200, 20);
        label2.setText("0px");

        label3.setLayoutY(10);
        label3.setLayoutX(210);
        label3.setWrapText(true);
        label3.setPrefSize(200, 20);
        label3.setText("0px");

        label4.setLayoutY(10);
        label4.setLayoutX(310);
        label4.setWrapText(true);
        label4.setPrefSize(200, 20);
        label4.setText("0px");

        totalS.setLayoutX(350);
        totalS.setLayoutY(160);
        totalS.setWrapText(true);
        totalS.setPrefSize(200, 20);
        totalS.setText("Total: 0px");


        root_pane.getChildren().addAll(rectangle1,rectangle2, rectangle3, rectangle4, label1, label2, label3, label4, totalS);
        javafx.scene.Scene scene = new Scene(root_pane, 500, 250, Color.TRANSPARENT);

        stage.setScene(scene);
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        stage.setTitle("Hello World");
//        stage.setScene(new Scene(root, 300, 275));
        stage.show();



        Thread thread5 = new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                double value = rectangle1.getHeight() * rectangle1.getWidth() + rectangle2.getHeight() *
                                        rectangle2.getWidth() + rectangle3.getHeight() * rectangle3.getWidth() +
                                        rectangle4.getHeight() * rectangle4.getWidth();
                                totalS.setText("Total: " + value+ "px");
                            }
                        });
                        Thread.sleep(50);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        thread5.start();
    }




    private void tickPixels(Rectangle rec, Label label){
        label.setText(rec.getHeight()* rec.getWidth() + "px");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
