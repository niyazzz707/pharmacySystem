package com.sample.pharmacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    HomePage homePage=new HomePage();

   //============Login Pane==================

    @FXML
    private AnchorPane LoginPane;

    @FXML
    private Label loginErrorMessage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private Button enterButton;

    @FXML
    private ImageView Exit1;

    //======================================

    //============Registration Pane==================

    @FXML
    private AnchorPane RegistrationPane;

    @FXML
    private Label signErrorMessage;

    @FXML
    private TextField signUsernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField signPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private ImageView Exit;

    //===============================================

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Exit.setOnMouseClicked(mouseEvent -> System.exit(0));
        Exit1.setOnMouseClicked(mouseEvent -> System.exit(0));
    }


    public void enterButtonOnActive(ActionEvent event) {
        validateLogin();
    }

    public void loginSignUpButtonActive(ActionEvent event) {
        registerPaneShow();
        usernameField.setText("");
        passwordField.setText("");
    }

    public void loginPaneShow() {
        LoginPane.setVisible(true);
        RegistrationPane.setVisible(false);
    }

    public void registerPaneShow() {
        LoginPane.setVisible(false);
        RegistrationPane.setVisible(true);
    }

    public void validateLogin() {
        String username=usernameField.getText();
        String pass=passwordField.getText();

      if(username.equals("") && pass.equals("")) {
          loginErrorMessage.setText("Please enter your username and password");
      }
      else {
          try {
              Class.forName("org.postgresql.Driver");
              Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pharmacy", "postgres", "niyazbek");

              PreparedStatement ps = con.prepareStatement("select * from users where login = ? and password = ?");

              ps.setString(1, username);
              ps.setString(2, pass);

              ResultSet rs = ps.executeQuery();

              if (rs.next()) {
                  Stage log = (Stage) Exit1.getScene().getWindow();
                  log.close();
                  homePage.welcome();


              } else {
                  loginErrorMessage.setText("Invalid login or password, please try again");
                  usernameField.setText("");
                  passwordField.setText("");
                  usernameField.requestFocus();
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
    }

    public void signUpButtonOnActive(ActionEvent event) {

        if (signPasswordField.getText().equals(confirmPasswordField.getText())) {
            registerUser();
            signUsernameField.setText("");
            emailField.setText("");
            signPasswordField.setText("");
            confirmPasswordField.setText("");
        }
        else {
            signErrorMessage.setText("Password does not match");
        }
    }

    public void registerUser() {
        String signUsername = signUsernameField.getText();
        String signPassword = signPasswordField.getText();
        String signEmail = emailField.getText();
        String confirmPass=confirmPasswordField.getText();

        if (signUsername.equals("") || signPassword.equals("") || signEmail.equals("") || confirmPass.equals("")) {
            signErrorMessage.setText("Please, fill in the boxes!");
            signUsernameField.setText("");
            emailField.setText("");
            signPasswordField.setText("");
            signUsernameField.requestFocus();
        } else {
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pharmacy", "postgres", "niyazbek");

                PreparedStatement ps = con.prepareStatement("INSERT INTO users(login, password, email) VALUES (?,?,?)");

                ps.setString(1, signUsername);
                ps.setString(2, signPassword);
                ps.setString(3, signEmail);

                ps.executeUpdate();
                ps.close();
                loginPaneShow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
