package com.codedata.rbac.common.utils;

import cn.hutool.core.io.FileUtil;

import java.io.File;

public class DirUtil {
    static String baseDir;

    static {
        String os = System.getProperty("os.name");
        if (os.startsWith("Window")) {

            baseDir = "d:\\weatherX";
        } else {
            baseDir = "/opt/weatherX";
        }
        if (!FileUtil.exist(baseDir)){
            FileUtil.mkdir(baseDir);
        }
    }

    public static String getForecastDir(String filename) {
        if (!FileUtil.exist(baseDir + File.separator + "forecast")){
            FileUtil.mkdir(baseDir + File.separator + "forecast");
        }
        return baseDir + File.separator + "forecast" + File.separator + filename;
    }
    public static String getJujubeDir(String filename) {
        if (!FileUtil.exist(baseDir + File.separator + "jujube")){
            FileUtil.mkdir(baseDir + File.separator + "jujube");
        }
        return baseDir + File.separator + "jujube" + File.separator + filename;
    }


}
