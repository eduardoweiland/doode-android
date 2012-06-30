package net.doode.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;

import net.doode.android.BuddyPressXMLRPCClient;

/**
 * Main application's Activity class.
 *
 * It's used by Android system to launch the application.
 *
 * @author Eduardo Weiland
 */
public class Doode extends Activity {

    private boolean logged = false;

    private boolean keepLogged = false;

    final private BuddyPressXMLRPCClient client;

    final private String apiUrl = "http://www.doode.net/wp-content/plugins/buddypress-xmlrpc-receiver/bp-xmlrpc.php";

    /**
     * Constructor. Initialize the XML-RPC client.
     */
    public Doode() {
        // Creates the XMLRPCClient and connects to doode.net
        client = new BuddyPressXMLRPCClient( apiUrl );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify if the user is already logged or show the login screen
        if (logged || keepLogged) {
            setContentView(R.layout.main);
        } else {
            setContentView(R.layout.login);
        }

        ((Button) findViewById(R.id.btnLogin)).setOnClickListener( btnLoginClick );
    }

    /**
     * Try to log-in the user with the provided credentials.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     */
    public void login( String username, String password ) {
        try {
            client.login( username, password );
            logged = true;
        } catch (Exception e) {
            String errMsg = e.getMessage();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(errMsg)
                   .setTitle("Erro");

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    OnClickListener btnLoginClick = new OnClickListener() {
        public void onClick(View view) {
            String username = ((EditText) findViewById(R.id.txtUsername)).toString();
            String password = ((EditText) findViewById(R.id.txtPassword)).toString();
            keepLogged      = ((CheckBox) findViewById(R.id.chbRemember)).isChecked();

            login( username, password );
        }
    };
}
