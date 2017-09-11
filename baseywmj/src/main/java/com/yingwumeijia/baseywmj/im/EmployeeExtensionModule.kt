package com.yingwumeijia.baseywmj.im

import com.yingwumeijia.baseywmj.im.plugin.OrderPlugin
import com.yingwumeijia.baseywmj.im.plugin.VipPlugin
import io.rong.imkit.DefaultExtensionModule
import io.rong.imkit.plugin.IPluginModule
import io.rong.imkit.widget.provider.FilePlugin
import io.rong.imlib.model.Conversation

/**
 * Created by jamisonline on 2017/7/28.
 */
class EmployeeExtensionModule : DefaultExtensionModule() {

    override fun getPluginModules(conversationType: Conversation.ConversationType?): MutableList<IPluginModule> {
        val pluginModules = super.getPluginModules(conversationType)
        pluginModules.indices
                .map { pluginModules[it] }
                .filterIsInstance<FilePlugin>()
                .forEach { pluginModules.remove(it) }
        pluginModules.add(OrderPlugin())
        return pluginModules    }
}