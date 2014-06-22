/**
 * 
 */
package com.pipehype;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

public class Voegel extends Activity {

	Integer anzahlVoegel = 0;
	boolean sound = true;
	
	//Ein Vogel wird hinzugezählt.
	public void voegelAddieren(){		
		anzahlVoegel ++;
	}
	
	//Die Anzahl der angelockten Vogeldamen wird zurückgegeben.
	public String getVoegel(){
		return "Sie haben " + anzahlVoegel +" Vogeldame(n) angelockt!";	
	}
	
	//Vogelzwitschern für den Zeitpunkt an dem eine Vogeldame angelockt wurde wird gespielt.
	public void vogelSound(Context context){	
		MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.blm);
		if(sound == true){
			mediaPlayer.setVolume((float) 0.3,(float) 0.3);
		}
		else if(sound == false) {
			mediaPlayer.setVolume((float) 0.0,(float) 0.0);
		}
		mediaPlayer.start();	
	}
	
}
