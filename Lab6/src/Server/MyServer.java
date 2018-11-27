package Server;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/*
Вариант 10 = 0 : length = 110px, width = 20px, color = red
 */


public class MyServer extends Application {
    private boolean flag_direction = true;
    private boolean flag_direction1 = true;
    private boolean flag_direction2 = true;
    private boolean flag_direction3 = true;
    private boolean flag_direction4 = true;

    private final double MAX_LENGHT = 110;
    private final double width = 20;
    private final Color color = Color.RED;

    private Rectangle createRectangle(double startX, double startY, double height,
                                      double width, Color color){
        Rectangle r = new Rectangle(startX, startY, width, height);
        r.setFill(color);
        return r;
    }

    Rectangle rectangle1 = createRectangle(10, 50, 0, width, color);
    Label label1 = new Label("0px");

    Rectangle rectangle2 = createRectangle(110, 50, 0, width, color);
    Label label2 = new Label("0px");


    Rectangle rectangle3 = createRectangle(210, 50, 0, width, color);
    Label label3 = new Label("0px");


    Rectangle rectangle4 = createRectangle(310, 50, 0, width, color);
    Label label4 = new Label("0px");

    Label totalS = new Label("0px");

    private Pane root_pane = new Pane();

    private Label portLabel = new Label("Port 8000");
    private Label connectionsLabel = new Label("Connections: 0");

    private TextArea serverStatusTextArea = new TextArea();

    private Task<Void> server_task;
    private final int PORT = 8000;

    private Socket socket;

    private CallBacks impl = new CallBacks() {

        public double tickPixels(int num){
            double result = 0;
            Rectangle rectangle = null;
            Label label = null;
            switch (num){
                case 1:
                    rectangle = rectangle1;
                    label = label1;
                    break;
                case 2:
                    rectangle = rectangle2;
                    label = label2;
                    break;
                case 3:
                    rectangle = rectangle3;
                    label = label3;
                    break;
                case 4:
                    rectangle = rectangle4;
                    label = label4;
                    break;
            }
            result = rectangle.getHeight()* rectangle.getWidth();
            label.setText(result + "px");
            return result;
        }

        @Override
        public void pulseRectangle(int num){
            Rectangle rectangle = null;
            switch (num){
                case 1:
                    rectangle = rectangle1;
                    break;
                case 2:
                    rectangle = rectangle2;
                    break;
                case 3:
                    rectangle = rectangle3;
                    break;
                case 4:
                    rectangle = rectangle4;
                    break;
            }
            if(rectangle == rectangle1)
                flag_direction = flag_direction1;
            else if (rectangle == rectangle2)
                flag_direction = flag_direction2;
            else if (rectangle == rectangle3)
                flag_direction = flag_direction3;
            else if (rectangle == rectangle4)
                flag_direction = flag_direction4;
            if(flag_direction == true && rectangle.getHeight() < MAX_LENGHT){
                rectangle.setHeight(rectangle.getHeight() + 1);
            }

            if(rectangle1.getHeight() == 1){
                flag_direction = true;
            }
            if(rectangle1.getHeight() == MAX_LENGHT) {
                flag_direction = false;
            }
        }

        @Override
        public double updateTotal() {
            double value = rectangle1.getHeight() * rectangle1.getWidth() + rectangle2.getHeight() *
                    rectangle2.getWidth() + rectangle3.getHeight() * rectangle3.getWidth() +
                    rectangle4.getHeight() * rectangle4.getWidth();
            totalS.setText("Total: " + value + "px");
            return value;
        }

        @Override
        public void updateConnections(int value) {
            connectionsLabel.setText("Connections: "+value);
        }


        @Override
        public void updateRightText(String s) {
            serverStatusTextArea.appendText("\n"+ s);
        }
    };
    @Override
    public void start(Stage primaryStage) throws Exception{
        setupScene();

        primaryStage.setY(500);
        primaryStage.setY(100);
        primaryStage.setTitle("Лабораторна робота #2 | Колесник С.Ю. | ТП-413");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root_pane, 600, 250, Color.TRANSPARENT));
        primaryStage.show();
        setupServer();
    }

    private void setupScene() {

        portLabel.setLayoutX(375);
        portLabel.setLayoutY(10);

        GridPane gridPane = new GridPane();
        gridPane.setLayoutX(20);
        gridPane.setLayoutY(20);
        gridPane.setHgap(55);
        gridPane.setVgap(20);
        gridPane.add(label1,0,0);
        gridPane.add(label2,1,0);
        gridPane.add(label3,2,0);
        gridPane.add(label4,3,0);

        gridPane.add(rectangle1,0,1);
        gridPane.add(rectangle2,1,1);
        gridPane.add(rectangle3,2,1);
        gridPane.add(rectangle4,3,1);

        totalS.setLayoutX(100);
        totalS.setLayoutY(200);

        connectionsLabel.setLayoutX(475);
        connectionsLabel.setLayoutY(10);

        serverStatusTextArea.setPrefSize(200, 150);
        serverStatusTextArea.setWrapText(true);
        serverStatusTextArea.setLayoutY(25);
        serverStatusTextArea.setLayoutX(350);

        root_pane.getChildren().addAll(serverStatusTextArea,
                connectionsLabel,portLabel, gridPane,totalS);
    }

    private void setupServer() {
            server_task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        ServerSocket serverSocket = new ServerSocket(PORT);

                        while (true) {
                            socket = serverSocket.accept();
                            new Thread(new Server(socket, impl)).start();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        server_task.cancel();
                    }
                    return null;
                }
            };
            new Thread(server_task).start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
