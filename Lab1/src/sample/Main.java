package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import javafx.event.ActionEvent;

import java.awt.*;

public class Main extends Application {

    //    final int THICKNESS_MAX = 50;
    Pane root_pane = new Pane();
    Group group = new Group();

    Slider slider;
    javafx.scene.control.Button btn_inc;
    javafx.scene.control.Button btn_dec;
    RadioButton rb_1;
    RadioButton rb_2;
    RadioButton rb_3;

    CheckBox cb_1;
    CheckBox cb_2;
    CheckBox cb_3;

    javafx.scene.control.TextArea textArea;
    DropShadow dropShadow;
    Text text;

    int counter = 0;
    int MAX_VAL = 50;

    private void Shadow(){
        dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(javafx.scene.paint.Color.GRAY);
    }

    private void CreateGraphNodes() {
        Shadow();
        group.setEffect(dropShadow);

        text = new Text();
        text.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));
        text.setFill(javafx.scene.paint.Color.BLUE);
    }

    private void CreateControlNodes() {
        ToggleGroup tg = new ToggleGroup();
        rb_1 = new RadioButton("RadioButton 1");
        rb_1.setToggleGroup(tg);

        rb_2 = new RadioButton("RadioButton 2");
        rb_2.setToggleGroup(tg);
        rb_2.setSelected(true);

        rb_3 = new RadioButton("RadioButton 3");
        rb_3.setToggleGroup(tg);

        cb_1 = new CheckBox("CheckBox 1");
        cb_2 = new CheckBox("CheckBox 2");
        cb_3 = new CheckBox("CheckBox 3");

        btn_inc = new javafx.scene.control.Button();
        btn_inc.setText("Увеличить");

        btn_inc.setLayoutX(120);
        btn_inc.setLayoutY(200);
        btn_inc.setTextFill(javafx.scene.paint.Color.BROWN);
        btn_inc.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        btn_dec = new javafx.scene.control.Button();
        btn_dec.setText("Уменьшить");
        btn_dec.setLayoutX(20);
        btn_dec.setLayoutY(200);
        btn_dec.setTextFill(javafx.scene.paint.Color.BROWN);
        btn_dec.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 12));

        textArea = new javafx.scene.control.TextArea();
        textArea.setLayoutX(290);
        textArea.setLayoutY(20);
        textArea.setPrefSize(200, 150);
        textArea.setText("Лабораторная работа № 1");
        textArea.setWrapText(true);

        slider = new Slider();
        slider.setLayoutX(290);
        slider.setLayoutY(200);
        slider.setPrefWidth(200);
        slider.setBlockIncrement(1.0);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(5);
        slider.setMax(MAX_VAL);
        slider.setMin(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(false);

        slider.setValue(counter);

        GridPane gp = new GridPane();
        gp.setHgap(50);
        gp.setVgap(10);
        gp.setPadding(new javafx.geometry.Insets(25, 0, 0, 20));
        gp.add(rb_1, 0, 0);
        gp.add(rb_2, 0, 1);
        gp.add(rb_3, 0, 2);

        gp.add(cb_1, 1, 0);
        gp.add(cb_2, 1, 1);
        gp.add(cb_3, 1, 2);

        gp.add(text, 0, 8);

        group.getChildren().addAll(gp, slider, btn_dec, btn_inc, textArea);
        root_pane.getChildren().addAll(group);

        toDisplay(counter);
    }

    public void toDisplay(double val){
        String s = Integer.toString(counter);
        text.setText("Счетчик: " + s);
        textArea.appendText("\nСчетчик: " + s);
        slider.setValue(counter);
    }

    private void onAction() {
        btn_inc.setOnAction((ActionEvent event) -> {
            if (counter < MAX_VAL) {
                counter += 1;
            }
            toDisplay(counter);
        });

        btn_dec.setOnAction((ActionEvent event) -> {
            if(counter > 1) {
                counter -= 1;
            }
            toDisplay(counter);
        });

        slider.valueProperty().addListener((observable -> {
            if(slider.isValueChanging()) {
                counter = (int) slider.getValue();

                toDisplay(counter);
            }
        }));

        cb_1.setOnAction((ActionEvent event) -> {
            if(cb_1.isSelected()){
                textArea.appendText("\nУстановлен CheckBox 1");
            } else {
                textArea.appendText("\n Снят CheckBox 1");
            }
        });

        cb_2.setOnAction((ActionEvent event) -> {
            if(cb_2.isSelected()){
                textArea.appendText("\nУстановлен CheckBox 2");
            }else{
                textArea.appendText("\nСнят CheckBox 2");
            }
        });

        cb_3.setOnAction((ActionEvent event) -> {
            if(cb_3.isSelected()){
                textArea.appendText("\nУстановлен CheckBox 3");
            }else {
                textArea.appendText("\nСнят CheckBox 3");
            }
        });

        rb_1.setOnAction((ActionEvent t) -> {
            textArea.appendText("\nУстановлен RadioButton 1");
        });
        rb_2.setOnAction((ActionEvent t) -> {
            textArea.appendText("\nУстановлен RadioButton 2");
        });
        rb_3.setOnAction((ActionEvent t) -> {
            textArea.appendText("\nУстановлен RadioButton 3");
        });
    }
    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Лабораторна робота #2 | Колесник С.Ю. | ТП-413");
        stage.setResizable(false);
        CreateGraphNodes();
        CreateControlNodes();
        onAction();
        javafx.scene.Scene scene = new javafx.scene.Scene(root_pane, 500, 250, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
