package io.keepcoding.fhonlop.fundandroid;

import java.util.UUID;

/**
 * Created by javi on 4/4/16.
 */
public class Order {

    private UUID orderID;
    private Plate plate;
    private Table table;
    private String notes;

    public Order(Plate plate, Table table, String notes) {

        this.orderID = UUID.randomUUID();
        this.plate = plate;
        this.table = table;
        this.notes = notes;
    }

    public UUID getOrderID() {
        return orderID;
    }

    public void setOrderID(UUID orderID) {
        this.orderID = orderID;
    }

    public Plate getPlate() {
        return plate;
    }

    public void setPlate(Plate plate) {
        this.plate = plate;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
