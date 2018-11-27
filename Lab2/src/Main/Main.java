package Main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

/*
Вариант 10 = 0 : maxHeight = 110px, RECTANGLE_WIDTH = 20px, RECTANGLE_COLOR = red
 */
public class Main extends Application {

    public final static int DELAY = 50;

    private final Pane MAIN_WINDOW = new Pane();
    private final Label WIN_LABEL_TOTAL = this.createLabel(350, 160,"Total: 0px");
    private final int MARGIN_TOP = 50;

    private final int RECTANGLE_WIDTH = 20;
    private final int RECTANGLE_MAX_HEIGHT = 200;
    private final Color RECTANGLE_COLOR = Color.DARKGRAY;

    public final Rectangle[] WIN_RECTANGLES = {
        this.createRectangle(10, MARGIN_TOP, 1, RECTANGLE_WIDTH, RECTANGLE_COLOR),
        this.createRectangle(40, MARGIN_TOP, 1, RECTANGLE_WIDTH, RECTANGLE_COLOR),
        this.createRectangle(70, MARGIN_TOP, 1, RECTANGLE_WIDTH, RECTANGLE_COLOR),
        this.createRectangle(100, MARGIN_TOP, 1, RECTANGLE_WIDTH, RECTANGLE_COLOR)
    };
    public final Label[] WIN_LABELS = {
            this.createLabel(10, 20, "0px"),
            this.createLabel(60, 20, "0px"),
            this.createLabel(110, 20, "0px"),
            this.createLabel(160, 20, "0px"),
    };

    public final boolean[] THREAD_FLAGS = {true, true, true, true};

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        this.createMainWindow(stage);
        this.createWorkerThreads();
    }

    private void createWorkerThreads() {
        for (int i = 0; i < this.WIN_RECTANGLES.length; ++i) {
            Thread thread = createThread(i);
            thread.start();
        }
        this.createTotalThread();
    }

    private void createTotalThread() {
        Thread calculateTotalSquaresThread = new Thread(() -> {
            boolean isAtLeastOneThreadActive = true;
            while(isAtLeastOneThreadActive){
                try {
                    Platform.runLater(() -> ((List<Rectangle>) Arrays.asList(WIN_RECTANGLES))
                            .stream()
                            .map(rect -> rect.getHeight()*rect.getWidth())
                            .reduce((xSquare, ySquare) -> xSquare + ySquare)
                            .ifPresent(sumOfSquares -> WIN_LABEL_TOTAL.setText("Total: " + sumOfSquares+ "px")));
                    Thread.sleep(Main.DELAY );
                    for (boolean tf: THREAD_FLAGS) isAtLeastOneThreadActive = isAtLeastOneThreadActive && tf;
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        calculateTotalSquaresThread.start();
    }

    private Thread createThread(int i) {
        return new Thread(() -> {
            while (this.THREAD_FLAGS[i]) {
                try {
                    Platform.runLater(() -> enlargeRectangleHeightByThread(i));
                    Thread.sleep(Main.DELAY);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void enlargeRectangleHeightByThread(int threadIdx){
        if(this.THREAD_FLAGS[threadIdx]){
            this.WIN_RECTANGLES[threadIdx].setHeight(this.WIN_RECTANGLES[threadIdx].getHeight() + 1);
            this.WIN_LABELS[threadIdx].setText(this.WIN_RECTANGLES[threadIdx].getHeight() * this.WIN_RECTANGLES[threadIdx].getWidth() + "px");
        }
        this.THREAD_FLAGS[threadIdx] = this.WIN_RECTANGLES[threadIdx].getHeight() < RECTANGLE_MAX_HEIGHT;
    }

    private void createMainWindow(Stage stage) {
        final ObservableList<Node> windowChildren = MAIN_WINDOW.getChildren();
        windowChildren.addAll(this.WIN_LABELS);
        windowChildren.addAll(this.WIN_RECTANGLES);
        windowChildren.addAll(WIN_LABEL_TOTAL);

        Scene scene = new Scene(MAIN_WINDOW, 500, 300, Color.TRANSPARENT);

        stage.setTitle("Лабораторна робота #2 | Колесник С.Ю. | ТП-413");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    private Rectangle createRectangle(double startX, double startY, double height, double width, Color color){
        Rectangle r = new Rectangle(startX, startY, width, height);
        r.setFill(color);
        return r;
    }
    private Label createLabel(int x, int y, String text) {
        Label newLabel = new Label();
        newLabel.setLayoutY(y);
        newLabel.setLayoutX(x);
        newLabel.setWrapText(true);
        newLabel.setPrefSize(200, 20);
        newLabel.setText(text);
        return newLabel;
    }
}
