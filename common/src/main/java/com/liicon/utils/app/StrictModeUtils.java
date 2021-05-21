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

package com.liicon.utils.app;

import android.annotation.TargetApi;
import android.os.StrictMode;

import com.liicon.utils.version.SdkVersion;
import com.liicon.utils.version.SdkVersionCodes;

/**
 * 严苛模式
 * @version  v0.1  king  2014-11-19  严苛模式
 */
public class StrictModeUtils {

    @TargetApi(11)
    public static void openStrictMode(Class<?> T) {
        if (SdkVersion.isSupport(SdkVersionCodes.GINGERBREAD)) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder =
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog();

            if (SdkVersion.isSupport(SdkVersionCodes.HONEYCOMB)) {
                threadPolicyBuilder.penaltyFlashScreen();
                vmPolicyBuilder.setClassInstanceLimit(T, 1);
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }
    }

}
