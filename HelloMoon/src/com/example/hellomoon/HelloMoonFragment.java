package com.example.hellomoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonFragment extends Fragment {
	private AudioPlayer mPlayer=new AudioPlayer();
	private Button mPlayButton;
	private Button mStopButton;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 保存设备配置变化前的状态 ，这个值默认设置为 false，
		 * 表明其不会被保留。为true时会一直保留并在需要时传递给activity重建
		 * */
		setRetainInstance(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup parent,
			Bundle savedInstanceState){
		View v=inflater.inflate(R.layout.fragment_hellomoon, parent,false);
		mPlayButton=(Button)v.findViewById(R.id.hellomoon_playButton);
		mPlayButton.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mPlayer.play(getActivity());
				
			}
		});
		mStopButton=(Button)v.findViewById(R.id.hellomoon_stopButton);
		mStopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPlayer.stop();
			}
		});
		return v;
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		mPlayer.stop();
	}
}
