package models;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "dinosaurs")
public class Dinosaur {

    private int id;
    private DynoType species;
    private String name;
    private String breed;
    private Paddock paddock;
    private int hungerLevel;


    public Dinosaur() {
    }


    public Dinosaur(DynoType species, String breed, String name, Paddock paddock, int hungerLevel) {
        this.id = id;
        this.species = species;
        this.breed = breed;
        this.name = name;
        this.paddock = paddock;
        this.hungerLevel = hungerLevel;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "species")
    public DynoType getSpecies() {
        return species;
    }

    public void setSpecies(DynoType species) {
        this.species = species;
    }

    @Column(name = "breed")
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paddock_id", nullable = false)
    public Paddock getPaddock() {
        return paddock;
    }

    public void setPaddock(Paddock paddock) {
        this.paddock = paddock;
    }

    @Column(name = "hunger_level")
    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public void feed(){
        Random rn = new Random();
        int foodCount = rn.nextInt(5) + 1;
        hungerLevel += foodCount;
    }

    public boolean checkHungry(){
        return hungerLevel < 2;
    }

    public void sleep(){
        if(paddock.getPaddockState() == PaddockState.SLEEPING){
            hungerLevel = 0;
        }
    }
}
