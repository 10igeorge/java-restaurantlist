import org.sql2o.*;
import java.util.List;

public class Restaurant {
  private int id;
  private String name;
  private int cuisine_id;

  public Restaurant (String name, int cuisine_id) {
    this.name = name;
    this.cuisine_id = cuisine_id;
  }

  public int getId() {
    return id;
  }

  public int getCuisineId(){
    return cuisine_id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getName().equals(newRestaurant.getName()) &&
        this.getId() == newRestaurant.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants(name, cuisine_id) VALUES (:name, :cuisine_id)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .addParameter("cuisine_id", cuisine_id)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<Restaurant> all() {
    String sql = "SELECT id, name, cuisine_id FROM restaurants";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }

  public static Restaurant find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM restaurants WHERE id=:id";
      Restaurant restaurant = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Restaurant.class);
      return restaurant;
    }
  }

  public void update(String newName) {
    this.name = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET name=:name WHERE id=:id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    String sql = "DELETE FROM restaurants WHERE id=:id";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  // public Cuisine getType(){
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "SELECT * FROM cuisine WHERE cuisine_id=:cuisine.id";
  //     return con.createQuery(sql)
  //       .addParameter("cuisine.id", cuisine_id)
  //       .executeAndFetch(Cuisine.class);
  //   }
  // }

  //   TODO: Create method to get cuisine type
  // *******************************************************/

}
