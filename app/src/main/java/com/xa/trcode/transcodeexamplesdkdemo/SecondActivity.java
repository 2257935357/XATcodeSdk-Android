package com.xa.trcode.transcodeexamplesdkdemo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xa.transcode.XATSCodeSDK;
import com.xa.transcode.bean.XACatalog;
import com.xa.transcode.bean.XAContent;
import com.xa.transcode.maintranscode.IBooksTransContentCallback;
import com.xa.trcode.transcodeexamplesdkdemo.fragment.CatalogListFragment;

import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private static final String EXTRA_URL = "com.xa.trcode.demo.secondactivity.book.url";

    public static void start(Context context, String url) {
        context.startActivity(new Intent(context, SecondActivity.class).putExtra(EXTRA_URL, url));
    }

    private String currentBookUrl;

    private DrawerLayout drawerLayout;
    private Button btnMenu, btnPre, btnNext;
    private FrameLayout mCatalogListContainer;
    private TextView tvTextView, tvContent;
    private XAContent currentXAContent;
    private int currentCatallogIndex;
    private List<XACatalog> XACatalogList;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载");
        progressDialog.setCancelable(false);
        currentBookUrl = getIntent().getStringExtra(EXTRA_URL);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        tvTextView = findViewById(R.id.catalog_name);
        drawerLayout = findViewById(R.id.drawer_layout);
        btnMenu = findViewById(R.id.catalog_menu);
        mCatalogListContainer = findViewById(R.id.lf_fragmnt_container);
        tvContent = findViewById(R.id.content_tv);
        btnPre = findViewById(R.id.pre_tv);
        btnNext = findViewById(R.id.next_tv);
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());//内容文字可滑动
    }

    private void initListener() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog.isShowing()) {
                    return;
                }
                if (XACatalogList != null) {
                    if (currentCatallogIndex - 1 < 0) {
                        currentCatallogIndex = 0;
                        return;
                    } else {
                        currentCatallogIndex--;
                    }
                    tvTextView.setText(XACatalogList.get(currentCatallogIndex).getTitle());
                    getNovelContent(XACatalogList.get(currentCatallogIndex).getUrl());
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialog.isShowing()) {
                    return;
                }
                if (XACatalogList != null) {
                    if (currentCatallogIndex + 1 >= XACatalogList.size()) {
                        currentCatallogIndex = XACatalogList.size() - 1;
                        return;
                    } else {
                        currentCatallogIndex++;
                    }
                    tvTextView.setText(XACatalogList.get(currentCatallogIndex).getTitle());
                    getNovelContent(XACatalogList.get(currentCatallogIndex).getUrl());
                }
            }
        });
    }

    private void initData() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.lf_fragmnt_container, CatalogListFragment.newInstance(currentBookUrl));
        ft.commitNow();
    }

    public void setCurrentBookName(String name, String catalogUrl, int index) {
        tvTextView.setText(name);
        drawerLayout.closeDrawer(Gravity.START, true);
        getNovelContent(catalogUrl);
        currentCatallogIndex = index;
    }

    private void getNovelContent(String catalogUrl) {
        progressDialog.show();
        XATSCodeSDK.getInstanceSdk().obtainBooksCurrentCatalogueContent(catalogUrl, new IBooksTransContentCallback() {
            @Override
            public void onSuccess(XAContent result) {
                currentXAContent = result;
                tvContent.setText(result.getContent());
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable throwable) {
                progressDialog.dismiss();
            }
        });
    }

    public void getCatalogList(List<XACatalog> result) {
        XACatalogList = result;
    }
}