package rs.risk.roller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import rs.risk.roller.RollController.rollFlags;
import android.util.Log;

public class CombatController {
	private static Random rand = new Random();
	private static int numAtk;
	private static int numDef;
	private static ArrayList<String> combatLog;
	
	public static ArrayList<String> getResult(int numAttackers, int numDefenders, rollFlags action){
		numAtk = numAttackers;
		numDef = numDefenders;
		combatLog = new ArrayList<String>();
		
		switch(action){
			case ROLL_CUSTOM:
				Log.d("ROLLS", "roll all");
				while(numAtk > 0 && numDef > 0){
					combat(numAtk,numDef);
				}
				break;
			case ROLL_THREE:
				Log.d("ROLLS", "roll three");
				combat(3,numDef);
				break;
			case ROLL_TWO:
				Log.d("ROLLS", "roll three");
				combat(2,numDef);
				break;
			case ROLL_ONE:
				Log.d("ROLLS", "roll one");
				combat(1,numDef);
				break;
			default:
				Log.d("ROLLS", "Error.");
				break;
		}
		RiskRollerActivity.getInstance().updateFields(numAtk, numDef);
		return combatLog;
	}
	
	private static void combat(int attackers, int defenders){
		Integer[] attackerDices = (attackers >= 3)? getDiceRolls(3):getDiceRolls(attackers);	//1 or 2
		Integer[] defendersDices = (defenders >= 2)? getDiceRolls(2):getDiceRolls(1);	//1
		combatLog.add("Attackers:{" + Arrays.toString(attackerDices) + ", Defenders:{" + Arrays.toString(defendersDices) + "}");
		for(int i = 0; i < defendersDices.length && i < attackerDices.length; i++){
			if(attackSuccessful(attackerDices[i], defendersDices[i])) {
				combatLog.add("    Attacker(" + attackerDices[i] + ") beats Defender(" + defendersDices[i] + ") army");
				numDef--;
			} else {
				combatLog.add("    Defender(" + defendersDices[i] + ") beats Attacker(" + attackerDices[i] + ") army");
				numAtk--;
			}
		}
	}
	
	private static boolean attackSuccessful(int atkRoll, int defRoll){
		if(atkRoll > defRoll){
			return true;
		} else {
			return false;
		}
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
