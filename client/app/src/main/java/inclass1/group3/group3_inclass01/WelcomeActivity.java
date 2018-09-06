package inclass1.group3.group3_inclass01;

import android.content.Intent;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.jar.Attributes;

public class WelcomeActivity extends AppCompatActivity {
    public String token,userID;
    EditText Name,Age,Address,Weight;

    public static  apiCalls caller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        caller.activity=WelcomeActivity.this;

        caller=new apiCalls();
        token =getIntent().getExtras().getString(MainActivity.KEY_TOKEN);
      //  userID =getIntent().getExtras().get(MainActivity.KEY_USERID).toString();

        Name= findViewById(R.id.proname);
        Age=findViewById(R.id.proage);
        Address = findViewById(R.id.proaddress);
        Weight= findViewById(R.id.proweight);

       caller.getUserProfile(token,userID);
        final Button btnSave = findViewById(R.id.btnSaveProfile);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString();
                String age = Age.getText().toString();
                String w = Weight.getText().toString();
                String add = Address.getText().toString();

                caller.saveUserProfile(name,age,w,add);
                setEnabledforAll(false);

                btnSave.setVisibility(View.INVISIBLE);
            }
        });

Button btnedit= findViewById(R.id.btnedit);
btnedit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       setEnabledforAll(true);
btnSave.setVisibility(View.VISIBLE);
    }
});

    }

    private void setEnabledforAll(boolean b) {
        Name= findViewById(R.id.proname);
        Age=findViewById(R.id.proage);
        Address = findViewById(R.id.proaddress);
        Weight= findViewById(R.id.proweight);
        Name.setEnabled(b);
        Age.setEnabled(b);
        Address.setEnabled(b);
        Weight.setEnabled(b);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }
    public void showProfile(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_menu_item:
                Toast.makeText(getApplicationContext(),"You pressed logout",Toast.LENGTH_SHORT).show();
MainActivity.caller.deleteToken();
                Intent registerIntent = new Intent(this,MainActivity.class);
                startActivity(registerIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
