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
package me.stkai.mybatis.log.console

import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.ui.ConsoleView
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import me.stkai.mybatis.log.action.ToolBarActions
import me.stkai.mybatis.log.util.PrintUtils
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 * @author St.kai
 * @version 1.0
 * @since 2021-09-09 19:33
 */
class MybatisLogToolWindow : ToolWindowFactory {
    companion object {
        fun createToolbar(project: Project, consoleView: ConsoleView): ActionToolbar {
            val group = DefaultActionGroup()
            group.add(ToolBarActions.Reformat())
            group.add(ToolBarActions.ShowType())
            group.addSeparator()
            group.add(consoleView.createConsoleActions()[2])
            group.add(consoleView.createConsoleActions()[3])
            group.add(consoleView.createConsoleActions()[5])
            return ActionManager.getInstance().createActionToolbar("EventLog", group, false)
        }
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        // MyBatis Log 面板
        val consolePanel = JPanel()

        val textConsoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project)
        val consoleView = textConsoleBuilder.console
        // 设置日志输出 view
        PrintUtils.consoleView = consoleView

        consolePanel.layout = BorderLayout()
        consolePanel.add(consoleView.component, BorderLayout.CENTER)

        // 工具条
        val toolbar = createToolbar(project, consoleView)
        toolbar.setTargetComponent(consolePanel)

        val simpleToolWindowPanel = SimpleToolWindowPanel(false, true)
        simpleToolWindowPanel.setContent(consolePanel)
        simpleToolWindowPanel.toolbar = toolbar.component

        val jComponent = simpleToolWindowPanel.component
        toolbar.setTargetComponent(consoleView.component)

        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(jComponent, "", false)
        toolWindow.contentManager.addContent(content)
    }
}
