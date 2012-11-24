package rs.risk.roller;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class RollController {
	public static enum rollFlags{
		ROLL_ONE,ROLL_TWO, ROLL_THREE,ROLL_CUSTOM, NO_ACTION;
	}
	
	public static OnClickListener rollBtnOnClick(final rollFlags rollFlag){
		return new OnClickListener() {
			public void onClick(View v) {
				RiskRollerActivity mainActivity = (RiskRollerActivity) RiskRollerActivity.getInstance();
				int attacker_count = mainActivity.getNumAttackers();
				int defender_count = mainActivity.getNumDefenders();
				
				if(attacker_count <= 0 || defender_count <= 0){
					noCombat();
				} else {
					ArrayList<String>combatLog = CombatController.getResult(attacker_count, defender_count, rollFlag);
					mainActivity.addCombatLog(combatLog);
					mainActivity.disableUnusableRollBtns();
				}
			}
		};
		
	}
	
	private static void noCombat() {
		Toast.makeText(RiskRollerActivity.getInstance(), "No combat", Toast.LENGTH_SHORT).show();
	}
}