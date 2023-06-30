package org.example;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {

            Socket clientSocket = new Socket("localhost", 6001);

            InputStream inputStream = clientSocket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            String fileName = dataInputStream.readUTF();
            long fileSize = dataInputStream.readLong();

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));

            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytesRead = 0;

            while (totalBytesRead < fileSize && (bytesRead = inputStream.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalBytesRead))) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                System.out.println(totalBytesRead);
            }

            bufferedOutputStream.close();
            inputStream.close();
            clientSocket.close();

            System.out.println("File received successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}