package fr.leicht.androideip;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Tab3Fragment extends Fragment {
	private MainActivity myActivity = null;
	
	@Override
	public void onAttach(Activity myActivity) {
		super.onAttach(myActivity);
		this.myActivity = (MainActivity)myActivity;
	}

	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        return (LinearLayout) inflater.inflate(R.layout.tab3fragment, container, false);
    }

}
