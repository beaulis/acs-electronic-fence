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
import lombok.ToString;

/**
 * 表示地理坐标点的类。
 *
 * 该类用于封装地理坐标的经度和纬度信息。
 * 经度（longitude）和纬度（latitude）都是浮点数，分别表示地理坐标中的经度和纬度。
 * 经度范围为-180到180，纬度范围为-90到90。
 */
@Data
@ToString
public class Point {
    /**
     * 地理坐标的经度。
     *
     * 经度值范围为-180到180，东经为正数，西经为负数。
     */
    double lng;

    /**
     * 地理坐标的纬度。
     *
     * 纬度值范围为-90到90，北纬为正数，南纬为负数。
     */
    double lat;

    /**
     * 构造函数，用于创建一个地理坐标点。
     *
     * @param lng 经度值，范围为-180到180。
     * @param lat 纬度值，范围为-90到90。
     */
    public Point(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }
}

