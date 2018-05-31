package models;

import java.util.ArrayList;
import java.util.List;

public enum PaddockState {

    RAMPAGE("Rampaging"),
    CALM("calm"),
    SLEEPING("sleeping");
//    EATING("eating"),
//    BATHING("bathing"),
//    GRAZING("grazing"),
//    DOZING("dozing"),
//    CHARGING("charging"),
//    STROLLING("strolling"),
//    NAPPING("napping"),
//    ROAMING("roaming");

    private String description;

    PaddockState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
