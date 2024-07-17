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
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * AcsApi 类实现了 Serializable 接口，用于创建 AcsEFInterface 的实例。
 * 该类提供静态方法来获取配置为处理 JSON 数据的 AcsEFInterface 实例。
 */
public class AcsApi implements Serializable {

    /**
     * 无参数方法，用于创建并返回一个配置为处理 JSON 数据的 AcsEFInterface 实例。
     *
     * @return AcsEFInterface 的新实例，配置为处理 JSON。
     * @throws InstantiationException 如果类无法实例化。
     * @throws IllegalAccessException 如果无权访问类的构造函数。
     */
    @SneakyThrows
    public static AcsEFInterface json() {
        return AcsEFInterface.class.newInstance().getEFApi("JSON");
    }

    /**
     * 带有一个参数的方法，用于创建并返回一个配置为处理 JSON 数据并使用指定数据路径的 AcsEFInterface 实例。
     *
     * @param dataPath 指定的数据路径，用于配置 AcsEFInterface 实例。
     * @return AcsEFInterface 的新实例，配置为处理 JSON 并使用指定的数据路径。
     * @throws InstantiationException 如果类无法实例化。
     * @throws IllegalAccessException 如果无权访问类的构造函数。
     */
    @SneakyThrows
    public static AcsEFInterface jsonAbsDataPath(String dataPath) {
        return AcsEFInterface.class.newInstance().getEFApi("JSON", dataPath);
    }

}

