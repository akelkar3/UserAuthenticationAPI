package inclass1.group3.group3_inclass01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Aliandro on 4/3/2018.
 */

public class apiCalls {
    static public String remoteIP="http://ec2-54-91-96-147.compute-1.amazonaws.com";
static public Activity activity;
    final String TAG="test";
static public String token;

    public apiCalls() {
if (this.token == null)
    this.token = getToken();
    }


    public void saveToken(String Token,String name,String userId){


        Log.d(TAG, "saveToken: "+ getToken());
    }
    public void deleteToken(){
        SharedPreferences sharedPref = activity.getSharedPreferences(
                "mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
       editor.clear();
        editor.apply();

         Log.d(TAG, "deleted token: ");
    }
    public  String getToken(){

        String ret;
        SharedPreferences sharedPref =activity.getSharedPreferences(
                "mypref", Context.MODE_PRIVATE);

        ret = sharedPref.getString("token",null);

        return ret;
    }
    public  String getName(){

        String ret;
        SharedPreferences sharedPref = activity.getSharedPreferences(
                "mypref", Context.MODE_PRIVATE);

        ret = sharedPref.getString("userName",null);

        return ret;
    }  public  String getuserId(){

        String ret;
        SharedPreferences sharedPref =activity.getSharedPreferences(
                "mypref", Context.MODE_PRIVATE);

        ret = sharedPref.getString("userId",null);

        return ret;
    }
    public void loginApi( String username,String password)
    {

        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(remoteIP+"/api/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str;
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    //System.out.println(responseBody.string());
                    str=responseBody.string();
                }
               // str= response.body().string();
                Log.d(TAG, "onResponse: "+str );
                Gson gson = new Gson();

                final ResponseApi result=  (ResponseApi) gson.fromJson(str, ResponseApi.class); // Fails to deserialize foo.value as Bar
                if (!result.status.equalsIgnoreCase("error")) {


                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.status.equalsIgnoreCase("error")) {
                            Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }else {
                            SharedPreferences sharedPref =  activity.getSharedPreferences(
                                    "mypref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            //saving user full name and user Id that might require on threads or messages activity
                            Log.d("tesetdelete", "saveToken: "+  result.getToken()+" name: "+result.getUserFullName());
                            editor.putString("token", result.getToken());
                            editor.putString("userName",result.getUserFullName());
                            editor.putString("userId",result.getUser_id());
                            editor.apply();
                            Intent intent = new Intent(activity, WelcomeActivity.class);
                            intent.putExtra(MainActivity.KEY_TOKEN, getToken());
                            intent.putExtra(MainActivity.KEY_NAME,getName());
                            intent.putExtra(MainActivity.KEY_USERID,getuserId());
                            activity.startActivity(intent);
                            activity.finish();
                        }
                     //   Toast.makeText(activity, "token created successfully", Toast.LENGTH_SHORT).show();
                        //do something more.

                    }
                });
            }

        });

    }



    public void SignUpApi(final String username, final String password, String fname, String lname)
    {

        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email", username)
                .add("password", password)
                .add("fname", fname)
                .add("lname", lname)
                .build();
        Request request = new Request.Builder()
                .url(remoteIP+"/api/signup")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure Signup: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str;
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    //System.out.println(responseBody.string());
                    str=responseBody.string();
                }
                // str= response.body().string();
                Log.d(TAG, "onResponse: "+str );
                Gson gson = new Gson();

                final ResponseApi result=  (ResponseApi) gson.fromJson(str, ResponseApi.class); // Fails to deserialize foo.value as Bar
                if (!result.status.equalsIgnoreCase("error")) {
                //    Log.d(TAG, "onResponse: get messages "+result.messages.size());
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show();
                    //  saveToken(result.token.toString(),result.getUserFullName(),result.getUser_id());
                }else   {
                    loginApi(username,password);
                }


            }
        });

    }

    public void deleteThreads(String id) {
        final OkHttpClient client = new OkHttpClient();
        Log.d("testdelete", "deleteThreads: "+token +" id : "+id);
        Request request = new Request.Builder()
                .url(remoteIP+"/api/thread/delete/"+id)
                .addHeader("Authorization","BEARER "+token)

                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str;
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    //System.out.println(responseBody.string());
                    str=responseBody.string();
                }
                // str= response.body().string();
                Log.d(TAG, "onResponse: "+str );
                Gson gson = new Gson();

                final ResponseApi result=  (ResponseApi) gson.fromJson(str, ResponseApi.class); // Fails to deserialize foo.value as Bar
                Log.d(TAG, "onResponse: after delete"+result.status);

                //getThreads(getToken(),getuserId());
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.status.equalsIgnoreCase("error")) {
                            Toast.makeText(ThreadsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        //   Toast.makeText(activity, "token created successfully", Toast.LENGTH_SHORT).show();
                        //do something more.

                    }
                });*/
            }

        });
    }
    public void deleteMessages(String id, final String threadID) {
        final OkHttpClient client = new OkHttpClient();
        Log.d("testdelete", " deleteMessages: "+token +" id : "+id);
        Request request = new Request.Builder()
                .url(remoteIP+"/api/message/delete/"+id)
                .addHeader("Authorization","BEARER "+token)

                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+ e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str;
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    //System.out.println(responseBody.string());
                    str=responseBody.string();
                }
                // str= response.body().string();
                Log.d(TAG, "onResponse: "+str );
                Gson gson = new Gson();

                final ResponseApi result=  (ResponseApi) gson.fromJson(str, ResponseApi.class); // Fails to deserialize foo.value as Bar
                Log.d(TAG, "onResponse: after delete"+result.status);
                if (result.status.equalsIgnoreCase("ok")) {
                    //getMessages(getToken(),threadID);
                }
                else{
                   // Toast.makeText(activity, result.status, Toast.LENGTH_SHORT).show();
                }
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.status.equalsIgnoreCase("error")) {
                            Toast.makeText(ThreadsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        //   Toast.makeText(activity, "token created successfully", Toast.LENGTH_SHORT).show();
                        //do something more.

                    }
                });*/
            }

        });
    }
//    public void getThreads(String token,String currentUserId1)
//    {
//        final String currentUserId= currentUserId1;
//        final OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread")
//                .addHeader("Authorization","BEARER "+token)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure getThreads: ");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str;
//                try (ResponseBody responseBody = response.body()) {
//                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//                    Headers responseHeaders = response.headers();
//
//                    //System.out.println(responseBody.string());
//                    str=responseBody.string();
//                }
//                // str= response.body().string();
//                Log.d(TAG, "onResponse: "+str );
//                Gson gson = new Gson();
//
//                final ResponseApi result=  (ResponseApi) gson.fromJson(str, ResponseApi.class); // Fails to deserialize foo.value as Bar
//                if (!result.status.equalsIgnoreCase("error")) {
//                    Log.d(TAG, "onResponse: getThread"+result.threads.size());
//
//                    //  saveToken(result.token.toString(),result.getUserFullName(),result.getUser_id());
//                }
//
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (result.status.equalsIgnoreCase("error")) {
//                            Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
//                        }else {
//                            Log.d(TAG, "run: here" + currentUserId);
//                            ListView listView = (ListView)activity.findViewById(R.id.messagesListView);
//                            ThreadsAdapter  threadsAdapter= new ThreadsAdapter(activity,R.layout.item_view,result.threads,currentUserId);
//                            listView.setAdapter(threadsAdapter);
//                            //  threadsAdapter.notifyDataSetChanged();
//                        }
//                        //   Toast.makeText(activity, "token created successfully", Toast.LENGTH_SHORT).show();
//                        //do something more.
//
//                        Log.d(TAG, "run: accessing ui " );
//                    }
//                });
//            }
//        });
//
//    }
//
//  public void addMessage(String message, final String threadID)
//    { final String useToken =this.token;
//        final String UserId=getuserId();
//        final OkHttpClient client = new OkHttpClient();
//        RequestBody formBody = new FormBody.Builder()
//                .add("message", message)
//                .add("thread_id", threadID)
//                .build();
//        Request request = new Request.Builder()
//                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/message/add")
//                .addHeader("Authorization","BEARER "+token)
//                .post(formBody)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure: ");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str;
//                try (ResponseBody responseBody = response.body()) {
//                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//                    Headers responseHeaders = response.headers();
//                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
//                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                    }
//
//                    //System.out.println(responseBody.string());
//                    str=responseBody.string();
//                }
//                // str= response.body().string();
//                Log.d(TAG, "onResponse: "+str );
//                Gson gson = new Gson();
//
//                final MessageResponse result=  (MessageResponse) gson.fromJson(str, MessageResponse.class); // Fails to deserialize foo.value as Bar
//                if (!result.status.equalsIgnoreCase("error")) {
//
//                    getMessages("",threadID);
//
//                    // saveToken(result.token.toString(),result.getUserFullName(),result.getUser_id());
//                }
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (result.status.equalsIgnoreCase("error")) {
//                            Toast.makeText(activity, result.message.message, Toast.LENGTH_SHORT).show();
//                        }else {
//                            TextView threadTitle= activity.findViewById(R.id.messageName);
//                            threadTitle.setText("");
//
//                        }
//                        //   Toast.makeText(activity, "token created successfully", Toast.LENGTH_SHORT).show();
//                        //do something more.
//
//                    }
//                });
//            }
//
//        });
//
//    }
//    public void getMessages(String token, final String threadID)
//    {
//       // final String currentUserId= currentUserId1;
//        token=this.token;
//        final OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/messages/"+threadID)
//                .addHeader("Authorization","BEARER "+token)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure getMessages: ");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str;
//                try (ResponseBody responseBody = response.body()) {
//                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//                    Headers responseHeaders = response.headers();
//
//                    //System.out.println(responseBody.string());
//                    str=responseBody.string();
//                }
//                // str= response.body().string();
//                Log.d(TAG, "onResponse: "+str );
//                Gson gson = new Gson();
//
//                final ResponseApi result=  (ResponseApi) gson.fromJson(str, ResponseApi.class); // Fails to deserialize foo.value as Bar
//                if (!result.status.equalsIgnoreCase("error")) {
//                    Log.d(TAG, "onResponse: get messages "+result.messages.size());
//
//                    //  saveToken(result.token.toString(),result.getUserFullName(),result.getUser_id());
//                }
//
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (result.status.equalsIgnoreCase("error")) {
//                            Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
//                        }else if (result.messages.size()>0){
//                            Log.d(TAG, "run: here");
//                            ListView listView = (ListView)activity.findViewById(R.id.messagesListView);
//
//                           // Log.d(TAG, "run: in main ui thread "+ call.getuserId());
//                            MessageAdapter  messageAdapter= new MessageAdapter(activity,R.layout.item_view,result.messages,getuserId());
//                            listView.setAdapter(messageAdapter);
//                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
//                                    long viewId = view.getId();
//                                    if (viewId == R.id.deleteButton) {
//                                     //   apiCalls caller = new apiCalls();
//                                        deleteMessages(result.messages.get(position).id,threadID);
//                                    }
//                                }
//                            });
//                            //  threadsAdapter.notifyDataSetChanged();
//                        }else{
//                            Toast.makeText(activity, "No messages available", Toast.LENGTH_SHORT).show();
//                        }
//                        //   Toast.makeText(activity, "token created successfully", Toast.LENGTH_SHORT).show();
//                        //do something more.
//
//                        Log.d(TAG, "run: accessing ui " );
//                    }
//                });
//            }
//        });
//
//    }

}
