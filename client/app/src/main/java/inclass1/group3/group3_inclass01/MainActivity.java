package inclass1.group3.group3_inclass01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userLogin,userPwd;
    public static  apiCalls caller;

    public static String KEY_TOKEN="tokenKey";
    public static String KEY_NAME="userNameKey";
    public static String KEY_USERID="userIdKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // before calling any api call assign
        caller.activity=MainActivity.this;

        caller=new apiCalls();

        userLogin = findViewById(R.id.txtEmail);
        userPwd = findViewById(R.id.txtPwd);

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=userLogin.getText().toString();
                String password= userPwd.getText().toString();
                if( email!=null&&!email.equals("")&&password!=null&&!password.equals("")){
//call respective api calls through the app
                    caller.loginApi(email,password);

                }else{
                    Toast.makeText(getApplicationContext(),"User credentials incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
                finish();
            }
        });
    }
}
