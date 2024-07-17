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
import java.util.List;
import java.util.Map;

/**
 * 表示多边形节点的类，用于构建多边形的树状结构。
 * 该类实现了Serializable接口，以便可以将其序列化。
 */
@Data
@Accessors(chain = true)
public class PolygonNode implements Serializable {

    /**
     * 节点的级别，用于表示节点在树中的深度或层次。
     */
    Integer level;

    /**
     * 节点的唯一标识代码。
     */
    String code;

    /**
     * 节点的省级标识代码。
     */
    String p_code;

    /**
     * 节点所属城市的代码。
     */
    String c_code;

    /**
     * 节点所属区域的代码。
     */
    String a_code;

    /**
     * 节点所代表的多边形的字符串表示。
     */
    List<String> polygons;

    /**
     * 节点所代表的多边形的具体坐标点列表。
     */
    List<List<Point>> polygonPoints;

    /**
     * 存储子节点的映射，键为子节点的标识，值为子节点对象。
     */
    Map<String, PolygonNode> children;

    /**
     * 检查当前节点是否具有子节点。
     *
     * @return 如果当前节点有子节点，则返回true；否则返回false。
     */
    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }
}

