package com.example.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.SurfaceHolder;

public class AudioPlayer {
	private MediaPlayer mPlayer;
	public void stop() {
		if(mPlayer!=null){
			mPlayer.release();
			mPlayer=null;
		}
	}
	//��ֻ֤��һ��MediaPlayerʵ��
	public  void  play(Context c) {
		stop();
		
		//mPlayer=MediaPlayer.create(c, R.raw.one_small_step);//����������������Ƶ
		mPlayer=MediaPlayer.create(c, R.raw.one_small_step);
		mPlayer.setOnCompletionListener(new OnCompletionListener() {			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				stop();
			}
		});
		mPlayer.start();
	}
}