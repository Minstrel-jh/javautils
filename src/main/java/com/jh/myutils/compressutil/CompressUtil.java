package com.jh.myutils.compressutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class CompressUtil {
	/**
	 * 解压缩zip文件
	 * @param fileSrc
	 * 			zip文件路径
	 * @param destDir
	 * 			解压目录
	 * @return
	 * 			文件数目
	 * @throws IOException
	 */
	public static int unzip(String fileSrc, String destDir) throws IOException {
		byte[] tmpByte = new byte[1024];
		ZipFile zipFile = null;
		InputStream is = null;
		OutputStream os = null;
		int fileNum = 0;
		zipFile = new ZipFile(fileSrc, "GBK");
		for(Enumeration<ZipEntry> entries=zipFile.getEntries(); entries.hasMoreElements();) {
			ZipEntry entry = entries.nextElement();
			File file = new File(destDir+entry.getName());
			if(entry.isDirectory()) {
				file.mkdirs();
			} else {
				File parent = file.getParentFile();
				if(!parent.exists()) {
					parent.mkdirs();
				}
				is = zipFile.getInputStream(entry);
				os = new FileOutputStream(file);
				int len = 0;
				while((len=is.read(tmpByte))>0){
					os.write(tmpByte, 0, len);
				}
				fileNum++;
				is.close();
				os.flush();
				os.close();
			}
		}
		zipFile.close();
		return fileNum;
	}
}
