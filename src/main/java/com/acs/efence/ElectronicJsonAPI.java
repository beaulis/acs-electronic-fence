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

import com.acs.efence.model.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 电子围栏JSON API，继承自AcsEFInterface，实现Serializable接口。
 * 用于处理电子围栏的区域数据，提供判断点是否在围栏内以及匹配区域代码的功能。
 */
public class ElectronicJsonAPI extends AcsEFInterface implements Serializable {

    /**
     * 默认构造函数，使用指定的JSON路径初始化电子围栏数据。
     */
    public ElectronicJsonAPI() {
        super.POLYGON_LISTS = this.initMemRegionData(ElectronicJsonLoader.load("input\\region_polygon.json"));
        super.POLYGON_NODES = this.initMemData(ElectronicJsonLoader.load("input\\region_polygon.json"));
    }

    /**
     * 带有自定义JSON路径的构造函数。
     *
     * @param jsonPath 包含电子围栏数据的JSON文件路径。
     */
    public ElectronicJsonAPI(String jsonPath) {
        super.POLYGON_LISTS = this.initMemRegionData(ElectronicJsonLoader.load(jsonPath));
        super.POLYGON_NODES = this.initMemData(ElectronicJsonLoader.load(jsonPath));
    }

    /**
     * 初始化区域数据内存映射。
     *
     * @param data 区域数据列表。
     * @return 包含区域数据的映射表。
     */
    private Map<String, PolygonNode> initMemRegionData(List<Polygon> data) {
        // 根据区域代码分组聚合区域数据
        Map<String, List<Polygon>> sd = data.stream().collect(Collectors.groupingBy(Polygon::getCode, Collectors.toList()));

        ConcurrentHashMap<String, PolygonNode> concatMap = new ConcurrentHashMap<>();
        sd.forEach((k, v) -> {
            Polygon regionPolygon = v.get(0);
            PolygonNode children = new PolygonNode()
                    .setCode(regionPolygon.getCode())
                    .setP_code(regionPolygon.getP_code())
                    .setC_code(regionPolygon.getC_code())
                    .setA_code(regionPolygon.getA_code())
                    .setLevel(regionPolygon.getLevel())
                    .setPolygons(v.stream().map(Polygon::getPolygon).collect(Collectors.toList()));

            // 处理多边形点数据
            List<List<Point>> points = children.getPolygons().stream().map(s -> {
                String[] lnglat = s.split(";");
                return Arrays.stream(lnglat).map(t -> {
                    String[] key = t.split(",");
                    return new Point(Double.parseDouble(key[0]), Double.parseDouble(key[1]));
                }).collect(Collectors.toList());
            }).collect(Collectors.toList());
            children.setPolygonPoints(points);

            concatMap.put(regionPolygon.getCode(), children);
        });
        return concatMap;
    }

    /**
     * 初始化父级区域数据内存映射。
     *
     * @param data 区域数据列表。
     * @return 包含父级区域数据的映射表。
     */
    private Map<String, PolygonNode> initMemData(List<Polygon> data) {
        Map<String, List<Polygon>> s = data.stream().collect(Collectors.groupingBy(Polygon::getParent_code, Collectors.toList()));
        return initMap(s, "CHN");
    }

    /**
     * 递归初始化区域数据内存映射。
     *
     * @param pMap 区域数据的分组映射。
     * @param parent 父级区域代码。
     * @return 包含指定父级区域数据的映射表。
     */
    private ConcurrentHashMap<String, PolygonNode> initMap(Map<String, List<Polygon>> pMap, String parent) {
        List<Polygon> polygons = pMap.get(parent);
        if (polygons == null) return null;
        Map<String, List<Polygon>> listMap = polygons.stream().collect(Collectors.groupingBy(Polygon::getCode, Collectors.toList()));

        ConcurrentHashMap<String, PolygonNode> concatMap = new ConcurrentHashMap<>();
        listMap.forEach((k, v) -> {
            Polygon regionPolygon = v.get(0);
            PolygonNode children = new PolygonNode()
                    .setCode(regionPolygon.getCode())
                    .setP_code(regionPolygon.getP_code())
                    .setC_code(regionPolygon.getC_code())
                    .setA_code(regionPolygon.getA_code())
                    .setLevel(regionPolygon.getLevel())
                    .setPolygons(v.stream().map(Polygon::getPolygon).collect(Collectors.toList()));

            // 处理多边形点数据
            List<List<Point>> points = children.getPolygons().stream().map(s -> {
                String[] lnglat = s.split(";");
                return Arrays.stream(lnglat).map(t -> {
                    String[] key = t.split(",");
                    return new Point(Double.parseDouble(key[0]), Double.parseDouble(key[1]));
                }).collect(Collectors.toList());
            }).collect(Collectors.toList());
            children.setPolygonPoints(points)
                    .setChildren(initMap(pMap, regionPolygon.getCode()));

            concatMap.put(regionPolygon.getCode(), children);
        });
        return concatMap;
    }

    /**
     * 判断点是否在指定的电子围栏内。
     *
     * @param code 区域代码。
     * @param point 待判断的点。
     * @return 包含判断结果和消息的比较结果对象。
     */
    @Override
    public CompareResult toCompare(String code, Point point) {
        if (code == null || point == null) throw new RuntimeException("访问参数丢失");
        CompareResult compareResult = new CompareResult();
        Boolean hit = super.fenceHitByCode(POLYGON_LISTS, code, point);
        compareResult.setPoint(point)
                .setCompareCode(code)
                .setVerified(hit)
                .setMessage(hit ? "Hit the electronic fence." : "Outside the electronic fence area.");
        return compareResult;
    }

    /**
     * 使用函数式接口处理判断点是否在电子围栏内的结果。
     *
     * @param code 区域代码。
     * @param point 待判断的点。
     * @param mapper 将比较结果转换为指定类型的函数。
     * @param <B> 转换后的类型。
     * @return 转换后的结果。
     */
    @Override
    public <B> B toCompare(String code, Point point, Function<? super CompareResult, ? extends B> mapper) {
        CompareResult result = this.toCompare(code, point);
        return mapper.apply(result);
    }

    /**
     * 匹配点所在的区域。
     *
     * @param point 待匹配的点。
     * @return 包含匹配结果的匹配结果对象。
     */
    @Override
    public MatchResult toMatch(Point point) {
        if (point == null) throw new RuntimeException("访问参数丢失");
        PolygonNode polygonNode = super.fenceHit(POLYGON_NODES, point);
        if (polygonNode == null) return null;
        return new MatchResult()
                .setProvinceCode(polygonNode.getP_code())
                .setCityCode(polygonNode.getC_code())
                .setAreaCode(polygonNode.getA_code());
    }

    /**
     * 使用函数式接口处理匹配点所在区域的结果。
     *
     * @param point 待匹配的点。
     * @param mapper 将匹配结果转换为指定类型的函数。
     * @param <B> 转换后的类型。
     * @return 转换后的结果。
     */
    @Override
    public <B> B toMatch(Point point, Function<? super MatchResult, ? extends B> mapper) {
        MatchResult match = this.toMatch(point);
        return mapper.apply(match);
    }
}
