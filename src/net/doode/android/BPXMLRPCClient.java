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

public class BPXMLRPCClient extends XMLRPCClient {

    private String mUserName;
    private String mService;
    private String mApiKey;

    /**
     * Initializes the client.
     *
     * @param url The URL to connect
     */
    public BPXMLRPCClient( String url ) {
        super( url );
    }

    /**
     * Initializes the client.
     *
     * @param url The URL to connect
     */
    public BPXMLRPCClient( URL url ) {
        super( url );
    }

    /**
     * Initializes the client.
     *
     * @param uri The URL to connect
     */
    public BPXMLRPCClient( URI uri ) {
        super( uri );
    }

    /**
     * Initializes the client and tries to log-in.
     *
     * @param url      The URL to connect
     * @param username The user's login name
     */
    public BPXMLRPCClient( String url, String username, String service, String apikey ) throws XMLRPCException {
        super( url );
        login( username, service, apikey );
    }

    /**
     * Initializes the client and tries to log-in.
     *
     * @param url      The URL to connect
     * @param username The user's login name
     */
    public BPXMLRPCClient( URL url, String username, String service, String apikey ) throws XMLRPCException {
        super( url );
        login( username, service, apikey );
    }

    /**
     * Initializes the client and tries to log-in.
     *
     * @param url      The URL to connect
     * @param username The user's login name
     */
    public BPXMLRPCClient( URI uri, String username, String service, String apikey ) throws XMLRPCException {
        super( uri );
        login( username, service, apikey );
    }

    public void requestApiKey( String username, String service ) throws XMLRPCException {
        mUserName = username;
        mService  = service;

        final class RequestApiKeyTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String result) {
                mApiKey = result;
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    HashMap<?,?> result = (HashMap<?,?>) call( "bp.requestApiKey", params[0], params[1] );

                    if ( (Boolean) result.get( "confirmation" ) ) {
                        return (String) result.get( "apikey" );
                    }
                } catch (XMLRPCException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        new RequestApiKeyTask().execute( username, service );
    }

    /**
     * Try to log-in the user.
     *
     * @param username The user's login name
     */
    public void login( String username, String service, String apikey ) {
        mUserName = username;
        mService  = service;
        mApiKey   = apikey;

        final class LoginTask extends AsyncTask<String, Void, Boolean> {
            @Override
            protected void onPostExecute(Boolean result) {
                Doode.logged = result;
            }

            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    call( "bp.verifyConnection", params[0], params[1], params[2] );
                    return true;
                } catch (XMLRPCException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }

        new LoginTask().execute( username, service, apikey );
    }

    public boolean updateProfileStatus( String status ) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "status", status );

        final class UpdateProfileStatusTask extends AsyncTask<Map<?, ?>, Void, Object> {
            @Override
            protected void onPostExecute( Object result ) {
                //Boolean success = (Boolean) ((HashMap<?,?>) result).get( "confirmation" );
                //Doode.onRequestDone( success, result );
            }

            @Override
            protected Object doInBackground(Map<?, ?>... params) {
                Object result = null;
                try {
                    result = call( "bp.updateProfileStatus", mUserName, mService, mApiKey, params[0] );
                } catch (XMLRPCException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }

        new UpdateProfileStatusTask().execute( data );
        return true;
    }

    public void getActivity( String scope, int max ) throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "scope", scope );
        data.put( "max"  , max   );

        call( "bp.getActivity", mUserName, mService, mApiKey, data );
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

        call( "bp.updateExternalBlogPostStatus", mUserName, mService, mApiKey, data );
    }

    public void deleteExternalBlogPostStatus( String postId, String activityId) throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "blogpostid", postId     );
        data.put( "activityid", activityId );

        call( "bp.deleteExternalBlogPostStatus", mUserName, mService, mApiKey, data );
    }

    public void getMyFriends() throws XMLRPCException {
        call( "bp.getMyFriends", mUserName, mService, mApiKey );
    }

    public void getMyFollowers() throws XMLRPCException {
        call( "bp.getMyFollowers", mUserName, mService, mApiKey );
    }

    public void getMyFollowing() throws XMLRPCException {
        call( "bp.getMyFollowing", mUserName, mService, mApiKey );
    }

    public void getMyGroups() throws XMLRPCException {
        call( "bp.getMyGroups", mUserName, mService, mApiKey );
    }

    public void getNotifications() throws XMLRPCException {
        call( "bp.getNotifications", mUserName, mService, mApiKey );
    }

}
