package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(6001);


            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                OutputStream outputStream = clientSocket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                File file = new File("hazelcast-5.3.0.zip");
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                dataOutputStream.writeUTF(file.getName());
                long fileSize = file.length();
                dataOutputStream.writeLong(fileSize);

                byte[] buffer = new byte[16384];
                int bytesRead;

                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    System.out.println(bytesRead);
                }

                bufferedInputStream.close();
                outputStream.close();
                clientSocket.close();

                System.out.println("File sent successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}