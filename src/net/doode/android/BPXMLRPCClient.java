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

import android.os.AsyncTask;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

public class BPXMLRPCClient {

    final private XMLRPCClient client;

    private String _username;
    private String _password;
   // private Object _params[];

    /**
     * Initializes the client.
     *
     * @param url The URL to connect
     */
    public BPXMLRPCClient( String url ) {
        client = new XMLRPCClient( url );
    }

    /**
     * Initializes the client.
     *
     * @param url The URL to connect
     */
    public BPXMLRPCClient( URL url ) {
        client = new XMLRPCClient( url );
    }

    /**
     * Initializes the client.
     *
     * @param uri The URL to connect
     */
    public BPXMLRPCClient( URI uri ) {
        client = new XMLRPCClient( uri );
    }

    /**
     * Initializes the client and tries to log-in.
     *
     * @param url      The URL to connect
     * @param username The user's login name
     * @param password The user's password
     */
    public BPXMLRPCClient( String url, String username, String password ) throws XMLRPCException {
        client = new XMLRPCClient( url );
        login( username, password );
    }

    /**
     * Initializes the client and tries to log-in.
     *
     * @param url      The URL to connect
     * @param username The user's login name
     * @param password The user's password
     */
    public BPXMLRPCClient( URL url, String username, String password ) throws XMLRPCException {
        client = new XMLRPCClient( url );
        login( username, password );
    }

    /**
     * Initializes the client and tries to log-in.
     *
     * @param url      The URL to connect
     * @param username The user's login name
     * @param password The user's password
     */
    public BPXMLRPCClient( URI uri, String username, String password ) throws XMLRPCException {
        client = new XMLRPCClient( uri );
        login( username, password );
    }

    /**
     * Try to log-in the user.
     *
     * @param username The user's login name
     * @param password The user's password
     */
    public void login( String username, String password ) {
        _username = username;
        _password = password;

        final class LoginTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPostExecute(Boolean result) {
                Doode.logged = result;
            }
            
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    client.call( "bp.verifyConnection", "teste", "f52dcbfad467e34c8b5d56987f0ebd1d" );
                    return true;
                } catch (XMLRPCException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }
        
        new LoginTask().execute();
    }

    public void updateProfileStatus( String status ) throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "status", status );

        client.call( "bp.updateProfileStatus", _username, _password, data );
    }

    public void getActivity( String scope, int max ) throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "scope", scope );
        data.put( "max"  , max   );

        client.call( "bp.getActivity", _username, _password, data );
    }

    public void getActivity( String scope ) throws XMLRPCException {
        getActivity( scope, 35 );
    }

    public void updateExternalBlogPostStatus( String status, String title, String url, String permalink, String postId ) throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "status"           , status    );
        data.put( "blogtitle"        , title     );
        data.put( "blogurl"          , url       );
        data.put( "blogpostpermalink", permalink );
        data.put( "blogpostid"       , postId    );

        client.call( "bp.updateExternalBlogPostStatus", _username, _password, data );
    }

    public void deleteExternalBlogPostStatus( String postId, String activityId) throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "blogpostid", postId     );
        data.put( "activityid", activityId );

        client.call( "bp.deleteExternalBlogPostStatus", _username, _password, data );
    }

    public void getMyFriends() throws XMLRPCException {
        client.call( "bp.getMyFriends", _username, _password );
    }

    public void getMyFollowers() throws XMLRPCException {
        client.call( "bp.getMyFollowers", _username, _password );
    }

    public void getMyFollowing() throws XMLRPCException {
        client.call( "bp.getMyFollowing", _username, _password );
    }

    public void getMyGroups() throws XMLRPCException {
        client.call( "bp.getMyGroups", _username, _password );
    }

    public void getNotifications() throws XMLRPCException {
        client.call( "bp.getNotifications", _username, _password );
    }

}
