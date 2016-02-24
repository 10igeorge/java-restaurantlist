import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class CuisineTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Cuisine.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Cuisine firstCuisine = new Cuisine("Chinese");
    Cuisine secondCuisine = new Cuisine("Chinese");
    assertTrue(firstCuisine.equals(secondCuisine));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Cuisine myCuisine = new Cuisine("Chinese");
    myCuisine.save();
    assertTrue(Cuisine.all().get(0).equals(myCuisine));
  }

  @Test
  public void find_findCusineInDatabase_true() {
    Cuisine myCuisine = new Cuisine("Chinese");
    myCuisine.save();
    Cuisine savedCuisine = Cuisine.find(myCuisine.getId());
    assertTrue(myCuisine.equals(savedCuisine));
  }

  @Test
  public void getType_returnsNameOfCuisine_true() {
    Cuisine myCuisine = new Cuisine("Chinese");
    assertTrue(myCuisine.getType().equals("Chinese"));
  }

  @Test
    public void getRestaurants_retrievesAllRestaurantsFromDatabase_restaurantsList() {
      Cuisine myCuisine = new Cuisine("Chinese");
      myCuisine.save();
      Restaurant firstRestaurant = new Restaurant("Chinese Restaurant 1", myCuisine.getId(), "info");
      firstRestaurant.save();
      Restaurant secondRestaurant = new Restaurant("Chinese Restaurant 2", myCuisine.getId(), "info");
      secondRestaurant.save();
      Restaurant[] restaurants = new Restaurant[] { firstRestaurant, secondRestaurant };
      assertTrue(myCuisine.getRestaurants().containsAll(Arrays.asList(restaurants)));
  }
}
