package com.example.get_post_example;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

public class HttpGet {

    public String get(String getUrl) throws IOException {

        URL url = new URL(getUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        return this.read(con.getInputStream());
    }

    private String read(InputStream is) throws IOException{

        BufferedReader in = null;
        String inputLine;
        StringBuilder body;

        try {

            in = new BufferedReader(new InputStreamReader(is));
            body = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                body.append(inputLine);
            }

            in.close();

            return body.toString();
        } catch(IOException ioe) {
            throw ioe;
        } finally {
            this.closeQueitely(in);
        }
    }

    protected void closeQueitely(Closeable closeable) {

        try {
            if (closeable != null) {closeable.close();}
        } catch (IOException ex) { ex.printStackTrace();}

    }
}
