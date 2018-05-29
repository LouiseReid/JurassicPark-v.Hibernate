package db;

import models.Dinosaur;
import models.DynoType;
import models.Paddock;

public class Seeds {

    public static void main(String[] args) {

        DBHelper.deleteAll(Dinosaur.class);
        DBHelper.deleteAll(Paddock.class);

        Paddock trexPen = new Paddock("Tyrannosaur Enclosure", DynoType.CARNIVORE, 2, 10, 3);
        DBHelper.saveOrUpdate(trexPen);

        Dinosaur trex = new Dinosaur(DynoType.CARNIVORE, "T-Rex", trexPen, 6 );
        DBHelper.saveOrUpdate(trex);
    }
}
