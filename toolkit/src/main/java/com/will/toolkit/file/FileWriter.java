/*
 * $Header: FileWriter.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-2 下午01:33:27
 * $Owner: will
 */
package com.will.toolkit.file;

import com.will.toolkit.util.ByteUtil;
import com.will.toolkit.util.DateUtil;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

/**
 * FileWriter
 * @author will
 * @version 1.0.0.0 2012-5-2 下午01:33:27
 */
public class FileWriter {
    /** log  */
    private static final Logger log = LoggerFactory.getLogger(FileWriter.class);
    
    /**
     * FileWriter
     */
    private static FileWriter fileWriter = new FileWriter();
    
    /**
     * RandomAccessFile
     */
    private RandomAccessFile raf;
    
    /**
     * 当前文件
     */
    private File file;
    
    /**
     * 当前文件名
     */
    private String fileName;
    
    /**
     * 当前日期
     */
    private Date day;
    
    /**
     * builder
     */
    private StringBuilder builder = new StringBuilder();
    
    /**
     * 获得实例
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:04:29
     */
    public static FileWriter getInstance() {
        return fileWriter;
    }
    
    /**
     * 写文件
     * @param bytes
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:06:52
     */
    public synchronized String writeFile(byte[] bytes) {
        builder.setLength(0);
        try {
            RandomAccessFile raf = getAccessFile();
            
            // 获得当前偏移值
            long offset = raf.getFilePointer();
            
            // 先写长度
            raf.write(ByteUtil.intToBytes(bytes.length, FileUtil.LENGTH));
            
            // 写文件内容
            raf.write(bytes);
            
            builder.append(fileName).append(offset);
            
        } catch (IOException e) {
            log.error("write file error", e);
        }
        
        return builder.toString();
    }

    /**
     * 获得随机读写文件
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:08:29
     * @throws IOException 
     */
    private RandomAccessFile getAccessFile() throws IOException {
        Calendar cg = Calendar.getInstance();
        if (raf == null) {
            createNewFile(cg.getTime());
            if (file.exists()) {
                raf.seek(raf.length());
            }
            
            day = cg.getTime();
        } 
        
        if (cg.get(Calendar.HOUR_OF_DAY) == 0) {
            if (!DateUtil.isSameDay(day, cg.getTime())) {
                // 记录当前日期
                day = cg.getTime();
                
                // 关闭流
                raf.close();
                
                // 创建一个新文件
                createNewFile(cg.getTime());
            }
        }
        return raf;
    }

    /**
     * 创建一个新文件
     * @version 1.0.0.0 2012-5-2 下午04:32:48
     * @throws FileNotFoundException 
     */
    private void createNewFile(Date date) throws FileNotFoundException {
        String filePath = FileUtil.getPath() + File.separator + DateUtil.formatDate(date, DateUtil.DATE_FULL_COMPACT);
        file = new File(filePath);
        fileName = file.getName();
        
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        
        raf = new RandomAccessFile(file, "rw");        
    }
}
