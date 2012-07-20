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

import android.app.Application;
import android.content.Context;

import net.doode.android.BPXMLRPCClient;
import net.doode.android.DoodeDB;

/**
 * Main application's class.
 *
 * @author Eduardo Weiland
 */
public class Doode extends Application {

	private static Doode instance; 

    public static BPXMLRPCClient client;
    public static DoodeDB        doodeDB;
    public static boolean        logged;

    // DEBUG: need to connect to an external device to test
    final private String apiUrl = "http://192.168.0.47/blog/wp-content/plugins/buddypress-xmlrpc-receiver/bp-xmlrpc.php";

    /**
     * Constructor. Initialize the XML-RPC client and load settings.
     */
    @Override
    public void onCreate() {
    	instance = this;
    	
        // Creates the XMLRPCClient and connects to doode.net
        client  = new BPXMLRPCClient( apiUrl );
        // TODO: need to call this inside an activity?
        doodeDB = new DoodeDB( this );

    	super.onCreate();
    }
    
    /**
     * Constructor. Initialize the XML-RPC client and load settings.
     *
    public Doode() {
    	instance = this;
    	
        // Creates the XMLRPCClient and connects to doode.net
        client  = new BPXMLRPCClient( apiUrl );
        // TODO: need to call this inside an activity?
        //doodeDB = new DoodeDB( this );
    }*/

    public static void onRequestDone( Boolean success, Object result ) {
    	// TODO: implement. there is no better way?
    }

    public static Context getAppContext() {
    	return instance;
    }

}
