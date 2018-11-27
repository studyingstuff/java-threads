package Server;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MyServer extends Application {

    private Pane root_pane = new Pane();

    private Label serverLabel = new Label("MyServer");
    private Label portLabel = new Label("Port 8000");
    private Label connectionsLabel = new Label("Connections: 0");

    private TextArea serverTextArea = new TextArea();
    private TextArea serverStatusTextArea = new TextArea();

    private Task<Void> server_task;
    private final int PORT = 8000;

    private Socket socket;

    private CallBacks impl = new CallBacks() {
        @Override
        public void returnResult(int operation, double value) {

        }

        @Override
        public void updateConnections(int value) {
            connectionsLabel.setText("Connections: "+value);
        }

        @Override
        public void updateLeftText(String s) {
            serverTextArea.appendText("\n" + s);
        }

        @Override
        public void updateRightText(String s) {
            serverStatusTextArea.appendText("\n"+ s);
        }
    };
    @Override
    public void start(Stage primaryStage) throws Exception{
        setupScene();
        primaryStage.setX(500);
        primaryStage.setY(500);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Лабораторна робота #5 | Колесник С.Ю. | ТП-413");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root_pane, 600, 250, Color.TRANSPARENT));
        primaryStage.show();
        setupServer();
    }

    private void setupScene() {
        serverLabel.setLayoutY(10);
        serverLabel.setLayoutX(5);

        portLabel.setLayoutX(300);
        portLabel.setLayoutY(10);

        connectionsLabel.setLayoutX(350);
        connectionsLabel.setLayoutY(10);

        serverTextArea.setPrefSize(200,150);
        serverTextArea.setWrapText(true);
        serverTextArea.setLayoutY(25);
        serverTextArea.setLayoutX(10);

        serverStatusTextArea.setPrefSize(200, 150);
        serverStatusTextArea.setWrapText(true);
        serverStatusTextArea.setLayoutY(25);
        serverStatusTextArea.setLayoutX(350);

        root_pane.getChildren().addAll(serverTextArea, serverStatusTextArea,
                connectionsLabel,portLabel,serverLabel);
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
