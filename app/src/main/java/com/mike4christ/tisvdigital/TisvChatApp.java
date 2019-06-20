package com.mike4christ.tisvdigital;

import android.app.Application;

public class TisvChatApp extends Application {
    private static boolean sIsChatActivityOpen = false;

    public static boolean isChatActivityOpen() {
        return sIsChatActivityOpen;
    }

    public static void setChatActivityOpen(boolean isChatActivityOpen) {
        sIsChatActivityOpen = isChatActivityOpen;
    }

    public void onCreate() {
        super.onCreate();
    }
}
