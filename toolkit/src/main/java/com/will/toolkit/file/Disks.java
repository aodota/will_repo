package com.will.toolkit.file;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedList;

/**
 * 磁盘操作的帮助函数集合
 *
 * @author zozoh(zozohtnt@gmail.com)
 * @author bonyfish(mc02cxj@gmail.com)
 */
public abstract class Disks {
    /**
     * 整理路径。 将会合并路径中的 ".."
     *
     * @param path 路径
     * @return 整理后的路径
     */
    public static String getCanonicalPath(String path) {
        if (StringUtils.isBlank(path))
            return path;
        String[] pa = path.split("[\\\\/]");
        LinkedList<String> paths = new LinkedList<String>();
        for (String s : pa) {
            if (StringUtils.isBlank(s)) {
                continue;
            }
            if ("..".equals(s)) {
                if (paths.size() > 0)
                    paths.removeLast();
                continue;
            }
            if (".".equals(s)) {
                // pass
            } else {
                paths.add(s);
            }
        }
        if (path.charAt(0) == '/')
            return Joiner.on('/').appendTo(new StringBuilder(), paths).insert(0, '/').toString();
        return Joiner.on('/').appendTo(new StringBuilder(), paths).toString();
    }

    /**
     * @return 当前账户的主目录全路径
     */
    public static String home() {
        return System.getProperty("user.home");
    }

    /**
     * @param path 相对用户主目录的路径
     * @return 相对用户主目录的全路径
     */
    public static String home(String path) {
        return home() + path;
    }

    /**
     * 获取一个路径的绝对路径。如果该路径不存在，则返回null
     *
     * @param path 路径
     * @return 绝对路径
     */
    public static String absolute(String path) {
        return absolute(path,
                Thread.currentThread().getContextClassLoader(),
                "UTF-8");
    }

    /**
     * 获取一个路径的绝对路径。如果该路径不存在，则返回null
     *
     * @param path        路径
     * @param klassLoader 参考 ClassLoader
     * @param enc         路径编码方式
     * @return 绝对路径
     */
    public static String absolute(String path,
                                  ClassLoader klassLoader,
                                  String enc) {
        path = normalize(path, enc);
        if (StringUtils.isEmpty(path))
            return null;

        File f = new File(path);
        if (!f.exists()) {
            URL url = null;
            try {
                url = klassLoader.getResource(path);
                if (null == url)
                    url = Thread.currentThread()
                            .getContextClassLoader()
                            .getResource(path);
                if (null == url)
                    url = ClassLoader.getSystemResource(path);
            } catch (Throwable e) {
            }
            if (null != url)
                return normalize(url.getPath(), "UTF-8");// 通过URL获取String,一律使用UTF-8编码进行解码
            return null;
        }
        return path;
    }

    /**
     * 让路径变成正常路径，将 ~ 替换成用户主目录
     *
     * @param path 路径
     * @return 正常化后的路径
     */
    public static String normalize(String path) {
        return normalize(path, "UTF-8");
    }

    /**
     * 让路径变成正常路径，将 ~ 替换成用户主目录
     *
     * @param path 路径
     * @param enc  路径编码方式
     * @return 正常化后的路径
     */
    public static String normalize(String path, String enc) {
        if (StringUtils.isEmpty(path))
            return null;
        if (path.charAt(0) == '~')
            path = Disks.home() + path.substring(1);
        try {
            return URLDecoder.decode(path, enc);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
