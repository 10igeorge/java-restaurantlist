import org.sql2o.*;
import java.util.List;

public class Cuisine {
  private int id;
  private String type;


  public Cuisine (String type) {
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object otherCuisine){
    if (!(otherCuisine instanceof Cuisine)) {
      return false;
    } else {
      Cuisine newCuisine = (Cuisine) otherCuisine;
      return this.getType().equals(newCuisine.getType()) &&
        this.getId() == newCuisine.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO cuisine (type) VALUES (:type)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("type", this.type)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<Cuisine> all() {
    String sql = "SELECT id, type FROM cuisine";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(Cuisine.class);
    }
  }

  // public void update(String newType) {
  //   this.mType = newType;
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "UPDATE cuisine SET type=:type WHERE id=:id";
  //     try(Connection con = DB.sql2o.open()) {
  //       con.createQuery(sql)
  //       .addParameter("type", mType)
  //       .executeUpdate();
  //     }
  //   }
  // }

  public static void deleteCuisine(int id) {
    String sql = "DELETE FROM cuisine WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public static Cuisine find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM cuisine where id=:id";
      Cuisine Cuisine = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Cuisine.class);
      return Cuisine;
    }
  }

  public List<Restaurant> getRestaurants(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM restaurants where cuisine_id=:id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Restaurant.class);
    }
  }
}
