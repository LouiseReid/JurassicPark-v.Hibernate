package models;

public enum PaddockState {

    RAMPAGE("Rampage"),
    CALM("Calm"),
    SLEEPING("Sleeping"),
    EATING("Eating"),
    ROAMING("Roaming");

    private String description;

    PaddockState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
