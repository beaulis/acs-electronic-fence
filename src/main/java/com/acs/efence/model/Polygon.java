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
 * 多边形类，用于表示具有层级关系的多边形区域。
 * 该类实现了Serializable接口，使得多边形对象可以被序列化。
 */
@Data
@Accessors(chain = true)
public class Polygon implements Serializable {

    /**
     * 区域代码，用于唯一标识一个多边形区域。
     */
    String code;

    /**
     * 父区域代码，用于表示当前多边形区域所属的上级区域。
     */
    String parent_code;

    /**
     * 更高层次的区域代码，用于表示当前多边形区域在更大范围内的位置。
     */
    String p_code;

    /**
     * 中层区域代码，用于表示当前多边形区域在中层范围内的位置。
     */
    String c_code;

    /**
     * 最细层级的区域代码，用于表示当前多边形区域在最细层级范围内的位置。
     */
    String a_code;

    /**
     * 区域级别，用于表示当前多边形区域的层级。
     */
    Integer level;

    /**
     * 多边形的几何信息，通常是以字符串形式表示的多边形坐标点。
     */
    String polygon;
}

