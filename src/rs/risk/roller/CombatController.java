package rs.risk.roller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import rs.risk.roller.RollController.rollFlags;
import sun.misc.Sort;
import android.util.Log;

public class CombatController {
	private static Random rand = new Random();
	private static int numAtk;
	private static int numDef;
	private static CombatResult combatLog;
	private static List<String> rollLog;
	
	public static CombatResult getResult(int numAttackers, int numDefenders, rollFlags action){
		numAtk = numAttackers;
		numDef = numDefenders;
		combatLog = new CombatResult(numAtk, 0, numDef, 0, action);
		rollLog = new ArrayList<String>();
		
		switch(action){
			case ROLL_ALL:
				Log.d("ROLLS", "roll all");
				while(numAtk > 0 && numDef > 0){
					combat(numAtk,numDef);
				}
				break;
			case ROLL_THREE:
				Log.d("ROLLS", "roll three");
				combat(numAtk,numDef);
				break;
			case ROLL_ONE:
				Log.d("ROLLS", "roll one");
				combat(1,numDef);
				break;
		}
		RiskRollerActivity.getInstance().updateFields(combatLog.getAtkDeath(), numAtk, combatLog.getDefDeath(), numDef);
		combatLog.setRollLog(rollLog);
		return combatLog;
	}
	
	private static void combat(int attackers, int defenders){
		Integer[] attackerDices = (attackers >= 3)? getDiceRolls(3):getDiceRolls(attackers);	//1 or 2
		Integer[] defendersDices = (defenders >= 2)? getDiceRolls(2):getDiceRolls(1);	//1
		Log.d("COMBAT", "Attackers:{" + Arrays.toString(attackerDices) + ", Defenders:{" + Arrays.toString(defendersDices) + "}");
		for(int i = 0; i < defendersDices.length && i < attackerDices.length; i++){
			if(attackSuccessful(attackerDices[i], defendersDices[i])) {
				Log.d("COMBAT", "Attacker(" + attackerDices[i] + ") beats Defender(" + defendersDices[i] + ") army");
				combatLog.addDefDeath();
				numDef--;
			} else {
				Log.d("COMBAT", "Defender(" + defendersDices[i] + ") beats Attacker(" + attackerDices[i] + ") army");
				combatLog.addAtkDeath();
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
