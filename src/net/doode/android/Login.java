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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/*
 * TODO: the user won't want to enter the apikey manually, how can we make it easier?
 */

public class Login extends Activity {

    private boolean logged     = false;
    private boolean keepLogged = false;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        // verify if the user is already logged or show the login screen
        if (logged || keepLogged) {
            setContentView( R.layout.main );
        } else {
            setContentView( R.layout.login );
            ((Button) findViewById( R.id.btnLogin )).setOnClickListener( btnLoginClick );
        }

    }

    /**
     * Try to log-in the user with the provided credentials.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     */
    public boolean login( String username, String password ) {
        Doode.client.login( username, password );
        setContentView( R.layout.main );
        return true;
    }

    OnClickListener btnLoginClick = new OnClickListener() {
        public void onClick(View view) {
            String username = ((EditText) findViewById( R.id.txtUsername )).toString();
            String password = ((EditText) findViewById( R.id.txtPassword )).toString();
            keepLogged      = ((CheckBox) findViewById( R.id.chbRemember )).isChecked();

            login( username, password );
        }
    };
}
