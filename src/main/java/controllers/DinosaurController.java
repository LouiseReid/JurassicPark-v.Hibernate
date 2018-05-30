package controllers;

import db.DBHelper;
import models.Dinosaur;
import models.Paddock;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class DinosaurController {

    public DinosaurController(){
        this.setUpEndPoints();
    }

    private void setUpEndPoints(){

        get("/dinosaurs", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Dinosaur> dinosaurs = DBHelper.getAll(Dinosaur.class);
            model.put("dinosaurs", dinosaurs);
            model.put("template", "templates/dinosaurs/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/:id/feed", (req, res) -> {
            String strId = req.params(":id");
            Integer intId = Integer.parseInt(strId);
            Dinosaur dinosaur = DBHelper.find(Dinosaur.class,intId);
            dinosaur.feed();
            DBHelper.save(dinosaur);
            res.redirect(req.headers("referer"));
            return null;
        }, new VelocityTemplateEngine());
    }
}
