package GUI.MODEL;

import BE.User;
import BLL.LoginManager;

public class LoginModel {
    LoginManager myLoginManager = new LoginManager();


    /**
     * Attempt a login with the given credentials.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return returns a user object if successful, else null.
     */
    public User attemptLogin(String username, String password){
        myLoginManager.attemptLogin(username,password);
        return LoginManager.getCurrentUser();
    }
}

