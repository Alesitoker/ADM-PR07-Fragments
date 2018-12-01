package es.iessaladillo.alex.adm_pr07_fragments.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

public class IntentsUtils {
    private IntentsUtils() {}

    public static Intent newEmail(String email) {
        return new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+ email));
    }

    public static Intent newDial(String phoneNumber) {
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.trim()));
    }

    public static Intent newSearchInMap(String address) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
    }

    public static Intent newWebSearch(String url) {
        Intent intentWebSearch = new Intent(Intent.ACTION_VIEW);
        String head = "";
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            head = "http://";
        }
        intentWebSearch.setData(Uri.parse(head + url));
        return intentWebSearch;
    }

    public static boolean isAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> appList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return appList.size() > 0;
    }
}
