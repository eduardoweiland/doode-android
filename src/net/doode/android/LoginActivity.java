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

import java.util.HashMap;

import org.xmlrpc.android.XMLRPCException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.login );

        ((Button) findViewById( R.id.btnLogin )).setOnClickListener( btnLoginClick );
    }

    OnClickListener btnLoginClick = new OnClickListener() {
        public void onClick(View view) {
            String username = ((EditText) findViewById( R.id.txtUsername )).toString();
            RequestApiKeyTask task = new RequestApiKeyTask();
            task.execute( username, Doode.deviceId );
        }
    };

    private class RequestApiKeyTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            if ( result != null) {
                // save option in DB
                finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HashMap<?,?> result = (HashMap<?,?>) Doode.client.call( "bp.requestApiKey", params[0], params[1] );

                if ( (Boolean) result.get( "confirmation" ) ) {
                    return (String) result.get( "apikey" );
                }
            } catch (XMLRPCException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
