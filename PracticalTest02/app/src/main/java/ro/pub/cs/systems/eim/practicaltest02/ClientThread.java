package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread extends Thread {
    private String address;
    private int port;
    private String hour;
    private String minute;

    private Socket socket;

    public ClientThread(String address, int port, String message) {
        this.address = address;
        this.port = port;
        if(message.contains("set")) {
            String[] arrOfString = message.split(",");
            this.hour = arrOfString[1];
            this.minute = arrOfString[2];

            Log.e(Constants.TAG, "[CLIENT THREAD] " + this.hour);
            Log.e(Constants.TAG, "[CLIENT THREAD] " + this.minute);
        }
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            printWriter.println(address);
            printWriter.flush();

            printWriter.println(hour);
            printWriter.flush();

            printWriter.println(minute);
            printWriter.flush();

            String timerInformation;
            while ((timerInformation = bufferedReader.readLine()) != null) {
                final String finalizedtimeInformation = timerInformation;

                /*weatherForecastTextView.post(new Runnable() {
                    @Override
                    public void run() {

                        weatherForecastTextView.setText(finalizedWeateherInformation);
                    }
                });*/
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }

    }
}