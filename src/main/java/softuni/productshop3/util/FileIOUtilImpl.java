package softuni.productshop3.util;

import java.io.*;

public class FileIOUtilImpl implements FileIOUtil {
    @Override
    public String readFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine())!=null){
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    public void writeFile(String content, String path) throws IOException {
        File file = new File(path);
        FileWriter fr = new FileWriter(file);
        fr.write(content);
        fr.close();
    }
}
