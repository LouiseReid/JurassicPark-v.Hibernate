package models;

public enum PaddockState {

    RAMPAGE("Rampaging"),
    CALM("calm"),
    SLEEPING("sleeping"),
    EATING("eating"),
    ROAMING("roaming");

    private String description;

    PaddockState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
