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
    private List<PaddockState> paddockStates;
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
        paddockState = null;
        dinosaurs = new ArrayList<>();
        paddockStates = new ArrayList<>();
        generatePaddockStates();
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

    @Transient
    public List<PaddockState> getPaddockStates() {
        return paddockStates;
    }

    public void setPaddockStates(List<PaddockState> paddockStates) {
        this.paddockStates = paddockStates;
    }

    private void generatePaddockStates(){
        Collections.addAll(paddockStates, PaddockState.values());
    }

    @Transient
    private int gtNumStates(){
        return paddockStates.size();
    }

    @Transient
    public PaddockState gtStateAtIndex(int index){
        return paddockStates.get(index);
    }

    @Transient
    public int gtRandomState(){
        Random rand = new Random();
        int listSize = gtNumStates();
        int random = rand.nextInt(listSize);
        return random;
    }

    @Transient
    public PaddockState gtState(){
        int index = gtRandomState();
        return gtStateAtIndex(index);
    }

    public int paddockSize(){
        return dinosaurs.size();
    }

    public void addToPaddock(Dinosaur dinosaur){
        if(species == dinosaur.getSpecies() && paddockSize() < dinoCapacity) {
            dinosaurs.add(dinosaur);
        }
    }

    public void Rampage(){
        if(paddockState == PaddockState.RAMPAGE && species == DynoType.CARNIVORE){
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
        dinosaur.setPaddock(paddock2);
        paddock2.addToPaddock(dinosaur);
    }

    public void generateCustomers(){
        if (paddockState == PaddockState.ROAMING || paddockState == PaddockState.EATING || paddockState == PaddockState.CALM) {
            Random rn = new Random();
            int custCount = rn.nextInt(10) + 1;
            do {
                customerCount += custCount;
            } while (customerCount <= customerCapacity);
        } else if(paddockState == PaddockState.RAMPAGE || paddockState == PaddockState.SLEEPING){
            customerCount = 0;
        }
    }

}
