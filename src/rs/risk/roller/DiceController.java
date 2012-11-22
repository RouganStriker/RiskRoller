package rs.risk.roller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rs.risk.roller.RollController.rollFlags;
import android.util.Log;

public class DiceController {
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
				if(numAtk >= 3) {
					Log.d("ROLLS", "roll all");
					while(numAtk > 0 && numDef > 0){
						if(numAtk >= 3){
							multiCombat(false, numDef == 1);
						} else if(numAtk == 2) {
							multiCombat(true, numDef == 1);
						} else {
							singleCombat();
						}
					}
					break;
				}
				// Else fall through
			case ROLL_THREE:
				Log.d("ROLLS", "roll three");
				if(numAtk >= 3){
					multiCombat(false, numDef == 1);
					break;
				} else if(numAtk == 2) {
					multiCombat(true, numDef == 1);
					break;
				}
			case ROLL_ONE:
				Log.d("ROLLS", "roll one");
				singleCombat();
				break;
		}
		RiskRollerActivity.getInstance().updateFields(combatLog.getAtkDeath(), numAtk, combatLog.getDefDeath(), numDef);
		combatLog.setRollLog(rollLog);
		return combatLog;
	}
	
	private static void multiCombat(boolean atkHasOnlyTwoLeft, boolean defHasOnlyOneLeft){
		Random r = new Random();
		int redDie1 = r.nextInt(6);
		int redDie2 = r.nextInt(6);
		int redDie3 = r.nextInt(6);
		int blueDie1 = r.nextInt(6);
		int blueDie2 = r.nextInt(6);
		Log.d("DICE","Red dies: " + redDie1 + " : " + redDie2 + " : " + redDie3);
		Log.d("DICE","Blue dies: " + blueDie1 + " : " + blueDie2);
		
		if(atkHasOnlyTwoLeft){
			singleCombat(redDie1 > redDie2 ? redDie1 : redDie2, blueDie1 > blueDie2 ? blueDie1 : blueDie2);
			if(!defHasOnlyOneLeft)
				singleCombat(redDie1 > redDie2 ? redDie2 : redDie1, blueDie1 > blueDie2 ? blueDie2 : blueDie1);
		} else {
			int highRed = redDie1;
			int lowRed = redDie2 > redDie3 ? redDie2 : redDie3;
			int otherRed = redDie2 > redDie3 ? redDie3 : redDie2;
			if(highRed >= lowRed){
				// highRed is highest, lowRed is second highest, order is correct
			} else if (highRed >= otherRed) {
				// highRed is lower than lowRed, but highRed is higher than otherRed
				otherRed = highRed;
				highRed = lowRed;
				lowRed = otherRed;
			} else {
				// highRed is smallest
				highRed = lowRed;
				lowRed = otherRed;
			}
			singleCombat(highRed, blueDie1 > blueDie2 ? blueDie1 : blueDie2);
			if(!defHasOnlyOneLeft)
				singleCombat(lowRed, blueDie1 > blueDie2 ? blueDie2 : blueDie1);
			
		}
	}
	
	private static void singleCombat(){
		singleCombat(rand.nextInt(6), rand.nextInt(6));
	}
	private static void singleCombat(int red, int blue){
		rollLog.add("Atk: " + red + " Def: " + blue);
		
		if(red > blue){
			combatLog.addDefDeath();
			numDef--;
		} else {
			combatLog.addAtkDeath();
			numAtk--;
		}
		
		Log.d("ROLLS", "Atk: " + red + " Def: " + blue + " New Totals: " + numAtk + " vs " + numDef);
	}
}
