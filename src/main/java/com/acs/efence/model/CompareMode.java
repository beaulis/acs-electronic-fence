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

import lombok.Getter;

/**
 * 比较模式枚举，用于指定比较的地理区域级别。
 * <p>
 * 该枚举定义了三种比较模式：省、市、区，分别对应中国的行政区域划分。
 * 可以根据具体的业务需求，选择不同的比较模式来进行地理数据的比较操作。
 */
@Getter
public enum CompareMode {

    /**
     * 省份级别比较模式。
     * 在这种模式下，比较操作将专注于省份级别的数据。
     */
    PROVINCE,

    /**
     * 市级级别比较模式。
     * 在这种模式下，比较操作将专注于城市级别的数据。
     */
    CITY,

    /**
     * 区域级别比较模式。
     * 在这种模式下，比较操作将专注于区域（如区、县）级别的数据。
     */
    AREA

}

