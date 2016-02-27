import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Stylist.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame_true() {
    Stylist firstStylist = new Stylist("Raymond");
    Stylist secondStylist = new Stylist("Raymond");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void save_savesStylistToTheDatabase_true() {
    Stylist testStylist = new Stylist("Billford");
    testStylist.save();
    assertTrue(Stylist.all().get(0).equals(testStylist));
  }

  @Test
  public void find_findsStylistInDatabase() {
    Stylist testStylist1 = new Stylist("Raymond");
    Stylist testStylist2 = new Stylist("Billford");
    Stylist testStylist3 = new Stylist("Gregorio");
    testStylist1.save();
    testStylist2.save();
    testStylist3.save();
    Stylist savedStylist = Stylist.find(testStylist2.getId());
    assertTrue(test.equals(savedStylist));
  }

  @Test
  public void find_stylistNamefromStylistID() {
    Stylist testStylist1 = new Stylist("Raymond");
    Stylist testStylist2 = new Stylist("Billford");
    Stylist testStylist3 = new Stylist("Gregorio");
    testStylist1.save();
    testStylist2.save();
    testStylist3.save();
    Stylist savedStylist = Stylist.find(testStylist2.getId());
    String stylistName = savedStylist.getStylistName();
    assertEquals("Billford",(savedStylist.getStylistName()));
  }



}
