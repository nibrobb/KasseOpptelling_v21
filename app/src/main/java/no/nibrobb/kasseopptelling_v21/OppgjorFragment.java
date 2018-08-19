package no.nibrobb.kasseopptelling_v21;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class OppgjorFragment extends Fragment {
	
	private View v;
	
	private FrameLayout mRoot;
	
	public OppgjorFragment(){
		// Empty because yeah...
	}
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_options_oppgjor, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.options_oppgjor_1:
				Snackbar ssnack = Snackbar.make(getActivity().findViewById(android.R.id.content), "Something", Snackbar.LENGTH_SHORT);
				/*CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
						ssnack.getView().getLayoutParams();*/
				FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)
						ssnack.getView().getLayoutParams();
				params.setMargins(0, 0, 0, MainActivity.nav_bar_height);
				ssnack.getView().setLayoutParams(params);
				ssnack.show();
				
				return true;
		}
		return super.onOptionsItemSelected(item);
		// Alternativ:
		// return false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		v = inflater.inflate(R.layout.fragment_oppgjor, container, false);
		
		mRoot = v.findViewById(R.id.frame_oppgjor);
		
		Snackbar mySnackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "OppgjørFregment created! (maybe)",
				Snackbar.LENGTH_LONG);
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)
				mySnackbar.getView().getLayoutParams();
		params.setMargins(0, 0, 0, MainActivity.nav_bar_height);
		mySnackbar.getView().setLayoutParams(params);
		mySnackbar.show();
		
		return v;
	}
}
