package com.yingwumeijia.baseywmj.im

import com.yingwumeijia.baseywmj.im.plugin.VipPlugin
import io.rong.imkit.DefaultExtensionModule
import io.rong.imkit.plugin.IPluginModule
import io.rong.imkit.widget.provider.FilePlugin
import io.rong.imlib.model.Conversation

/**
 * Created by jamisonline on 2017/7/28.
 */
class CustomerExtensionModule : DefaultExtensionModule() {

    override fun getPluginModules(conversationType: Conversation.ConversationType?): MutableList<IPluginModule> {
        val pluginModules = super.getPluginModules(conversationType)
        for (i in pluginModules.indices) {
            val pluginModule = pluginModules[i]
            if (pluginModule is FilePlugin) pluginModules.remove(pluginModule)
        }
        pluginModules.add(VipPlugin())
//        pluginModules.add(new PayApplyPlugin());
//        pluginModules.add(OrderPlugin())
        return pluginModules
    }
}