/***************************************************************************
 *   Copyright (C) 2012                                                    *
 *   Author : Stephane LEICHT	stephane at leicht.fr                      *
 *                                                                         *
 *  This program is free software: you can redistribute it and/or modify   *
 *  it under the terms of the GNU General Public License as published by   * 
 *  the Free Software Foundation, either version 3 of the License, or      *
 *  (at your option) any later version.                                    *
 *                                                                         *
 *  This program is distributed in the hope that it will be useful,        * 
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of         *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the          *
 *  GNU General Public License for more details.                           *
 *                                                                         *
 *  You should have received a copy of the GNU General Public License      *
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.  *
 ***************************************************************************/
package fr.leicht.androideip;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import se.opendataexchange.ethernetip4j.clx.SimpleLogixCommunicator;
import se.opendataexchange.ethernetip4j.exceptions.*;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener, OnClickListener{
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private Context mContext;
    private Boolean enExecution;
	private SimpleLogixCommunicator comm;
	private List<Tag> myTags = new ArrayList<Tag>();
	private static Tag Temp_Ext;
	private static Tag intensite_A;
	private static Tag EJP;
	private static Tag Temp_Confort_Quentin;
	private static Tag Temp_Confort_Tim;
	private static Tag Temp_Confort_Salle;
	private static Tag Autorisation_IHM_Plancher;
	private static Tag BP_IHM_Chauffage_Quentin;
	private static Tag BP_IHM_Chauffage_Tim;
	private static Tag BP_IHM_Chauffage_Salle;
	private static Tag Temp_Veranda;
	private static Tag Temp_Tim;
	private static Tag Temp_Salle;
	private static Tag Temp_Quentin;
	private static Tag BP_IHM_Light_SS_Milieu;
	private static Tag BP_IHM_Light_SS_Chenil;
	private static Tag BP_IHM_Light_SS_Garage;
	private static Tag Light_SS_Milieu;
	private static Tag Light_SS_Chenil;
	private static Tag Light_SS_Garage;
	private static Tag BP_IHM_Light_Devant;
	private static Tag BP_IHM_Off_Light_Devant;
	private static Tag Light_Devant;
	private static Tag Time_Reste_Light_Devant;

	public static final int MSG_ERR = 0;
	public static final int MSG_CNF = 1;
	public static final int MSG_IND = 2;
	public static final int MSG_END = 3;
	
	private static Tags Hora;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section3).setTabListener(this));
        
        Thread threadInit = new Thread(new Runnable() {

            public void run() {
            	Message msg = null;
		        try {
		        	comm = new SimpleLogixCommunicator("192.168.1.18", 0xAF12);
		        	//Declaration des Tag
		        	myTags.add(EJP = new Tag(comm,"EJP",30000));
		        	myTags.add(Temp_Ext = new Tag(comm,"Temp_Ext",60000));
		        	myTags.add(Temp_Confort_Quentin = new Tag(comm,"Temp_Confort_Quentin",10000));
		        	myTags.add(Temp_Confort_Salle = new Tag(comm,"Temp_Confort_Salle",10000));
		        	myTags.add(Temp_Confort_Tim = new Tag(comm,"Temp_Confort_Tim",10000));
		        	myTags.add(Temp_Tim = new Tag(comm,"Temp_Tim",60000));
		        	myTags.add(Temp_Salle = new Tag(comm,"Temp_Salle",60000));
		        	myTags.add(Temp_Quentin = new Tag(comm,"Temp_Quentin",60000));
		        	myTags.add(Autorisation_IHM_Plancher = new Tag(comm,"Autorisation_IHM_Plancher",5000));
		        	myTags.add(BP_IHM_Chauffage_Quentin = new Tag(comm,"BP_IHM_Chauffage_Quentin",5000));
		        	myTags.add(BP_IHM_Chauffage_Salle = new Tag(comm,"BP_IHM_Chauffage_Salle",5000));
		        	myTags.add(BP_IHM_Chauffage_Tim = new Tag(comm,"BP_IHM_Chauffage_Tim_Enabled",5000));
		        	myTags.add(intensite_A = new Tag(comm,"intensite_A",1000));
		        	myTags.add(Temp_Veranda = new Tag(comm,"Temp_Veranda",60000));
		        	myTags.add(BP_IHM_Light_SS_Milieu = new Tag(comm,"BP_IHM_Light_SS_Milieu",60000));
		        	myTags.add(Light_SS_Milieu = new Tag(comm,"Light_SS_Milieu",500));
		        	
		        	myTags.add(BP_IHM_Light_SS_Chenil = new Tag(comm,"BP_IHM_Light_SS_Chenil",60000));
		        	myTags.add(Light_SS_Chenil = new Tag(comm,"Light_SS_Chenil",500));
		        	
		        	myTags.add(BP_IHM_Light_SS_Garage = new Tag(comm,"BP_IHM_Light_SS_Garage",60000));
		        	myTags.add(Light_SS_Garage = new Tag(comm,"Light_SS_Garage",500));
		        	
		        	myTags.add(Time_Reste_Light_Devant = new Tag(comm,"Time_Reste_Light_Devant",500));
		        	myTags.add(BP_IHM_Light_Devant = new Tag(comm,"BP_IHM_Light_Devant",50000));
		        	myTags.add(BP_IHM_Off_Light_Devant = new Tag(comm,"BP_IHM_Off_Light_Devant",50000));
		        	myTags.add(Light_Devant = new Tag(comm,"Light_Devant",5000));
		        	
		        	Hora = new Tags(comm,"Hora",7);
		        	
	                enExecution = true;
	                Integer i=0;
	       			while (enExecution) {
	                    try {
	                    	i++;
	                    	for (Tag tag : myTags) {
	                    		tag.updateTag();
	                    	}
	                    	/*Hora.updateTag();
	                    	for (int k=0;k<7;k++) {
	                    		Log.d("[EIP]","Hora :"+Hora.getStringValue(k)+"/"+k);
	                    	}*/
	                    	//Log.d("[AndroidEIP]Main","WRITE Cycle :"+i);
	                    	UpdateIHM("Cycle :"+i);
	                    	
							Thread.sleep(100);
	                    } catch (InterruptedException e) {
	                    	Log.d("[AndroidEIP]Main",e.getMessage());                      	
	                    } catch (PathSegmentException e) {
	                    	Log.d("[AndroidEIP]Main",e.getMessage());
						} catch (ItemNotFoundException e) {
							Log.d("[AndroidEIP]Main",e.getMessage());
						} catch (ProcessingAttributesException e) {
							Log.d("[AndroidEIP]Main",e.getMessage());
						} catch (InsufficientCommandException e) {
							Log.d("[AndroidEIP]Main",e.getMessage());
						} catch (InsufficientNrOfAttributesException e) {
							Log.d("[AndroidEIP]Main",e.getMessage());
						} catch (OtherWithExtendedCodeException e) {
							Log.d("[AndroidEIP]Main",e.getMessage());
						} catch (ResponseBufferOverflowException e) {
							Log.d("[AndroidEIP]Main",e.getMessage());
						} catch (InvalidTypeException e) {
							Log.d("[AndroidEIP]Main",e.getMessage());
						} catch (EmbeddedServiceException e) {
							Log.d("[AndroidEIP]Main",e.getMessage());
						}
	                }
				} catch (IOException e) {
					msg = mHandler.obtainMessage(MSG_ERR, (Object) e.getMessage());
	                mHandler.sendMessage(msg);
					Log.d("[AndroidEIP]Main",e.getMessage());
				} catch (NotImplementedException e) {
					msg = mHandler.obtainMessage(MSG_ERR, (Object) e.getMessage());
	                mHandler.sendMessage(msg);
					Log.d("[AndroidEIP]Main",e.getMessage());		        	
		        } 
        	}
        });
        
        threadInit.start();
       
    }
    
    public void UpdateIHM(final String resultat)
    {
    	//Déposer le Runnable dans la file d'attente de l'UI thread
    	runOnUiThread(new Runnable() {
           @Override
           public void run() {
           		//code exécuté par l'UI thread
        	   Fragment frag = (Fragment) getSupportFragmentManager().findFragmentById(R.id.container);
        	   //Tab CHAUFFAGE
        	   if (frag != null && frag.getTag()=="Tab1") {        		   
        		   TextView msgTextView = (TextView) findViewById(R.id.TextViewTempQuentin);
        		   msgTextView.setText("Température Quentin : "+Temp_Quentin.getStringValue()+" °C");
        		   
        		   msgTextView = (TextView) findViewById(R.id.TextViewTempTim);
        		   msgTextView.setText("Température Tim : "+Temp_Tim.getStringValue()+" °C");
        		   
        		   msgTextView = (TextView) findViewById(R.id.TextViewTempSalle);
        		   msgTextView.setText("Température Salle : "+Temp_Salle.getStringValue()+" °C");
        		   
        		   msgTextView = (TextView) findViewById(R.id.TextViewTempVeranda);
        		   msgTextView.setText("Température Véranda : "+Temp_Veranda.getStringValue()+" °C");
        		   
        		   msgTextView = (TextView) findViewById(R.id.textViewConsigneQuentin);
        		   msgTextView.setText("Consigne : "+Temp_Confort_Quentin.getStringValue("#.0")+" °C");
        		   
        		   msgTextView = (TextView) findViewById(R.id.TextViewConsigneTim);
        		   msgTextView.setText("Consigne : "+Temp_Confort_Tim.getStringValue("#.0")+" °C");
        		   
        		   msgTextView = (TextView) findViewById(R.id.TextViewConsigneSalle);
        		   msgTextView.setText("Consigne : "+Temp_Confort_Salle.getStringValue("#.0")+" °C");
        		   
        		   msgTextView = (TextView) findViewById(R.id.textViewTempExt);
        		   msgTextView.setText("Température Ext : "+Temp_Ext.getStringValue("#.0")+" °C");
        		   
        		   Switch switch1 = (Switch) findViewById(R.id.switch1);
        		   switch1.setChecked(Autorisation_IHM_Plancher.getBoolValue());
        		   
        		   switch1 = (Switch) findViewById(R.id.SwitchChaufQ);
        		   switch1.setChecked(BP_IHM_Chauffage_Quentin.getBoolValue());
        		   
        		   switch1 = (Switch) findViewById(R.id.SwitchChauffS);
        		   switch1.setChecked(BP_IHM_Chauffage_Salle.getBoolValue());
        		   
        		   switch1 = (Switch) findViewById(R.id.SwitchChaufT);
        		   switch1.setChecked(BP_IHM_Chauffage_Tim.getBoolValue());
        		   
        	   }
        	   //Tab Light
        	   if (frag != null && frag.getTag()=="Tab2") {
        		   RadioButton radio1 = (RadioButton) findViewById(R.id.radioButtonSSMilieu);
        		   radio1.setChecked(Light_SS_Milieu.getBoolValue());
        		   radio1 = (RadioButton) findViewById(R.id.radioButtonChenil);
        		   radio1.setChecked(Light_SS_Chenil.getBoolValue());
        		   radio1 = (RadioButton) findViewById(R.id.radioButtonGarage);
        		   radio1.setChecked(Light_SS_Garage.getBoolValue());
        		   Switch switch1 = (Switch) findViewById(R.id.switchLightDevant);
        		   switch1.setChecked(Light_Devant.getBoolValue());
        		   switch1.setText("Lumière Devant : "+Time_Reste_Light_Devant.getStringValue()+" S");
        		   
        	   }
        	   //Tab Divers
        	   if (frag != null && frag.getTag()=="Tab3") {
        		   RadioButton radio2 = (RadioButton) findViewById(R.id.radioButtonEJP);
        		   radio2.setChecked(EJP.getBoolValue());
        		   
        	   }
        	   setTitle("AndroidEIP  "+resultat);
            }
        });
    }
    //**************************************************
    // WARNING : OnClick in XML !!!!
    //**************************************************
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.radioButtonSSMilieu:
            	BP_IHM_Light_SS_Milieu.setValue(true);
            break;
            case R.id.radioButtonChenil:
            	BP_IHM_Light_SS_Chenil.setValue(true);
            break;
            case R.id.radioButtonGarage:
            	BP_IHM_Light_SS_Garage.setValue(true);
            break;
            case R.id.switch1:
            	Switch switch1 = (Switch) findViewById(R.id.switch1);
				Autorisation_IHM_Plancher.setValue(switch1.isChecked());
            break;
            case R.id.SwitchChaufQ:
            	Switch switch2 = (Switch) findViewById(R.id.SwitchChaufQ);
				BP_IHM_Chauffage_Quentin.setValue(switch2.isChecked());
            break;
            case R.id.SwitchChaufT:
            	Switch switch4 = (Switch) findViewById(R.id.SwitchChaufT);
				BP_IHM_Chauffage_Tim.setValue(switch4.isChecked());
            break;
            case R.id.SwitchChauffS:
            	Switch switch5 = (Switch) findViewById(R.id.SwitchChauffS);
				BP_IHM_Chauffage_Salle.setValue(switch5.isChecked());
            break;
            case R.id.switchLightDevant:
            	Switch switch3 = (Switch) findViewById(R.id.switchLightDevant);
				BP_IHM_Off_Light_Devant.setValue(true);
				switch3.setChecked(false);
            break;
            case R.id.buttonLightDevantAdd:
				BP_IHM_Light_Devant.setValue(true);
            break;
            case R.id.ButtonSPQsub:
            	float vFloat = Temp_Confort_Quentin.getFloatValue() - (float) 0.1 ;
				Temp_Confort_Quentin.setValue(vFloat);
            break;
            case R.id.buttonSPQadd:
            	float vFloat2 = Temp_Confort_Quentin.getFloatValue() + (float) 0.1 ;
				Temp_Confort_Quentin.setValue(vFloat2);
            break;
            case R.id.ButtonSPTsub:
            	float vFloat3 = Temp_Confort_Tim.getFloatValue() - (float) 0.1 ;
				Temp_Confort_Tim.setValue(vFloat3);
            break;
            case R.id.ButtonSPTadd:
            	float vFloat4 = Temp_Confort_Tim.getFloatValue() + (float) 0.1 ;
				Temp_Confort_Tim.setValue(vFloat4);
            break;
            case R.id.ButtonSPSsub:
            	float vFloat5 = Temp_Confort_Salle.getFloatValue() - (float) 0.1 ;
				Temp_Confort_Salle.setValue(vFloat5);
            break;
            case R.id.ButtonSPSadd:
            	float vFloat6 = Temp_Confort_Salle.getFloatValue() + (float) 0.1 ;
				Temp_Confort_Salle.setValue(vFloat6);
            break;
        }
    }
    
	protected void onDestroy() {
		enExecution = false;
		super.onDestroy();
	}
	
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    	switch (tab.getPosition()) {
    	case 0:
    		//fragment = 
    		getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, new Tab1Fragment(),"Tab1")
            .commit();
    		break;
    	case 1:
    		getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, new Tab2Fragment(),"Tab2")
            .commit();
    		break;
    	case 2:
    		getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, new Tab3Fragment(),"Tab3")
            .commit();
    		break;
        default:
        	getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, new Tab1Fragment(),"Tab1")
            .commit();
    	}
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    
    final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String text2display = null;
            switch (msg.what) {
            case MSG_IND:
            	//
                break;
            case MSG_ERR:
                text2display = (String) msg.obj;
                Toast.makeText(mContext, "Error: " + text2display,Toast.LENGTH_LONG).show();
                break;
            case MSG_END:
                //
                break;    
            case MSG_CNF:
                text2display = (String) msg.obj;
                Toast.makeText(mContext, "Info: " + text2display,Toast.LENGTH_LONG).show();
                break;
            default: // 
                break;
            }
        }
    };

}
