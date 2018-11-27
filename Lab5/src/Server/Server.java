package Server;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.Socket;

//var 0(10) : +, *, sin, tan
public class Server implements Runnable {
    static int numberOfConnections = 0;
    private Socket socket;
    private CallBacks callBacks;

    @Override
    public void run() {
        boolean isSusccess = false;
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            int operation = 0;
            double firstOperand, secondOperand;

            callBacks.updateRightText(">>> Connected");
            numberOfConnections++;
            while(true) {
                Platform.runLater(() -> callBacks.updateConnections(Server.numberOfConnections));
                System.out.println((operation = dataInputStream.readInt()) + ", " + (firstOperand = dataInputStream.readDouble()) +
                        ", " + (secondOperand = dataInputStream.readDouble()));
//                callBacks.returnResult(operation, calculateResult(operation, firstOperand, secondOperand));
                double result = calculateResult(operation, firstOperand, secondOperand);

//                dataOutputStream.write(operation);
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
//                dataOutputStream.writeDouble(result);
//                dataOutputStream.flush();
//                byte[] arr = new byte[1024];
                pw.print(result +"\n");
                pw.flush();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
//var 0(10) : +, *, sin, tan

    private double calculateResult(int operation, double firstOperand, double secondOperand) {
        double result = 0;
        switch (operation){
            case 1:
                result = firstOperand + secondOperand;
                callBacks.updateLeftText("Операнды: \n" + firstOperand + "\n"+
                        secondOperand + "\nСумма: " + result);
                break;
            case 3:
                result = firstOperand * secondOperand;
                callBacks.updateLeftText("Операнды: \n" + firstOperand + "\n"+
                        secondOperand + "\nПроизведение: " + result);
                break;
            case 7:
                result = Math.sin(firstOperand);
                callBacks.updateLeftText("Операнды: \n" + firstOperand + "\n"+
                        secondOperand + "\nСинус: " + result);
                break;
            case 15:
                result = Math.tan(firstOperand);
                callBacks.updateLeftText("Операнды: \n" + firstOperand + "\n"+
                        secondOperand + "\nТангенс: " + result);
                break;
        }
        return result;
    }

    public Server(Socket socket, CallBacks callBacks) {
        this.socket = socket;
        this.callBacks = callBacks;
    }
}
