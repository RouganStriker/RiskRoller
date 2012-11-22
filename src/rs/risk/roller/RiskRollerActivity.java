package rs.risk.roller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class RiskRollerActivity extends Activity {
	private static RiskRollerActivity instance;
	private List<CombatResult> combatLog;
	private int attackerCasualties;
	private int defenderCasualties;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        combatLog = new ArrayList<CombatResult>();
        instance = this;
        setupButtons();
    }

    private void setupButtons() {
		findViewById(R.id.roll_one).setOnClickListener(RollController.rollBtnOnClick(RollController.rollFlags.ROLL_ONE));
		findViewById(R.id.roll_three).setOnClickListener(RollController.rollBtnOnClick(RollController.rollFlags.ROLL_THREE));
		findViewById(R.id.roll_all).setOnClickListener(RollController.rollBtnOnClick(RollController.rollFlags.ROLL_ALL));
		
		findViewById(R.id.clear_all).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				clearFields();
			}
		});
	}

	private void clearFields() {
		((TextView)findViewById(R.id.attacker_death)).setText("0");
		((TextView)findViewById(R.id.defender_death)).setText("0");
		((EditText)findViewById(R.id.numAttackers)).setText("");
		((EditText)findViewById(R.id.numDefenders)).setText("");
		attackerCasualties = 0;
		defenderCasualties = 0;
	}
	
	public static RiskRollerActivity getInstance(){
    	if(instance == null){
    		return new RiskRollerActivity();
    	} else {
    		return instance;
    	}
    }
	
	public void updateFields(int atkDeath, int remainAtk, int defDeath, int remainDef){
		attackerCasualties += atkDeath;
		defenderCasualties += defDeath;
		((TextView)findViewById(R.id.attacker_death)).setText(""+attackerCasualties);
		((TextView)findViewById(R.id.defender_death)).setText(""+defenderCasualties);
		((EditText)findViewById(R.id.numAttackers)).setText(""+remainAtk);
		((EditText)findViewById(R.id.numDefenders)).setText(""+remainDef);
	}
	
	public void addCombatLog(CombatResult combatLog){
		this.combatLog.add(combatLog);
	}
}