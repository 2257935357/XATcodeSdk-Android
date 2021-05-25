package com.xa.trcode.transcodeexamplesdkdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xa.transcode.XATSCodeSDK;
import com.xa.transcode.bean.XATransCodeBookEntity;
import com.xa.transcode.spider.ISearchBookCompleteListaner;
import com.xa.transcode.spider.SEARCH_TYPE_SOURCE;
import com.xa.trcode.transcodeexamplesdkdemo.adapter.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button testXsData1;
    private RadioGroup radioGroup;
    private int searchType;
    private EditText editText;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter = new BookAdapter(new ArrayList<XATransCodeBookEntity>());
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        radioGroup.check(R.id.baidu_radio);

    }

    private void initView() {
        testXsData1 = findViewById(R.id.test_btn1);
        radioGroup = findViewById(R.id.radio_group);
        editText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.book_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(bookAdapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在搜索");
        progressDialog.setCancelable(false);
    }

    private void initListener() {
        testXsData1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.baidu_radio:
                        searchType = SEARCH_TYPE_SOURCE.SEARCH_BAIDU_SOURCE;
                        break;
                    case R.id.san60_radio:
                        searchType = SEARCH_TYPE_SOURCE.SEARCH_360_SOURCE;
                        break;
                    case R.id.sm_radio:
                        searchType = SEARCH_TYPE_SOURCE.SEARCH_SHENMA_SOURCE;
                        break;
                    default:
                        break;
                }
            }
        });
        bookAdapter.setItemOnClick(new BookAdapter.ItemOnClick() {
            @Override
            public void onClick(String url) {
                SecondActivity.start(MainActivity.this, url);
            }
        });
    }

    private void search() {
        progressDialog.show();
        XATSCodeSDK.getInstanceSdk().obtainBooksRoster(editText.getText().toString(), searchType, new ISearchBookCompleteListaner() {
            @Override
            public void onSuccessSearch(List<XATransCodeBookEntity> result) {
                bookAdapter.setNewData(result);
                progressDialog.dismiss();
            }

            @Override
            public void onErrorSearch(Throwable throwable) {
                Log.d("onErrorSearch", "onErrorSearch ====== " + throwable.getMessage());
            }
        });
    }


}