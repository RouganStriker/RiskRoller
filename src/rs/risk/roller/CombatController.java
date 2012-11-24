package rs.risk.roller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import rs.risk.roller.RollBtnController.RollFlags;
import android.util.Log;

public class CombatController {
	private static Random rand = new Random();
	private static int numAtk;
	private static int numDef;
	private static ArrayList<String> combatLog;
	
	public static ArrayList<String> getResult(int numAttackers, int numDefenders, RollFlags action){
		numAtk = RiskRollerActivity.getInstance().getNumAttackers();
		numDef = numDefenders;
		combatLog = new ArrayList<String>();
		
		int attackerStopValue = numAtk - numAttackers;
		switch(action){
			case ROLL_CUSTOM:
				Log.d("ROLLS", "roll all");
				while(numAtk > attackerStopValue && numDef > 0){
					combat(numAtk,numDef);
				}
				break;
			case ROLL_THREE:
				combat(3,numDef);
				break;
			case ROLL_TWO:
				combat(2,numDef);
				break;
			case ROLL_ONE:
				combat(1,numDef);
				break;
			default:
				break;
		}
		RiskRollerActivity.getInstance().updateFields(numAtk, numDef);
		return combatLog;
	}
	
	private static void combat(int attackers, int defenders){
		ArrayList<String> current_log = new ArrayList<String>();
		Integer[] attackerDices = (attackers >= 3)? getDiceRolls(3):getDiceRolls(attackers);	//1 or 2
		Integer[] defendersDices = (defenders >= 2)? getDiceRolls(2):getDiceRolls(1);	//1
		current_log.add("Attackers:{" + Arrays.toString(attackerDices) + ", Defenders:{" + Arrays.toString(defendersDices) + "}");
		for(int i = 0; i < defendersDices.length && i < attackerDices.length; i++){
			if(attackerDices[i] > defendersDices[i]) {
				current_log.add("    Attacker(" + attackerDices[i] + ") beats Defender(" + defendersDices[i] + ") army");
				numDef--;
			} else {
				current_log.add("    Defender(" + defendersDices[i] + ") beats Attacker(" + attackerDices[i] + ") army");
				numAtk--;
			}
		}
		current_log.addAll(combatLog);
		combatLog = current_log;
	}
	
	private static Integer[] getDiceRolls(int numOfRolls){
		Integer[] diceRolls = new Integer[numOfRolls];
		for(int i = 0; i < numOfRolls; i++) {
			diceRolls[i] = rand.nextInt(5) + 1;
		}
		Arrays.sort(diceRolls, Collections.reverseOrder());
		return diceRolls;
	}
}
