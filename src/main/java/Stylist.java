import org.sql2o.*;
import java.util.List;

public class Stylist {
  private int id;
  private String stylist_name;

  public Stylist (String stylistName) {
    this.stylist_name = stylistName;
  }

  @Override
  public boolean equals(Object otherStylist){
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getStylistName().equals(newStylist.getStylistName()) &&
      this.getId() == newStylist.getId();
    }
  }

  //GETTER METHODS//

  public int getId() {
    return id;
  }

  public String getStylistName() {
    return stylist_name;
  }

  //SETTER METHODS//

  public void setName(String newStylistName) {
    stylist_name = newStylistName;
  }

  //CREATE//

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (stylist_name) VALUES (:stylist_name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("stylist_name", this.stylist_name)
      .executeUpdate()
      .getKey();
    }
  }

  //READ//

  public static List<Stylist> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists";
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  //FIND//

  public static Stylist find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetchFirst(Stylist.class);
    }
  }

  //UPDATE//

  public void update() {
    try(Connection con DB.sql2o.open()) {
      String sql = "UPDATE stylists SET stylist_name = :stylist_name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("stylist_name", this.stylist_name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  //DELETE//

  public void delete() {
    try(Connection con DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
