package io.keepcoding.fhonlop.fundandroid;

import java.util.UUID;

/**
 * Created by javi on 4/4/16.
 */
public class Table {

    private UUID mID;
    private String mName;

    public Table () {
        mID = UUID.randomUUID();
        mName = "N/A";
    }

    public Table (UUID id, String name) {
        mID = id;
        mName = name;
    }

    public UUID getID() {
        return mID;
    }

    public void setID(UUID ID) {
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
