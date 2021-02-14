import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private static class sReadMsg extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = in.readLine();
                    if (str.equals("stop")) {
                        clientSocket.close();
                        break;
                    }
                    System.out.println(str);
                }
            } catch (IOException e) {
            }
        }
    }


    public static class sWriteMsg extends Thread {

        @Override
        public void run() {
            BufferedReader outMessage = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String userWord;
                try {
                    userWord = outMessage.readLine();
                    if (userWord.equals("stop")) {
                        out.write("stop" + "\n");
                        clientSocket.close();
                        break;
                    } else {
                        out.write(userWord + "\n");
                    }
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();

                }

            }
        }
    }

    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) throws IOException {

        server = new ServerSocket(8081);
        System.out.println("Сервер запущен!");
        clientSocket = server.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        new sReadMsg().start();
        new sWriteMsg().start();

    }
}
