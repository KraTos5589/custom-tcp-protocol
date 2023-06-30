package custom.tcp.protocol.client;

import custom.tcp.protocol.app.Server;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ClientTest {
  public static final String NULL = "null";
  private Server server;
  private Client client;

  @BeforeEach
  public void setUp() throws IOException {
    System.out.println("--------------Test Started--------------");
    server = new Server(1234);
    server.start();
    client = new Client("localhost", 1234);
  }

  @AfterEach
  public void tearDown() throws IOException, InterruptedException {
    client.closeServer();
    server.shutdown();
    System.out.println("--------------Test Finished--------------");
    System.out.println();
  }

  @Test
  public void testEmptyGet() {
    String key = "abc";

    String valueReceivedFromGet = client.get(key);

    Assertions.assertEquals(NULL, valueReceivedFromGet);
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

    Assertions.assertEquals(NULL, valueReceivedFromGet);
  }
}
