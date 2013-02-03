package com.example.buttongame;

import android.app.Activity;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {
	
	public static MediaPlayer mp = null;
	
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /** Called when the user presses the button */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == 0xe110){
	        //this.finish();
	    	//System.exit(0);
	    }
	}
    
    public void displayMessage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	startActivityForResult(intent, 0xe110);
    	playSound(com.example.buttongame.R.raw.introfinal);
    }
    
    //plays a sound file
         private void playSound(int sFile)
       {
            //set up MediaPlayer   
           final int medFile = sFile;
               
              Thread thread = new Thread(new Runnable()       {
                 
              public void run() {
                  mp = MediaPlayer.create(getApplicationContext(), medFile);
                  mp.start();
               }
               });
               thread.start();
                
         }
    
}
