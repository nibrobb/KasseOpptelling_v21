package no.nibrobb.kasseopptelling_v21;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
	
	static int nav_bar_height;
	
	private CalculatorFragment calculatorFragment;
	private OppgjorFragment oppgjorFragment;
	private SettingsFragment settingsFragment;
	private Toolbar mToolbar;
	
	private BottomNavigationView navigation;
	
	private static AccessibilityManager accessibilityManager;
	
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_calculator:
					setFragment(calculatorFragment);
					return true;
				case R.id.navigation_oppgjor:
					setFragment(oppgjorFragment);
					return true;
				case R.id.navigation_settings:
					setFragment(settingsFragment);
					return true;
			}
			return false;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mToolbar = findViewById(R.id.action_bar);
		setSupportActionBar(mToolbar);
		
		navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		
		/** Sets nav_bar_height to the correct pixel-value
		 * */
		int resourceId = getResources().getIdentifier("design_bottom_navigation_height", "dimen", getPackageName());
		if (resourceId > 0) {
			nav_bar_height = getResources().getDimensionPixelSize(resourceId);
		}
		
		calculatorFragment = new CalculatorFragment();
		oppgjorFragment = new OppgjorFragment();
		settingsFragment = new SettingsFragment();
		
		accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
		
		// Sets CalculatorFragment as the default fragment
		setFragment(calculatorFragment);
	}
	
	static void forceSnackbar(Snackbar snackbar){
		// Only force when necessary, and don't animate when TalkBack or similar services are enabled
		boolean shouldForceAnimate = false;
		//boolean shouldForceAnimate = !accessibilityManager.isEnabled() && accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_SPOKEN).size() == 0;
		// Alternativ metode
		if(accessibilityManager.isEnabled()){
			Log.d("AccessibilityManager", "is enabled");
			if(accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_SPOKEN).size() == 0){
				Log.d("FEEDBACK_SPOKEN", "Is equal to 0");
				if(!accessibilityManager.isTouchExplorationEnabled()){
					Log.d("Touch Exploration", "Is not enabled");
					shouldForceAnimate = true;
				}
			}
		} else {
			Log.d("AccessibilityManager", "is not enabled");
			shouldForceAnimate = true;
		}
		
		if(shouldForceAnimate){
			try {
				Field accManagerField = BaseTransientBottomBar.class.getDeclaredField("mAccessibilityManager");
				accManagerField.setAccessible(true);
				AccessibilityManager accManager = (AccessibilityManager) accManagerField.get(snackbar);
				@SuppressWarnings("JavaReflectionMemberAccess") Field isEnabledField = AccessibilityManager.class.getDeclaredField("mIsEnabled");
				isEnabledField.setAccessible(true);
				isEnabledField.setBoolean(accManager, false);
				accManagerField.set(snackbar, accManager);
			} catch (Exception e) {
				Log.d("Snackbar", "Reflection error: " + e.toString());
			}
		}
	}
	
	private void setFragment(@NonNull Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.main_frame, fragment);
		fragmentTransaction.commit();
	}
	
	public void buttonClick(View v) {
		calculatorFragment.calculatorFragmentButtonClick(v);
	}
}

