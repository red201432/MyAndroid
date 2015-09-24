package com.example.criminalintent;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.internal.widget.AdapterViewCompat.AdapterContextMenuInfo;
import android.support.v7.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment  extends ListFragment{
	private ArrayList<Crime> mCrimes;
	private static final String TAG="CrimeListFragment"; 
	private static final int REQUEST_CRIME=1;
	 private boolean mSubtitleVisible;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crime_title_label);
		setHasOptionsMenu(true);
		mCrimes=CrimeLab.get(getActivity()).getCrimes();
		
	
//		ArrayAdapter<Crime> adapter=new ArrayAdapter<Crime>(getActivity(),
//				android.R.layout.simple_list_item_1,mCrimes);
		CrimeAdapter adapter=new CrimeAdapter(mCrimes);
		setListAdapter(adapter);
		setRetainInstance(true);
		mSubtitleVisible=false;
	}
	@Override
	public void onListItemClick(ListView l,View v,int position,long id){
//		Crime c=(Crime)(getListAdapter()).getItem(position);
		Crime c=((CrimeAdapter)getListAdapter()).getItem(position);
//		Log.d(TAG, c.getmTitle()+ " was Clicked");
		Intent i=new Intent(getActivity(),CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getmId());
		startActivity(i);
		//startActivityForResult(i, REQUEST_CRIME);
	}
	@Override
	public void onActivityResult(int requestCode,int reusltCode,Intent data){
		
	}
	
	private class CrimeAdapter extends ArrayAdapter<Crime>{
		public CrimeAdapter(ArrayList<Crime> crimes){
			super(getActivity(), 0,crimes);
		}
		@Override 
		public View getView(int position,View convertView,ViewGroup parent){			
			if(convertView==null){
				convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
			}
			
			Crime c=getItem(position);
			TextView titleTextView=(TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getmTitle());
			TextView dateTextView=(TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
			dateTextView.setText(c.getmDate().toString());
			CheckBox solvedCheckBox=(CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.ismSolved());
			
			return convertView;
		}
	}
	/*一般来说要保证fragment视图得到刷新，在onResume()方法内更新代码是最安全的*/
	@Override
	public void onResume(){
		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	/*生成菜单选项*/
	@Override
	public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);	//传入菜单文件的资源ID填充到Menu实例中。
	}
	
	/*响应菜单事件*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.menu_item_new_crime://点击添加按钮，
			Crime crime=new Crime();
			CrimeLab.get(getActivity()).add(crime);
			Intent i=new Intent(getActivity(),CrimePagerActivity.class);
			i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
			startActivityForResult(i, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			if(getActivity().getActionBar().getSubtitle()==null){
			getActivity().getActionBar().setSubtitle(R.string.subtitle);
			item.setTitle(R.string.hide_subtitle);
			} else {
				getActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
			}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {   
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
            }
        }
        
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(listView);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            
                @Override
				public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
					// TODO Auto-generated method stub
                	
                	  switch (item.getItemId()) {
                      case R.id.menu_item_delete_crime:
                          CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
                          CrimeLab crimeLab = CrimeLab.get(getActivity());
                          for (int i = adapter.getCount() - 1; i >= 0; i--) {
                              if (getListView().isItemChecked(i)) {
                                  crimeLab.deleteCrime(adapter.getItem(i));
                              }
                          }
                          mode.finish(); 
                          adapter.notifyDataSetChanged();
                          return true;
                      default:
                          return false;
				}
                }
				@Override
				public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					MenuInflater inflater=mode.getMenuInflater();
					inflater.inflate(R.menu.crime_list_item_context, menu);
					return true;
				}

				@Override
				public void onDestroyActionMode(android.view.ActionMode mode) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id,
						boolean checked) {
					// TODO Auto-generated method stub
					
				}
            });            
        }
        return v;
    }
    
	
	@Override
	public void onCreateContextMenu(ContextMenu menu,View v,ContextMenuInfo menuInfo)
	{
		getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
	}
	
	@Override
	public boolean  onContextItemSelected(MenuItem item){
		AdapterContextMenuInfo info=(AdapterContextMenuInfo)item.getMenuInfo();
		int position=info.position;
		CrimeAdapter adapter=(CrimeAdapter)getListAdapter();
		Crime crime=adapter.getItem(position);
		
		switch (item.getItemId()) {
		case R.id.menu_item_delete_crime:
			CrimeLab.get(getActivity()).deleteCrime(crime);
			adapter.notifyDataSetChanged();
			return true;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
}
