package rs.risk.roller;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CombatLogAdapter extends ArrayAdapter<String> {
	private ArrayList<String> combat_logs;
	
	public CombatLogAdapter(Context context, int textViewResourceId, ArrayList<String> combat_logs) {
		super(context, textViewResourceId, combat_logs);
		this.combat_logs = combat_logs;
	}
	
	public CombatLogAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.combat_logs = new ArrayList<String>();
	}

	@Override
	public int getCount() {
		return combat_logs.size();
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View log_row_view = convertView;
            if (log_row_view == null) {
                LayoutInflater vi = RiskRollerActivity.getInstance().getLayoutInflater();
                log_row_view = vi.inflate(R.layout.log_row, null);
            }
            
            TextView row_tv = ((TextView)log_row_view.findViewById(R.id.combat_log_row));
            row_tv.setText(combat_logs.get(position));
            if(position == 0 || position == 1 || (position == 2 && combat_logs.get(position).charAt(0) != 'A')){
            	row_tv.setBackgroundResource(R.color.light_grey);
            } else {
            	row_tv.setBackgroundResource(R.color.dark_grey);
            }
            
            return log_row_view;
    }

	@Override
	public void clear() {
		super.clear();
		combat_logs.clear();
		notifyDataSetChanged();
	}
	
	public void add(ArrayList<String> combat_logs) {
		//Swapping order of logs, newest on top
		ArrayList<String> new_combat_logs = combat_logs;
		new_combat_logs.addAll(this.combat_logs);
		this.combat_logs = new_combat_logs;
	}
}
