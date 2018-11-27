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
import javafx.scene.control.CheckBox;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
//99999999 find Max
public class Main extends Application {

    private int numberOfCores = Runtime.getRuntime().availableProcessors();

    private Label arraySize = new Label("Розмір масиву");
    private Label threadsNumberText = new Label("Кіл-ть потоків");
    private Button calculate = new Button("Обчислити");
    private Pane root_pane = new Pane();
    private CheckBox[] coreBoxes = {new CheckBox("Core1"),
            new CheckBox("Core2"),
            new CheckBox("Core3"),
            new CheckBox("Core4"),
            new CheckBox("Core5"),
            new CheckBox("Core6"),
            new CheckBox("Core7"),
            new CheckBox("Core8")};

    private TextArea textArea = new TextArea();
    private TextArea arraySizeInput = new TextArea();
    private TextArea threadsNumberInput = new TextArea();
    private int numberOfActiveCores = 0;

    private void setupScene() {
        GridPane gp = new GridPane();
        gp.setHgap(30);
        gp.setVgap(10);
        gp.setPadding(new javafx.geometry.Insets(10,0,0, 10));
        gp.add(coreBoxes[0], 0,0);
        gp.add(coreBoxes[1], 0,1);
        gp.add(coreBoxes[2],1,0);
        gp.add(coreBoxes[3],1,1);
        gp.add(coreBoxes[4],2,0);
        gp.add(coreBoxes[5],2,1);
        gp.add(coreBoxes[6],3,0);
        gp.add(coreBoxes[7],3,1);

        arraySizeInput.setPrefSize(50,6);
        arraySizeInput.setText("10");
        arraySizeInput.setWrapText(true);

        threadsNumberInput.setPrefSize(50,6);
        threadsNumberInput.setText("10");
        threadsNumberInput.setWrapText(true);

        gp.add(arraySize,0,2);
        gp.add(threadsNumberText,0,3);
        gp.add(threadsNumberInput,1,3);
        gp.add(arraySizeInput,1,2);
        gp.add(calculate,2,2);


        for(int i = numberOfCores; i < coreBoxes.length; i++){
            coreBoxes[i].setDisable(true);
        }

        textArea.setLayoutX(300);
        textArea.setLayoutY(80);
        textArea.setPrefSize(200, 150);
        textArea.setText("Лабораторна робота #4");
        textArea.setWrapText(true);

        root_pane.getChildren().addAll(gp, textArea);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setupScene();
        setupAction();
        primaryStage.setTitle("Лабораторна робота #4 | Колесник С.Ю. | ТП-413");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root_pane, 600, 250, Color.TRANSPARENT));
        primaryStage.show();
    }

    private void setupAction() {
        calculate.setOnAction(__ -> {
            Integer arrSize = Integer.parseInt(arraySizeInput.getText());
            Integer numberOfThreads = Integer.parseInt(threadsNumberInput.getText()) * numberOfActiveCores;
            int[] array = new int[arrSize];

            Random random = new Random();
            for(int i = 0; i < array.length; i++){
                array[i] = 141098;
                //random.nextInt(array.length);
            }
            Date currentTime = new Date();
            textArea.appendText("\nКількість потоків: " + numberOfThreads +
                    "\nРезультат: " + calculateResult(array, new ForkJoinPool(numberOfThreads)));
            Date newTime = new Date();
            long msDelay = newTime.getTime() - currentTime.getTime();
            textArea.appendText("\nЧас виконання: "+msDelay + "ms");
        });
        for (CheckBox box:coreBoxes) {
            box.setOnAction(__ -> {
                if(box.isSelected())
                    numberOfActiveCores++;
                else
                    numberOfActiveCores--;
            });
        }
    }

    private Double calculateResult(int[] numbers, ForkJoinPool jp){
        return jp.invoke(new Max(numbers,0,numbers.length));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
class Max extends RecursiveTask<Double> {
    int front;
    int rear;
    int[] array;

    Max(int[] array, int front, int rear) {
        this.array = array;
        this.front = front;
        this.rear = rear;
    }

    protected Double compute() {

        if(rear - front <= 10) {
//            double sum = 0;

            double max = array[front];
            for(int i = front; i < rear; ++i)
                if(max < array[i])
                    max = array[i];
//                sum += array[i];
            return max;
        } else {
            int mid = front + (rear - front) / 2;
            Max left  = new Max(array, front, mid);
            Max right = new Max(array, mid, rear);
            left.fork();
            double rightResult = right.compute();
            double leftResult  = left.join();
            if(leftResult > rightResult)
                return leftResult;
            else
                return rightResult;
//            return leftResult + rightResult;
        }
    }
}