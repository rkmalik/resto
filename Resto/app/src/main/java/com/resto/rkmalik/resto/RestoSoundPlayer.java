package com.resto.rkmalik.resto;

/**
 * Created by shashankranjan on 3/18/15.
 */

import android.content.Context;
import android.media.MediaPlayer;

public class RestoSoundPlayer{
    public void playSound(Context context, int soundId){
        System.out.println("Is it created?");
        MediaPlayer mp = MediaPlayer.create(context, soundId);
        System.out.println("Yes, it is");
        mp.setVolume(1,1);
        System.out.println("Volume set");
        mp.start();
        System.out.println("And started");
    }
}
