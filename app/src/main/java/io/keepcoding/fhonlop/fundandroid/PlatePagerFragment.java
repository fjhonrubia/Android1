package io.keepcoding.fhonlop.fundandroid;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;
import java.util.UUID;

/**
 * Created by javi on 6/4/16.
 */
public class PlatePagerFragment extends Fragment {

    private static final String ARG_TABLE_ID = "table_id";
    private static final String ARG_PLATE_ID = "plate_id";

    private Table mTable;
    private Plate mPlate;

    private TextView mNameView;
    private ImageView mImageView;
    private TextView mDescriptionView;
    private EditText mNotesView;

    private ImageView mAllergenCelery;
    private ImageView mAllergenCrustacean;
    private ImageView mAllergenDairy;
    private ImageView mAllergenEggs;
    private ImageView mAllergenFish;
    private ImageView mAllergenGluten;
    private ImageView mAllergenLupin;
    private ImageView mAllergenMollucs;
    private ImageView mAllergenMustard;
    private ImageView mAllergenNuts;
    private ImageView mAllergenPeanuts;
    private ImageView mAllergenSesame;
    private ImageView mAllergenSoy;
    private ImageView mAllergenSulfitos;


    public static PlatePagerFragment newInstance(UUID tableID, UUID plateID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TABLE_ID, tableID);
        args.putSerializable(ARG_PLATE_ID, plateID);
        PlatePagerFragment fragment = new PlatePagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID tableId = (UUID)getArguments().getSerializable(ARG_TABLE_ID);
        UUID plateId = (UUID)getArguments().getSerializable(ARG_PLATE_ID);

        mTable = Orders.get(getActivity()).getTable(tableId);
        mPlate = Orders.get(getActivity()).getPlate(plateId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plate_pager_fragment, container, false);

        mNameView = (TextView)v.findViewById(R.id.plate_name_text_view);
        mNameView.setText(mPlate.getName());

        mImageView = (ImageView)v.findViewById(R.id.plate_image_view);
        mImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), mPlate.returnIDImage()));

        mDescriptionView = (TextView)v.findViewById(R.id.plate_description_text_view);
        mDescriptionView.setText(mPlate.getDescription());

        mNotesView = (EditText)v.findViewById(R.id.order_plate_notes_edit_text);
        mNotesView.setEnabled(false);

        mAllergenCelery = (ImageView)v.findViewById(R.id.celery_image_view);
        mAllergenCrustacean = (ImageView)v.findViewById(R.id.crustaceans_image_view);
        mAllergenDairy = (ImageView)v.findViewById(R.id.dairy_image_view);
        mAllergenEggs = (ImageView)v.findViewById(R.id.eggs_image_view);
        mAllergenFish = (ImageView)v.findViewById(R.id.fish_image_view);
        mAllergenGluten = (ImageView)v.findViewById(R.id.gluten_image_view);
        mAllergenLupin = (ImageView)v.findViewById(R.id.lupin_image_view);
        mAllergenMollucs = (ImageView)v.findViewById(R.id.mollucs_image_view);
        mAllergenMustard = (ImageView)v.findViewById(R.id.mustard_image_view);
        mAllergenNuts = (ImageView)v.findViewById(R.id.nuts_image_view);
        mAllergenPeanuts = (ImageView)v.findViewById(R.id.peanuts_image_view);
        mAllergenSesame = (ImageView)v.findViewById(R.id.sesame_image_view);
        mAllergenSoy = (ImageView)v.findViewById(R.id.soy_image_view);
        mAllergenSulfitos = (ImageView)v.findViewById(R.id.sulfitos_image_view);

        Map<String, Boolean> allergens = mPlate.getAllergens();

        if (allergens.get(mPlate.ALLERGEN_CELERY)) {
            mAllergenCelery.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_CRUSTACEANS)) {
            mAllergenCrustacean.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_DAIRY)) {
            mAllergenDairy.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_EGGS)) {
            mAllergenEggs.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_FISH)) {
            mAllergenFish.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_GLUTEN)) {
            mAllergenGluten.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_LUPIN)) {
            mAllergenLupin.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_MOLLUCS)) {
            mAllergenMollucs.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_MUSTARD)) {
            mAllergenMustard.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_NUTS)) {
            mAllergenNuts.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_PEANUTS)) {
            mAllergenPeanuts.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_SESAME)) {
            mAllergenSesame.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_SOY)) {
            mAllergenSoy.setVisibility(View.VISIBLE);
        }

        if (allergens.get(mPlate.ALLERGEN_SULFITOS)) {
            mAllergenSulfitos.setVisibility(View.VISIBLE);
        }

        return v;

    }

}
