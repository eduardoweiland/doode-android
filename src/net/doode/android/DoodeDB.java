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

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DoodeDB {

    // DB structure
    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME    = "doode";

    // Table: settings
    private static final String SETTINGS_TABLE        = "settings";
    private static final String CREATE_TABLE_SETTINGS = "create table if not exists "
            + SETTINGS_TABLE + " (id integer primary key autoincrement, username text, "
            + "apikey text);";

    private SQLiteDatabase db;

    /**
     * Constructor. Open a database connection and prepare it for use.
     * 
     * @param ctx
     */
    public DoodeDB( Context ctx ) {
        db = ctx.openOrCreateDatabase( DATABASE_NAME, 0, null );
        prepareDB();
    }
    
    /**
     * Prepare the database for use.
     */
    private void prepareDB() {
        int version = db.getVersion();

        // is a new installation
        if (version < 1) {
            db.execSQL( CREATE_TABLE_SETTINGS );
            db.setVersion( DATABASE_VERSION );
        }

        if ( db.needUpgrade( DATABASE_VERSION ) ) {
            // future...
        }
    }

    /**
     * Saves login information on the database.
     * 
     * @param username The user's username
     * @param apikey   The user's apikey
     * @return True if the information was saved, false otherwise.
     * @see ::loadLoginInfo()
     */
    public boolean saveLoginInfo( String username, String apikey ) {
        ContentValues values = new ContentValues();
        values.put( "username", username );
        values.put( "apikey",   apikey );

        return ( db.insert( SETTINGS_TABLE, null, values ) > 0 );
    }

    /**
     * Load previously saved login info.
     * 
     * @return A vector with username and apikey or null if not found. 
     * @see ::saveLoginInfo()
     */
    public Vector<String> loadLoginInfo() {
        Cursor c = db.query( SETTINGS_TABLE, new String[] {"username", "apikey"},
                             null, null, null, null, null );

        c.moveToFirst();

        Vector<String> returnVector = new Vector<String>();
        if ( c.getString(0) != null ) {
            returnVector.add( c.getString(0) );
            returnVector.add( c.getString(1) );
        } else {
            returnVector = null;
        }
        c.close();

        return returnVector;
    }
}
