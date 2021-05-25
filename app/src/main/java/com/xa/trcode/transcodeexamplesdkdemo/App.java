package com.xa.trcode.transcodeexamplesdkdemo;

import android.app.Application;

import com.xa.transcode.XATSCodeSDK;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XATSCodeSDK.init(this,"1002");
    }
}
