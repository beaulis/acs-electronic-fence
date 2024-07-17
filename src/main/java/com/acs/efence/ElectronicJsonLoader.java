/*
Copyright 2024 Beaulis Wechat:B000l8

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.acs.efence;

import com.acs.efence.model.Polygon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 电子文件JSON加载器类，用于从指定的JSON文件中加载多边形数据。
 */
public class ElectronicJsonLoader {

    /**
     * 从指定的JSON路径加载多边形数据。
     *
     * @param JSON_PATH JSON文件的路径，相对于classpath。
     * @return 包含多个多边形的列表。
     * @throws RuntimeException 如果发生IO异常，将抛出运行时异常。
     */
    static List<Polygon> load(String JSON_PATH) {
        // 获取当前类的类加载器
        ClassLoader classLoader = ElectronicJsonLoader.class.getClassLoader();
        // 创建Gson实例，用于JSON和Java对象之间的转换
        Gson gson = new Gson();
        try (InputStream inputStream = classLoader.getResourceAsStream(JSON_PATH)) {
            // 创建InputStreamReader，用于读取输入流，并指定字符编码为UTF-8
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            // 创建JsonReader，用于解析JSON数据
            JsonReader jsonReader =new JsonReader(reader);
            // 使用Gson从JsonReader中反序列化出ArrayList<Polygon>对象
            return gson.fromJson(jsonReader, new TypeToken<ArrayList<Polygon>>() {});
        } catch (IOException e) {
            // 如果发生IO异常，抛出运行时异常，并包含异常信息
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

}

