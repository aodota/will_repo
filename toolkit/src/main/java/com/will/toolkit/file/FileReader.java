/*
 * $Header: FileReader.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-2 下午04:44:38
 * $Owner: will
 */
package com.will.toolkit.file;

import com.will.toolkit.util.ByteUtil;
import org.apache.commons.collections4.map.LRUMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.util.Map;

/**
 * FileReader
 * @author will
 * @version 1.0.0.0 2012-5-2 下午04:44:38
 */
public class FileReader {
    /** log  */
    private static final Log log = LogFactory.getLog(FileReader.class);
    
    /**
     * FileReader
     */
    private static FileReader fileReader = new FileReader();
    
    /**
     * MAP
     */
    @SuppressWarnings("unchecked")
    private Map<String, RandomAccessFile> MAP = new LRUMap<>(100);
    
    /**
     * 长度字节数组
     */
    private byte[] bytes = new byte[FileUtil.BIT_LENGTH];
    
    /**
     * 最大文件内容长度
     */
    private static final int MAX_FILE_LEN = 10000000;
    
    /**
     * 获取实例
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:48:11
     */
    public static FileReader getInstance() {
        return fileReader;
    }

    /**
     * 通过ID读取文件内容
     * @param id
     * @return
     */
    public byte[] readFile(String id) {
        byte[] result = null;
        try {
            String fid = id.substring(0, 8);
            RandomAccessFile raf = MAP.get(fid);
            if (null == raf) {
                raf = getAccessFile(fid);
                if (null != raf) {
                    MAP.put(id, raf);
                }
            }
            
            if (null != raf) {
                synchronized (raf) {
                    // 获得便宜值
                    long offset = Long.parseLong(id.substring(fid.length()));
                    
                    raf.seek(offset);
                    
                    // 读取长度信息
                    raf.read(bytes, 0, bytes.length);
                    int len = ByteUtil.bytesToInt(bytes, ByteOrder.BIG_ENDIAN);
                    
                    if (len > MAX_FILE_LEN || len <= 0) {
                        return null;
                    }
                    
                    // 读取文件信息
                    result = new byte[len];
                    raf.read(result, 0, len);
                }
            }
        } catch (Exception e) {
            log.error("read file error", e);
        }
        
        return result;
    }

    /**
     * 获取文件
     * @param fid
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:49:34
     * @throws FileNotFoundException 
     */
    private RandomAccessFile getAccessFile(String fid) throws FileNotFoundException {
        String path = FileUtil.getPath() + File.separator + fid;
        
        File file = new File(path);
        if (file.exists()) {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            return raf;
        }
        
        return null;
    }
}
