package com.v1nsai.mibudget;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class miBudget extends Activity {
	
	//private View layout = (View)View.inflate(this, R.layout.mibudget, null);
	private Button setbutton;// = (Button)layout.findViewById(R.id.setbutton);
	private Button madebutton;// = (Button)layout.findViewById(R.id.madebutton);
	private Button spentbutton;// = (Button)layout.findViewById(R.id.spentbutton);
	private EditText addmoney;// = (EditText)layout.findViewById(R.id.addmoney);
	private EditText submoney;// = (EditText)layout.findViewById(R.id.submoney);
	private EditText setbal;// = (EditText)layout.findViewById(R.id.setbal);
	private TextView balanceview;// = (TextView)layout.findViewById(R.id.balanceview);
	
	private static float balance;
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;
	private static final String BALANCE = "balance";
	private static final String PREFS_NAME = "budget";
	DecimalFormat df;
	
	

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mibudget);
        
        balanceview = (TextView)findViewById(R.id.balanceview);
        setbal = (EditText)findViewById(R.id.setbal);
        addmoney = (EditText)findViewById(R.id.addmoney);
        submoney = (EditText)findViewById(R.id.submoney);
        spentbutton = (Button)findViewById(R.id.spentbutton);
        madebutton = (Button)findViewById(R.id.madebutton);
        setbutton = (Button)findViewById(R.id.setbutton);
        
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        df = new DecimalFormat("###.##");

        madebutton.setOnClickListener( new OnClickListener(){
        	public void onClick( View v ){
        		if(addmoney.getVisibility() == View.INVISIBLE )
        			addmoney.setVisibility(View.VISIBLE);
        		else if(addmoney.getVisibility() == View.VISIBLE ){
        			balance += Float.valueOf(df.format(Float.valueOf( addmoney.getText().toString() )));
        			editor.putFloat(BALANCE, balance);
        			editor.commit();
        			balanceview.setText("$" + String.valueOf(settings.getFloat(BALANCE, -9999)));
				}
        		
        	}
        });
        spentbutton.setOnClickListener( new OnClickListener(){
        	public void onClick( View v ){
        		if( submoney.getVisibility() == View.INVISIBLE )
        			submoney.setVisibility(View.VISIBLE);
        		else if( submoney.getVisibility() == View.VISIBLE ){
        			balance -= Float.valueOf(df.format(Float.valueOf( submoney.getText().toString() )));
        			editor.putFloat(BALANCE, balance);
        			editor.commit();
        			balanceview.setText("$" + String.valueOf(settings.getFloat(BALANCE, -1)));
        		}
        	}
        });
        setbutton.setOnClickListener(new OnClickListener(){
        	public void onClick( View v ){
        		if( setbal.getVisibility() == View.INVISIBLE )
        			setbal.setVisibility(View.VISIBLE);
        		else if(setbal.getVisibility() == View.VISIBLE ){
        			balance = Float.valueOf(df.format(Float.valueOf(setbal.getText().toString())));
        			editor.putFloat(BALANCE, balance);
        			editor.commit();
        			balanceview.setText("$" + String.valueOf(settings.getFloat(BALANCE, -1)));
				}
        		
        	}
        });
    }
    
    protected void onPause(){
    	super.onPause();
    	setbal = (EditText)findViewById(R.id.setbal);
        addmoney = (EditText)findViewById(R.id.addmoney);
        submoney = (EditText)findViewById(R.id.submoney);
    	
    	setbal.setVisibility(View.INVISIBLE);
    	addmoney.setVisibility(View.INVISIBLE);
    	submoney.setVisibility(View.INVISIBLE);
    }
    
    protected void onResume(){
    	super.onResume();
    	df = new DecimalFormat("###.##");
    	settings = getSharedPreferences(PREFS_NAME, 0);
        
        if (settings.contains(BALANCE))
        	balance = settings.getFloat(BALANCE, -99999);
        else
        	balance = 0;
        
        balance = Float.valueOf(df.format(balance));
        balanceview = (TextView)findViewById(R.id.balanceview);
        balanceview.setText("$" + String.valueOf(balance));
        
    }
}