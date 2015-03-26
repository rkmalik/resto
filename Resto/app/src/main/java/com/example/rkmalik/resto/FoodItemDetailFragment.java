package com.example.rkmalik.resto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkmalik.data.FoodItem;
import com.example.rkmalik.model.DBHelper;
import com.example.rkmalik.model.RestaurantModel;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by shashankranjan on 3/16/15.
 */
public class FoodItemDetailFragment extends Fragment  implements TextToSpeech.OnInitListener{
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
    private TextToSpeech tts;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private FoodItem foodItem;

    public FoodItemDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id;
      /*  if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            itemName = getArguments().get(ARG_ITEM_ID).toString();
        }*/
        _context = this.getActivity();
        tts = new TextToSpeech(this.getActivity(), this);

        id = (Integer)getArguments().get("id");
        DBHelper dbHelper = new DBHelper(this.getActivity().getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        foodItem = RestaurantModel.getFoodItemDetailsFromId(database, id);
        database.close();

       // int restId = (Integer) getArguments().get("restId");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fooditem_detail, container, false);
        final Context context = getActivity();

        // Show the dummy content as text in a TextView.
        if (foodItem != null) {
            ((TextView) rootView.findViewById(R.id.item_name)).setText(foodItem.getName());//.setText(itemName);
            ((TextView) rootView.findViewById(R.id.item_sub_name)).setText(foodItem.getLocalName());
            ((TextView) rootView.findViewById(R.id.item_pronun)).setText(foodItem.getPhoneticName());
            ((ImageView) rootView.findViewById(R.id.image_vegnonveg_1)).setImageResource(foodItem.isVeg()?R.drawable.veg_icon:R.drawable.non_veg_icon);
            CheckBox favButton = ((CheckBox) rootView.findViewById(R.id.imageBtn_favorite_1));
            favButton.setChecked(foodItem.isFav()?true:false);

            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Favorite Clicked");
                    foodItem.setFav(!foodItem.isFav());
                    //UpdateFavourites(foodItem);
                    if(foodItem.isFav())
                        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT);
                    else
                        Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT);
                }
            });

            ImageView spkrBtn = ((ImageView) rootView.findViewById(R.id.imageBtn_speaker_1));
            spkrBtn.setImageResource(R.drawable.icon_audio);
            spkrBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Speaker Clicked");
                    Toast.makeText(context, "Speaker Clicked", Toast.LENGTH_SHORT);
                    speakOut(foodItem.getName());
                }
            });

            ((ImageView) rootView.findViewById(R.id.image_item_1)).setImageBitmap(foodItem.getMainImage());
            ((TextView) rootView.findViewById(R.id.item_nutrition_header)).setText("Nutrition");
            ((TextView) rootView.findViewById(R.id.item_nutrition_detail)).setText(String.valueOf(foodItem.getCalories() + " calories per " + foodItem.getServingSize()));
        }

        return rootView;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            // tts.setPitch(5); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            }
        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }

    private void speakOut(String text) {
        AudioManager am = (AudioManager)_context.getSystemService(Context.AUDIO_SERVICE);
        int amStreamMusicMaxVol = am.getStreamVolume(am.STREAM_VOICE_CALL);
        am.setStreamVolume(am.STREAM_VOICE_CALL, amStreamMusicMaxVol, 0);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
