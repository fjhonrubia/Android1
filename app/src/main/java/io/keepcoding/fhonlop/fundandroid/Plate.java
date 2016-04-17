package io.keepcoding.fhonlop.fundandroid;


import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by javi on 4/4/16.
 */
public class Plate {

    public static final String ALLERGEN_CELERY = "celery";
    public static final String ALLERGEN_CRUSTACEANS = "crustaceans";
    public static final String ALLERGEN_DAIRY = "dairy";
    public static final String ALLERGEN_EGGS = "eggs";
    public static final String ALLERGEN_FISH = "fish";
    public static final String ALLERGEN_GLUTEN = "gluten";
    public static final String ALLERGEN_LUPIN = "lupin";
    public static final String ALLERGEN_MOLLUCS = "mollucs";
    public static final String ALLERGEN_MUSTARD = "mustard";
    public static final String ALLERGEN_NUTS = "nuts";
    public static final String ALLERGEN_PEANUTS = "peanuts";
    public static final String ALLERGEN_SESAME = "sesame";
    public static final String ALLERGEN_SOY = "soy";
    public static final String ALLERGEN_SULFITOS = "sulfitos";


    private UUID mID;
    private String mName;
    private String mDescription;
    private String mPhoto;
    private Map<String, Boolean> mAllergens;
    private Double mPrice;
    private String mTable;

    public Plate()  {
        mID = UUID.randomUUID();
        initAllergens();
    }

    public Plate(String name, String description, Double price, String photo) {
        mID = UUID.randomUUID();
        mName = name;
        mDescription = description;
        mPrice = price;
        mPhoto = photo;
        initAllergens();
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

    public String getTable() {
        return mTable;
    }

    public void setTable(String table) {
        mTable = table;
    }

    public Map<String, Boolean> getAllergens() {
        return mAllergens;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public void setAllergen(String allergen) {
        mAllergens.put(allergen, true);
    }

    public int getAllergenByName(String allergen) {

        if (mAllergens.get(allergen)) {
            return 1;
        } else {
            return 0;
        }

    }

    public int returnIDImage() {
        switch (getPhoto()) {
            case "albondigas.jpg":
                return R.drawable.albondigas;
            case "flan.jpg":
                return R.drawable.flan;
            case "macarrones.jpg":
                return R.drawable.macarrones;
            case "churrasco.jpg":
                return R.drawable.churrasco;
            case "fresas.jpg":
                return R.drawable.fresas;
            case "lubina.jpg":
                return R.drawable.lubina;
            default:
                return R.drawable.plato;
        }
    }

    private void initAllergens() {
        mAllergens = new HashMap<String, Boolean>();

        mAllergens.put(ALLERGEN_CELERY, false);
        mAllergens.put(ALLERGEN_CRUSTACEANS, false);
        mAllergens.put(ALLERGEN_DAIRY, false);
        mAllergens.put(ALLERGEN_EGGS, false);
        mAllergens.put(ALLERGEN_FISH, false);
        mAllergens.put(ALLERGEN_GLUTEN, false);
        mAllergens.put(ALLERGEN_LUPIN, false);
        mAllergens.put(ALLERGEN_MOLLUCS, false);
        mAllergens.put(ALLERGEN_MUSTARD, false);
        mAllergens.put(ALLERGEN_NUTS, false);
        mAllergens.put(ALLERGEN_PEANUTS, false);
        mAllergens.put(ALLERGEN_SESAME, false);
        mAllergens.put(ALLERGEN_SOY, false);
        mAllergens.put(ALLERGEN_SULFITOS, false);
    }

}
