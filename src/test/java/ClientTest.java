import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Client.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Client firstClient = new Client("Seymore Butts", "senior");
    Client secondClient = new Client("Seymore Butts", "senior");
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_savesClientToTheDatabase_true() {
    Client testClient = new Client("Amanda Hugginkiss", "adult");
    testClient.save();
    assertTrue(Client.all().get(0).equals(testClient));
  }

  @Test
  public void setStylistId_savesAStylistType() {
    Client testClient = new Client("Bea O''Problem", "adult");
    testClient.save();
    testClient.setStylistId(1);
    testClient.update();
    assertTrue(Client.all().get(0).equals(testClient));
  }

  @Test
  public void find_returnsCorrectClient_True() {
    Client testClient1 = new Client("Anita Bath", "child");
    Client testClient2 = new Client("Amanda Hugginkiss", "adult");
    Client testClient3 = new Client("Bea O''Problem", "adult");
    testClient1.save();
    testClient2.save();
    testClient3.save();
    Client savedClient = Client.find(testClient2.getId());
    assertTrue(testClient2.equals(savedClient));
  }
}
