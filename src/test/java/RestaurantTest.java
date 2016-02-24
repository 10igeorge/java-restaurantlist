import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class RestaurantTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Restaurant.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Restaurant firstRestaurant = new Restaurant("Chinese Restaurant 1", 1, "info");
    Restaurant secondRestaurant = new Restaurant("Chinese Restaurant 1", 1, "info");
    assertTrue(firstRestaurant.equals(secondRestaurant));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Restaurant myRestaurant = new Restaurant("Chinese Restaurant 1", 1, "info");
    myRestaurant.save();
    assertTrue(Restaurant.all().get(0).equals(myRestaurant));
  }

  @Test
  public void save_assignsIdToObject() {
    Restaurant myRestaurant = new Restaurant("Chinese Restaurant 1", 1, "info");
    myRestaurant.save();
    Restaurant savedRestaurant = Restaurant.all().get(0);
    assertEquals(myRestaurant.getId(), savedRestaurant.getId());
  }

  @Test
  public void find_findsRestaurantInDatabase_true() {
    Restaurant myRestaurant = new Restaurant("Chinese Restaurant 5", 5, "info");
    myRestaurant.save();
    Restaurant savedRestaurant = Restaurant.find(myRestaurant.getId());
    assertTrue(myRestaurant.equals(savedRestaurant));
  }

  @Test
  public void all_savesIntoDatabase_true() {
    Restaurant myRestaurant = new Restaurant("Mow the lawn", 1, "info");
    myRestaurant.save();
    assertEquals(Restaurant.all().get(0).getName(), "Mow the lawn");
  }

  @Test
  public void find_findsAnotherRestaurantInDatabase_true() {
    Restaurant myRestaurant = new Restaurant("Mow the lawn", 1, "info");
    myRestaurant.save();
    Restaurant savedRestaurant = Restaurant.find(myRestaurant.getId());
    assertEquals(savedRestaurant.getName(), "Mow the lawn");
  }

  @Test
  public void save_savesCuisineIdIntoDB_true() {
    Cuisine myCuisine = new Cuisine("Household chores");
    myCuisine.save();
    Restaurant myRestaurant = new Restaurant("Mow the lawn", myCuisine.getId(), "info");
    myRestaurant.save();
    Restaurant savedRestaurant = Restaurant.find(myRestaurant.getId());
    assertEquals(savedRestaurant.getCuisineId(), myCuisine.getId());
  }

  @Test
  public void getInfo_returnsInfoAboutRestaurant_true(){
    Cuisine myCuisine = new Cuisine("Household chores");
    myCuisine.save();
    Restaurant myRestaurant = new Restaurant("Mow the lawn", myCuisine.getId(), "info");
    myRestaurant.save();
    assertTrue(myRestaurant.getInfo().equals("info"));
  }

}
