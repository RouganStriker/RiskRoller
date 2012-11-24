/*
 * Copyright (C) 2010-2012 Mike Novak <michael.novakjr@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rs.risk.roller;

import rs.risk.roller.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

public class NumberPickerDialog extends AlertDialog implements OnClickListener {
    private OnNumberSetListener mListener;
    private NumberPicker mNumberPicker;

    private int mInitialValue;

    public NumberPickerDialog(Context context, int theme, int initialValue) {
        super(context);
        initializeDialog(context, initialValue, R.attr.startRange, R.attr.endRange);
    }
    
    public NumberPickerDialog(Context context, int theme, int initialValue, int min, int max) {
    	super(context);
        initializeDialog(context, initialValue, min, max);
    }

	
    private void initializeDialog(Context context, int initialValue, int min, int max) {
		mInitialValue = initialValue;

        setButton(BUTTON_POSITIVE, context.getString(R.string.dialog_set_number), this);
        setButton(BUTTON_NEGATIVE, context.getString(R.string.dialog_cancel), (OnClickListener) null);

        LayoutInflater inflater = RiskRollerActivity.getInstance().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_number_picker, null);
        setView(view);

        mNumberPicker = (NumberPicker) view.findViewById(R.id.num_picker);
        mNumberPicker.setRange(min, max);
        mNumberPicker.setCurrent(mInitialValue);
	}

	public void setOnNumberSetListener(OnNumberSetListener listener) {
        mListener = listener;
    }

    public void setPickerRange(int min, int max){
    	mNumberPicker.setRange(min, max);
    }
    
    public void onClick(DialogInterface dialog, int which) {
        if (mListener != null) {
            mListener.onNumberSet(mNumberPicker.getCurrent());
        }
    }

    public interface OnNumberSetListener {
        public void onNumberSet(int selectedNumber);
    }
}
