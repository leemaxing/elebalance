/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley.toolbox;

import java.io.File;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.liicon.manager.FileManager;

public class Volley {

    /**
     * Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
     *
     * @param context A {@link android.content.Context} to use for creating the cache dir.
     * @param stack An {@link HttpStack} to use for the network, or null for default.
     * @return A started {@link RequestQueue} instance.
     */
    public static RequestQueue newRequestQueue(Context context, File cacheDir, HttpStack stack) {
        if (cacheDir == null) {
            throw new NullPointerException("'cacheDir' cannot be empty.");
        } else if (!cacheDir.exists()) {
            cacheDir = new File(FileManager.getCacheDirByData(context.getPackageName()), "http");
            cacheDir.mkdirs();
        } else if (!cacheDir.isDirectory()) {
            throw new NullPointerException("'cacheDir' must be Directory.");
        }
        
        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }

        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        queue.start();

        return queue;
    }

    /**
     * Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
     * 
    private static final String DEFAULT_CACHE_DIR = "volley";
    File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
    
    
    private static final String DEFAULT_CACHE_DIR = "cache/http_request";
        String strCacheDir = BasicCache.getCacheUsableDir(context, DEFAULT_CACHE_DIR);
        File cacheDir = new File(strCacheDir);
     * 
     * @param context A {@link Context} to use for creating the cache dir.
     * @param cacheDir
     * @return A started {@link RequestQueue} instance.
     */
    public static RequestQueue newRequestQueue(Context context, File cacheDir) {
        return newRequestQueue(context, cacheDir, null);
    }
    
    
}