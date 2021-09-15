/*
 * Copyright (c) 2017-2021. St.kai.
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

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.sql.SqlFileType
import org.apache.commons.io.FilenameUtils

/**
 * 格式化工具类
 * @author St.kai
 * @version 1.0
 * @since 2021-09-16 01:35
 */
object FormatUtils {

    /**
     * @param project
     * @param sql SQL
     * @return 格式化后的sql
     */
    fun reformatSql(project: Project, sql: String): String {
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val fileType = SqlFileType.INSTANCE
        val fileName = TEMPORARY + FilenameUtils.EXTENSION_SEPARATOR + fileType.defaultExtension
        val file = psiFileFactory.createFileFromText(fileName, fileType, sql)
        CodeStyleManager.getInstance(file.project).reformat(file)
        return file.text
    }

    private const val TEMPORARY = "temporary"
}
