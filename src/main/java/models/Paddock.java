package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

@Entity
@Table(name = "paddocks")
public class Paddock {

    private int id;
    private String name;
    private DynoType species;
    private int capacity;
    private int customerCount;
    private ArrayList<Dinosaur> dinosaurs;
    private ArrayList<PaddockState> paddockStates;
    private PaddockState paddockState;

    public Paddock(int id, String name, DynoType species, int capacity, int customerCount) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.capacity = capacity;
        this.customerCount = customerCount;
        paddockState = getState();
        dinosaurs = new ArrayList<>();
        paddockStates = new ArrayList<>();
        generatePaddockStates();
    }

    public Paddock() {
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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "species")
    public DynoType getSpecies() {
        return species;
    }

    public void setSpecies(DynoType species) {
        this.species = species;
    }

    @Column(name = "capacity")
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Column(name = "customerCount")
    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    @OneToMany(mappedBy = "paddock")
    public ArrayList<Dinosaur> getDinosaurs() {
        return dinosaurs;
    }

    public void setDinosaurs(ArrayList<Dinosaur> dinosaurs) {
        this.dinosaurs = dinosaurs;
    }

    @Column(name = "paddockState")
    public PaddockState getPaddockState() {
        return paddockState;
    }

    public void setPaddockState(PaddockState paddockState) {
        this.paddockState = paddockState;
    }

    @Transient
    public ArrayList<PaddockState> getPaddockStates() {
        return paddockStates;
    }

    public void setPaddockStates(ArrayList<PaddockState> paddockStates) {
        this.paddockStates = paddockStates;
    }

    private void generatePaddockStates(){
        Collections.addAll(paddockStates, PaddockState.values());
    }

    private int getNumStates(){
        return paddockStates.size();
    }

    public PaddockState getStateAtIndex(int index){
        return paddockStates.get(index);
    }

    public int getRandomState(){
        Random rand = new Random();
        int listSize = getNumStates();
        int random = rand.nextInt(listSize);
        return random;
    }

    public PaddockState getState(){
        int index = getRandomState();
        return getStateAtIndex(index);
    }

    public int paddockSize(){
        return dinosaurs.size();
    }

    public void addToPaddock(Dinosaur dinosaur){
        if(species == dinosaur.getSpecies() && paddockSize() < capacity) {
            dinosaurs.add(dinosaur);
        }
    }

    public void Rampage(){
        if(paddockState == PaddockState.RAMPAGE){
            dinosaurs.clear();
        }
    }

    public void removeDino(Dinosaur dinosaur){
        Iterator itr = dinosaurs.iterator();
        while (itr.hasNext()){
            Dinosaur removed = (Dinosaur)itr.next();
            if(removed == dinosaur){
                itr.remove();
            }
        }
    }

    public void transferDino(Dinosaur dinosaur, Paddock paddock1, Paddock paddock2){
        paddock1.removeDino(dinosaur);
        dinosaur.setLocation(paddock2.location);
        paddock2.addToPaddock(dinosaur);
    }
}
