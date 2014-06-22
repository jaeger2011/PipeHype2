package com.pipehype;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class Selection extends ActionBarActivity {

	//Bundle zum Übergeben von Werten an die Record.java.
	Bundle Level = new Bundle();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selection);
		
		//Custom Font
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/dyspepsia.ttf");
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setTypeface(tf);
        TextView b1 = (TextView) findViewById(R.id.button1);
        b1.setTypeface(tf);
        TextView b2 = (TextView) findViewById(R.id.button2);
        b2.setTypeface(tf);
        TextView b3 = (TextView) findViewById(R.id.button3);
        b3.setTypeface(tf);
        TextView b4 = (TextView) findViewById(R.id.button4);
        b4.setTypeface(tf);
        
        //Buttons für die Auswahl der Schwierigkeitsgrade 1-3 werden gesetzt.
		Button level1 = (Button) findViewById(R.id.button1);
		level1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				gotoLevel1();
			}
		});
		Button level2 = (Button) findViewById(R.id.button4);
		level2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				gotoLevel2();
			}
		});
		Button level3 = (Button) findViewById(R.id.button3);
		level3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				gotoLevel3();
			}
		});
		Button menu = (Button) findViewById(R.id.button2);
		menu.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				back_to_main();
			}
		});

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	//Level 1 wird aufgerufen.
	private void gotoLevel1(){
		//Die dem Level entsprechenden Werte werden in das Bundle "Level" gepackt und der darunter erstellten Instanz der Record.java übergeben.
		Level.putInt("Level", 20);
		Level.putInt("Stufe", 1);
		Intent intent = new Intent(this, Record.class);
    	intent.putExtras(Level);
		startActivity(intent);
    	this.finish();
    }
	
	public void gotoLevel2(){
		Level.putInt("Level", 30);
		Level.putInt("Stufe", 2);
		Intent intent = new Intent(this, Record.class);
		intent.putExtras(Level);
    	startActivity(intent);
    	this.finish();
    }
	
	public void gotoLevel3(){
		Level.putInt("Level", 45);
		Level.putInt("Stufe", 3);
		Intent intent = new Intent(this, Record.class);
		intent.putExtras(Level);
    	startActivity(intent);
    	this.finish();
    }
	
	private void back_to_main(){
    	this.finish();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selection, menu);
		return true;
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
			View rootView = inflater.inflate(R.layout.fragment_selection,
					container, false);
			return rootView;
		}
	}

}
