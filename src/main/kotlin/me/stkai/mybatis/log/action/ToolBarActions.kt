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

package me.stkai.mybatis.log.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import com.intellij.openapi.project.DumbAware

/**
 *
 * @author St.kai
 * @version 1.0
 * @since 2021-09-12 16:48
 */
class ToolBarActions {
    companion object {
        var isShowType = false
        var isReformat = false
    }

    /**
     * 显示字面量类型按钮
     */
    class ShowType :
        ToggleAction("LiteralType", "ShowLiteralType", AllIcons.Actions.ShowCode),
        DumbAware {
        override fun isSelected(e: AnActionEvent): Boolean {
            return isShowType
        }

        override fun setSelected(e: AnActionEvent, state: Boolean) {
            isShowType = state
        }
    }

    /**
     * 是否格式化按钮
     */
    class Reformat : ToggleAction("Reformat", "ReFormat SQL", AllIcons.Actions.PrettyPrint), DumbAware {
        override fun isSelected(e: AnActionEvent): Boolean {
            return isReformat
        }

        override fun setSelected(e: AnActionEvent, state: Boolean) {
            isReformat = state
        }
    }
}
