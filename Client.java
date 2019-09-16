/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmicalculatorcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        
        try (Socket socket = new Socket(args[0], 59898)) {
            Scanner scanner = new Scanner(System.in);
            BufferedReader s_in = null;
            s_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            
            while(true){
                try{
                    System.out.print(Client.recvAll(s_in));
                    out.println(scanner.nextLine());
                } catch(Exception e){
                    e.printStackTrace();
                }
                
            }
            
        }
    }
    
    public static char[] recvAll(BufferedReader in) throws IOException{
        char[] response;
        response = new char[1024];

        in.read(response);
        return response;
    }
}
