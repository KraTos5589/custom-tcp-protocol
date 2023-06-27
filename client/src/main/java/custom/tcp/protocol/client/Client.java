package custom.tcp.protocol.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
  private final String hostname;// = "localhost";
  private final int port;// = 1234;

  public Client(String hostname, int port) {
    this.hostname = hostname;
    this.port = port;
  }

  public String put(String key, String value) {
    return sendRequest("PUT " + key + "_" + value);
  }

  public String get(String key) {
    return sendRequest("GET " + key);
  }

  public String delete(String key) {
    return sendRequest("DELETE "+key);
  }

  public String closeServer() {
    return sendRequest("CLOSE");
  }

  private String sendRequest(String request) {
    try (Socket socket = new Socket(hostname, port)) {
      OutputStream output = socket.getOutputStream();
      PrintWriter writer = new PrintWriter(output, true);
      writer.println(request);
      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      String response = reader.readLine();
      System.out.printf("Request - <%s> Response - <%s>\n", request, response);
      return response;
    } catch (UnknownHostException ex) {
      System.out.println("Server not found: " + ex.getMessage());
      return "ERROR";
    } catch (IOException ex) {
      System.out.println("I/O error: " + ex.getMessage());
      return "ERROR";
    }
  }
}
