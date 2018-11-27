package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MyClient extends Application {

    private Label clientLabel = new Label("Client");
    private Label portLabel = new Label("Port");
    private Label hostLabel = new Label("Host");
    private Label firstOperandLabel = new Label("Operand 1");
    private Label secondOperandLabel = new Label("Operand 2");

    private TextArea statusArea = new TextArea();
    private TextArea portArea = new TextArea("8000");
    private TextArea hostArea = new TextArea("127.0.0.1");
    private TextArea firstArea = new TextArea("10");
    private TextArea secondArea = new TextArea("20");

    private Button connectButton = new Button("Connect");
    private Button disconnectButton = new Button("Disconnect");
    private Button submitButton = new Button("Submit");

    private RadioButton sumRb = new RadioButton("Sum");
    private RadioButton mulRb = new RadioButton("Mul");
    private RadioButton sinRb = new RadioButton("Sin");
    private RadioButton tanRb = new RadioButton("Tan");

    private int action_flag = 1;


    private int PORT = 8000;
    private String HOST = "127.0.0.1";

    private Socket socket;

    private Pane root_pane= new Pane();


    @Override
    public void start(Stage stage) throws Exception{
        setupScene();
        setupAction();
        stage.setX(500);
        stage.setY(100);
        stage.setTitle("Лабораторна робота #5 | Колесник С.Ю. | ТП-413");
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        Scene scene = new Scene(root_pane, 500,250, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }

    private void setupAction() {
        sumRb.setOnAction(__ -> {
            action_flag = 1;
        });
        mulRb.setOnAction(__ -> {
            action_flag = 3;
        });
        sinRb.setOnAction(__ -> {
            action_flag = 7;
        });
        tanRb.setOnAction(__ -> {
            action_flag = 15;
        });
        connectButton.setOnAction(__ -> {
                    try{
                        socket = new Socket(HOST, PORT);
                        Platform.runLater(() -> statusArea.appendText("\n>>> Connected: "+
                                "\nPort: "+PORT
                                +"\nHost: "+HOST + "\n"));

                    }catch (Exception ex){
                        Platform.runLater(() -> statusArea.appendText("\n== connection failed: "
                                +"\nPort: "+ PORT
                                +"\nHost: "+ HOST));
                        ex.printStackTrace();
                    }
        });
        disconnectButton.setOnAction(__ -> {
            try{
            if(socket != null)
                socket.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        });
        submitButton.setOnAction(__ -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (socket.isConnected()) {
                        if (reader.ready()) {
                            switch (action_flag){
                                case 1:
                                    statusArea.appendText("\nСумма: " + reader.readLine());
                                    break;
                                case 3:
                                    statusArea.appendText("\nПроизведение: " + reader.readLine());
                                    break;
                                case 7:
                                    statusArea.appendText("\nСинус: " + reader.readLine());
                                    break;
                                case 15:
                                    statusArea.appendText("\nТангенс: " + reader.readLine());
                                    break;
                            }
                        }
                    }
                        }catch (IOException ex){
                            System.out.println("IOException");
                            ex.printStackTrace();
                        }
                    }

            }).start();
            try {
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                outputStream.writeInt(action_flag);

                outputStream.writeDouble(Double.parseDouble(firstArea.getText()));
                outputStream.writeDouble(Double.parseDouble(secondArea.getText()));
                outputStream.flush();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        });
    }

    private void setupScene() {
        clientLabel.setLayoutY(5);
        clientLabel.setLayoutX(10);
        clientLabel.setWrapText(true);

        statusArea.setLayoutX(10);
        statusArea.setLayoutY(25);
        statusArea.setPrefSize(175,150);
        statusArea.setWrapText(true);

        connectButton.setLayoutX(425);
        connectButton.setLayoutY(20);
        connectButton.setWrapText(true);

        disconnectButton.setLayoutY(50);
        disconnectButton.setLayoutX(425);
        disconnectButton.setWrapText(true);

        submitButton.setLayoutY(215);
        submitButton.setLayoutX(425);
        submitButton.setWrapText(true);

        GridPane gp = new GridPane();
        gp.setHgap(20);
        gp.setVgap(10);

        portArea.setWrapText(true);
        portArea.setMaxWidth(75);
        portArea.setMaxHeight(15);
        hostArea.setWrapText(true);
        hostArea.setMaxWidth(75);
        hostArea.setMaxHeight(15);
        firstArea.setWrapText(true);
        firstArea.setMaxWidth(75);
        firstArea.setMaxHeight(15);
        secondArea.setWrapText(true);
        secondArea.setMaxWidth(75);
        secondArea.setMaxHeight(15);

        gp.add(portLabel,2,0);
        gp.add(portArea,3,0);
        gp.add(hostLabel,2,1);
        gp.add(hostArea,3,1);
        gp.add(firstOperandLabel,2,2);
        gp.add(firstArea,3,2);
        gp.add(secondOperandLabel,2,3);
        gp.add(secondArea,3,3);

        gp.setPadding(new Insets(10,0,0,100));

        ToggleGroup tg = new ToggleGroup();

        sumRb.setToggleGroup(tg);
        sumRb.setSelected(true);
        mulRb.setToggleGroup(tg);
        sinRb.setToggleGroup(tg);
        tanRb.setToggleGroup(tg);

        gp.add(sumRb,0,4);
        gp.add(mulRb,1,4);
        gp.add(sinRb,2,4);
        gp.add(tanRb,3,4);

        root_pane.getChildren().addAll(clientLabel, statusArea, connectButton,disconnectButton, submitButton, gp);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
