/*
 * Copyright (c) 2014,KJFrameForAndroid 张涛 (kymjs123@gmail.com).
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
package org.kymjs.aframe;

public final class KJConfig {

    public static final double VERSION = 1.32;

    public static final boolean isDebug = true;

    /** 声音改变的广播 */
    public static final String RECEIVER_MUSIC_CHANGE = KJConfig.class
            .getName() + "org.kymjs.android.frame.music_change";
    /** 错误处理广播 */
    public static final String RECEIVER_ERROR = KJConfig.class
            .getName() + "org.kymjs.android.frame.error";
    /** 无网络警告广播 */
    public static final String RECEIVER_NOT_NET_WARN = KJConfig.class
            .getName() + "org.kymjs.android.frame.notnet";
    /** preference键值对 */
    public static final String SETTING_FILE = "kjframe_preference";
    public static final String ONLY_WIFI = "only_wifi";

    /** 图片缓存路径、http数据缓存路径 */
    public static final String cachePath = "/KJLibrary/";
    /** 图片本地缓存文件名前缀 */
    public static final String IMAGE_PREFIX = "KJLibrary_";
    /** 数据库名 */
    public static final String DBNAME = "KJLibrary.db";
    /** http数据通信中 */
    public static final String HttpParamsKey = "KJFrameForAndroid_File";
}
