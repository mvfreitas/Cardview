package br.edu.unis.atv5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadWidgets();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cleanFields();
    }

    private void loadWidgets() {
        this.edtUser = findViewById(R.id.login_edt_user);
        this.edtUser.requestFocus();
        this.edtPassword = findViewById(R.id.login_edt_password);
        Button btnLogin = findViewById(R.id.login_btn_login);
        btnLogin.setOnClickListener(view -> login());
        TextView txtNewAccount = findViewById(R.id.login_txt_new_account);
        txtNewAccount.setOnClickListener(view -> createNewAccount());
    }

    private void login() {
        hideKeyboard();
        if (this.isFieldsEmpty()) {
            Toast.makeText(this, R.string.txt_field_required, Toast.LENGTH_SHORT).show();
            this.edtUser.requestFocus();
            return;
        }
        if (!this.authenticate()) {
            Toast.makeText(this, R.string.txt_credentials_wrong, Toast.LENGTH_SHORT).show();
            this.edtUser.requestFocus();
            return;
        }
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    private void createNewAccount() {
        startActivity(new Intent(this, CreateUserActivity.class));
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtUser.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);
    }

    private boolean isFieldsEmpty() {
        return this.edtUser.getText().toString().isEmpty()
                || this.edtPassword.getText().toString().isEmpty();
    }

    private boolean authenticate() {
        String user = this.edtUser.getText().toString();
        String password = this.edtPassword.getText().toString();
        br.edu.unis.sqlite.SQLiteHelper db = br.edu.unis.sqlite.SQLiteHelper.getInstance(this);
        return db.authenticateUser(user, password);
    }

    private void cleanFields() {
        this.edtUser.setText("");
        this.edtPassword.setText("");
        this.edtUser.requestFocus();
    }
}
