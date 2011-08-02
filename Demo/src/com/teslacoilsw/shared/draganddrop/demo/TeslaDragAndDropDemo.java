
package com.teslacoilsw.shared.draganddrop.demo;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.teslacoilsw.shared.draganddrop.R;
import com.teslacoilsw.shared.draganddrop.TouchInterceptorListView;

public class TeslaDragAndDropDemo extends ListActivity {
	private ListView mListView;
	
	ArrayList<String> mArrayListOptions = new ArrayList<String>(Arrays.asList(new String[] {
			"1. One", "2. Two", "3. Three", "4. Four", "5. Five", "Elephant"
	}));
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mListView = getListView();
        mListView.setOnCreateContextMenuListener(this);
        
        ((TouchInterceptorListView) mListView).setDropListener(mDropListener);
        mListView.setAdapter(new MyArrayAdapter(getApplicationContext(), 
        		R.layout.dragrow, mArrayListOptions));
        mListView.setCacheColorHint(0);
        ((TouchInterceptorListView) mListView).setDropListener(mDropListener);
        mListView.setDivider(null);
        mListView.setSelector(R.drawable.list_selector_background);
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
        // clear the listeners so we won't get any more callbacks
    	TouchInterceptorListView lv = (TouchInterceptorListView) getListView();
        lv.setDropListener(null);
    }
    
    private static class ViewHolder {
        TextView label;
    }
    private class MyArrayAdapter extends ArrayAdapter<String>{ 
    	private int mLayoutId;
    	
    	MyArrayAdapter(Context context, int row_layout_id, ArrayList<String> arraylist) {
			super(context, row_layout_id, arraylist);
			mLayoutId = row_layout_id;
		}
		
		@Override
		public View getView(int position, View convertView,
							ViewGroup parent)
		{
			View row = convertView;
			ViewHolder holder;
			
			if (row == null) {													
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(mLayoutId, parent, false);
				
				holder = new ViewHolder();
				holder.label = (TextView) row.findViewById(R.id.textView);
				row.setTag(holder);
			} else {
				holder = (ViewHolder) row.getTag();
			}
			
			holder.label.setText(getItem(position));
			
			return(row);
		}
		
		@Override
		public long getItemId (int position) {
			return position;
		}
    }
    
    private TouchInterceptorListView.DropListener mDropListener =
        new TouchInterceptorListView.DropListener() {
        public void drop(int from, int to) {
        	MyArrayAdapter adapter = (MyArrayAdapter) getListView().getAdapter();
        	String object = adapter.getItem(from);
        	adapter.remove(object);
        	adapter.insert(object, to);
        }
    };
}