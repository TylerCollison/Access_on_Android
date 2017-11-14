package com.access.accessonandroid.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Tyler Collison on 11/12/2017.
 */

public class NetworkAdapter implements INetworkAdapter {

    private String address;

    public NetworkAdapter(String serverAddress) {
        address = serverAddress;
    }

    @Override
    public void postToServer(INetworkOperation request,
                             INetworkCallback responseCallback) {
        new AsyncClient().execute(address, request, responseCallback);
    }

    private static void post(String serverAddress, INetworkOperation request,
                        INetworkCallback responseCallback) throws IOException {
        //Establish the connection with the server
        URL url = new URL(serverAddress);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        //Send the authentication request
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(request.toJsonString());
        outputStream.flush();
        outputStream.close();

        connection.getResponseCode();

        try {
            //Get the response input stream
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            //Form the response into a JSON string
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            //Form the JSON string response into a status response object
            responseCallback.execute(builder.toString());
        } finally {
            //Disconnect from the server
            connection.disconnect();
        }
    }

    private static class AsyncClient extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object[] objects) {
            //Get the parameters for the server and conversation
            String serverAddress = objects[0].toString();
            INetworkOperation request = (INetworkOperation) objects[1];
            INetworkCallback responseCallback = (INetworkCallback) objects[2];
            //Send authenticate the conversation
            try {
                post(serverAddress, request, responseCallback);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
