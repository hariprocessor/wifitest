package com.nunuplanet.test.wifi;


import java.text.Collator;
import java.util.Comparator;


/**
 * Created by hari on 10/7/2016.
 */
public class WiFiData {
    public String BSSID;
    public String SSID;
    public int level;
    public String venueName;

    public static final Comparator<WiFiData> COMPARATOR = new Comparator<WiFiData>() {
        private final Collator collator =Collator.getInstance();
        @Override
        public int compare(WiFiData wiFiList, WiFiData t1) {
            return collator.compare(String.valueOf(wiFiList.level), String.valueOf(t1.level));
        }
    };

}
