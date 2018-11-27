package Rectangle;

import javafx.scene.paint.Color;

import java.util.*;
import java.util.function.Consumer;

public class RectangleThreadPool {
    public static int MARGIN_LEFT = 20;
    public static int MARGIN_TOP = 20;
    public static int RECTANGLE_WIDTH = 100;
    public static int SPACE = 80;

    private int _count = 0;

    private List<RectangleThread> _threads;
    private List<ColorRectangle> _rectangles;

    RectangleThreadPool(int threadCount, int maxHeight, Color color) {
        this._count = threadCount;
        for (int i = 0; i < this._count; ++i) {
            this._rectangles.set(i, new ColorRectangle(new Position(RectangleThreadPool.MARGIN_LEFT + i * RectangleThreadPool.SPACE, RectangleThreadPool.MARGIN_TOP, 1, RectangleThreadPool.RECTANGLE_WIDTH ), color));
            this._threads.set(i, new RectangleThread(i, this._rectangles.get(i), maxHeight));
        }
    }

    public Double getTotalSquare() {
        return this._rectangles
                .stream()
                .map(rect -> rect.getHeight() * rect.getWidth())
                .reduce((xSquare, ySquare) -> xSquare + ySquare)
                .orElse(0.0);
    }

    public RectangleThread getThread(int threadId) { return this.getThreadById(threadId).get(); }
    public void getThread(int threadId, Consumer<? super RectangleThread> consumer) { this.getThreadById(threadId).ifPresent(consumer); }

    public void getRectangleAt(int index, Consumer<? super ColorRectangle> consumer) { this.getThreadById(index).ifPresent(t -> consumer.accept(t.rectangle)); }
    public ColorRectangle getRectangleAt(int index) { return this.getThreadById(index).map(t -> t.rectangle).get(); }

    private Optional<RectangleThread> getThreadById(int threadId) { return this._threads.stream().filter(t -> t.ID == threadId).findFirst(); }

}
