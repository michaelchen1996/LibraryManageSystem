package cn.edu.scu.zy.librarymanagesystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.*;

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

    public FrameLayout mainLayout;
    public ProgressBar progressBar;

    public RelativeLayout loginLayout;
    public Button visitorButton, loginButton;
    public EditText idEditText, pwdEditText;

    public LinearLayout menuLayout;
    public SearchView menuSearchView;
    public TextView readerMenuWelcomeTextView, readerMenuBorrowedTextView;
    public TextView readerMenuReservedTextView, readerMenuExpireTextView;

    public LinearLayout borrowedLayout;
    public ListView borrowedListView;

    public LinearLayout reservedLayout;
    public ListView reservedListView;

    public LinearLayout searchLayout;
    public SearchView searchSearchView;
    public ListView searchListView;

    public LinearLayout userLayout;

    public LinearLayout bookLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLayoutId = R.id.layout_login;

        mainLayout = (FrameLayout) findViewById(R.id.layout_main);
        progressBar = (ProgressBar) findViewById(R.id.progerss_bar);

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

        borrowedLayout = (LinearLayout) findViewById(R.id.layout_borrowed);
        borrowedListView = (ListView) findViewById(R.id.list_borrowed);

        reservedLayout = (LinearLayout) findViewById(R.id.layout_reserved);
        reservedListView = (ListView) findViewById(R.id.list_reserved);

        searchLayout = (LinearLayout) findViewById(R.id.layout_search);
        searchSearchView = (SearchView) findViewById(R.id.search_search);
        searchListView = (ListView) findViewById(R.id.list_search);

        bookLayout = (LinearLayout) findViewById(R.id.layout_book);

        userLayout = (LinearLayout) findViewById(R.id.layout_user);


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
                menuSearchView.clearFocus();
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

        borrowedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> map= (HashMap<String, Object>)borrowedListView.getItemAtPosition(i);
                String book_id = (String) map.get("book_id");
                Toast.makeText(MainActivity.this, book_id, Toast.LENGTH_SHORT).show();
                //do renew here
                renewHelper(book_id);
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
                showBookInfoHelper((String) map.get("call_num"));
            }
        });


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
                    int borrowedBookAmount = resultObj.getInt("borrow_num");
                    int reservedBookAmount = resultObj.getInt("reserve_num");
                    int expireAmount = resultObj.getInt("expire_num");
                    readerMenuWelcomeTextView.setText("Hello "+usrName);
                    readerMenuBorrowedTextView.setText("Borrowed("+String.valueOf(borrowedBookAmount)+")");
                    readerMenuReservedTextView.setText("Reserved("+String.valueOf(reservedBookAmount)+")");
                    readerMenuExpireTextView.setText("Expire-date("+String.valueOf(expireAmount)+")");
                    changeView(menuLayout);

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


    public void borrowedListHelper() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("pwd", pwd);
        client.post(url + "borrowed.php", param, new AsyncHttpResponseHandler() {
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


    public void renewHelper(String book_id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("pwd", pwd);
        param.put("book_id", book_id);
        client.post(url + "renew.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //judge if the response is TRUE
                if (new String(responseBody).equals("True")) {
                    borrowedListHelper();
                } else {
                    Toast.makeText(MainActivity.this, "too many times", Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(MainActivity.this, "connect...plz wait", Toast.LENGTH_SHORT).show();
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
        //change lyout
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("query", s);
        client.post(url + "search.php", param, new AsyncHttpResponseHandler() {
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


    public void showBookInfoHelper(String call_num) {
        changeView(bookLayout);
        Toast.makeText(MainActivity.this, "this is the book: " + call_num, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            switch (currentLayoutId) {
                case R.id.layout_login:
                    //exit
                    return super.onKeyDown(keyCode, event);
                case R.id.layout_menu:
                    changeView(loginLayout);
                    break;
                case R.id.layout_user:
                case R.id.layout_search:
                case R.id.layout_borrowed:
                case R.id.layout_reserved:
                    loginHelper();
                    changeView(menuLayout);
                    break;
                case R.id.layout_book:
                    changeView(searchLayout);
                    break;
            }
        }
        return false;
    }


    public void changeView(View view){
        findViewById(currentLayoutId).setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
        currentLayoutId = view.getId();
    }

}
