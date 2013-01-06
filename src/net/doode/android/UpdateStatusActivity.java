/**************************************************************************
 *  This file is part of the Doode Android project                        *
 *  Copyright (C) 2012 Eduardo Weiland                                    *
 *  duduweiland@users.sourceforge.net                                     *
 *                                                                        *
 *  This program is free software: you can redistribute it and/or modify  *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  This program is distributed in the hope that it will be useful,       *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>  *
 **************************************************************************/

package net.doode.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * Activity class for posting a new status update.
 *
 * @author Eduardo Weiland
 */
public class UpdateStatusActivity extends SherlockActivity implements OnClickListener {

    public static final String TAG = "UpdateStatusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_status);

        ((Button) findViewById(R.id.btnSend)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnCancel)).setOnClickListener(this);

        Intent intent = getIntent();
        String action = intent.getAction();

        if (Intent.ACTION_SEND == action) {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((EditText) findViewById(R.id.txtStatusMessage)).setText(text);
        }
    }

    public void onClick(View view) {
        final String status = ((EditText) findViewById(R.id.txtStatusMessage)).getText().toString();
        switch (view.getId()) {
            // send the activity
            case R.id.btnSend:
                Log.d(TAG, "Send status update");
                final ProgressDialog wait = ProgressDialog.show(this,
                        getString(R.string.wait),
                        getString(R.string.sending_update),
                        true, false);

                Doode.client.updateProfileStatus(status, new OnXMLRPCResult() {
                    public void onSuccess(Object result) {
                        Log.d(TAG, "Success: " + result.toString());
                        Toast.makeText(getApplicationContext(), "Update posted", Toast.LENGTH_SHORT).show();
                        wait.dismiss();
                        finish();
                    }

                    public void onFault(int faultCode, String faultString) {
                        // TODO: that's a fault we need to take care and inform the user
                        Log.d(TAG, "Fault: " + faultCode + " - " + faultString);
                        wait.dismiss();
                    }
                });
                break;

            // cancel posting this activity
            case R.id.btnCancel:
                Log.d(TAG, "Canceling status update post...");
                finish();
                break;
        }
    }
}
