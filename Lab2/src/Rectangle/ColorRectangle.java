package Rectangle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class ColorRectangle extends Rectangle {
    ColorRectangle(Position position, Color color) {
        super(position.x, position.y, position.w, position.h);
        this.setFill(color);
    }

    public Double addToHeightAndGetSquare(int add) {
        this.setHeight(this.getHeight() + add);
        return this.getSquare();
    }

    public Double getSquare() {
        return this.getWidth() * this.getHeight();
    }
}
