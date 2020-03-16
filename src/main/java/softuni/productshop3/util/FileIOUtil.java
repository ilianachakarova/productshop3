package softuni.productshop3.util;

import java.io.IOException;

public interface FileIOUtil {
    String readFile(String filePath) throws IOException;
    void writeFile(String content, String path) throws IOException;
}
