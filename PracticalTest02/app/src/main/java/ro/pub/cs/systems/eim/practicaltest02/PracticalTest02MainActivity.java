package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity {

    // Server
    private EditText serverPortEditText = null;
    private Button connectButton = null;

    // Client
    private EditText clientAddressEditText = null;
    private EditText clientPortEditText = null;
    private EditText hourEditText = null;
    private EditText minuteEditText = null;
    private Button setButton = null;
    private Button resetButton = null;
    private Button pollButton = null;
    private TextView resultTextView = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!",
                Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
            Log.i(Constants.TAG, "[MAIN ACTIVITY] Server thread starts on port " + serverPort +"!");
        }
    }

    private SetTimerButtonClickListener setTimerButtonClickListener = new SetTimerButtonClickListener();
    private class SetTimerButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.e(Constants.TAG, "[CLIENT THREAD] intru");
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty() || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }

            String hour = hourEditText.getText().toString();
            String minute = minuteEditText.getText().toString();

            if(hour == null || hour.isEmpty() || minute == null || minute.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (hout / minute) should be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            resultTextView.setText("Timer created");

            if(v.getId() == R.id.set_button) {
                String message = "set," + hour + "," + minute + "\n";

                clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), message);
                //clientThread.start();
            }

            if(v.getId() == R.id.reset_button) {
                String message = "reset\n";

                clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), message);
                //clientThread.start();
            }

            if(v.getId() == R.id.poll_button) {
                String message = "poll\n";

                clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), message);
                //clientThread.start();
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        Log.i(Constants.TAG, "[MAIN ACTIVITY] onCreate() callback method has been invoked");

        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);

        connectButton = (Button)findViewById(R.id.connect_button);
        connectButton.setOnClickListener(connectButtonClickListener);

        clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        hourEditText = (EditText)findViewById(R.id.hour_edit_text);
        minuteEditText = (EditText)findViewById(R.id.minute_edit_text);

        setButton = (Button)findViewById(R.id.set_button);
        setButton.setOnClickListener(setTimerButtonClickListener);

        resetButton = (Button)findViewById(R.id.reset_button);
        pollButton = (Button)findViewById(R.id.poll_button);
    }
}