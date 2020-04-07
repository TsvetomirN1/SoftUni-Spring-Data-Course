package com.softuni.springintroex.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileUtil {
   List<String> readFileContent(String filePath) throws IOException;
}
