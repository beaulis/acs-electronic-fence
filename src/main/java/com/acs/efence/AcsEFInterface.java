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

import com.acs.efence.model.CompareResult;
import com.acs.efence.model.MatchResult;
import com.acs.efence.model.Point;
import com.acs.efence.model.PolygonNode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * AcsEFInterface 类实现了 Serializable 接口，用于提供与ACS（Advanced Computer System）相关的功能接口。
 * 它主要包括了获取不同模型的EF接口实现、点与多边形的比较和匹配功能。
 */
public class AcsEFInterface implements Serializable {
    /**
     * 存储多边形列表的映射，键为多边形标识，值为多边形节点。
     */
    protected Map<String, PolygonNode> POLYGON_LISTS;
    /**
     * 存储多边形节点的映射，键为节点标识，值为多边形节点。
     */
    protected Map<String, PolygonNode> POLYGON_NODES;

    /**
     * 根据模型名称获取相应的EF接口实现。
     *
     * 此方法根据传入的模型名称决定是否返回一个EF接口的实现。目前只支持"JSON"模型，
     * 如果传入的模型名称为"JSON"，则返回一个ElectronicJsonAPI的实例；否则，不返回任何实现。
     *
     * @param model 模型名称，用于确定返回的EF接口实现类型。
     * @return 如果模型名称为"JSON"，则返回一个ElectronicJsonAPI的实例；否则返回null。
     * @throws RuntimeException 如果模型名称为null，则抛出运行时异常。
     */
    AcsEFInterface getEFApi(String model) {
        // 检查模型名称是否为null
        if (model == null) throw new RuntimeException("Run model lose.");
        // 根据模型名称决定是否返回ElectronicJsonAPI的实例
        return model.equals("JSON")?new ElectronicJsonAPI():null;
    }

    /**
     * 根据模型类型获取相应的EF接口实现。
     *
     * 此方法用于根据指定的模型类型和数据路径，返回相应的EF接口实现对象。如果模型类型为"JSON"，则返回一个电子文件API实现，
     * 否则返回null。这允许系统根据不同的模型类型动态地选择和使用不同的API实现，提高了系统的灵活性和可扩展性。
     *
     * @param model 模型的类型，用于确定使用的API实现。
     * @param dataPath 电子文件的数据路径，用于初始化API实现。
     * @return 如果模型类型为"JSON"，则返回一个电子文件API实现对象；否则返回null。
     * @throws RuntimeException 如果模型类型为null，则抛出运行时异常。
     */
    AcsEFInterface getEFApi(String model, String dataPath) {
        // 检查模型类型是否为空，如果为空则抛出运行时异常
        if (model == null) throw new RuntimeException("Run model lose.");
        // 根据模型类型决定返回的API实现对象
        return model.equals("JSON")?new ElectronicJsonAPI(dataPath):null;
    }


    public CompareResult toCompare(String code, Point point) {
        return null;
    }

    public <B> B toCompare(String code, Point point, Function<? super CompareResult, ? extends B> mapper) {
        return null;
    }

    public MatchResult toMatch(Point point){
        return null;
    }

    public <B> B toMatch(Point point, Function<? super MatchResult, ? extends B> mapper) {
        return null;
    }

    /**
     * 检查给定点是否落在任何一个多边形内。
     * 如果点落在某个多边形内，且该多边形有子多边形，则继续检查子多边形。
     * 如果点落在一个多边形内，且该多边形没有子多边形，则返回该多边形节点。
     * 如果没有找到点落在任何多边形内，则返回null。
     *
     * @param nodes 多边形节点的映射，键为节点标识，值为多边形节点对象。
     * @param point 待检查的点。
     * @return 如果点落在任何一个多边形内，则返回对应的多边形节点；否则返回null。
     */
    protected PolygonNode fenceHit(Map<String, PolygonNode> nodes, Point point) {
        // 如果节点映射为空，则直接返回null。
        if (nodes.isEmpty()) return null;

        // 遍历多边形节点映射中的每个条目。
        for (Map.Entry<String, PolygonNode> entry : nodes.entrySet()) {
            PolygonNode node = entry.getValue();
            List<List<Point>> polygons = node.getPolygonPoints();

            // 遍历多边形节点中的每个多边形。
            for (List<Point> poly : polygons) {
                // 检查当前点是否落在当前多边形内。
                if (isInPolygon(point, poly)) {
                    // 如果当前多边形有子多边形，则递归检查子多边形。
                    if (node.hasChildren()) {
                        PolygonNode polygonNode = fenceHit(node.getChildren(), point);
                        // 如果点落在子多边形内，则返回子多边形节点。
                        if (polygonNode != null) {
                            return polygonNode;
                        }
                    }
                    // 如果当前多边形没有子多边形，或者点没有落在子多边形内，则返回当前多边形节点。
                    return entry.getValue();
                }
            }
        }
        // 如果遍历完所有多边形后都没有找到点落在任何多边形内，则返回null。
        return null;
    }



    /**
     * 判断给定的点是否被代码对应的多边形节点所包含。
     *
     * @param nodes 一个映射代码到多边形节点的映射表。
     * @param code 多边形节点的唯一标识代码。
     * @param point 需要判断是否在多边形内的点。
     * @return 如果点在多边形内返回true，否则返回false。
     */
    protected Boolean fenceHitByCode(Map<String, PolygonNode> nodes, String code, Point point) {
        // 根据代码获取对应的多边形节点
        PolygonNode polygonNode = nodes.get(code);

        // 遍历多边形节点中的所有多边形点列表
        for (List<Point> pointList : polygonNode.getPolygonPoints()) {
            // 判断当前多边形点列表组成的多边形是否包含给定的点
            boolean polygon = isInPolygon(point, pointList);
            if (polygon) {
                // 如果点在多边形内，返回true
                return true;
            }
        }
        // 如果遍历完所有多边形点列表，点都不在任何多边形内，返回false
        return false;
    }

    /**
     * 判断一个点是否在多边形内。
     * 使用射线穿过法来判断，从点向任意方向引一条射线，统计射线与多边形边界的交点数。
     * 如果交点数为奇数，则点在多边形内部；如果交点数为偶数，则点在多边形外部。
     *
     * @param point 待判断的点。
     * @param pts 多边形的顶点列表。
     * @return 如果点在多边形内部返回true，否则返回false。
     */
    private static boolean isInPolygon(Point point, List<Point> pts) {
        /* 多边形的顶点数量 */
        int N = pts.size();
        /* 初始时假设点在多边形的边界上或是一个顶点 */
        boolean boundOrVertex = true;
        /* 交点计数器 */
        int intersectCount = 0;
        /* 浮点数比较的精度阈值 */
        double precision = 2e-10;
        /* 当前考察的两个多边形顶点 */
        Point p1, p2;
        /* 当前点 */
        Point p = point;

        /* 从第一个顶点开始 */
        p1 = pts.get(0);
        for (int i = 1; i <= N; ++i) {
            /* 如果当前点与p1重合，则直接返回边界或顶点的判断结果 */
            if (p.equals(p1)) {
                return boundOrVertex;
            }

            /* 获取下一个顶点 */
            p2 = pts.get(i % N);
            /* 如果当前点在p1和p2的垂直投影之外，则跳过当前边，考虑下一条边 */
            if (p.getLng() < Math.min(p1.getLng(), p2.getLng()) || p.getLng() > Math.max(p1.getLng(), p2.getLng())) {
                p1 = p2;
                continue;
            }

            /* 射线与边的相交判断 */
            /* 射线穿过算法 */
            if (p.getLng() > Math.min(p1.getLng(), p2.getLng()) && p.getLng() < Math.max(p1.getLng(), p2.getLng())) {
                if (p.getLat() <= Math.max(p1.getLat(), p2.getLat())) {
                    /* 如果p1和p2的经度相同，且当前点在它们的纬度范围内，则当前点在边界上 */
                    if (p1.getLng() == p2.getLng() && p.getLat() >= Math.min(p1.getLat(), p2.getLat())) {
                        return boundOrVertex;
                    }

                    /* 如果p1和p2的纬度相同，且当前点在它们的经度范围内，则交点数加一 */
                    if (p1.getLat() == p2.getLat()) {
                        if (p1.getLat() == p.getLat()) {
                            return boundOrVertex;
                        } else {
                            ++intersectCount;
                        }
                    } else {
                        /* 计算交点的纬度 */
                        double xinters = (p.getLng() - p1.getLng()) * (p2.getLat() - p1.getLat()) / (p2.getLng() - p1.getLng()) + p1.getLat();
                        /* 如果当前点的纬度与交点的纬度相差小于精度，则当前点在边界上 */
                        if (Math.abs(p.getLat() - xinters) < precision) {
                            return boundOrVertex;
                        }
                        /* 如果当前点的纬度小于交点的纬度，则交点数加一 */
                        if (p.getLat() < xinters) {
                            ++intersectCount;
                        }
                    }
                }
            } else {
                /* 如果当前点在p1和p2的垂直投影之内，则交点数加一 */
                if (p.getLng() == p2.getLng() && p.getLat() <= p2.getLat()) {
                    Point p3 = pts.get((i + 1) % N);
                    if (p.getLng() >= Math.min(p1.getLng(), p3.getLng()) && p.getLng() <= Math.max(p1.getLng(), p3.getLng())) {
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            /* 更新当前边 */
            p1 = p2;
        }
        /* 如果交点数为奇数，则点在多边形内部；否则在外部 */
        return intersectCount % 2 != 0;
    }


}
