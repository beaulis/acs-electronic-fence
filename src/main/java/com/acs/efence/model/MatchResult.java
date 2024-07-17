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
package com.acs.efence.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * 匹配结果类，用于存储区域匹配的信息。
 * 该类包含了区域的层级信息，从大到小分别为省份、城市、区域代码。
 * 通过这些代码，可以唯一标识一个具体的区域。
 */
@Data
@Accessors(chain = true)
public class MatchResult implements Serializable {

    /**
     * 省份代码，用于标识匹配结果所在的省份。
     * 代码格式遵循国家规定的行政区划代码标准。
     */
    String areaCode;

    /**
     * 城市代码，用于标识匹配结果所在的城市。
     * 代码格式遵循国家规定的行政区划代码标准。
     */
    String cityCode;

    /**
     * 省份代码，用于标识匹配结果所在的省份。
     * 代码格式遵循国家规定的行政区划代码标准。
     */
    String provinceCode;
}
