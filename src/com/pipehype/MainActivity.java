package com.pipehype;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	public static Voegel voegel = new Voegel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //TextView Font
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/dyspepsia.ttf");
        //Button Fonts
        TextView tv = (TextView) findViewById(R.id.CustomFontText);
        tv.setTypeface(tf);
        TextView b1 = (TextView) findViewById(R.id.button1);
        b1.setTypeface(tf);
        TextView b2 = (TextView) findViewById(R.id.button2);
        b2.setTypeface(tf);
        TextView b3 = (TextView) findViewById(R.id.button3);
        b3.setTypeface(tf);
        TextView b4 = (TextView) findViewById(R.id.toggleButton1);
        b4.setTypeface(tf);
        
        //Logo anzeigen
        final ImageView imgView;
        imgView = (ImageView) findViewById(R.id.imageView1);
        imgView.setImageResource(R.drawable.pipehype_logo_all);
    
        
        //Hintergrundsound
        final MediaPlayer mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.backgroundsound);
        mediaPlayer1.setVolume((float) 0.1, (float) 0.1);
        mediaPlayer1.setLooping(true);
        mediaPlayer1.start();
        
        //Button zum Deaktivieren und Aktivieren des Hintergrungsounds
        ToggleButton button_Sound = (ToggleButton) findViewById(R.id.toggleButton1);
        button_Sound.setOnCheckedChangeListener(new OnCheckedChangeListener(){		
			@Override public void onCheckedChanged(final CompoundButton button_Start, boolean state) {
				if(state){
					mediaPlayer1.setVolume((float) 0.1, (float) 0.1);
					voegel.sound = true;
					
				}
				else{
					mediaPlayer1.setVolume((float) 0.0, (float) 0.0);
					voegel.sound = false;
					
				}	
			}		
        });
        
        //Layout buttons um zwischen den Activities zu schalten
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				goToSelection();
			}
        });
        
        //Button zur Anleitung
        Button button_anl = (Button) findViewById(R.id.button2);
        button_anl.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				showAnleitung();
			}
			
        });
        
        //Button zum Beenden der App
        Button button_schliessen = (Button) findViewById(R.id.button3);
        button_schliessen.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mediaPlayer1.stop();
				close();
			}       	
        });
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }   
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //Layout-intents zur Verknüpfung der Activities
    //Weiterleitung zur Selection.java.
    private void goToSelection(){
    	Intent intent = new Intent(this, Selection.class);
    	startActivity(intent);	
    }
    
    //Eine neue Instanz von Anleitung.java wird erstellt.
    private void showAnleitung(){
    	Intent intent = new Intent(this, Anleitung.class);
    	startActivity(intent);
    }
    
    //Schließt das Programm.
    private void close(){
    	this.finish();
    }
    
	public void backgroundSound(Context context){	
		
		
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
