package no.nibrobb.kasseopptelling_v21;


import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.lang.reflect.Field;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorFragment extends Fragment {
	
	private int i1000, i500, i200, i100, i50 = 0;    //Initialize notes
	private int i20, i10, i5, i1 = 0;                //Initialize coins
	
	private EditText et1000, et500, et200, et100, et50;        //Set up all variables for EditText objects
	private EditText et20, et10, et5, et1;
	
	private TextView twTotal;
	
	private FloatingActionButton mFAB;
	
	private CoordinatorLayout mCalcView;
	
	private AccessibilityManager accessibilityManager;
	
	private View.OnClickListener mFabClickListener = new View.OnClickListener() {
		@Override
		//@SuppressWarnings("ConstantConditions") // Fixe all masinga om NullPointerException
		public void onClick(View v) {
			// getActivity().findViewById(android.R.id.content)
			//Snackbar snackbar = Snackbar.make(v, "Animated snackbar, yaay", Snackbar.LENGTH_SHORT);
			Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main_calculator_view), "Animated snackbar, yaay", Snackbar.LENGTH_SHORT);
			MainActivity.forceSnackbar(snackbar);
			//CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams();
			//FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
			//params.setMargins(0, 0, 0, MainActivity.nav_bar_height);
			//snackbar.getView().setLayoutParams(params);
			
			snackbar.show();
			
			/*Snackbar mySnackbar = Snackbar.make(mCalcViewgetActivity().findViewById(R.id.main_calculator_view), "FAB clicked!",
					Snackbar.LENGTH_SHORT);
			//FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mySnackbar.getView().getLayoutParams();
			CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mySnackbar.getView().getLayoutParams();
			//params.setMargins(0, 0, 0, MainActivity.nav_bar_height);
			mySnackbar.getView().setLayoutParams(params);
			mySnackbar.show();*/
		}
	};
	
	// Reason for not being local variables: planning to use these values in OppgjørFragment
	private int sum1000, sum500, sum200, sum100, sum50, sum20, sum10, sum5, sum1;
	private int total;
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Sets this view to have an Options Menu (three dots)
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_calculator, container, false);
		mCalcView = getActivity().findViewById(R.id.main_calculator_view);
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_options_calculator, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.clear_values:
				clearValues();
				//final View mViewForSnackbar = getActivity().findViewById(android.R.id.content);
				final CoordinatorLayout mViewForSnackbar = getActivity().findViewById(R.id.main_calculator_view);
				final Snackbar undoSnackbar = Snackbar.make(mViewForSnackbar, "Verdier tilbakestilt",
						Snackbar.LENGTH_LONG);
				undoSnackbar.setAction("Angre", new View.OnClickListener() {
					@Override
					// This could go wrong, I am sure of it...
					public void onClick(View v) {
						
						Snackbar.make(v, "Angret", Snackbar.LENGTH_SHORT).show();
						
						// Do something when UNDO is clicked
					}
				});
				undoSnackbar.setActionTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
				//FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) undoSnackbar.getView().getLayoutParams();
				//params.setMargins(0, 0, 0, MainActivity.nav_bar_height);
				//undoSnackbar.getView().setLayoutParams(params);
				undoSnackbar.show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		et1000 = view.findViewById(R.id.tusenAnt);
		et500 = view.findViewById(R.id.femHundreAnt);
		et200 = view.findViewById(R.id.toHundreAnt);
		et100 = view.findViewById(R.id.hundreAnt);
		et50 = view.findViewById(R.id.femtiAnt);
		
		et20 = view.findViewById(R.id.tjueAnt);
		et10 = view.findViewById(R.id.tiAnt);
		et5 = view.findViewById(R.id.femAnt);
		et1 = view.findViewById(R.id.enAnt);
		
		twTotal = view.findViewById(R.id.total);
		
		mFAB = view.findViewById(R.id.fab);
		mFAB.setOnClickListener(mFabClickListener);
		
		updateTotal();
		
		initListeners();
		
	}
	
	private void initListeners() {
		/** Add listener to 1000*/
		et1000.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (et1000.getText().length() != 0)
					i1000 = Integer.parseInt(et1000.getText().toString());
				// Oppdater total-beløp
				updateTotal();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		/** Add listener to 500*/
		et500.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (et500.getText().length() != 0)
					i500 = Integer.parseInt(et500.getText().toString());
				updateTotal();
			}
		});
		
		/** Add listener to 200*/
		et200.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (et200.getText().length() != 0)
					i200 = Integer.parseInt(et200.getText().toString());
				updateTotal();
			}
		});
		
		/** Add listener to 100*/
		et100.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (et100.getText().length() != 0)
					i100 = Integer.parseInt(et100.getText().toString());
				updateTotal();
			}
		});
		
		/** Add listener to 50*/
		et50.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (et50.getText().length() != 0)
					i50 = Integer.parseInt(et50.getText().toString());
				updateTotal();
			}
		});
		
		/** Add listener to 20*/
		et20.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (et20.getText().length() != 0)
					i20 = Integer.parseInt(et20.getText().toString());
				updateTotal();
			}
		});
		
		/** Add listener to 10*/
		et10.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (et10.getText().length() != 0)
					i10 = Integer.parseInt(et10.getText().toString());
				updateTotal();
			}
		});
		
		/** Add listener to 5*/
		et5.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (et5.getText().length() != 0)
					i5 = Integer.parseInt(et5.getText().toString());
				updateTotal();
			}
		});
		
		/** Add listener to 1*/
		et1.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (et1.getText().length() != 0)
					i1 = Integer.parseInt(et1.getText().toString());
				updateTotal();
			}
		});
	}
	
	private void updateTotal() {
		twTotal.setText(getResources().getString(R.string.totalt) + sumAllValues() + " kr");
	}
	
	public void buttonClick(View v) {
		switch (v.getId()) {
			case R.id.minus1000:
				if (i1000 > 0) {
					i1000 -= 1;
					if (i1000 == 0) {
						et1000.setText("");
					} else {
						et1000.setText("" + i1000);
					}
				}
				break;
			case R.id.pluss1000:
				i1000 += 1;
				et1000.setText("" + i1000);
				break;
			case R.id.minus500:
				if (i500 > 0) {
					i500 -= 1;
					if (i500 == 0) {
						et500.setText("");
					} else {
						et500.setText("" + i500);
					}
				}
				break;
			case R.id.pluss500:
				i500 += 1;
				et500.setText("" + i500);
				break;
			case R.id.minus200:
				if (i200 > 0) {
					i200 -= 1;
					if (i200 == 0) {
						et200.setText("");
					} else {
						et200.setText("" + i200);
					}
				}
				break;
			case R.id.pluss200:
				i200 += 1;
				et200.setText("" + i200);
				break;
			case R.id.minus100:
				if (i100 > 0) {
					i100 -= 1;
					if (i100 == 0) {
						et100.setText("");
					} else {
						et100.setText("" + i100);
					}
				}
				break;
			case R.id.pluss100:
				i100 += 1;
				et100.setText("" + i100);
				break;
			case R.id.minus50:
				if (i50 > 0) {
					i50 -= 1;
					if (i50 == 0) {
						et50.setText("");
					} else {
						et50.setText("" + i50);
					}
				}
				break;
			case R.id.pluss50:
				i50 += 1;
				et50.setText("" + i50);
				break;
			case R.id.minus20:
				if (i20 > 0) {
					i20 -= 1;
					if (i20 == 0) {
						et20.setText("");
					} else {
						et20.setText("" + i20);
					}
				}
				break;
			case R.id.pluss20:
				i20 += 1;
				et20.setText("" + i20);
				break;
			case R.id.minus10:
				if (i10 > 0) {
					i10 -= 1;
					if (i10 == 0) {
						et10.setText("");
					} else {
						et10.setText("" + i10);
					}
				}
				break;
			case R.id.pluss10:
				i10 += 1;
				et10.setText("" + i10);
				break;
			case R.id.minus5:
				if (i5 > 0) {
					i5 -= 1;
					if (i5 == 0) {
						et5.setText("");
					} else {
						et5.setText("" + i5);
					}
				}
				break;
			case R.id.pluss5:
				i5 += 1;
				et5.setText("" + i5);
				break;
			case R.id.minus1:
				if (i1 > 0) {
					i1 -= 1;
					if (i1 == 0) {
						et1.setText("");
					} else {
						et1.setText("" + i1);
					}
				}
				break;
			case R.id.pluss1:
				i1 += 1;
				et1.setText("" + i1);
				break;
		}
	}
	
	private int sumAllValues() {
		sum1000 = i1000 * 1000;
		sum500 = i500 * 500;
		sum200 = i200 * 200;
		sum100 = i100 * 100;
		sum50 = i50 * 50;
		sum20 = i20 * 20;
		sum10 = i10 * 10;
		sum5 = i5 * 5;
		sum1 = i1;
		
		total = (sum1000 + sum500 + sum200 + sum100 + sum50 + sum20 + sum10 + sum5 + sum1);
		
		return total;
	}
	
	/**
	 * Resets all values of EditTextViews and accompanying integer variables
	 */
	private void clearValues() {
		i1000 = 0;
		et1000.setText("");
		i500 = 0;
		et500.setText("");
		i200 = 0;
		et200.setText("");
		i100 = 0;
		et100.setText("");
		i50 = 0;
		et50.setText("");
		i20 = 0;
		et20.setText("");
		i10 = 0;
		et10.setText("");
		i5 = 0;
		et5.setText("");
		i1 = 0;
		et1.setText("");
		
		updateTotal();
		
		et1000.requestFocus();
	}
}
