package com.lbh.light;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;

public class L_lightActivity extends Activity {
	
	private Camera camera = null;   
    boolean isLight;
    PowerManager powerManager = null;   
    WakeLock wakeLock = null; 
   
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        OpenLightOn();
        
        powerManager = (PowerManager)this.getSystemService(POWER_SERVICE);
        this.wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN) {
		if(!isLight)
		isLight = true;
		else isLight = false;
		if(isLight)
		CloseLightOff();
		else
			OpenLightOn();
		}
		return false;
	}
    

	@Override
	protected void onResume() {
		this.wakeLock.acquire();
		super.onResume();
	}

	@Override
	protected void onPause() {
		CloseLightOff();
		this.wakeLock.release(); 
		super.onPause();
	}

	private void OpenLightOn()    {   
	    if ( null == camera )   
	    {   
	        camera = Camera.open();       
	    }   
	       
	    Camera.Parameters parameters = camera.getParameters();                
	    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);     
	    camera.setParameters( parameters );              
	    camera.autoFocus( new Camera.AutoFocusCallback (){     
	    public void onAutoFocus(boolean success, Camera camera) {     
	            }                    
	        });    
	    camera.startPreview();       
	}   
	   
	private void CloseLightOff()   {   
	    if ( camera != null )   
	    {   
	        camera.stopPreview();   
	        camera.release();   
	        camera = null;   
	    }          
	}  
  
}