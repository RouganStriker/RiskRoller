package rs.risk.roller;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class RiskRollerActivity extends Activity {
	private static RiskRollerActivity instance;
	private ListView combat_log_table;
	private CombatLogAdapter combatAdapter;
	private int attacker_count = 0;
	private int defender_count = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.risk_main);
        instance = this;
        setupButtons();
        setupCombatLogListView();
    }
    
	public static RiskRollerActivity getInstance(){
    	if(instance == null){
    		return new RiskRollerActivity();
    	} else {
    		return instance;
    	}
    }
    
    private void setupCombatLogListView(){
    	combat_log_table = (ListView)findViewById(R.id.logTable);

    	View header = getLayoutInflater().inflate(R.layout.log_row_header, null);
    	combat_log_table.addHeaderView(header);
    	
    	combatAdapter = new CombatLogAdapter(this, R.layout.log_row);
    	combat_log_table.setAdapter(combatAdapter);
    }
    private void setupButtons() {
		findViewById(R.id.roll_one_btn).setOnClickListener(RollBtnController.rollBtnOnClick(RollBtnController.RollFlags.ROLL_ONE));
		findViewById(R.id.roll_two_btn).setOnClickListener(RollBtnController.rollBtnOnClick(RollBtnController.RollFlags.ROLL_TWO));
		findViewById(R.id.roll_three_btn).setOnClickListener(RollBtnController.rollBtnOnClick(RollBtnController.RollFlags.ROLL_THREE));
		findViewById(R.id.roll_custom_btn).setOnClickListener(RollBtnController.rollBtnOnClick(RollBtnController.RollFlags.ROLL_CUSTOM));
		
		findViewById(R.id.reset_btn).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(RiskRollerActivity.getInstance());
				final View dialog_layout = getLayoutInflater().inflate(R.layout.new_combat_dialog,null);
				builder.setView(dialog_layout);
	
				builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						int attacker_count, defender_count;
						try{
							attacker_count = Integer.parseInt(((EditText)dialog_layout.findViewById(R.id.dialog_attacker_count)).getText().toString());
							defender_count = Integer.parseInt(((EditText)dialog_layout.findViewById(R.id.dialog_defender_count)).getText().toString());
							clearFields();
							updateFields(attacker_count, defender_count);
							disableUnusableRollBtns();
						} catch (NumberFormatException e) {
							// Exit dialog, user probably didn't enter any 
						}
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// Exit Dialog
					}
				});
				AlertDialog newCombatDialog = builder.create();
				newCombatDialog.setCanceledOnTouchOutside(true);
				newCombatDialog.show();
			}
		});
	}

	private void clearFields() {
		updateFields(0,0);
		combatAdapter.clear();
		enableRollBtns();
	}
	
	private void enableRollBtns() {
		findViewById(R.id.roll_one_btn).setClickable(true);
		((Button)findViewById(R.id.roll_one_btn)).setTextColor(new Color().BLACK);
		findViewById(R.id.roll_two_btn).setClickable(true);
		((Button)findViewById(R.id.roll_two_btn)).setTextColor(new Color().BLACK);
		findViewById(R.id.roll_three_btn).setClickable(true);
		((Button)findViewById(R.id.roll_three_btn)).setTextColor(new Color().BLACK);
	}
	
	public void disableUnusableRollBtns(){
		if(attacker_count <= 2){
			findViewById(R.id.roll_three_btn).setClickable(false);
			((Button)findViewById(R.id.roll_three_btn)).setTextColor(new Color().GRAY);
		}
		if(attacker_count <= 1){
			findViewById(R.id.roll_two_btn).setClickable(false);
			((Button)findViewById(R.id.roll_two_btn)).setTextColor(new Color().GRAY);
		}
		if(attacker_count <= 0){
			findViewById(R.id.roll_one_btn).setClickable(false);
			((Button)findViewById(R.id.roll_one_btn)).setTextColor(new Color().GRAY);
		}
	}

	public void updateFields(int remainAtk, int remainDef){
		setNumAttackers(remainAtk);
		setNumDefenders(remainDef);
	}
	
	public void addCombatLog(ArrayList<String> combatLogs){
		combatAdapter.add(combatLogs);
		combatAdapter.notifyDataSetChanged();
	}
	
	public int getNumAttackers(){
		return attacker_count;
	}
	
	public int getNumDefenders(){
		return defender_count;
	}
	
	private void setNumAttackers(int new_attacker_count){
		((TextView)findViewById(R.id.attacker_count)).setText("" + new_attacker_count);
		attacker_count = new_attacker_count;
	}
	
	private void setNumDefenders(int new_defender_count){
		((TextView)findViewById(R.id.defender_count)).setText("" + new_defender_count);
		defender_count = new_defender_count;
	}
}