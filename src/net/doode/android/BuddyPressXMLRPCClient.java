package net.doode.android;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

public class BuddyPressXMLRPCClient {

    final private XMLRPCClient client;

    private String username;
    private String password;

    public BuddyPressXMLRPCClient( String url ) {
        client = new XMLRPCClient( url );
    }

    public BuddyPressXMLRPCClient( URL url ) {
        client = new XMLRPCClient( url );
    }

    public BuddyPressXMLRPCClient( URI uri ) {
        client = new XMLRPCClient( uri );
    }

    public BuddyPressXMLRPCClient( String url, String username, String password ) throws XMLRPCException {
        client = new XMLRPCClient( url );
        login( username, password );
    }

    public BuddyPressXMLRPCClient( URL url, String username, String password ) throws XMLRPCException {
        client = new XMLRPCClient( url );
        login( username, password );
    }

    public BuddyPressXMLRPCClient( URI uri, String username, String password ) throws XMLRPCException {
        client = new XMLRPCClient( uri );
        login( username, password );
    }

    public void login( String user, String pass ) throws XMLRPCException {
        username = user;
        password = pass;
        
        client.call( "bp.verifyConnection", username, password );
    }

    public void updateProfileStatus( String status ) throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "status", status );

        client.call( "bp.updateProfileStatus", username, password, data );
    }

    public void getActivity( String scope, int max ) throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "scope", scope );
        data.put( "max"  , max   );

        client.call( "bp.getActivity", username, password, data );
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

        client.call( "bp.updateExternalBlogPostStatus", username, password, data );
    }

    public void deleteExternalBlogPostStatus( String postId, String activityId) throws XMLRPCException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "blogpostid", postId     );
        data.put( "activityid", activityId );

        client.call( "bp.deleteExternalBlogPostStatus", username, password, data );
    }

    public void getMyFriends() throws XMLRPCException {
        client.call( "bp.getMyFriends", username, password );
    }

    public void getMyFollowers() throws XMLRPCException {
        client.call( "bp.getMyFollowers", username, password );
    }

    public void getMyFollowing() throws XMLRPCException {
        client.call( "bp.getMyFollowing", username, password );
    }

    public void getMyGroups() throws XMLRPCException {
        client.call( "bp.getMyGroups", username, password );
    }

    public void getNotifications() throws XMLRPCException {
        client.call( "bp.getNotifications", username, password );
    }

}
