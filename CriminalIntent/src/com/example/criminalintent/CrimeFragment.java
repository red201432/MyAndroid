package com.example.criminalintent;

import java.util.Date;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.UUID;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class CrimeFragment extends Fragment {
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;

	public static final String EXTRA_CRIME_ID="com.example.criminalintent.crime_id";
	public static final String DIALOG_DATE="date";
	private static final int REQUEST_DATE=0;
	
	private ImageButton mPhotoButton;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		/*获取CrimeActivity的intent*/
		//UUID crimeId=(UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
		/*从fragment获取id， 给viewPager*/
		UUID crimeId=(UUID)getArguments().getSerializable(EXTRA_CRIME_ID);		
		mCrime=CrimeLab.get(getActivity()).getCrime(crimeId);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState){
		View view=inflater.inflate(R.layout.fragment_crime, parent,false);
		mPhotoButton=(ImageButton)view.findViewById(R.id.crime_ImageButton);
		mPhotoButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(),CrimeCameraFragment.class);
				startActivity(i);
			}
		});
		
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
			if(NavUtils.getParentActivityName(getActivity())!=null){
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);//让应用图标转变为按钮，并显示一个向左的图标
			
			}
		}		
		mTitleField=(EditText)view.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getmTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				mCrime.setmTitle(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		mDateButton=(Button)view.findViewById(R.id.crime_date);
		mDateButton.setText(mCrime.getmDate().toString());
		//mDateButton.setEnabled(false);
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm=getActivity().getSupportFragmentManager();
				//DatePickerFragment dialog=new DatePickerFragment();
				DatePickerFragment dialog=DatePickerFragment.newInstance(mCrime.getmDate());
				//将CrimeFragment设置为目标fragment
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
		});
		mSolvedCheckBox=(CheckBox)view.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.ismSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mCrime.setmSolved(isChecked);
			}
		});		
		return view;
	}
	/*
	 * 必须要在fragment创建后，添加给activity前完成将argument bundle 给fragment
	 * */
	public static CrimeFragment newInstance(UUID crimeId){
		Bundle args=new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		
		CrimeFragment fragment=new CrimeFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onActivityResult(int requestCode,int resultCode,Intent data){
		if(resultCode!=Activity.RESULT_OK) return ;
		if(requestCode==REQUEST_DATE){
			Date date=(Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mCrime.setmDate(date);
			mDateButton.setText(mCrime.getmDate().toString());
		}
	}	
	
	/*保存数据到本地文件*/
	@Override
	public void onPause(){
		super.onPause();
		CrimeLab.get(getActivity()).saveCrimes();
	}

}
