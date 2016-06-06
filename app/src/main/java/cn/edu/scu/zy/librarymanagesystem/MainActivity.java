package cn.edu.scu.zy.librarymanagesystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.*;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import cz.msebera.android.httpclient.Header;



public class MainActivity extends AppCompatActivity {

    public final static String url = "http://120.27.112.37/lms/";

    public int currentLayoutId;
    String id;
    String pwd;

//    String content;
    String scanResult;

    public FrameLayout mainLayout;
    public ProgressBar progressBar;

    public RelativeLayout loadingLayout;

    public RelativeLayout loginLayout;
    public Button visitorButton, loginButton;
    public EditText idEditText, pwdEditText;

    public LinearLayout menuLayout;
    public SearchView menuSearchView;
    public TextView readerMenuWelcomeTextView, readerMenuBorrowedTextView;
    public TextView readerMenuReservedTextView, readerMenuExpireTextView;
    public ImageButton readerMenuScanImageButton;

    public LinearLayout adminMenuLayout;
    public TextView adminMenuWelcomeTextView;
    public TextView adminMenuAuditTextView;
    public TextView adminMenuReaderManageTextView;
    public TextView adminMenuBookManageTextView;
    public Button adminMenuBorrowButton, adminMenuReturnButton;

    public LinearLayout borrowedLayout;
    public ListView borrowedListView;

    public LinearLayout reservedLayout;
    public ListView reservedListView;

    public LinearLayout searchLayout;
    public SearchView searchSearchView;
    public ListView searchListView;

    public LinearLayout userLayout;
    public TextView userNameTextView;
    public TextView userIdTextView;
    public TextView userPhoneTextView;
    public TextView userAddressTextView;
    public TextView userEmailTextView;

    public LinearLayout bookInfoLayout;
    public TextView bookInfoTitileTextView, bookInfoAuthorTextView, bookInfoCallNumberTextView;
    public TextView bookInfoIsbnTextView, bookInfoStatusTextView, bookInfoLocationTextView;
    public Button bookInfoReserveButton;
    public Button bookInfoReviewButton;
    public Button bookInfoSechandButton;

    public LinearLayout reviewLayout;
    public ListView reviewListView;
    public Button reviewAddButton;
    public EditText reviewEditText;

    public LinearLayout sechandLayout;
    public ListView sechandListView;
    public Button sechandAddButton;
    public EditText sechandEditText;

    public void findAllViewById() {
        mainLayout = (FrameLayout) findViewById(R.id.layout_main);
        progressBar = (ProgressBar) findViewById(R.id.progerss_bar);

        loadingLayout = (RelativeLayout) findViewById(R.id.layout_loading);

        loginLayout = (RelativeLayout) findViewById(R.id.layout_login);
        visitorButton = (Button) findViewById(R.id.button_visitor);
        loginButton = (Button) findViewById(R.id.button_login);
        idEditText = (EditText) findViewById(R.id.edit_id);
        pwdEditText = (EditText) findViewById(R.id.edit_password);

        menuLayout = (LinearLayout) findViewById(R.id.layout_menu);
        menuSearchView = (SearchView) findViewById(R.id.search_menu);
        readerMenuWelcomeTextView = (TextView) findViewById(R.id.text_readermenu_welcome);
        readerMenuBorrowedTextView = (TextView) findViewById(R.id.text_readermenu_borrowed);
        readerMenuReservedTextView = (TextView) findViewById(R.id.text_readermenu_reserved);
        readerMenuExpireTextView = (TextView) findViewById(R.id.text_readermenu_expire);
        readerMenuScanImageButton = (ImageButton) findViewById(R.id.imgbutton_scan);

        adminMenuLayout = (LinearLayout) findViewById(R.id.layout_adminmenu);
        adminMenuWelcomeTextView = (TextView) findViewById(R.id.text_adminmenu_welcome);
        adminMenuAuditTextView = (TextView) findViewById(R.id.text_adminmenu_audit);
        adminMenuReaderManageTextView = (TextView) findViewById(R.id.text_adminmenu_readermanage);
        adminMenuBookManageTextView = (TextView) findViewById(R.id.text_adminmenu_bookmanage);
        adminMenuBorrowButton = (Button) findViewById(R.id.button_adminmenu_borrow);
        adminMenuReturnButton = (Button) findViewById(R.id.button_adminmenu_return);

        borrowedLayout = (LinearLayout) findViewById(R.id.layout_borrowed);
        borrowedListView = (ListView) findViewById(R.id.list_borrowed);

        reservedLayout = (LinearLayout) findViewById(R.id.layout_reserved);
        reservedListView = (ListView) findViewById(R.id.list_reserved);

        searchLayout = (LinearLayout) findViewById(R.id.layout_search);
        searchSearchView = (SearchView) findViewById(R.id.search_search);
        searchListView = (ListView) findViewById(R.id.list_search);

        userLayout = (LinearLayout) findViewById(R.id.layout_user);
        userNameTextView = (TextView) findViewById(R.id.text_user_name);
        userIdTextView = (TextView) findViewById(R.id.text_user_id);
        userPhoneTextView = (TextView) findViewById(R.id.text_user_phone);
        userAddressTextView = (TextView) findViewById(R.id.text_user_address);
        userEmailTextView = (TextView) findViewById(R.id.text_user_email);

        bookInfoLayout = (LinearLayout) findViewById(R.id.layout_bookinfo);
        bookInfoTitileTextView = (TextView) findViewById(R.id.show_title);
        bookInfoAuthorTextView = (TextView) findViewById(R.id.show_author);
        bookInfoIsbnTextView = (TextView) findViewById(R.id.show_isbn);
        bookInfoCallNumberTextView = (TextView) findViewById(R.id.show_num);
        bookInfoStatusTextView = (TextView) findViewById(R.id.show_status);
        bookInfoLocationTextView = (TextView) findViewById(R.id.show_location);
        bookInfoReserveButton = (Button) findViewById(R.id.show_button_reserve);
        bookInfoReviewButton = (Button) findViewById(R.id.show_button_review);
        bookInfoSechandButton = (Button) findViewById(R.id.show_button_sechand);

        reviewLayout = (LinearLayout) findViewById(R.id.layout_review);
        reviewListView = (ListView) findViewById(R.id.list_review);
        reviewAddButton = (Button) findViewById(R.id.button_review_add);
        reviewEditText = (EditText) findViewById(R.id.edit_review);

        sechandLayout = (LinearLayout) findViewById(R.id.layout_sechand);
        sechandListView = (ListView) findViewById(R.id.list_sechand);
        sechandAddButton = (Button) findViewById(R.id.button_sechand_add);
        sechandEditText = (EditText) findViewById(R.id.edit_sechand);
    }

    public void setAllListener() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginHelper();

            }
        });

        visitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//visitor login
            }
        });

        menuSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchSearchView.setQuery(s, true);
                changeView(searchLayout);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //suggestion list
                return false;
            }
        });

        readerMenuWelcomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//user info show page
                Toast.makeText(MainActivity.this, "user info", Toast.LENGTH_SHORT).show();
                userHelper();
            }
        });

        readerMenuBorrowedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrowedListHelper();
            }
        });

        readerMenuReservedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reservedListHelper();
            }
        });

        readerMenuExpireTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrowedListHelper();
            }
        });

        readerMenuScanImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });

        adminMenuAuditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        adminMenuReaderManageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        adminMenuBookManageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        adminMenuBorrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 1);
            }
        });

        adminMenuReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 2);
            }
        });

        borrowedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> map= (HashMap<String, Object>)borrowedListView.getItemAtPosition(i);
                String book_id = (String) map.get("book_id");
                Toast.makeText(MainActivity.this, book_id, Toast.LENGTH_SHORT).show();
                //do renew here
            }
        });

        reservedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> map= (HashMap<String, Object>)reservedListView.getItemAtPosition(i);
                String book_id = (String) map.get("book_id");
                Toast.makeText(MainActivity.this, book_id, Toast.LENGTH_SHORT).show();
                //do cancel reserve here
                reservedListHelper();
            }
        });

        searchSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchSearchView.clearFocus();
                searchHelper(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> map= (HashMap<String, Object>)searchListView.getItemAtPosition(i);
                bookInfoHelper((String) map.get("call_num"));
            }
        });

        bookInfoReserveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "reserve function needs to inplement..." , Toast.LENGTH_SHORT).show();
            }
        });

        bookInfoReviewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                reviewHelper();
            }
        });

        bookInfoSechandButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sechandHelper();
            }
        });

        reviewAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                reviewAddHelper();
            }
        });

        sechandAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                sechandAddHelper();
            }
        });

    }

    public void changeView(View view){
        findViewById(currentLayoutId).setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
        currentLayoutId = view.getId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLayoutId = R.id.layout_login;

        findAllViewById();

        setAllListener();

    }


    public void loginHelper() {
        id = idEditText.getText().toString();
        pwd = pwdEditText.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("pwd", pwd);
        client.post(url + "login.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
//                Toast.makeText(MainActivity.this, "connect...plz wait", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(MainActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();

                //recieved info from server
                try {
                    JSONObject resultObj = new JSONObject(new String(responseBody));
                    String usrName = resultObj.getString("username");
                    if (resultObj.getString("user_type").equals("reader")) {
                        //set reader info
                        int borrowedBookAmount = resultObj.getInt("borrow_num");
                        int reservedBookAmount = resultObj.getInt("reserve_num");
                        int expireAmount = resultObj.getInt("expire_num");
                        readerMenuWelcomeTextView.setText("Hello " + usrName);
                        readerMenuBorrowedTextView.setText("Borrowed(" + String.valueOf(borrowedBookAmount) + ")");
                        readerMenuReservedTextView.setText("Reserved(" + String.valueOf(reservedBookAmount) + ")");
                        readerMenuExpireTextView.setText("Expire-date(" + String.valueOf(expireAmount) + ")");
                        changeView(menuLayout);
                    } else if (resultObj.getString("user_type").equals("admin")) {
                        //set admin info
                        adminMenuWelcomeTextView.setText("Hello, admin: "+usrName);
                        changeView(adminMenuLayout);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "JSON error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(String.valueOf(statusCode));
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void userHelper(){


        userNameTextView.setText("Park");
        userIdTextView.setText("2014141463007");
        userPhoneTextView.setText("13223332333");
        userAddressTextView.setText("7th block");
        userEmailTextView.setText("233 AT gmail.com");
        changeView(userLayout);
    }

    public void borrowedListHelper() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("pwd", pwd);
        client.post(url + "borrowed.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                Toast.makeText(MainActivity.this, "connect...plz wait", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(MainActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();

                //recieved info from server
                try {
                    JSONArray resultArray = new JSONArray(new String(responseBody));
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    Map<String, Object> map;
                    for (int i=0; i < resultArray.length(); i++) {
                        JSONObject item = new JSONObject(resultArray.getString(i));
                        //add into Borrowed instance
                        map = new HashMap<String, Object>();
                        map.put("title", item.getString("title"));
                        map.put("author", item.getString("author"));
                        map.put("book_id", item.getString("book_id"));
                        map.put("expire", item.getString("expire"));
                        map.put("times", item.getString("times"));
                        list.add(map);
                    }
                    //from instance, change listView
                    SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list,
                            R.layout.list_item_borrowed,
                            new String[]{"title", "author", "book_id", "expire", "times"},
                            new int[]{R.id.borrowed_item_title, R.id.borrowed_item_author,
                                    R.id.borrowed_item_book_id, R.id.borrowed_item_date,
                                    R.id.borrowed_item_times});
                    borrowedListView.setAdapter(adapter);
                    changeView(borrowedLayout);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "JSON error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(String.valueOf(statusCode));
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void reservedListHelper() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("pwd", pwd);
        client.post(url + "reserved.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                Toast.makeText(MainActivity.this, "connect...plz wait", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(MainActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();

                //recieved info from server
                try {
                    JSONArray resultArray = new JSONArray(new String(responseBody));
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    Map<String, Object> map;
                    for (int i=0; i < resultArray.length(); i++) {
                        JSONObject item = new JSONObject(resultArray.getString(i));
                        //add into Borrowed instance
                        map = new HashMap<String, Object>();
                        map.put("title", item.getString("title"));
                        map.put("author", item.getString("author"));
                        map.put("book_id", item.getString("book_id"));
                        map.put("date", item.getString("date"));
                        map.put("status", item.getString("status"));
                        list.add(map);
                    }
                    //from instance, change listView
                    SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list,
                            R.layout.list_item_reserved,
                            new String[]{"title", "author", "book_id", "date", "status"},
                            new int[]{R.id.reserved_item_title, R.id.reserved_item_author,
                                    R.id.reserved_item_book_id, R.id.reserved_item_date,
                                    R.id.reserved_item_status});
                    reservedListView.setAdapter(adapter);
                    changeView(reservedLayout);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "JSON error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(String.valueOf(statusCode));
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void searchHelper(String s) {
        Toast.makeText(MainActivity.this, "search: " + s, Toast.LENGTH_SHORT).show();
        //send query
        //list in search layout
        //change layout
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("query", s);
        client.post(url + "search.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                Toast.makeText(MainActivity.this, "connect...plz wait", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(MainActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();

                //recieved info from server
                try {
                    JSONArray resultArray = new JSONArray(new String(responseBody));
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    Map<String, Object> map;
                    for (int i=0; i < resultArray.length(); i++) {
                        JSONObject item = new JSONObject(resultArray.getString(i));
                        map = new HashMap<String, Object>();
                        map.put("title", item.getString("title"));
                        map.put("author", item.getString("author"));
                        map.put("isbn", item.getString("isbn"));
                        map.put("call_num", item.getString("call_num"));
                        map.put("status", item.getString("status"));
                        list.add(map);

                    }
                    //from instance, change listView
                    SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list,
                            R.layout.list_item_search,
                            new String[]{"title", "author", "isbn", "call_num", "status"},
                            new int[]{R.id.search_item_title, R.id.search_item_author,
                                    R.id.search_item_isbn, R.id.search_item_call_num,
                                    R.id.search_item_status});
                    searchListView.setAdapter(adapter);
                    changeView(searchLayout);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "JSON error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(String.valueOf(statusCode));
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void bookInfoHelper(String call_num) {

        Toast.makeText(MainActivity.this, "this is the book: " + call_num, Toast.LENGTH_SHORT).show();


        bookInfoTitileTextView.setText("title");
        bookInfoAuthorTextView.setText("author");
        bookInfoIsbnTextView.setText("isbn");
        bookInfoCallNumberTextView.setText("call_num");
        bookInfoStatusTextView.setText("status");
        bookInfoLocationTextView.setText("location");

        changeView(bookInfoLayout);
    }

    public void reviewHelper(){


//        JSONArray resultArray = new JSONArray(new String(responseBody));
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
//                    for (int i=0; i < resultArray.length(); i++) {
//                        JSONObject item = new JSONObject(resultArray.getString(i));
        map = new HashMap<String, Object>();
        map.put("username", "username:");
        map.put("mark", "mark");
        map.put("content", "blablabla...");
        list.add(map);


        //from instance, change listView
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list,
                R.layout.list_item_review,
                new String[]{"username", "mark", "content"},
                new int[]{R.id.review_item_username, R.id.review_item_mark,
                        R.id.review_item_content});
        reviewListView.setAdapter(adapter);
        changeView(reviewLayout);


    }

    public void sechandHelper(){
        //        JSONArray resultArray = new JSONArray(new String(responseBody));
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
//                    for (int i=0; i < resultArray.length(); i++) {
//                        JSONObject item = new JSONObject(resultArray.getString(i));
        map = new HashMap<String, Object>();
        map.put("username", "Park");
        map.put("content", "I'm sechand info...");

        list.add(map);

        //from instance, change listView
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list,
                R.layout.list_item_sechand,
                new String[]{"username", "content"},
                new int[]{R.id.sechand_item_username, R.id.sechand_item_content});
        sechandListView.setAdapter(adapter);
        changeView(sechandLayout);
    }

    public void reviewAddHelper(){
        String content = reviewEditText.getText().toString();

        Toast.makeText(MainActivity.this, "what you bb : " + content, Toast.LENGTH_SHORT).show();

    }

    public void sechandAddHelper(){
        String content = sechandEditText.getText().toString();

        Toast.makeText(MainActivity.this, "what you bb : " + content, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // menu scan icon
            if (requestCode==0) {
                Bundle bundle = data.getExtras();
                scanResult = bundle.getString("result");
                menuSearchView.setQuery(scanResult, true);
                Toast.makeText(MainActivity.this, "scan: " + scanResult, Toast.LENGTH_SHORT).show();
            }
            //borrow
            if (requestCode==1) {
                Bundle bundle = data.getExtras();
                scanResult = bundle.getString("result");
                Toast.makeText(MainActivity.this, "scan: " + scanResult, Toast.LENGTH_SHORT).show();

            }
            //retrun
            if (requestCode==2) {
                Bundle bundle = data.getExtras();
                scanResult = bundle.getString("result");
                Toast.makeText(MainActivity.this, "scan: " + scanResult, Toast.LENGTH_SHORT).show();
                //get bookname from internet

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Return book: "+scanResult);
                builder.setTitle("Confirm");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //borrow
                        Toast.makeText(MainActivity.this, "borrow: " + scanResult, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            switch (currentLayoutId) {
                case R.id.layout_login:
                    //exit
//                    return super.onKeyDown(keyCode, event);
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Quit?");
                    builder.setTitle("Confirm");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            MainActivity.this.finish();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();
                    break;
                }
                case R.id.layout_menu:
                case R.id.layout_adminmenu:
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Logout?");
                    builder.setTitle("Confirm");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            changeView(loginLayout);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();
                    break;
                }
                case R.id.layout_user:
                case R.id.layout_search:
                case R.id.layout_borrowed:
                case R.id.layout_reserved:
                    loginHelper();
                    changeView(menuLayout);
                    break;
                case R.id.layout_bookinfo:
                    changeView(searchLayout);
                    break;
                case R.id.layout_review:
                case R.id.layout_sechand:
                    changeView(bookInfoLayout);
                    break;
            }
        }
        return false;
    }


}
