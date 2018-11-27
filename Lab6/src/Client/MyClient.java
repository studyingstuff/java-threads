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

    public static final int FLAG_START_ONE = 0;
    public static final int FLAG_STOP_ONE = 1;
    public static final int FLAG_START_TWO = 2;
    public static final int FLAG_STOP_TWO = 3;
    public static final int FLAG_START_THREE = 4;
    public static final int FLAG_STOP_THREE = 5;
    public static final int FLAG_START_FOUR = 6;
    public static final int FLAG_STOP_FOUR = 7;


    private Label clientLabel = new Label("Client");
    private Label portLabel = new Label("Port");
    private Label hostLabel = new Label("Host");
    private Label threadOneLabel = new Label("Thread 1");
    private Label threadTwoLabel = new Label("Thread 2");
    private Label threadThreeLabel = new Label("Thread 3");
    private Label threadFourLabel = new Label("Thread 4");
    private Label greyAreaOfRec = new Label("Площадь прямоугольника кв.см");
    private Label statusAreaOfRec = new Label("Общая площадь кв.см: 0");
    private Label statusOfFirst = new Label("0");
    private Label statusOfSecond = new Label("0");
    private Label statusOfThird = new Label("0");
    private Label statusOfFourth = new Label("0");

    private TextArea statusArea = new TextArea();
    private TextArea portArea = new TextArea("8000");
    private TextArea hostArea = new TextArea("127.0.0.1");


    private Button connectButton = new Button("Connect");
    private Button disconnectButton = new Button("Disconnect");

    private RadioButton startThreadOne = new RadioButton("Start");
    private RadioButton startThreadTwo = new RadioButton("Start");
    private RadioButton startThreadThree = new RadioButton("Start");
    private RadioButton startThreadFour = new RadioButton("Start");

    private RadioButton cancelThreadOne = new RadioButton("Cancel");
    private RadioButton cancelThreadTwo = new RadioButton("Cancel");
    private RadioButton cancelThreadThree = new RadioButton("Cancel");
    private RadioButton cancelThreadFour = new RadioButton("Cancel");

    private Integer PORT = null;
    private String HOST = null;//"127.0.0.1"

    private Socket socket;

    private Pane root_pane = new Pane();


    @Override
    public void start(Stage stage) throws Exception {
        setupScene();
        setupAction();
        stage.setY(500);
        stage.setY(500);
        stage.setTitle("Лабораторна робота #2 | Колесник С.Ю. | ТП-413");
        stage.setResizable(false);
        Scene scene = new Scene(root_pane, 500, 250, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }

    private void setupAction() {

        connectButton.setOnAction(__ -> {
            try {
                HOST = hostArea.getText();
                PORT = Integer.parseInt(portArea.getText());
                socket = new Socket(HOST, PORT);
                Platform.runLater(() -> statusArea.appendText("\n>>> Connected: " +
                        "\nPort: " + PORT
                        + "\nHost: " + HOST + "\n"));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            while (socket.isConnected()) {
                                if (reader.ready()) {
//                                            System.out.println(reader.readLine());
                                    String[] split = reader.readLine().split(",");
                                    switch (split[0]) {
                                        case "1":
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    statusOfFirst.setText(split[1]);
                                                    statusAreaOfRec.setText(split[2]);
                                                }
                                            });
                                            break;
                                        case "2":
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    statusOfSecond.setText(split[1]);
                                                    statusAreaOfRec.setText(split[2]);
                                                }
                                            });
                                            break;
                                        case "3":
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    statusOfThird.setText(split[1]);
                                                    statusAreaOfRec.setText(split[2]);
                                                }
                                            });
                                            break;
                                        case "4":
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    statusOfFourth.setText(split[1]);
                                                    statusAreaOfRec.setText(split[2]);
                                                }
                                            });
                                            break;
                                    }
//                                            switch (reader.readLine().charAt(0)){
//                                                case '1':
//                                                    statusOfFirst.setText(reader.readLine());
//                                                    break;
//                                                case '2':
//                                                    statusOfSecond.setText(reader.readLine());
//                                                    break;
//                                                case '3':
//                                                    statusOfThird.setText(reader.readLine());
//                                                    break;
//                                                case '4':
//                                                    statusOfFourth.setText(reader.readLine());
//                                                    break;
//                                            }
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();

            } catch (Exception ex) {
                Platform.runLater(() -> statusArea.appendText("\n== connection failed: "
                        + "\nPort: " + PORT
                        + "\nHost: " + HOST));
                ex.printStackTrace();
            }
        });
        disconnectButton.setOnAction(__ -> {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        startThreadOne.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(FLAG_START_ONE);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        startThreadTwo.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(FLAG_START_TWO);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        startThreadThree.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(FLAG_START_THREE);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        startThreadFour.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(FLAG_START_FOUR);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cancelThreadOne.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(FLAG_STOP_ONE);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cancelThreadTwo.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(FLAG_STOP_TWO);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cancelThreadThree.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(FLAG_STOP_THREE);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cancelThreadFour.setOnAction(__ -> {
            try {
                if (socket != null) {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeInt(FLAG_STOP_FOUR);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
//        submitButton.setOnAction(__ -> {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try{
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    while (socket.isConnected()) {
//                        if (reader.ready()) {
//                            switch (action_flag){
//                                case 1:
//                                    statusArea.appendText("\nСумма: " + reader.readLine());
//                                    break;
//                                case 3:
//                                    statusArea.appendText("\nПроизведение: " + reader.readLine());
//                                    break;
//                                case 7:
//                                    statusArea.appendText("\nСинус: " + reader.readLine());
//                                    break;
//                                case 15:
//                                    statusArea.appendText("\nТангенс: " + reader.readLine());
//                                    break;
//                            }
//                        }
//                    }
//                        }catch (IOException ex){
//                            System.out.println("IOException");
//                            ex.printStackTrace();
//                        }
//                    }
//
//            }).start();
//            try {
//                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
//
//                outputStream.writeInt(action_flag);
//
//                outputStream.writeDouble(Double.parseDouble(firstArea.getText()));
//                outputStream.writeDouble(Double.parseDouble(secondArea.getText()));
//                outputStream.flush();
//            }catch (IOException ex){
//                ex.printStackTrace();
//            }
//        });
    }

    private void setupScene() {
        clientLabel.setLayoutY(5);
        clientLabel.setLayoutX(10);
        clientLabel.setWrapText(true);

        statusArea.setLayoutX(10);
        statusArea.setLayoutY(25);
        statusArea.setPrefSize(175, 75);
        statusArea.setWrapText(true);

        connectButton.setLayoutX(400);
        connectButton.setLayoutY(20);
        connectButton.setWrapText(true);

        disconnectButton.setLayoutY(50);
        disconnectButton.setLayoutX(400);
        disconnectButton.setWrapText(true);

        statusAreaOfRec.setLayoutX(10);
        statusAreaOfRec.setLayoutY(100);
        statusAreaOfRec.setWrapText(true);

        greyAreaOfRec.setLayoutX(10);
        greyAreaOfRec.setLayoutY(220);
        greyAreaOfRec.setWrapText(true);


        GridPane gp = new GridPane();
        gp.setHgap(20);
        gp.setVgap(10);

        portArea.setWrapText(true);
        portArea.setMaxWidth(75);
        portArea.setMaxHeight(15);
        hostArea.setWrapText(true);
        hostArea.setMaxWidth(75);
        hostArea.setMaxHeight(15);

        gp.add(portLabel, 2, 0);
        gp.add(portArea, 3, 0);
        gp.add(hostLabel, 2, 1);
        gp.add(hostArea, 3, 1);

        gp.setPadding(new Insets(10, 0, 0, 225));

        ToggleGroup tg1 = new ToggleGroup();
        ToggleGroup tg2 = new ToggleGroup();
        ToggleGroup tg3 = new ToggleGroup();
        ToggleGroup tg4 = new ToggleGroup();
        startThreadOne.setToggleGroup(tg1);
        cancelThreadOne.setToggleGroup(tg1);
        startThreadTwo.setToggleGroup(tg2);
        cancelThreadTwo.setToggleGroup(tg2);
        startThreadThree.setToggleGroup(tg3);
        cancelThreadThree.setToggleGroup(tg3);
        startThreadFour.setToggleGroup(tg4);
        cancelThreadFour.setToggleGroup(tg4);

        GridPane gp2 = new GridPane();
        gp2.setHgap(20);
        gp2.setVgap(10);

        gp2.add(threadOneLabel, 0, 0);
        gp2.add(threadTwoLabel, 1, 0);
        gp2.add(threadThreeLabel, 2, 0);
        gp2.add(threadFourLabel, 3, 0);

        gp2.add(startThreadOne, 0, 1);
        gp2.add(cancelThreadOne, 0, 2);
        gp2.add(startThreadTwo, 1, 1);
        gp2.add(cancelThreadTwo, 1, 2);
        gp2.add(startThreadThree, 2, 1);
        gp2.add(cancelThreadThree, 2, 2);
        gp2.add(startThreadFour, 3, 1);
        gp2.add(cancelThreadFour, 3, 2);

        gp2.add(statusOfFirst, 0, 3);
        gp2.add(statusOfSecond, 1, 3);
        gp2.add(statusOfThird, 2, 3);
        gp2.add(statusOfFourth, 3, 3);

        gp2.setPadding(new Insets(100, 0, 0, 175));


        root_pane.getChildren().addAll(clientLabel, gp2, connectButton, disconnectButton, gp, greyAreaOfRec, statusAreaOfRec, statusArea);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
