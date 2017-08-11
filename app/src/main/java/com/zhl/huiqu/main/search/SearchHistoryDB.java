package com.zhl.huiqu.main.search;

import android.content.Context;

import org.aisen.android.component.orm.SqliteUtility;
import org.aisen.android.component.orm.SqliteUtilityBuilder;

/**
 * Created by wangdan on 17/2/19.
 */

public class SearchHistoryDB {

    static void setup(Context context) {
        new SqliteUtilityBuilder().configDBName("SearchDB").configVersion(1).build(context);
    }

    static SqliteUtility getDB() {
        return SqliteUtility.getInstance("SearchDB");
    }

}
