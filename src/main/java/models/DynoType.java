package models;

public enum DynoType {

    CARNIVORE("Carnivore"),
    HERBIVORE("Herbivore");

    private String description;

    DynoType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
