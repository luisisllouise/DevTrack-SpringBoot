package cn.auroralab.devtrack.util;

import cn.auroralab.devtrack.environment.Environment;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceFileLoader {
    public static String readFile(String classPath) {
        StringBuilder res = new StringBuilder();
        try {
            InputStream is = new DefaultResourceLoader().getResource("classpath:VCodeEmailTemplate.html").getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String data = null;
            int lineCount = 0;
            while ((data = br.readLine()) != null) {
                if (lineCount > 0) res.append(Environment.EOL);
                res.append(data);
                lineCount++;
            }
            br.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res.toString();
    }
}
