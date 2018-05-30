package db;

import models.Dinosaur;
import models.DynoType;
import models.Paddock;

import java.util.List;

public class Seeds {

    public static void main(String[] args) {

        DBHelper.deleteAll(Dinosaur.class);
        DBHelper.deleteAll(Paddock.class);

        Paddock trexPen = new Paddock("Tyrannosaur Enclosure", DynoType.CARNIVORE, 2, 10, 3);
        DBHelper.save(trexPen);
        Paddock  greatPlains = new Paddock("The Great Plains", DynoType.HERBIVORE, 10, 30, 10);
        DBHelper.save(greatPlains);
        Paddock pettingZoo = new Paddock("Gentle Giants Petting Zoo", DynoType.HERBIVORE, 5, 10, 2);
        DBHelper.save(pettingZoo);
        Paddock triceratops = new Paddock("Triceratops Enclosure", DynoType.CARNIVORE, 6, 10, 2);
        DBHelper.save(triceratops);
        Paddock aviary = new Paddock("The Aviary", DynoType.HERBIVORE, 40, 20, 10);
        DBHelper.save(aviary);

        Dinosaur trex = new Dinosaur(DynoType.CARNIVORE, "T-Rex", "Rex", trexPen, 6 );
        DBHelper.save(trex);
        Dinosaur blue = new Dinosaur(DynoType.CARNIVORE, "Velociraptor", "Blue", triceratops, 3);
        DBHelper.save(blue);
        Dinosaur charlie = new Dinosaur(DynoType.CARNIVORE, "Velociraptor","Charlie", triceratops, 1);
        DBHelper.save(charlie);
        DBHelper.save(trex);
        Dinosaur delta = new Dinosaur(DynoType.CARNIVORE, "Velociraptor","Delta", triceratops, 2);
        DBHelper.save(delta);
        Dinosaur echo = new Dinosaur(DynoType.CARNIVORE, "Velociraptor","Echo", triceratops, 1);
        DBHelper.save(echo);

        Dinosaur peter = new Dinosaur(DynoType.HERBIVORE, "Pterosaurs", "Peter", aviary, 10);
        DBHelper.save(peter);
        Dinosaur polly = new Dinosaur(DynoType.HERBIVORE, "Pterosaurs", "Polly", aviary, 5);
        DBHelper.save(polly);
        Dinosaur penelope = new Dinosaur(DynoType.HERBIVORE, "Pterodactyl", "Penelope", aviary, 6);
        DBHelper.save(penelope);
        Dinosaur francis = new Dinosaur(DynoType.HERBIVORE, "Pterodactyl", "Francis", aviary, 6);
        DBHelper.save(francis);
        Dinosaur ted = new Dinosaur(DynoType.HERBIVORE, "Pteranodon", "Ted", aviary, 1);
        DBHelper.save(ted);
        Dinosaur bob = new Dinosaur(DynoType.HERBIVORE, "Pteranodon", "Bob", aviary, 2);
        DBHelper.save(bob);

        Dinosaur terry = new Dinosaur(DynoType.HERBIVORE, "Triceratop", "Terry", greatPlains, 1);
        DBHelper.save(terry);
        Dinosaur tracy = new Dinosaur(DynoType.HERBIVORE, "Triceratop", "Tracy", greatPlains, 1);
        DBHelper.save(tracy);
        Dinosaur agnes = new Dinosaur(DynoType.HERBIVORE, "Brachiosaurus", "Agnes", greatPlains, 1);
        DBHelper.save(agnes);
        Dinosaur holly = new Dinosaur(DynoType.HERBIVORE, "Triceratop", "Holly", greatPlains, 1);
        DBHelper.save(holly);
        Dinosaur bruno = new Dinosaur(DynoType.HERBIVORE, "Brachiosaurus", "Bruno", greatPlains, 1);
        DBHelper.save(bruno);

        Dinosaur steve = new Dinosaur(DynoType.HERBIVORE, "Brachiosaurus", "Steve", pettingZoo, 6);
        DBHelper.save(steve);
        Dinosaur jim = new Dinosaur(DynoType.HERBIVORE, "Triceratop", "Steve", pettingZoo, 6);
        DBHelper.save(jim);

        Paddock paddock = DBHelper.find(Paddock.class, trexPen.getId());

        List<Paddock> paddockList = DBHelper.getAll(Paddock.class);

        Dinosaur dinosaur = DBHelper.find(Dinosaur.class, trex.getId());

    }
}
