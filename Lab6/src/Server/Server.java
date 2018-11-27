package Server;

import Client.MyClient;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.Socket;


//var 0(10) : +, *, sin, tan
public class Server implements Runnable {
    Thread thread1 = new Thread(){
        @Override
        public void run() {
            while (running1) {
//                    System.out.println("Test");
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            callBacks.pulseRectangle(1);
                            double res = callBacks.tickPixels(1);
                            double total = callBacks.updateTotal();
                            pw.println("1,"+ res+","+total);
                            pw.flush();
                            }
                    });
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
//                    try {
//                        Thread.sleep(50);
//                        pulseRectangle();
//                        calculatePixels();
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
            }
        }

    };
    Thread thread2 = new Thread(){
        @Override
        public void run() {
            while (running2) {
//                    System.out.println("Test");
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            callBacks.pulseRectangle(2);
                            double res = callBacks.tickPixels(2);
                            double total = callBacks.updateTotal();
                            pw.println("2,"+ res+","+total);
                            pw.flush();
                        }
                    });
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
//                    try {
//                        Thread.sleep(50);
//                        pulseRectangle();
//                        calculatePixels();
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
            }
        }

    };
    Thread thread3 = new Thread(){
        @Override
        public void run() {
            while (running3) {
//                    System.out.println("Test");
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            callBacks.pulseRectangle(3);
                            double res = callBacks.tickPixels(3);
                            double total = callBacks.updateTotal();
                            pw.println("3,"+ res+","+total);
                            pw.flush();
                        }
                    });
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
//                    try {
//                        Thread.sleep(50);
//                        pulseRectangle();
//                        calculatePixels();
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
            }
        }

    };

    Thread thread4 = new Thread() {
        @Override
        public void run() {
            while (running4) {
//                    System.out.println("Test");
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            callBacks.pulseRectangle(4);
                            double res = callBacks.tickPixels(4);
                            double total = callBacks.updateTotal();
                            pw.println("4,"+ res+","+total);
                            pw.flush();
                        }
                    });
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                    try {
//                        Thread.sleep(50);
//                        pulseRectangle();
//                        calculatePixels();
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
            }
        }
    };
        private boolean running1 = true;
        private boolean running4 = true;
        private boolean running2 = true;
        private boolean running3 = true;
    static int numberOfConnections = 0;
    private Socket socket;
    private CallBacks callBacks;
    PrintWriter pw = null;

    @Override
    public void run() {
        boolean isSusccess = false;
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            int action = 0;
            double firstOperand, secondOperand;
            callBacks.updateRightText(">>> Connected");
            numberOfConnections++;
            pw = new PrintWriter(socket.getOutputStream(), true);
            while(true) {
                Platform.runLater(() -> callBacks.updateConnections(Server.numberOfConnections));
                System.out.println((action = dataInputStream.readInt()));

                switch (action){
                    case MyClient.FLAG_START_ONE:
                        thread1.start();
                        break;
                        case MyClient.FLAG_STOP_ONE:
                            running1 = false;
                            break;
                            case MyClient.FLAG_START_TWO:
                                thread2.start();
                                break;
                                case MyClient.FLAG_STOP_TWO:
                                    running2 = false;
                                    break;
                                    case MyClient.FLAG_START_THREE:
                                        thread3.start();
                                        break;
                                        case MyClient.FLAG_STOP_THREE:
                                            running3 = false;
                                            break;
                                            case MyClient.FLAG_START_FOUR:
                                                thread4.start();
                                                break;
                                                case MyClient.FLAG_STOP_FOUR:
                                                    running4 = false;
                                                    break;
                }
//                callBacks.returnResult(operation, calculateResult(operation, firstOperand, secondOperand));
//                double result = calculateResult(action, firstOperand, secondOperand);

//                dataOutputStream.write(operation);
//                dataOutputStream.writeDouble(result);
//                dataOutputStream.flush();
//                byte[] arr = new byte[1024];
//                pw.print(action);
//                pw.print(result +"\n");
                pw.flush();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
//var 0(10) : +, *, sin, tan

    private double calculateResult(int operation, double firstOperand, double secondOperand) {
        double result = 0;
//        switch (operation){
//            case 1:
//                result = firstOperand + secondOperand;
//                callBacks.updateLeftText("Операнды: \n" + firstOperand + "\n"+
//                        secondOperand + "\nСумма: " + result);
//                break;
//            case 3:
//                result = firstOperand * secondOperand;
//                callBacks.updateLeftText("Операнды: \n" + firstOperand + "\n"+
//                        secondOperand + "\nПроизведение: " + result);
//                break;
//            case 7:
//                result = Math.sin(firstOperand);
//                callBacks.updateLeftText("Операнды: \n" + firstOperand + "\n"+
//                        secondOperand + "\nСинус: " + result);
//                break;
//            case 15:
//                result = Math.tan(firstOperand);
//                callBacks.updateLeftText("Операнды: \n" + firstOperand + "\n"+
//                        secondOperand + "\nТангенс: " + result);
//                break;
//        }
        return result;
    }
    public Server(Socket socket, CallBacks callBacks) {
        this.socket = socket;
        this.callBacks = callBacks;
    }
}
