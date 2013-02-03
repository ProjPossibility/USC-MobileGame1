package com.example.buttongame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.media.MediaPlayer;

public class Accelerometer extends Activity implements SensorEventListener{
	
	private float mLastX, mLastY, mLastZ;
	private boolean mInitialized;
	private MediaPlayer mp = null; 
	private MediaPlayer mp2 = null; // one being used
	
	Thread thread;
	Timer t;
	
	Random ranGen;
	int turns;
	ArrayList<Integer> moves;
	ArrayList<Boolean> moveMade;

	private SensorManager mSensorManager;

	private Sensor mAccelerometer;

	private final float NOISE = (float) 2.0;
	
	public int time = 1;

	 @SuppressLint("NewApi")
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	
	     // Get the message from the intent
	     Intent intent = getIntent();
	     String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	     
	     //super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_accelerometer);
		    mInitialized = false;
		    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		    mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		    

		    
		    t = new Timer();
		    playSound(com.example.buttongame.R.raw.back);
		    t.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							
							if (time % 10== 0){
								playClip(com.example.buttongame.R.raw.passitfinal);
										
							}
							
							else if(time >= 3 && time < 25 && time%3 == 0){
								playClip(com.example.buttongame.R.raw.flipe);
							}
							
							else if (time >= 5 && time < 25 && time % 5 == 0)
							{
								playClip(com.example.buttongame.R.raw.shakeit);
							}
							
							TextView tv = (TextView) findViewById(R.id.textView1);
							tv.setText(String.valueOf(time));
							time += 1;
							
							
						}
						
					});
				}
	        	
	        }, 0, 1000);
	     
	 }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case android.R.id.home:
	            NavUtils.navigateUpFromSameTask(this);
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	    
	    
	    protected void onResume() {
	    	super.onResume();
	    	mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	    }
	    protected void onPause() {
	    	super.onPause();
	    	mSensorManager.unregisterListener(this);
	    }
	    @Override
	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    	// can be safely ignored for this demo
	    }
	    	
	    @Override
	    public void onSensorChanged(SensorEvent event) {
			

	    	
	    	//int j = 0;

	    	TextView tvX= (TextView)findViewById(R.id.x_axis);
	    	TextView tvY= (TextView)findViewById(R.id.y_axis);
	    	TextView tvZ= (TextView)findViewById(R.id.z_axis);
	    	ImageView iv = (ImageView)findViewById(R.id.image);
	    	float x = event.values[0];
	    	float y = event.values[1];
	    	float z = event.values[2];	 
	    	
	    	if (!mInitialized) {
	    		mLastX = x;
	    		mLastY = y;
	    		mLastZ = z;
	    		tvX.setText("0.0");
	    		tvY.setText("0.0");
	    		tvZ.setText("0.0");
	    		mInitialized = true;
	    	}  
	    	else {
	    		
	    		float deltaX = Math.abs(mLastX - x);
	    		float deltaY = Math.abs(mLastY - y);
	    		float deltaZ = Math.abs(mLastZ - z);
	    		if (deltaX < NOISE) deltaX = (float)0.0;
	    		if (deltaY < NOISE) deltaY = (float)0.0;
	    		if (deltaZ < NOISE) deltaZ = (float)0.0;
	    		mLastX = x;
	    		mLastY = y;
	    		mLastZ = z;
	    		tvX.setText(Float.toString(deltaX));
	    		tvY.setText(Float.toString(deltaY));
	    		tvZ.setText(Float.toString(deltaZ));
	    		iv.setVisibility(View.VISIBLE);
	    		if (deltaX > deltaY) {
	    			iv.setImageResource(R.drawable.horizontal);
	    	
	    		}
	    			if(time%2 == 1 && deltaY > deltaX){
	    				playClip(com.example.buttongame.R.raw.win2);
	    			}
	    	
	    			else if(time%5 == 1 && deltaX > deltaY){
	    				playClip(com.example.buttongame.R.raw.win2);
	    			}
	    			
	    			else if(time >= 27){ 
	    				playClip(com.example.buttongame.R.raw.gameoverfinal); 
	    				t.cancel(); t.purge(); this.finish(); }
	    			
	    			
	    		if (deltaY > deltaX) {
	    			iv.setImageResource(R.drawable.vertical);
	    		} 
	    		else {
	    			iv.setVisibility(View.INVISIBLE);
	    		}
	    		
	    		//j++;
	    		}
	    	}
	    	
	    	 private void playSound(int sFile)
	         {
	             //set up MediaPlayer   
	            final int medFile = sFile;
	                
	            mp = MediaPlayer.create(getApplicationContext(), medFile);
	            //mp.setLooping(true);
	            mp.start();
	                 
	          }
	    
	    	 private void playClip(int sFile)
	         {
	             //set up MediaPlayer   
	            final int medFile = sFile;
	               
	             mp2 = MediaPlayer.create(getApplicationContext(), medFile);
	             try{
	             mp2.prepare();
	             }
	             catch(Exception e){
	            	 //do shit
	             }
	             mp2.start();
	             //mp2.reset();
	          }
	}





