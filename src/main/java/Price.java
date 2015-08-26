import org.sql2o.*;
import java.util.List;


public class Price {
  private int price_id;
  private int price_range;

  public Price (int price_range) {
    this.price_range = price_range;
  }

  public int getPriceId() {
    return price_id;
  }

  public int getPriceRange() {
    return price_range;
  }

  public static List<Price> all() {
    String sql = "SELECT * FROM prices";
    try (Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Price.class);
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO prices (price_range) VALUES (:price_range)";
      this.price_id = (int) con.createQuery(sql, true)
        .addParameter("price_range", price_range)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherPrice){
    if (!(otherPrice instanceof Price)) {
      return false;
    } else {
      Price newPrice = (Price) otherPrice;
      return this.getPriceRange() == newPrice.getPriceRange() &&
        this.getPriceId() == newPrice.getPriceId();
    }
  }

  public Price find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM price WHERE price_id=:id";
      Price price = con.createQuery(sql)
        .addParameter("id", price_id)
        .executeAndFetchFirst(Price.class);
      return price;
    }
  }

  public void update(int newPriceRange) {
    this.price_range = newPriceRange;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE prices SET price_range=:price_range WHERE price_id=:id";
      con.createQuery(sql)
        .addParameter("price_range", newPriceRange)
        .addParameter("id", price_id)
        .executeUpdate();
      }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM prices WHERE price_id=:id";
      con.createQuery(sql)
        .addParameter("id", price_id)
        .executeUpdate();
    }
  }

  public List<Price> getPrices() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM prices WHERE price_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.price_id)
        .executeAndFetch(Price.class);
    }
  }
}