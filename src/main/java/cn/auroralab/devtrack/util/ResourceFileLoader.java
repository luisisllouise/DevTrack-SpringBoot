package cn.auroralab.devtrack.util;

import cn.auroralab.devtrack.environment.Environment;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 资源文件加载工具类，用于操作resources文件夹中的文件。
 *
 * @author Guanyu Hu
 * @since 2022-10-22
 */
public class ResourceFileLoader {
    /**
     * 读取指定路径下的文件内容。
     *
     * @param classPath resources下的相对路径。
     * @return 文件文本。
     * @author Guanyu Hu
     * @since 2022-10-22
     */
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
