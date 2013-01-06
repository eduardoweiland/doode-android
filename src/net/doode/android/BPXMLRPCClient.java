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

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

import android.os.AsyncTask;

public class BPXMLRPCClient extends XMLRPCClient {

    private String mUserName;
    private String mService;
    private String mApiKey;

    private final int DEFAULT_MAX_ACTIVITY = 35;

    /**
     * Initializes the client.
     *
     * @param url The URL to connect
     */
    public BPXMLRPCClient(String url) {
        super(url);
    }

    /**
     * Initializes the client.
     *
     * @param url The URL to connect
     */
    public BPXMLRPCClient(URL url) {
        super(url);
    }

    /**
     * Initializes the client.
     *
     * @param uri The URL to connect
     */
    public BPXMLRPCClient(URI uri) {
        super(uri);
    }

    /**
     * Initializes the client and tries to log-in.
     *
     * @param url The URL to connect
     * @param username The user's login name
     * @param service The service name
     * @param apikey The generated ApiKey
     */
    public BPXMLRPCClient(String url, String username, String service, String apikey)
            throws XMLRPCException {
        super(url);
        login(username, service, apikey);
    }

    /**
     * Initializes the client and tries to log-in.
     *
     * @param url The URL to connect
     * @param username The user's login name
     * @param service The service name
     * @param apikey The generated ApiKey
     */
    public BPXMLRPCClient(URL url, String username, String service, String apikey)
            throws XMLRPCException {
        super(url);
        login(username, service, apikey);
    }

    /**
     * Initializes the client and tries to log-in.
     *
     * @param url The URL to connect
     * @param username The user's login name
     * @param service The service name
     * @param apikey The generated ApiKey
     */
    public BPXMLRPCClient(URI uri, String username, String service, String apikey)
            throws XMLRPCException {
        super(uri);
        login(username, service, apikey);
    }

    /**
     * Request a new service ApiKey for authentication.
     *
     * @param username Username to connect
     * @param service Service name to connect
     * @param callback Called on result
     */
    public void requestApiKey(String username, String service, final OnXMLRPCResult callback) {
        mUserName = username;
        mService = service;

        final class RequestApiKeyTask extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    mApiKey = result;
                    callback.onSuccess(result);
                }
                else {
                    callback.onFault(0, "");
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    Object result = call("bp.requestApiKey", params[0], params[1]);
                    if (result != null) {
                        HashMap<?,?> map = (HashMap<?,?>) result;

                        if ((Boolean) map.get("confirmation")) {
                            return (String) map.get("apikey");
                        }
                    }
                }
                catch (/*XMLRPC*/Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        new RequestApiKeyTask().execute(username, service);
    }

    /**
     * Try to log-in the user.
     *
     * @param username
     *            The user's login name
     */
    public void login(String username, String service, String apikey) {
        mUserName = username;
        mService = service;
        mApiKey = apikey;

        final class LoginTask extends AsyncTask<String, Void, Boolean> {

            @Override
            protected void onPostExecute(Boolean result) {
                Doode.logged = result;
            }

            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    call("bp.verifyConnection", params[0], params[1], params[2]);
                    return true;
                }
                catch (XMLRPCException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }

        new LoginTask().execute(username, service, apikey);
    }

    /**
     * Send a new status update to the server.
     *
     * @param status The status to be posted
     * @param callback Called on result
     */
    public void updateProfileStatus(String status, final OnXMLRPCResult callback) {
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("status", status);

        final class UpdateProfileStatusTask extends AsyncTask<String, Void, Object> {

            @Override
            protected void onPostExecute(Object result) {
                if (result != null) {
                    Boolean success = (Boolean) ((HashMap<?,?>) result).get("confirmation");
                    if (success) {
                        callback.onSuccess(result);
                        return;
                    }
                }
                callback.onFault(-1, "");
            }

            @Override
            protected Object doInBackground(String... params) {
                Object result = null;
                try {
                    result = call("bp.updateProfileStatus", mUserName, mService, mApiKey, params[0]);
                }
                catch (XMLRPCException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }

        new UpdateProfileStatusTask().execute(status);
    }

    /**
     * Get a list of site activity. This can be filtered with {@link scope}.
     *
     * @param scope Scope for filtering site activity. See {@link BPActivityScope}.
     * @param max Maximum of status updates to be retrieved.
     * @param callback Called on result.
     */
    public void getActivity(String scope, int max, final OnXMLRPCResult callback) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("scope", scope);
        data.put("max", max);

        final class GetActivityTask extends AsyncTask<Map<?, ?>, Void, Object> {

            @Override
            protected void onPostExecute(Object result) {
                if (result != null) {
                    Boolean success = (Boolean) ((HashMap<?,?>) result).get("confirmation");
                    if (success) {
                        callback.onSuccess(result);
                        return;
                    }
                }
                callback.onFault(-1, "");
            }

            @Override
            protected Object doInBackground(Map<?, ?>... params) {
                Object result = null;
                try {
                    result = call("bp.updateProfileStatus", mUserName, mService, mApiKey, params[0]);
                }
                catch (XMLRPCException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }

        new GetActivityTask().execute(data);
    }

    public void getActivity(String scope, final OnXMLRPCResult callback) {
        getActivity(scope, DEFAULT_MAX_ACTIVITY, callback);
    }

    public void updateExternalBlogPostStatus(String status, String title,
            String url, String permalink, String postId) throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("status", status);
        data.put("blogtitle", title);
        data.put("blogurl", url);
        data.put("blogpostpermalink", permalink);
        data.put("blogpostid", postId);

        call("bp.updateExternalBlogPostStatus", mUserName, mService, mApiKey,
                data);
    }

    public void deleteExternalBlogPostStatus(String postId, String activityId)
            throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("blogpostid", postId);
        data.put("activityid", activityId);

        call("bp.deleteExternalBlogPostStatus", mUserName, mService, mApiKey,
                data);
    }

    public void getMyFriends() throws XMLRPCException {
        call("bp.getMyFriends", mUserName, mService, mApiKey);
    }

    public void getMyFollowers() throws XMLRPCException {
        call("bp.getMyFollowers", mUserName, mService, mApiKey);
    }

    public void getMyFollowing() throws XMLRPCException {
        call("bp.getMyFollowing", mUserName, mService, mApiKey);
    }

    public void getMyGroups() throws XMLRPCException {
        call("bp.getMyGroups", mUserName, mService, mApiKey);
    }

    public void getNotifications() throws XMLRPCException {
        call("bp.getNotifications", mUserName, mService, mApiKey);
    }

}
