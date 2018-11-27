package Rectangle;

import javafx.application.Platform;

public class RectangleThread {
    public static int THREAD_DELAY_NS = 50;

    public int ID = 0;
    public int maxHeight = 1;
    public boolean isRunning = false;
    public Thread thread = null;
    public ColorRectangle rectangle = null;
    public String text = "0px";

    RectangleThread(int id, ColorRectangle rectangle, int maxHeight) {
        this.ID = id;
        this.rectangle = rectangle;
        this.maxHeight = maxHeight;
        this.thread = this.build();
    }

    public void run() {
        this.thread.start();
    }

    private Thread build() {
        return new Thread(() -> {
            while (this.rectangle.getHeight() < this.maxHeight) {
                Platform.runLater(() -> this.incrementHeight());
                this.sleepTick();
            }
        });
    }

    private void sleepTick() {
        try { Thread.sleep(RectangleThread.THREAD_DELAY_NS); }
        catch (InterruptedException e){ e.printStackTrace(); }
    }


    private void incrementHeight() {
        this.text = this.rectangle.addToHeightAndGetSquare(1).toString() + "px";
    }

}
