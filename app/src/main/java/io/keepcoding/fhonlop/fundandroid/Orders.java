package io.keepcoding.fhonlop.fundandroid;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by javi on 4/4/16.
 */
public class Orders {

    private static Orders sOrders;

    private Context mContext;

    private List<Table> mTables;
    private List<Plate> mPlates;
    private List<Order> mOrders;

    private UUID tableSelected;

    public static Orders get(Context context) {

        if (sOrders ==  null) {
            sOrders = new Orders(context);
        }

        return sOrders;
    }

    private Orders(Context context) {

        //Se inicializan las mesas
        mTables = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Table table = new Table();
            table.setName("Mesa #" + i);
            mTables.add(table);
        }


        //Se inicializan los platos
        mPlates = new ArrayList<>();
        /*for (int i = 0; i < 20; i++) {
            Plate plate = new Plate();
            plate.setName("Plato #" + i);
            plate.setDescription("Descripción del plato #" + i);
            plate.setPrice(2.0);
            mPlates.add(plate);
        }*/

        //Se inicializan las órdenes
        mOrders = new ArrayList<>();

    }

    public UUID getTableSelected() {
        return tableSelected;
    }

    public void setTableSelected(UUID tableSelected) {
        this.tableSelected = tableSelected;
    }

    public List<Table> getTables() {
        return mTables;
    }

    public List<Plate> getPlates() {
        return mPlates;
    }

    public void setPlates(List<Plate> plates) {
        mPlates = plates;
    }

    public List<Order> getOrders() {
        return mOrders;
    }

    public List<Order> getOrdersByTable(UUID tableID) {
        List<Order> auxOrders = new ArrayList<>();
        for (Order order: mOrders) {
            if (order.getTable().getID().equals(tableID)) {
                auxOrders.add(order);
            }
        }
        return auxOrders;
    }

    public Table getTable(UUID id) {
        for (Table table: mTables) {
            if (table.getID().equals(id)) {
                return table;
            }
        }
        return  null;
    }

    public Plate getPlate(UUID id) {
        for (Plate plate: mPlates) {
            if (plate.getID().equals(id)) {
                return plate;
            }
        }
        return null;
    }

    public void addOrder(Order o) {
        mOrders.add(o);
    }

}
