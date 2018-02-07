package com.lc.utils;



import org.jdom2.JDOMException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by dell on 2017/2/20.
 */
public class InputStreamUtil {

    /**
     * InputStream转map
     * @param inStream
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    public static Map<String, String> inputStream2Map(InputStream inStream) throws IOException, JDOMException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String resultStr = new String(outSteam.toByteArray(), "UTF-8");
        Map<String, String> resultMap = XMLUtil.doXMLParse(resultStr);
        return resultMap;
    }

}
