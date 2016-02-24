import java.util.Map;
import java.util.HashMap;
import java.util.List;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("restaurants", Restaurant.all());
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String restaurantName = request.queryParams("restaurantName");
      String cuisineType = request.queryParams("cuisineType");
      String info = request.queryParams("info");
      Cuisine newCuisine = new Cuisine(cuisineType);
      newCuisine.save();
      Restaurant newRestaurant = new Restaurant(restaurantName, newCuisine.getId(), info);
      newRestaurant.save();
      model.put("cuisines", Cuisine.all());
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/cuisine", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.queryParams("cuisineId")));
      List<Restaurant> restaurants = cuisine.getRestaurants();
      model.put("restaurants", restaurants);
      model.put("cuisine", cuisine);
      model.put("template", "templates/restaurantsbycuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/restaurants/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":id")));
      Cuisine cuisine = Cuisine.find(restaurant.getCuisineId());
      model.put("restaurant", restaurant);
      model.put("cuisine", cuisine);
      model.put("template", "templates/newrestaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/delete/restaurant/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params(":id"));
      Restaurant.delete(id);
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
