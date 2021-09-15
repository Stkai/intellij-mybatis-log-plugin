/*
 * Copyright (c) 2021. St.kai.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.stkai.mybatis.log.type

/**
 * MyBatis Java 数据类型
 * @author St.kai
 * @version 1.0
 * @since 2021-09-08 00:11
 */
enum class JavaType(showType: Boolean, type: kotlin.String) {
    String(false, ""),
    Integer(false, ""),
    Date(true, LiteralType.DATE),
    Time(true, LiteralType.TIME),
    Timestamp(true, LiteralType.TIMESTAMP),
    LocalDate(true, LiteralType.DATE),
    LocalTime(true, LiteralType.TIME),
    LocalDateTime(true, LiteralType.TIMESTAMP);

    /**
     * 是否显示字面量
     */
    private val showType: Boolean

    /**
     * 字面量名称
     */
    private val type: kotlin.String

    fun isShowType(): Boolean {
        return showType
    }

    fun getType(): kotlin.String {
        return type
    }

    init {
        this.showType = showType
        this.type = type
    }
}

internal object LiteralType {
    internal const val DATE = "date"
    internal const val TIME = "time"
    internal const val TIMESTAMP = "timestamp"
}
