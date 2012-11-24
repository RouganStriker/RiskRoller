package rs.risk.roller;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class RollBtnController {
	public static enum RollFlags{
		ROLL_ONE,ROLL_TWO, ROLL_THREE,ROLL_CUSTOM, NO_ACTION;
	}
	
	public static OnClickListener rollBtnOnClick(final RollFlags rollFlag){
		return new OnClickListener() {
			public void onClick(View v) {
				final RiskRollerActivity mainActivity = (RiskRollerActivity) RiskRollerActivity.getInstance();
				int attacker_count = mainActivity.getNumAttackers();
				final int defender_count = mainActivity.getNumDefenders();
				
				if(attacker_count <= 0 || defender_count <= 0){
					noCombat();
				} else {
					if(rollFlag == RollFlags.ROLL_CUSTOM) {
						NumberPickerDialog picker_dialog = new NumberPickerDialog(mainActivity, -1, attacker_count, 0, attacker_count);
						picker_dialog.setTitle(R.string.dialog_picker_title);
						picker_dialog.setOnNumberSetListener(new NumberPickerDialog.OnNumberSetListener() {
							public void onNumberSet(int selectedNumber) {
								if(selectedNumber > 0) {
									ArrayList<String>combatLog = CombatController.getResult(selectedNumber, defender_count, rollFlag);
									mainActivity.addCombatLog(combatLog);
									mainActivity.disableUnusableRollBtns();
								}
							}
						});
						picker_dialog.setCanceledOnTouchOutside(true);
						picker_dialog.show();
					} else {
						ArrayList<String>combatLog = CombatController.getResult(attacker_count, defender_count, rollFlag);
						mainActivity.addCombatLog(combatLog);
						mainActivity.disableUnusableRollBtns();
					}
				}
			}
		};
		
	}
	
	private static void noCombat() {
		Toast.makeText(RiskRollerActivity.getInstance(), "No combat", Toast.LENGTH_SHORT).show();
	}
}