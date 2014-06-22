/**
 * 
 */
package com.pipehype;

import java.io.IOException;

import android.media.MediaRecorder;

public class DB {
	
        static final private double FILTER = 0.6;
        private MediaRecorder recorder = null;
        private double dB = 0.0;
        double mEMA = 0.0;

        public void start() {
                if (recorder == null) {
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    recorder.setOutputFile("/dev/null"); 
                    try {
						recorder.prepare();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
                    recorder.start();
                    mEMA = 0.0;
                }
        }
        
        //Amplitude auslesen
        public double getAmplitude() {
                if (recorder != null){
                        return  (recorder.getMaxAmplitude()/2700.0);
                }
                else{
                        return 0;
                }
        }
        //Umrechnen in dB
        public double getAmplitudeEMA() {
                double amplitude = getAmplitude();    
                mEMA = FILTER * amplitude + (1.0 - FILTER) * mEMA;  
                dB = 20 * Math.log10(mEMA/0.0002);
                return dB;
        }
        
        //Stoppen des Recorders...
        public void stop() {
                if (recorder != null) {
                        recorder.stop();       
                        recorder.release();
                        recorder = null;
                }
        }
 
    
}