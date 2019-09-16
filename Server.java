/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmicalculatorcli;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author angge
 */
public class Server {

    /**
     * Runs the server. When a client connects, the server spawns a new thread to do
     * the servicing and immediately returns to listening. The application limits the
     * number of threads via a thread pool (otherwise millions of clients could cause
     * the server to run out of resources by allocating too many threads).
     */
    public static void main(String[] args) throws Exception {
        try (ServerSocket listener = new ServerSocket(59898)) {
            System.out.println("The server is running...");
            ExecutorService pool = Executors.newFixedThreadPool(20);

            while (true) {
                pool.execute(new ServerThread(listener.accept()));
            }
        }
    }

    private static class ServerThread implements Runnable {
        private Socket socket;

        ServerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                
                out.println("Welcome to the server");
                out.print("Name\t: ");
                out.flush();
                String name = Server.recvAll(in);
                out.print("Weight\t: ");
                out.flush();
                Float weight = Float.valueOf(Server.recvAll(in));
                out.print("Height\t: ");
                out.flush();
                Float height = Float.valueOf(Server.recvAll(in));
                
                out.println("Your BMI is : "+ String.valueOf(weight / (height*height)));
                out.flush();    
                
            } catch (Exception e) {
                System.out.println("Error:" + socket);
            } finally {
                try { socket.close(); } catch (IOException e) {}
                System.out.println("Closed: " + socket);
            }
        }
    }
    
    public static String recvAll(Scanner in){
        String ret = "";
        try {
            ret += in.nextLine();
        } catch(Exception e){
            
        }
        return ret;
    }
}