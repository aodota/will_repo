package com.will.toolkit.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 资源扫描的帮助函数集
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class Scans {
	/** log */
	private static final Logger log = LoggerFactory.getLogger(Scans.class);
	
	/**
	 * 扫描指定包路径下的类
	 * @param pack
	 * @return
	 * @version 1.0.0.0 2011-7-18 下午05:19:24
	 */
	public static Set<Class<?>> getClasses(String pack) {
		// 类集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否迭代循环
		boolean recursive = true;
		// 将包的名字替换为路径名称
		String packageName = pack;
		String packageDirName = pack.replace(".", "/");
		// 定义一个枚举集合用来循环处理目录下的Class
		Enumeration<URL> dirs;
		
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);			
			while (dirs.hasMoreElements()) {
				// 获取元素
				URL url = dirs.nextElement();
				// 获取协议
				String protocol = url.getProtocol();
				// 文件形式
				if ("file".equals(protocol)) {
					// 获取文件的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描包下的文件,并添加到集合中去
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包
					JarFile jar;
					try {
						jar = ((JarURLConnection)url.openConnection()).getJarFile();
						Enumeration<JarEntry> entries = jar.entries();
						while (entries.hasMoreElements()) {
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							if (name.charAt(0) == '/') {
								// 以/开头的
								name = name.substring(1);
							}
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								if (idx != -1) {
									packageName = name.substring(0, idx).replace('/', '.');
								}
								
								if ((idx != -1) || recursive) {
									if (name.endsWith(".class") && !entry.isDirectory()) {
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
										} catch (ClassNotFoundException e) {
											log.error("", e);
										}
									}
								}
							}
						}
					} catch (IOException e) {
						log.error("", e);
					}
				} else if ("class".equalsIgnoreCase(protocol)) {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(url.getFile()));
                }
			}
		} catch (Exception e) {
			log.error("", e);
		}
		
		return classes;
		
	}

	/**
	 * 在文件里查找类
	 * @param packageName
	 * @param filePath
	 * @param recursive
	 * @param classes
	 * @version 1.0.0.0 2011-7-18 下午05:29:46
	 */
	private static void findAndAddClassesInPackageByFile(String packageName,
			String filePath, final boolean recursive, Set<Class<?>> classes) {
		File dir = new File(filePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		
		File[] dirFiles = dir.listFiles(new FileFilter() {			
			@Override
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		
		for (File file : dirFiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
				} catch (ClassNotFoundException e) {
					log.error("", e);
				}
			}
		}
	}
}
