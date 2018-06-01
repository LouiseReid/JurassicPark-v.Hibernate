package controllers;

import db.DBHelper;
import models.Dinosaur;
import models.DynoType;
import models.Paddock;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class PaddockController {

    public PaddockController(){
        this.setUpEndPoints();
    }

    private void setUpEndPoints(){

        get("/:id/details", (req, res) -> {
            String strId = req.params(":id");
            Integer intId = Integer.parseInt(strId);
            Paddock paddock = DBHelper.find(Paddock.class, intId);
            paddock.generateCustomers();
            DBHelper.save(paddock);
            DBHelper.generateNewState(paddock);
            HashMap<String, Object> model = new HashMap<>();
            List<Dinosaur> dinosaurs = DBHelper.dinosaursInPaddock(paddock);
            model.put("dinosaurs", dinosaurs);
            model.put("paddock", paddock);
            model.put("template", "templates/paddocks/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


        get("/paddock/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<DynoType> species = DBHelper.allSpecies();
            model.put("species", species);
            model.put("template", "templates/paddocks/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/paddocks", (req, res) -> {
            String name = req.queryParams("name");
            String speciesEnumValue = req.queryParams("species");
            DynoType species = DynoType.valueOf(speciesEnumValue);
            int dinoCapacity = Integer.parseInt(req.queryParams("dinoCapacity"));
            int customerCapacity = Integer.parseInt(req.queryParams("customerCapacity"));
            Paddock paddock = new Paddock(name, species, dinoCapacity, customerCapacity, 0);
            DBHelper.save(paddock);
            res.redirect("/");
            return null;
        }, new VelocityTemplateEngine());

        post("/:id/:id2/feed", (req, res) -> {
            String strId = req.params(":id2");
            Integer intId = Integer.parseInt(strId);
            Dinosaur dinosaur = DBHelper.find(Dinosaur.class, intId);
            dinosaur.feed();
            DBHelper.save(dinosaur);
            res.redirect(req.headers("referer"));
            return null;
        }, new VelocityTemplateEngine());

        post(":id/:id2/transfer", (req, res) -> {
            String strId = req.params(":id2");
            Integer intId = Integer.parseInt(strId);
            Dinosaur dinosaur = DBHelper.find(Dinosaur.class,intId);
            Paddock currentPaddock = dinosaur.getPaddock();
            Integer paddockId = Integer.parseInt(req.queryParams("paddock"));
            Paddock newPaddock = DBHelper.find(Paddock.class,paddockId);
            DBHelper.transferDino(dinosaur, currentPaddock, newPaddock);
            res.redirect(req.headers("referer"));
            return null;
        }, new VelocityTemplateEngine());

        get("/:id/:id2/transfer_options", (req, res) -> {
            String strId = req.params(":id2");
            Integer intId = Integer.parseInt(strId);
            Dinosaur dinosaur = DBHelper.find(Dinosaur.class,intId);
            List<Paddock> paddocks = DBHelper.availableTransferPaddocks(dinosaur);
            HashMap<String, Object> model = new HashMap<>();
            model.put("dinosaur", dinosaur);
            model.put("paddocks", paddocks);
            model.put("template", "templates/paddocks/transfer.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


    }

}
