package br.edu.unis.atv5;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard_menu_logout:
                finish();
                break;
            case R.id.dashboard_list_users:
                startActivity(new Intent(this, ListUsersActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
