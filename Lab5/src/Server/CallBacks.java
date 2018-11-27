package Server;

public interface CallBacks {
    public void updateLeftText(String s);
    public void updateRightText(String s);
    public void returnResult(int operation, double value);
    public void updateConnections(int value);
}
