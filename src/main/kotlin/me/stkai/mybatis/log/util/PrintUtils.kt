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

import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType

/**
 * 打印工具类
 *
 * @author St.kai
 * @version 1.0
 * @since 2021-09-09 20:40
 */
class PrintUtils(message: String, consoleViewContentType: ConsoleViewContentType) {
    init {
        consoleView.print(message + "\n", consoleViewContentType)
    }

    companion object {
        lateinit var consoleView: ConsoleView

        fun debug(message: String) {
            consoleView.print(message + "\n", ConsoleViewContentType.LOG_DEBUG_OUTPUT)
        }

        fun info(message: String) {
            consoleView.print(message + "\n", ConsoleViewContentType.LOG_INFO_OUTPUT)
        }

        fun warn(message: String) {
            consoleView.print(message + "\n", ConsoleViewContentType.LOG_WARNING_OUTPUT)
        }

        fun error(message: String) {
            consoleView.print(message + "\n", ConsoleViewContentType.ERROR_OUTPUT)
        }

        fun normal(message: String) {
            consoleView.print(message + "\n", ConsoleViewContentType.NORMAL_OUTPUT)
        }

        fun system(message: String) {
            consoleView.print(message + "\n", ConsoleViewContentType.SYSTEM_OUTPUT)
        }
    }
}
