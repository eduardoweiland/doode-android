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

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MainView extends TabActivity {
	
	@Override
	public void onCreate( Bundle savedInstanceState ) {
	    super.onCreate( savedInstanceState );

	    // load tabs layout
	    setContentView( R.layout.main );

	    Resources res   = getResources();
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;            // Resusable TabSpec for each tab
	    Intent intent;                   // Reusable Intent for each tab

	    // Add UpdateStatusActivity tab
	    intent = new Intent().setClass( this, UpdateStatusActivity.class );
	    spec   = tabHost.newTabSpec  ( "update_status" )
	    		        .setIndicator( res.getString(R.string.update_status_title) )
	                    .setContent  ( intent );
	    tabHost.addTab(spec);

	    // Add MentionsActivity tab
	    intent = new Intent().setClass( this, MentionsActivity.class );
	    spec   = tabHost.newTabSpec  ( "mentions" )
	    		        .setIndicator( res.getString(R.string.mentions_title) )
	                    .setContent  ( intent );
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
}
