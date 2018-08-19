package no.nibrobb.kasseopptelling_v21;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
	
	private CoordinatorLayout mRootLayout;
	private View mRootView;
	private View mAnotherView;
	
	public SettingsFragment() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		mRootView = inflater.inflate(R.layout.fragment_settings, container, false);
		
		mAnotherView = getActivity().findViewById(android.R.id.content);
		
		Snackbar snack = Snackbar.make(mAnotherView, "SettingsFragment view created!!", Snackbar.LENGTH_SHORT);
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snack.getView().getLayoutParams();
		params.setMargins(0, 0, 0, MainActivity.nav_bar_height);
		snack.getView().setLayoutParams(params);
		snack.show();
		
		mRootLayout = (CoordinatorLayout) mRootView.findViewById(R.id.container);
		return mRootView;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.settings_option_1:
			Snackbar snack = Snackbar.make(mAnotherView, "Snackbar works!", Snackbar.LENGTH_SHORT);
			FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snack.getView().getLayoutParams();
			//CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snack.getView().getLayoutParams();
			params.setMargins(0, 0, 0, MainActivity.nav_bar_height);
			snack.getView().setLayoutParams(params);
			snack.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_options_settings, menu);
	}
	
	
}
