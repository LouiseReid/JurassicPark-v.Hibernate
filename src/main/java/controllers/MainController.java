package controllers;

import db.DBHelper;
import db.Seeds;
import models.Paddock;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class MainController {

    public static void main(String[] args) {
        staticFileLocation("/public");
        DinosaurController dinosaurController = new DinosaurController();
        Seeds.seedData();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int customers = DBHelper.customerCount();
            model.put("customers", customers);
            model.put("template","templates/main.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }
}
