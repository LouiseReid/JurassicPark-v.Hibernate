package models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "paddocks")
public class Paddock {

    private int id;
    private String name;
    private DynoType species;
    private int dinoCapacity;
    private int customerCount;
    private int customerCapacity;
    private List<Dinosaur> dinosaurs;
    private PaddockState paddockState;

    public Paddock() {
    }

    public Paddock(String name, DynoType species, int dinoCapacity, int customerCapacity, int customerCount) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.dinoCapacity = dinoCapacity;
        this.customerCount = customerCount;
        this.customerCapacity = customerCapacity;
        paddockState = PaddockState.CALM;
        dinosaurs = new ArrayList<>();
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

    @Column(name = "dino_capacity")
    public int getDinoCapacity() {
        return dinoCapacity;
    }

    public void setDinoCapacity(int dinoCapacity) {
        this.dinoCapacity = dinoCapacity;
    }

    @Column(name = "customer_count")
    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    @Column(name = "customer_capacity")
    public int getCustomerCapacity() {
        return customerCapacity;
    }

    public void setCustomerCapacity(int customerCapacity) {
        this.customerCapacity = customerCapacity;
    }

    @OneToMany(mappedBy = "paddock", fetch = FetchType.EAGER)
    public List<Dinosaur> getDinosaurs() {
        return dinosaurs;
    }

    public void setDinosaurs(List<Dinosaur> dinosaurs) {
        this.dinosaurs = dinosaurs;
    }

    @Column(name = "paddock_state")
    public PaddockState getPaddockState() {
        return paddockState;
    }

    public void setPaddockState(PaddockState paddockState) {
        this.paddockState = paddockState;
    }

    public int paddockSize(){
        return dinosaurs.size();
    }

    public void addToPaddock(Dinosaur dinosaur){
        if(species == dinosaur.getSpecies() && paddockSize() < dinoCapacity) {
            dinosaurs.add(dinosaur);
        }
    }

    public boolean rampage(){
       return paddockState == PaddockState.RAMPAGE;
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
        dinosaur.setPaddock(paddock2);
        paddock2.addToPaddock(dinosaur);
    }

    public boolean atCapacity(){
        return customerCount == customerCapacity;
    }

    public void generateCustomers(){
        if (paddockState != PaddockState.RAMPAGE && !atCapacity()){
                customerCount += 2;
        }
    }

    public void loseCustomers(){
        customerCount -= 3;
    }

    public void lockdown(){
        customerCount = 0;
    }

}
