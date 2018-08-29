package inclass1.group3.group3_inclass01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText name,id,pwd,age,weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.txtname);
        id = findViewById(R.id.txtuserid);
        pwd = findViewById(R.id.txtpwd);
        age = findViewById(R.id.txtage);
        weight = findViewById(R.id.txtWeight);

        Toast.makeText(getApplicationContext(),"Registration form",Toast.LENGTH_SHORT).show();

        findViewById(R.id.btnCreateUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomePage = new Intent(RegisterActivity.this,WelcomeActivity.class);
                startActivity(welcomePage);
                finish();
            }
        });

    }



}
