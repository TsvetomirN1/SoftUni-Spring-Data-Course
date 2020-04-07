package com.softuni.springintroex.utils;


import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileUtilImpl implements FileUtil {
    @Override
    public List<String> readFileContent(String filePath) throws IOException {

       return Files.readAllLines(Paths.get(filePath))
               .stream()
               .filter(x->!x.isEmpty())
               .collect(Collectors.toList());
    }
}
