package cn.edu.scu.zy.librarymanagesystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.support.v7.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.xys.libzxing.zxing.encoding.EncodingUtils;

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

    public final static String url = "http://119.29.198.208/lms/";

    public int currentLayoutId;
    String id;
    String pwd;

//    String content;
//    String scanResult;

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
    public TextView userCollegeTextView;
    public TextView userGradeTextView;
    public TextView userAddressTextView;
    public TextView userEmailTextView;
    public ImageView userQrImageView;

    public LinearLayout bookInfoLayout;
    public TextView bookInfoTitileTextView;
    public TextView bookInfoAuthorTextView;
    public TextView bookInfoCallNumberTextView;
    public TextView bookInfoIsbnTextView;
    public TextView bookInfoYearTextView;
    public TextView bookInfoStatusTextView;
    public TextView bookInfoAbstractTextView;
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

    public LinearLayout adminMenuLayout;
    public TextView adminMenuWelcomeTextView;
    public Button adminMenuAuditButton;
    public Button adminMenuReaderManageButton;
    public Button adminMenuBookManageButton;
    public Button adminMenuBorrowButton;
    public Button adminMenuReturnButton;

    public LinearLayout bookManageLayout;


    private void findAllViewById() {
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
        userCollegeTextView = (TextView) findViewById(R.id.text_user_college);
        userGradeTextView = (TextView) findViewById(R.id.text_user_phone);
        userPhoneTextView = (TextView) findViewById(R.id.text_user_phone);
        userAddressTextView = (TextView) findViewById(R.id.text_user_address);
        userEmailTextView = (TextView) findViewById(R.id.text_user_email);
        userQrImageView = (ImageView) findViewById(R.id.image_user_qr);

        bookInfoLayout = (LinearLayout) findViewById(R.id.layout_bookinfo);
        bookInfoTitileTextView = (TextView) findViewById(R.id.text_bookinfo_title);
        bookInfoAuthorTextView = (TextView) findViewById(R.id.text_bookinfo_author);
        bookInfoIsbnTextView = (TextView) findViewById(R.id.text_bookinfo_isbn);
        bookInfoYearTextView = (TextView) findViewById(R.id.text_bookinfo_year);
        bookInfoCallNumberTextView = (TextView) findViewById(R.id.text_bookinfo_callnum);
        bookInfoStatusTextView = (TextView) findViewById(R.id.text_bookinfo_status);
        bookInfoAbstractTextView = (TextView) findViewById(R.id.text_bookinfo_abstract);
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

        adminMenuLayout = (LinearLayout) findViewById(R.id.layout_adminmenu);
        adminMenuWelcomeTextView = (TextView) findViewById(R.id.text_adminmenu_welcome);
        adminMenuAuditButton = (Button) findViewById(R.id.button_adminmenu_audit);
        adminMenuReaderManageButton = (Button) findViewById(R.id.button_adminmenu_readermanage);
        adminMenuBookManageButton = (Button) findViewById(R.id.button_adminmenu_bookmanage);
        adminMenuBorrowButton = (Button) findViewById(R.id.button_adminmenu_borrow);
        adminMenuReturnButton = (Button) findViewById(R.id.button_adminmenu_return);

        bookManageLayout = (LinearLayout) findViewById(R.id.layout_book_manage);
    }


    private void setAllListener() {

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
                changeView(adminMenuLayout);
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

        borrowedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> map= (HashMap<String, Object>)borrowedListView.getItemAtPosition(i);
                //do renew here
                renewHelper((String) map.get("book_id"));

            }
        });

        reservedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> map= (HashMap<String, Object>)reservedListView.getItemAtPosition(i);
                //do cancel reserve here
                reserveCancelHelper((String) map.get("call_num"));
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
                reserveHelper(bookInfoCallNumberTextView.getText().toString());
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

        adminMenuAuditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "audit not found", Toast.LENGTH_SHORT).show();
            }
        });

        adminMenuReaderManageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "reader manage not found", Toast.LENGTH_SHORT).show();
            }
        });

        adminMenuBookManageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView(bookManageLayout);
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
//                Toast.makeText(MainActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();

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
                        //make a QR image in info page
                        String contentString = id + pwd;
                        if (!contentString.equals("")) {
                            Bitmap qrCodeBitmap = EncodingUtils.createQRCode(contentString, 350, 350,
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                            userQrImageView.setImageBitmap(qrCodeBitmap);
                        }
                        changeView(menuLayout);
                    } else if (resultObj.getString("user_type").equals("admin")) {
                        //set admin info
                        adminMenuWelcomeTextView.setText("Hello, admin: "+usrName);
                        changeView(adminMenuLayout);
                    } else {
                        Toast.makeText(MainActivity.this, "Wrong id/pwd", Toast.LENGTH_SHORT).show();
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
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("pwd", pwd);
        client.post(url + "user_info.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject resultObj = new JSONObject(new String(responseBody));
                    userNameTextView.setText(resultObj.getString("name"));
                    userIdTextView.setText(resultObj.getString("id"));
                    userCollegeTextView.setText(resultObj.getString("college"));
                    userGradeTextView.setText(resultObj.getString("grade"));
                    userPhoneTextView.setText(resultObj.getString("phone"));
//                    userAddressTextView.setText(resultObj.getString("address"));
                    userAddressTextView.setText("");
                    userEmailTextView.setText(resultObj.getString("email"));
                    changeView(userLayout);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "JSON error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, "error "+String.valueOf(statusCode), Toast.LENGTH_SHORT).show();
            }
        });
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


    public void renewHelper(final String book_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Renew book: "+book_id+"?");
        builder.setTitle("Confirm");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams param = new RequestParams();
                param.put("id", id);
                param.put("pwd", pwd);
                param.put("book_id", book_id);
                client.post(url + "renew.php", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        JSONObject resultObj = null;
                        try {
                            resultObj = new JSONObject(new String(responseBody));
                            if (resultObj.getString("result").equals("true")) {
                                borrowedListHelper();
                                Toast.makeText(MainActivity.this, "Renew Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Renew Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
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
                        map.put("call_num", item.getString("call_num"));
                        map.put("date", item.getString("date"));
                        map.put("status", item.getString("status"));
                        list.add(map);
                    }
                    //from instance, change listView
                    SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list,
                            R.layout.list_item_reserved,
                            new String[]{"title", "author", "call_num", "date", "status"},
                            new int[]{R.id.reserved_item_title, R.id.reserved_item_author,
                                    R.id.reserved_item_callnum, R.id.reserved_item_date,
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


    public void reserveCancelHelper(final String title_id) {
        //do reserve cancel
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Cancel Reserve: "+title_id+"?");
        builder.setTitle("Confirm");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams param = new RequestParams();
                param.put("id", id);
                param.put("pwd", pwd);
                param.put("title_id", title_id);
                client.post(url + "reserve_cancel.php", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        JSONObject resultObj = null;
                        try {
                            resultObj = new JSONObject(new String(responseBody));
                            if (resultObj.getString("result").equals("true")) {
                                reservedListHelper();
                                Toast.makeText(MainActivity.this, "Cancel Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "cancel Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(MainActivity.this, "reserve_cancel.php not exist", Toast.LENGTH_SHORT).show();
                    }
                });
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
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("title_id", call_num);
        client.post(url + "book_info.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
//                    Toast.makeText(MainActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();
                    JSONObject resultObj = new JSONObject(new String(responseBody));
                    bookInfoTitileTextView.setText(resultObj.getString("title"));
                    bookInfoAuthorTextView.setText(resultObj.getString("author"));
                    bookInfoIsbnTextView.setText(resultObj.getString("isbn"));
                    bookInfoCallNumberTextView.setText(resultObj.getString("id"));
                    bookInfoYearTextView.setText(resultObj.getString("year"));
//                    bookInfoStatusTextView.setText(resultObj.getString("status"));
                    bookInfoStatusTextView.setText("");
                    bookInfoAbstractTextView.setText(resultObj.getString("abstract"));
                    changeView(bookInfoLayout);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "JSON error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public void reserveHelper(final String title_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Reserve book: "+title_id+"?");
        builder.setTitle("Confirm");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams param = new RequestParams();
                param.put("id", id);
                param.put("pwd", pwd);
                param.put("title_id", title_id);
                client.post(url + "reserve.php", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        JSONObject resultObj = null;
                        try {
                            resultObj = new JSONObject(new String(responseBody));
                            if (resultObj.getString("result").equals("true")) {
                                bookInfoHelper(title_id);
                                Toast.makeText(MainActivity.this, "Reserve Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Reserve Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
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


    public void reviewHelper(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("title_id", bookInfoCallNumberTextView.getText().toString());
        client.post(url + "review.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Toast.makeText(MainActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();
                    JSONArray resultArray = new JSONArray(new String(responseBody));
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    Map<String, Object> map;
                    for (int i=0; i < resultArray.length(); i++) {
                        JSONObject item = new JSONObject(resultArray.getString(i));
                        map = new HashMap<String, Object>();
                        map.put("username", item.getString("username"));
                        map.put("mark", item.getString("mark"));
                        map.put("content", item.getString("content"));
                        map.put("time", item.getString("time"));
                        list.add(map);
                    }
                    //from instance, change listView
                    SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list,
                            R.layout.list_item_review,
                            new String[]{"username", "mark", "content", "time"},
                            new int[]{R.id.review_item_username, R.id.review_item_mark,
                                    R.id.review_item_content, R.id.review_item_time});
                    reviewListView.setAdapter(adapter);
                    changeView(reviewLayout);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "JSON error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        }) ;
    }


    public void reviewAddHelper(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("pwd", pwd);
        param.put("title_id", bookInfoCallNumberTextView.getText().toString());
        param.put("content", reviewEditText.getText().toString());
        client.post(url + "review_add.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    reviewEditText.setText("");
                    JSONObject resultObj = new JSONObject(new String(responseBody));
                    if (resultObj.getString("result").equals("true")) {
                        reviewEditText.clearComposingText();
                        reviewHelper();
                        Toast.makeText(MainActivity.this, "Review Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Review Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public void sechandHelper(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("title_id", bookInfoCallNumberTextView.getText().toString());
        client.post(url + "sechand.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray resultArray = new JSONArray(responseBody.toString());
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    Map<String, Object> map;
                    for (int i=0; i < resultArray.length(); i++) {
                        JSONObject item = new JSONObject(resultArray.getString(i));
                        map = new HashMap<String, Object>();
                        map.put("username", item.getString("username"));
                        map.put("content", item.getString("content"));
                        map.put("time", item.getString("time"));
                        list.add(map);
                    }
                    //from instance, change listView
                    SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list,
                            R.layout.list_item_sechand,
                            new String[]{"username", "content", "time"},
                            new int[]{R.id.sechand_item_username, R.id.sechand_item_content,
                                    R.id.sechand_item_time});
                    sechandListView.setAdapter(adapter);
                    changeView(sechandLayout);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, "sechand.php not found", Toast.LENGTH_SHORT).show();
            }
        }) ;
    }


    public void sechandAddHelper(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("pwd", pwd);
        param.put("title_id", bookInfoCallNumberTextView.getText().toString());
        param.put("content", sechandEditText.getText().toString());
        client.post(url + "sechand_add.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject resultObj = new JSONObject(responseBody.toString());
                    if (resultObj.getString("result").equals("true")) {
                        reviewHelper();
                        Toast.makeText(MainActivity.this, "Publish Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Publish Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, "sechand_add.php not found", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void borrowDialog(final String book_id){

        LayoutInflater inflater = LayoutInflater.from(this);
        final View textEntryView = inflater.inflate(R.layout.layout_dialog_borrow, null);
        final EditText edtId = (EditText) textEntryView.findViewById(R.id.edit_dialog_borrow_id);
        final EditText edtPwd = (EditText) textEntryView.findViewById(R.id.edit_dialog_borrow_password);

        final  AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Confirm");
        builder.setMessage("Borrow:"+book_id);
        builder.setView(textEntryView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int whichButton){
                borrowHelper(book_id, edtId.getText().toString(), edtPwd.getText().toString());
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }


    public void borrowHelper(String book_id, String borrower_id, String borrower_pwd) {
        Toast.makeText(MainActivity.this, "borrow: "+book_id+" "+borrower_id+" "+borrower_pwd, Toast.LENGTH_SHORT).show();
        //borrow
    }


    public void returnDialog(final String book_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Return book: "+book_id);
        builder.setTitle("Confirm");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                returnHelper(book_id);
                dialogInterface.dismiss();
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

    public void returnHelper(String book_id) {
        Toast.makeText(MainActivity.this, "return: "+book_id, Toast.LENGTH_SHORT).show();
        //return
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // menu scan icon
            if (requestCode==0) {
                menuSearchView.setQuery(data.getExtras().getString("result"), true);
            }
            //borrow
            if (requestCode==1) {
                borrowDialog(data.getExtras().getString("result"));

            }
            //retrun
            if (requestCode==2) {
                returnDialog(data.getExtras().getString("result"));
            }
        }
    }


    public void quitDialog() {
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
    }


    public void logoutDialog() {
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
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            switch (currentLayoutId) {
                case R.id.layout_login:
                    //exit
                    quitDialog();
                    break;
                case R.id.layout_menu:
                case R.id.layout_adminmenu:
                    logoutDialog();
                    break;
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
                case R.id.layout_book_manage:
                    changeView(adminMenuLayout);
                    break;
            }
        }
        return false;
    }


}
