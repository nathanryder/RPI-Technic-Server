package me.nathanryder.server;

import com.pi4j.io.gpio.*;

import java.util.HashMap;


public class GPIOFunctions {

    private static GpioController gpio;
    private static HashMap<String, Integer> status = new HashMap<>();

    private static GpioPinDigitalOutput backPin;
    private static GpioPinDigitalOutput forwardPin;
    private static GpioPinDigitalOutput light1;
    private static GpioPinDigitalOutput light2;
    private static GpioPinDigitalOutput left;
    private static GpioPinDigitalOutput right;
    private static GpioPinDigitalOutput up;
    private static GpioPinDigitalOutput down;
    private static GpioPinDigitalOutput tDown;
    private static GpioPinDigitalOutput tUp;

    private static boolean blinking = false;


    public void setupGPIO() {
        gpio = GpioFactory.getInstance();

        backPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11);
        forwardPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10);
        light1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25);
        light2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27);
        left = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
        right = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05);
        up = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_12);
        down = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13);
        tDown = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
        tUp = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);


        backPin.setShutdownOptions(true, PinState.LOW);
        forwardPin.setShutdownOptions(true, PinState.LOW);
        light1.setShutdownOptions(true, PinState.LOW);
        light2.setShutdownOptions(true, PinState.LOW);
        left.setShutdownOptions(true, PinState.LOW);
        right.setShutdownOptions(true, PinState.LOW);
        up.setShutdownOptions(true, PinState.LOW);
        down.setShutdownOptions(true, PinState.LOW);
        tDown.setShutdownOptions(true, PinState.LOW);
        tUp.setShutdownOptions(true, PinState.LOW);

        status.put("lights", 0);
        status.put("forward", 0);
        status.put("back", 0);
        status.put("left", 0);
        status.put("right", 0);
        status.put("up", 0);
        status.put("down", 0);
        status.put("tup", 0);
        status.put("tdown", 0);
    }

    public void cleanup() {
        gpio.shutdown();
        gpio.unprovisionPin(backPin);
        gpio.unprovisionPin(forwardPin);
        gpio.unprovisionPin(light1);
        gpio.unprovisionPin(light2);
        gpio.unprovisionPin(left);
        gpio.unprovisionPin(right);
        gpio.unprovisionPin(up);
        gpio.unprovisionPin(down);
        gpio.unprovisionPin(tUp);
        gpio.unprovisionPin(tDown);
    }

    public void blinkLights() {
        if (status.get("lights") == 0) {
            blinking = true;
            status.put("lights", 1);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        if (!blinking) {
                            light1.low();
                            light2.low();
                            break;
                        }
                        light1.high();
                        light2.low();
                        try {
                            Thread.sleep(150);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        light1.low();
                        light2.high();
                        try {
                            Thread.sleep(150);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.start();
            if (!blinking) t.stop();
        } else {
            blinking = false;
            status.put("lights", 0);
        }

    }

    public void left() {
        if (status.get("left") == 0) {
            left.high();
            status.put("left", 1);
        } else {
            left.low();
            status.put("left", 0);
        }
    }

    public void right() {
        if (status.get("right") == 0) {
            right.high();
            status.put("right", 1);
        } else {
            right.low();
            status.put("right", 0);
        }
    }

    public void forward() {
        if (status.get("forward") == 0) {
            forwardPin.high();
            status.put("forward", 1);
        } else {
            forwardPin.low();
            status.put("forward", 0);
        }
    }

    public void back() {
        if (status.get("back") == 0) {
            backPin.high();
            status.put("back", 1);
        } else {
            backPin.low();
            status.put("back", 0);
        }
    }

    //Bucket functions
    public void bucketUp() {
        if (status.get("up") == 0) {
            up.high();
            status.put("up", 1);
        } else {
            up.low();
            status.put("up", 0);
        }
    }

    public void bucketDown() {
        if (status.get("down") == 0) {
            down.high();
            status.put("down", 1);
        } else {
            down.low();
            status.put("down", 0);
        }
    }

    public void tiltUp() {
        if (status.get("tup") == 0) {
            tUp.high();
            status.put("tup", 1);
        } else {
            tUp.low();
            status.put("tup", 0);
        }
    }

    public void tiltDown() {
        if (status.get("tdown") == 0) {
            tDown.high();
            status.put("tdown", 1);
        } else {
            tDown.low();
            status.put("tdown", 0);
        }
    }
}
