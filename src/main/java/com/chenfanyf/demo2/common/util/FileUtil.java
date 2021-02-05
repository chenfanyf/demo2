package com.chenfanyf.demo2.common.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
public class FileUtil {
    public static <T> T read(String file, Class<T> clazz) {
        String json = read(file);
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> readList(String file, Class<T> clazz) {
        String json = read(file);
        return JSON.parseArray(json, clazz);
    }

    private static String read(String file) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (IOException e) {
            log.error("ERROR {}", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }
}
