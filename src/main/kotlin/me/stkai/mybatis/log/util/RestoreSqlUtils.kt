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

package me.stkai.mybatis.log.util

import me.stkai.mybatis.log.action.ToolBarActions
import me.stkai.mybatis.log.type.JavaType
import org.apache.commons.lang3.EnumUtils

/**
 * 还原SQL工具类
 * @author St.kai
 * @version 1.0
 * @since 2021-09-07 23:00
 */
object RestoreSqlUtils {

    /**
     * sql行标识
     */
    const val PREPARING_STR = "Preparing:"

    /**
     * 参数行标识
     */
    const val PARAMETERS_STR = "Parameters:"

    private val PARAMETERS_REGEX = "(((\\([A-z]+\\))|null),\\s)|\\([A-z]+\\)".toRegex()
    private val TYPES_REGEX = "(\\([A-z]+\\))|null".toRegex()

    /**
     * 还原sql
     * @param preparing mybatis 生成的sql行
     * @param parameters mybatis 生成的参数行
     * @return 还原后的sql
     */
    fun restoreSQL(preparing: String, parameters: String): String {
        val preparingSql = preparing.substring(preparing.indexOf(PREPARING_STR) + PREPARING_STR.length).trim()
        val params = parameters.substring(parameters.indexOf(PARAMETERS_STR) + PARAMETERS_STR.length).trim()

        /**
         * 参数数组
         */
        val paramsArray = params.split(PARAMETERS_REGEX).dropLast(1).map { if (it == "") "null" else it }

        /**
         * 类型数组
         */
        val typeArray = mutableListOf<String>()
        TYPES_REGEX.findAll(params).forEach {
            if (it.groupValues[1] == "") typeArray.add("null")
            else typeArray.add(it.groupValues[1].removePrefix("(").removeSuffix(")"))
        }

        // 将sql中的 '?' 替换对应为参数 并添加字面量类型
        var resultSql = ""
        // 当前 '?' 所在索引
        var questionMarkIndex = -1
        // ' 数量
        var apostropheCount = 0
        preparingSql.forEach { c: Char ->
            if (c == '\'') {
                apostropheCount += 1
                return@forEach
            }
            if (apostropheCount % 2 != 0) {
                return@forEach
            }
            if (c == '?') {
                questionMarkIndex += 1
                if (EnumUtils.isValidEnumIgnoreCase(JavaType::class.java, typeArray[questionMarkIndex])) {
                    val type = EnumUtils.getEnum(JavaType::class.java, typeArray[questionMarkIndex])
                    if (ToolBarActions.isShowType && type.isShowType()) {
                        // 显示字面量类型 type 'params'
                        resultSql += "${type.getType()} '${paramsArray[questionMarkIndex]}'"
                        return@forEach
                    }
                    resultSql += "'${paramsArray[questionMarkIndex]}'"
                    return@forEach
                }
                resultSql += paramsArray[questionMarkIndex]
                return@forEach
            } else {
                resultSql += c
            }
        }
        return resultSql.trim()
    }
}
