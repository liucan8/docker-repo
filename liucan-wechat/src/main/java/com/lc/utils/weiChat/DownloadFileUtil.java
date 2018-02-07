package com.lc.utils.weiChat;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFileUtil {
	private Logger logger = Logger.getLogger("FILE");
	private String url;
	private int nStartPos = 0;
	private String path;
	private String dec;

	public DownloadFileUtil(String url, String path, String dec) {
		this.url = url;
		this.dec = dec;
		this.path = path;
	}

	public void run() {
		RandomAccessFile oSavedFile = null;
		try {
			URL xmlUrl = new URL(this.url);
			HttpURLConnection httpConnection = (HttpURLConnection) xmlUrl
					.openConnection();
			long nEndPos = getFileSize(this.url);
			String filePath = this.path + this.dec;
			oSavedFile = new RandomAccessFile(filePath, "rw");
			long nowPos = oSavedFile.length();
			if (nowPos != nEndPos) {
				httpConnection.setRequestProperty("User-Agent",
						"Internet Explorer");
				String sProperty = "bytes=0-";
				httpConnection.setRequestProperty("RANGE", sProperty);
				InputStream input = httpConnection.getInputStream();
				byte[] b = new byte[2048];
				int nRead;
				while (((nRead = input.read(b, 0, 2048)) > 0)
						&& (this.nStartPos < nEndPos)) {
					oSavedFile.write(b, 0, nRead);
					this.nStartPos += nRead;
				}
				httpConnection.disconnect();
				this.logger.info("下载" + this.url + "成功保存到:" + filePath);
			}
		} catch (IOException e) {
			this.logger.error("下载失败 " + this.url + e.getMessage());

			if (oSavedFile != null)
				try {
					oSavedFile.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		} finally {
			if (oSavedFile != null)
				try {
					oSavedFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	public static synchronized long getFileSize(String sURL) {
		int nFileLength = -1;
		try {
			URL url = new URL(sURL);
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
			httpConnection
					.setRequestProperty("User-Agent", "Internet Explorer");
			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				System.err.println("Error Code : " + responseCode);
				return -2L;
			}

			for (int i = 1;; i++) {
				String sHeader = httpConnection.getHeaderFieldKey(i);
				if (sHeader == null)
					break;
				if (sHeader.equals("Content-Length")) {
					nFileLength = Integer.parseInt(httpConnection
							.getHeaderField(sHeader));
					break;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return nFileLength;
	}

}
