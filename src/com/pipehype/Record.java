package com.pipehype;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import android.os.Build;

@SuppressLint("WrongCall")
public class Record extends Activity implements Callback{

	 //Levelwerte aus Selection.java werden im Bundle Level gesichert und uebergeben.
	 Bundle Level;
	 Integer zeitLevel, stufe;
	
	 //Benötigte Variablen
	 Integer counter = 0;
	 Integer zeit = 21000; // in ms 
	 double dbwert;
	 boolean db_active = false;
	 
	 //Objekte der Klassen DB und selection werden initialisiert.
	 DB db = new DB();
	 Selection selection = new Selection();
	 
	 //Progress- und RatingBars..
	 EditText Level_; 
	 RatingBar Bewertung;
	 ProgressBar Lautstaerke, Zeit;
	 
	// Hier wird der Handler definiert welcher die Message entgegen nimmt (siehe unten)
     final Handler handler = new Handler(this);
     final Handler handler1 = new Handler(this);
      
     
     Runnable runnable = new Runnable() {
         @Override
         public void run() {
             while (db_active == true && zeit > 0){
                 handler.sendEmptyMessage(0);
                 try {
                     Thread.sleep(100);
                 } catch (InterruptedException e) {
                 }  
             }
             }     
     };
    
     
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_record);
		
		//Custom Font
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/dyspepsia.ttf");
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setTypeface(tf);
        TextView b1 = (TextView) findViewById(R.id.button1);
        b1.setTypeface(tf);
        TextView b2 = (TextView) findViewById(R.id.button2);
        b2.setTypeface(tf);
        TextView b3 = (TextView) findViewById(R.id.toggleButton1);
        b3.setTypeface(tf);
        
		//Rating- und Progressbars fuer Bewertung, Lautstaerke und verbleibende Zeit
		Bewertung = (RatingBar) findViewById(R.id.ratingBar1);
		Bewertung.setIsIndicator(true);
		
		Lautstaerke = (ProgressBar) findViewById(R.id.progressBar1);
		Lautstaerke.setMax(40);
		
		Zeit = (ProgressBar) findViewById(R.id.progressBar2);
		Zeit.setMax(21);
		
		//Starten der "Aufnahmeactivity" DB.
		db.start(); 
		
		//Übergebene Werte aus der Selection.java werden ausgelesen.
		Level = getIntent().getExtras();
		zeitLevel = Level.getInt("Level");
		stufe = Level.getInt("Stufe");
		MainActivity.voegel.anzahlVoegel = 0;
		
		//Zurueck-Button
		Button button_Close = (Button) findViewById(R.id.button1);
		button_Close.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				close();		
			}	
		});
		
		//Neustarten-Button
		final Button button_Restart = (Button) findViewById(R.id.button2);
		button_Restart.setEnabled(false);	
		button_Restart.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				restart();		
			}	
		});
		
		//Button fuer Aufnahme des Pfeiftons wird mit Listener belegt.
		ToggleButton button_Start = (ToggleButton) findViewById(R.id.toggleButton1);
		button_Start.setOnCheckedChangeListener(new OnCheckedChangeListener(){		
			@Override public void onCheckedChanged(final CompoundButton button_Start, boolean state) {
				if(state){
					Thread thread = new Thread(runnable);
					//Ist der Button aktiv, wird der oben erstellte Thread "thread" gestartet. 
					Toast.makeText(getApplicationContext(), "Los geht's!", Toast.LENGTH_LONG).show();
					thread.start();
					db_active = true;
					
					if (button_Start.isChecked()){
		                handler1.postDelayed(new Runnable() {
		                    public void run() {
		                         button_Start.setChecked(false);
		                    }
		                }, zeit);}

				} else{
					db_active = false;	
					Toast.makeText(getApplicationContext(), MainActivity.voegel.getVoegel(), Toast.LENGTH_LONG).show();
					button_Start.setEnabled(false);
					button_Restart.setEnabled(true);			
				}
		}});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record, menu);
		return true;
	}
	
	public void restart(){
		db_active = false;	
		MainActivity.voegel.finish();
		db.stop();
		Intent intent = new Intent(this, Record.class);
		intent.putExtras(Level);
		startActivity(intent);
		this.finish();
	}
	
	//Activity wird geschlossen
	public void close(){	
		db_active = false;	
		MainActivity.voegel.finish();
		db.stop();
		Intent intent = new Intent(this, Selection.class);	
    	startActivity(intent);
    	this.finish();
	}

	@Override
	//MessageHandler fuer "handler" nimmt Nachrichten von "thread" entgegegen, waehrend dieser laeuft.
	public boolean handleMessage(Message arg0) {
		//Aktueller Lautstärkewert wird aus der Activity DB geholt.
		double dbWert = db.getAmplitudeEMA();
		//Zeit wird heruntergezaehlt
		zeit = zeit - 100;		
		//Die Progressbar füllt sich entsprechend des aktuellen Lautstärkewertes, nach passender Umrechnung des Werts.
		Lautstaerke.setProgress((int) dbWert - 54);
		//Progressbar Zeit "entleert" sich jede sekunde.
		Zeit.setProgress((zeit+1)/1000);
		
		//Bedingungen für das Gewinnen einer Vogeldame werden gesetzt.
		//Benötigte Lautstärke wird gesetzt.
		if(dbWert> 90){ 
			counter++;	
			//Anhand des sich erhöhenden Werts "counter" werden dann Methoden des Voegel Activity aufgerufen, wenn die dem Schwierigkeitsgrad entsprechende Zeit erreicht ist.
            if((counter % zeitLevel) == 0){
            	//Ein Vogel wird hinzugefügt.
        		MainActivity.voegel.voegelAddieren();
        		//"Bestätigungssound" wird ausgegeben.
        		MainActivity.voegel.vogelSound(getApplicationContext());
        		//RatingBar wird um einen Stern erhöht.
            	Bewertung.setRating(MainActivity.voegel.anzahlVoegel);
             }  
		}
		return false;
	}
	
}
