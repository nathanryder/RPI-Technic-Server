package me.nathanryder.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    GPIOFunctions gpio = Main.gpio;

    public void startServer(int port) {
        ServerSocket server;
        Socket client;
        InputStream input;

        try {
            server = new ServerSocket(port);
            System.out.println("Listening on port " + port);
            while (true) {

                client = server.accept();
                input = client.getInputStream();
                String inputString = inputStreamAsString(input).trim();

                while (true) {
                    InputStreamReader reader = new InputStreamReader(input);
                    parseCommand(inputString);
                    if (!reader.ready())
                        break;
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static String inputStreamAsString(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        br.close();
        return sb.toString();
    }

    public void parseCommand(String cmd) {
        System.out.println("Received: " + cmd);
        if (cmd.equalsIgnoreCase("lights")) {
            gpio.blinkLights();
        } else if (cmd.equalsIgnoreCase("forward")) {
            gpio.forward();
        } else if (cmd.equalsIgnoreCase("back")) {
            gpio.back();
        } else if (cmd.equalsIgnoreCase("left")) {
            gpio.left();
        } else if (cmd.equalsIgnoreCase("right")) {
            gpio.right();
        } else if (cmd.equalsIgnoreCase("up")) {
            gpio.bucketUp();
        } else if (cmd.equalsIgnoreCase("down")) {
            gpio.bucketDown();
        } else if (cmd.equalsIgnoreCase("tilt up")) {
            gpio.tiltUp();
        } else if (cmd.equalsIgnoreCase("tilt down")) {
            gpio.tiltDown();
        }
    }

}
