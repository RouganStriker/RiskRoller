package rs.risk.roller;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class RollController {
	public static enum rollFlags{
		ROLL_ONE,ROLL_THREE,ROLL_ALL, NO_ACTION;
	}
	
	public static OnClickListener rollBtnOnClick(final rollFlags rollFlag){
		return new OnClickListener() {
			public void onClick(View v) {
				RiskRollerActivity mainView = (RiskRollerActivity) RiskRollerActivity.getInstance();
				int numAttack = 0, numDefend = 0;
				
				String input = ((EditText) mainView.findViewById(R.id.numAttackers)).getText().toString();
				if(input != null && !input.equals("")){
					numAttack = Integer.parseInt(input);
				} else {
					emptyField();
					return;
				}
				
				input = ((EditText) mainView.findViewById(R.id.numDefenders)).getText().toString();
				if(input != null && !input.equals("")){
					numDefend = Integer.parseInt(input);
				} else {
					emptyField();
					return;
				}
				
				if(numAttack == 0 || numDefend == 0){
					noCombat();
				} else {
					rollDice(numAttack, numDefend, rollFlag);
				}
			}
		};
		
	}
	
	private static void rollDice(int numAttack, int numDefend, rollFlags rollFlag){
		CombatResult combatLog= DiceController.getResult(numAttack, numDefend, rollFlag);
		RiskRollerActivity.getInstance().addCombatLog(combatLog);
	}
	
	private static void noCombat() {
		// TODO Auto-generated method stub
		Toast.makeText(RiskRollerActivity.getInstance(), "No combat", Toast.LENGTH_SHORT).show();
	}


	private static void emptyField() {
		// TODO Auto-generated method stub
		Toast.makeText(RiskRollerActivity.getInstance(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
	}
}