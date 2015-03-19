package com.example.rkmalik.resto;

/**
 * Created by shashankranjan on 3/18/15.
 */

import android.content.Context;
import android.media.MediaPlayer;

public class RestoSoundPlayer{
    public void playSound(Context context, int soundId){
        MediaPlayer mp = MediaPlayer.create(context, soundId);
        mp.setVolume(1,1);
        mp.start();
    }
}
