package custom.tcp.protocol.client;

import custom.tcp.protocol.app.Server;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ClientTest {
  private Server server;
  private Client client;

//  public static void main(String[] args)  {
//    sendRequest("PUT abc_123");
//    sendRequest("PUT xyz_456");
//    sendRequest("GET xyz");
//    sendRequest("GET pqr");
//    sendRequest("GET abc");
//    sendRequest( "DELETE abc");
//    sendRequest("GET abc");
//    sendRequest("ZFHDF AHD");
//  }

  @BeforeEach
  public void setUp() throws IOException {
    server = new Server(1234);
    server.start();
    client = new Client("localhost", 1234);
  }

  @AfterEach
  public void tearDown() throws IOException, InterruptedException {
    client.closeServer();
    server.shutdown();
  }

  @Test
  public void testPutAndGet() {
    String key = "abc";
    String valuePutInCache = "123";
    client.put(key, valuePutInCache);

    String valueReceivedFromGet = client.get(key);

    Assertions.assertEquals(valuePutInCache, valueReceivedFromGet);
  }

  @Test
  public void testPutDeleteAndGet() {
    String key = "abc";
    String valuePutInCache = "123";
    client.put(key, valuePutInCache);
    client.delete(key);

    String valueReceivedFromGet = client.get(key);

    Assertions.assertEquals("null", valueReceivedFromGet);
  }
}
