package me.nathanryder.server;

import java.util.Scanner;

public class Main {

    static GPIOFunctions gpio = new GPIOFunctions();
    static Server serv = new Server();

    public static void main(String[] args) {
        gpio.setupGPIO();

        new Thread(new Runnable() {
            @Override
            public void run() {
                serv.startServer(8081);
            }
        }).start();

        debugControls();
        gpio.cleanup();
    }

    public static void debugControls() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enabled debug controls!");
        char cmd = ' ';
        while (cmd != 'x') {
            cmd = readCommand(sc);
            switch (cmd) {
                case '8':
                    gpio.forward();
                    break;
                case '2':
                    gpio.back();
                    break;
                case 'l':
                    gpio.blinkLights();
                    break;
                case '9':
                    gpio.forward();
                    break;
                case '1':
                    gpio.back();
                    break;
                case 'k':
                    gpio.blinkLights();
                    break;
                case '4':
                    gpio.left();
                    break;
                case '6':
                    gpio.right();
                    break;
                case '7':
                    gpio.left();
                    break;
                case '3':
                    gpio.right();
                    break;
                case 'w':
                    gpio.bucketUp();
                    break;
                case 'q':
                    gpio.bucketUp();
                    break;
                case 'e':
                    gpio.bucketDown();
                    break;
                case 'r':
                    gpio.bucketDown();
                    break;
                case 's':
                    gpio.tiltUp();
                    break;
                case 'a':
                    gpio.tiltUp();
                    break;
                case 'd':
                    gpio.tiltDown();
                    break;
                case 'f':
                    gpio.tiltDown();
                    break;
                case 'x':
                    System.out.println("Shutting down..");
                    break;
                default:
                    System.out.println("Unknown command!");
                    break;
            }
        }
    }


    public static char readCommand(Scanner sc) {
        char result = ' ';
        String in = sc.nextLine();
        if (!in.trim().isEmpty()) {
            result = in.trim().toLowerCase().charAt(0);
        }
        return result;
    }

}
