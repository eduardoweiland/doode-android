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

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {

    public static final int RESULT_ERROR = RESULT_FIRST_USER + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ((Button) findViewById(R.id.btnLogin)).setOnClickListener(this);
    }

    private void doLogin() {
        final String username = ((EditText) findViewById(R.id.txtUsername)).getText().toString();
        final ProgressDialog wait = ProgressDialog.show(this,
                getString(R.string.wait),
                getString(R.string.logging),
                true, false);

        Doode.client.requestApiKey(username, Doode.deviceId, new OnXMLRPCResult() {
            public void onSuccess(Object result) {
                wait.dismiss();
                String apikey = (String) result;
                Doode.doodeDB.saveLoginInfo(username, apikey);
                finish();
            }

            public void onFault(int faultCode, String faultString) {
                wait.dismiss();
                finish();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                doLogin();
                break;
        }
    }

}
