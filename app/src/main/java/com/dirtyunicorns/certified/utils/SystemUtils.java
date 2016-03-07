package com.dirtyunicorns.certified.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class SystemUtils {

    public static boolean isPackageInstalled(Context context, String pkg) {

        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(pkg))
                return true;
        }
        return false;

    }

}
