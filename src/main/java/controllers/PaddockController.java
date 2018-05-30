package controllers;

import db.DBHelper;
import models.Dinosaur;
import models.Paddock;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;

public class PaddockController {

    public PaddockController(){
        this.setUpEndPoints();
    }

    private void setUpEndPoints(){

        get("/:id", (req, res) -> {
            String strId = req.params(":id");
            Integer intId = Integer.parseInt(strId);
            Paddock paddock = DBHelper.find(Paddock.class, intId);
            HashMap<String, Object> model = new HashMap<>();
            List<Dinosaur> dinosaurs = DBHelper.dinosaursInPaddock(paddock);
            model.put("dinosaurs", dinosaurs);
            model.put("paddock", paddock);
            model.put("template", "templates/paddocks/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }

}
