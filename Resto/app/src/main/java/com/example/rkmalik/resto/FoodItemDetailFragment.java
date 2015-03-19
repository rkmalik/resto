package com.example.rkmalik.resto;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by shashankranjan on 3/16/15.
 */
public class FoodItemDetailFragment extends Fragment{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "name";

    /**
     * The dummy content this fragment is presenting.
     */
    private String itemName;
    private Context _context;
    private RestoSoundPlayer player;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public FoodItemDetailFragment() {
        player = new RestoSoundPlayer();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            itemName = getArguments().get(ARG_ITEM_ID).toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fooditem_detail, container, false);
        final Context context = getActivity();

        // Show the dummy content as text in a TextView.
        if (itemName != null) {
            ((TextView) rootView.findViewById(R.id.item_name)).setText(itemName);
            ((TextView) rootView.findViewById(R.id.item_sub_name)).setText("Alias Name");
            ((TextView) rootView.findViewById(R.id.item_pronun)).setText("/pronun /ciation");
            ((ImageView) rootView.findViewById(R.id.image_vegnonveg_1)).setImageResource(R.drawable.no_image);
            CheckBox favButton = ((CheckBox) rootView.findViewById(R.id.imageBtn_favorite_1));

            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Favorite Clicked");
                    Toast.makeText(context, "Favorite Clicked", Toast.LENGTH_SHORT);
                }
            });

            ImageView spkrBtn = ((ImageView) rootView.findViewById(R.id.imageBtn_speaker_1));
            spkrBtn.setImageResource(R.drawable.no_image);
            spkrBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Speaker Clicked");
                    Toast.makeText(context, "Speaker Clicked", Toast.LENGTH_SHORT);
                    player.playSound(context, R.raw.croissant);
                }
            });
            ((ImageView) rootView.findViewById(R.id.image_item_1)).setImageResource(R.drawable.chipotle);
            ((ImageView) rootView.findViewById(R.id.image_item_2)).setImageResource(R.drawable.polotropical);
            ((TextView) rootView.findViewById(R.id.item_nutrition_header)).setText("Nutrition (Serving Size : 1 cup sliced)");
            ((TextView) rootView.findViewById(R.id.item_nutrition_detail)).setText("1 calories");
        }

        return rootView;
    }
}
