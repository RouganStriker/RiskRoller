package rs.risk.roller;

import java.util.ArrayList;
import java.util.List;

import rs.risk.roller.RollController.rollFlags;

public class CombatResult {
	private int initalAttackers;
	private int attackDeath;
	private int initalDefenders;
	private int defendDeath;
	private rollFlags action;
	private List<String> rollLog;
	
	public CombatResult(){
		initalAttackers = 0;
		attackDeath = 0;
		initalDefenders = 0;
		defendDeath = 0;
		action = rollFlags.NO_ACTION;
		rollLog = new ArrayList<String>();
	}
	
	public CombatResult(int initialAttackers, int attackDeath, int initialDefenders, int defendDeath, rollFlags action){
		this.initalAttackers = initialAttackers;
		this.attackDeath = attackDeath;
		this.initalDefenders = initialDefenders;
		this.defendDeath = defendDeath;
		this.action = action;
		rollLog = new ArrayList<String>();
	}
	
	public void addAtkDeath(){ 
		attackDeath++;
	}
	
	public void addDefDeath(){
		defendDeath++;
	}
	
	public void setRollLog(List<String> rollLog){
		this.rollLog = rollLog;
	}
	
	public int getAtkDeath(){
		return attackDeath;
	}
	
	public int getDefDeath(){
		return defendDeath;
	}
	
	public rollFlags getAction(){
		return action;
	}
	
	public String getLog(){
		String rollFlag = "NONE";
		if(action == rollFlags.ROLL_ONE){
			rollFlag = "ROLL ONE";
		} else if(action == rollFlags.ROLL_THREE){
			rollFlag = "ROLL THREE";
		} else if(action == rollFlags.ROLL_ALL) {
			rollFlag = "ROLL ALL";
		}
		return "[" + rollFlag + "] : Atk - " + initalAttackers + ">" + (initalAttackers - attackDeath) + " Def - " + initalDefenders + ">" + (initalDefenders - defendDeath);
	}
	
}
