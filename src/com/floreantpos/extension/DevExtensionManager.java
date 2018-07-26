package com.floreantpos.extension;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.floreantpos.blockchyp.BlockChypPlugin;
import com.floreantpos.main.Application;
import com.floreantpos.util.JarUtil;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

public class DevExtensionManager {
    private List<FloreantPlugin> plugins;

    private static DevExtensionManager instance;
    
    
    
    public DevExtensionManager() {
        super();
    }

    public void initialize() {
        try {
            
            List<FloreantPlugin> floreantPlugins = new ArrayList<FloreantPlugin>();
            
            floreantPlugins.add(new MercuryGatewayPlugin());
            floreantPlugins.add(new BlockChypPlugin());
            floreantPlugins.add(new InginicoPlugin());
            floreantPlugins.add(new AuthorizeNetGatewayPlugin());
            
            this.plugins = Collections.unmodifiableList(floreantPlugins);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static List<FloreantPlugin> getPlugins() {
        return getInstance().plugins;
    }
    
    public static List<FloreantPlugin> getPlugins(Class pluginClass) {
        List<FloreantPlugin> list = new ArrayList<FloreantPlugin>();
        
        for (FloreantPlugin floreantPlugin : getInstance().plugins) {
            if(pluginClass.isAssignableFrom(floreantPlugin.getClass())) {
                list.add(floreantPlugin);
            }
        }
        
        return list;
    }
    
    public static FloreantPlugin getPlugin(Class pluginClass) {
        for (FloreantPlugin floreantPlugin : getInstance().plugins) {
            if(pluginClass.isAssignableFrom(floreantPlugin.getClass())) {
                return floreantPlugin;
            }
        }
        
        return null;
    }
    
    public static DevExtensionManager getInstance() {
        if(instance == null) {
            instance = new DevExtensionManager();
        }
        
        return instance;
    }

}
