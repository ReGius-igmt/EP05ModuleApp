package ru.regiuss.EP05.core.module;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleManager {
    private final ConcurrentHashMap<String, Module> modules;
    private static ModuleManager instance;

    public ModuleManager(){
        this.modules = new ConcurrentHashMap<>();
        instance = this;
    }

    public static ModuleManager getInstance() {
        return instance;
    }

    public void loadModules(EventHandler<WorkerStateEvent> onLoad){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                File modulesFolder = new File("./modules");
                modulesFolder.mkdir();
                File[] files = modulesFolder.listFiles();
                if(files != null){
                    for(File jar : files){
                        if(!(jar.isFile() && jar.getName().endsWith(".jar")))continue;
                        System.out.println(jar.getName());
                        Module m = loadModule(jar);
                        if(m == null)continue;
                        modules.put(m.getInfo().getName(), m);
                        m.onLoad();
                    }
                }
                return null;
            }
        };
        task.setOnSucceeded(onLoad);
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    public Module loadModule(File f){
        try {
            URLClassLoader child = new URLClassLoader(
                    new URL[] {f.toURI().toURL()},
                    getClass().getClassLoader()
            );
            InputStream info = child.getResourceAsStream("module.properties");
            if(info == null)throw new RuntimeException("module.properties error");
            Properties properties = new Properties();
            properties.load(info);
            ModuleInfo moduleInfo = new ModuleInfo(properties);
            Class<Module> module = (Class<Module>) Class.forName(moduleInfo.getMain(), true, child);
            Module m = module.getDeclaredConstructor().newInstance();
            m.setWorkDirectory(f.getParentFile().toPath().resolve(moduleInfo.getName()).toFile());
            m.setInfo(moduleInfo);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Module> getModules() {
        return modules.values();
    }
}
