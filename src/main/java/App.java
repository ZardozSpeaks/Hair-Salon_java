import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.List;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());

      int selectedStylistName = Integer.parseInt(request.queryParams("stylist"));
      List<Client> clientsByStylist = Client.findByStylist(selectedStylistName);
      String stylistName = Stylist.find(selectedStylistName).getStylistName();

      model.put ("listStylistName", stylistName);
      model.put ("clients", clientsByStylist);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/new-client", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/new-client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new-client", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String newName = request.queryParams("client");
      String ageGroup = request.queryParams("age-group");
      int stylist = Integer.parseInt(request.queryParams("stylist"));
      Client newClient = new Client(newName, ageGroup);
      newClient.save();
      newClient.setStylistId(stylist);
      newClient.updateNewClient();

      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/client/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/client.vtl");

      Client client = Client.find(Integer.parseInt(request.params(":id")));
      String stylistName = Stylist.find(client.getStylistId()).getStylistName();

      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("client", client);
      model.put("stylistName", stylistName);

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/client/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/client.vtl");

      Client client = Client.find(Integer.parseInt(request.params(":id")));
      client.setName(request.queryParams("client"));
      client.setStylistId(Integer.parseInt(request.queryParams("stylist")));
      client.update();

      Client updatedClient = Client.find(Integer.parseInt(request.params(":id")));
      String stylistName = Stylist.find(updatedClient.getStylistId()).getStylistName();
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("client", updatedClient);
      model.put("stylistName", stylistName);

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/new-stylist", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/new-stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new-stylist", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String newName = request.queryParams("stylist");
      Stylist newStylist = new Stylist(newName);
      newStylist.save();

      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  } //end of main
} // end of app
