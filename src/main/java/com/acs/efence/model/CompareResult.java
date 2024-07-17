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
 * 比较结果的类，用于封装比较操作后的信息。
 * 包含比较标识、焦点位置、验证结果和额外消息。
 */
@Data
@Accessors(chain = true)
public class CompareResult implements Serializable {

    /**
     * 比较操作的唯一标识码。
     */
    String compareCode;

    /**
     * 焦点位置的信息，指明比较中出现差异的精确位置。
     */
    Point point;

    /**
     * 比较结果的验证状态，指示比较是否通过。
     */
    Boolean verified;

    /**
     * 额外的消息或说明，用于提供关于比较结果的更多上下文信息。
     */
    String message;
}

