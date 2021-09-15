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

import com.intellij.execution.filters.Filter
import com.intellij.openapi.project.Project
import me.stkai.mybatis.log.action.ToolBarActions
import org.slf4j.LoggerFactory

/**
 *
 * @author St.kai
 * @version 1.0
 * @since 2021-09-07 20:12
 */
class LogFilter(private val project: Project) : Filter {
    val log: org.slf4j.Logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        /**
         * Sql行 日志
         */
        private lateinit var preparing: String

        /**
         * 参数行 日志
         */
        private lateinit var parameters: String
    }

    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        if (line.contains(RestoreSqlUtils.PREPARING_STR)) {
            preparing = line
        }

        if (line.contains(RestoreSqlUtils.PARAMETERS_STR)) {
            parameters = line

            try {
                // 还原后的SQL
                var restoredSql = RestoreSqlUtils.restoreSQL(preparing, parameters)

                if (ToolBarActions.isReformat) {
                    restoredSql = FormatUtils.reformatSql(project, restoredSql)
                }
                // 标题
                PrintUtils.system(preparing.substring(0, preparing.indexOf(RestoreSqlUtils.PREPARING_STR)).trim())
                if (restoredSql.toLowerCase().startsWith("insert")) {
                    PrintUtils.error(restoredSql)
                }
                if (restoredSql.toLowerCase().startsWith("delete")) {
                    PrintUtils.error(restoredSql)
                }
                if (restoredSql.startsWith("update")) {
                    PrintUtils.warn(restoredSql)
                }
                if (restoredSql.toLowerCase().startsWith("select")) {
                    PrintUtils.normal(restoredSql)
                }
                PrintUtils.normal("")
            } catch (e: Exception) {
                log.error("还原SQL失败", e)
                PrintUtils.error(e.toString())
            }
        }
        return null
    }
}
