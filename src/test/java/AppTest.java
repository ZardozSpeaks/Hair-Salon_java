import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Joe's Barber Shop");
  }

  @Test
  public void clients_AreDisplayed() {
    Client testClient = new Client("Seymore Butts", "senior");
    Client testClient2 = new Client("Amanda Hugginkiss", "adult");
    testClient.save();
    testClient2.save();
    String indexPath = "http://localhost:4567/";
    goTo(indexPath);
    assertThat(pageSource()).contains("Seymore Butts");
    assertThat(pageSource()).contains("Amanda Hugginkiss");
  }

  @Test
  public void clients_CanBeAdded() {
    Stylist testStylist = new Stylist("Billford");
    testStylist.save();
    String indexPath = "http://localhost:4567/";
    String addClientPath = "http://localhost:4567/new-restaurant";
    goTo(addClientPath);
    fill("#client").with("Seymore Butts");
    click("option",withText("Senior(over 65)"));
    click("option",withText("Billford"));
    click("#submit-client");
    assertThat(pageSource()).contains("Seymore Butts");
  }

}
