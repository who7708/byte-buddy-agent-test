package com.example.agent.plugin.loader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The <code>InterceptorInstanceLoader</code> is a classes finder and container.
 * <p>
 * This is a very important class in sky-walking's auto-instrumentation mechanism. If you want to fully understand why
 * need this, and how it works, you need have knowledge about Classloader appointment mechanism.
 * <p>
 */
public class InterceptorInstanceLoader {

    private static ConcurrentHashMap<String, Object> INSTANCE_CACHE = new ConcurrentHashMap<String, Object>();
    private static ReentrantLock INSTANCE_LOAD_LOCK = new ReentrantLock();
    private static Map<ClassLoader, ClassLoader> EXTEND_PLUGIN_CLASSLOADERS = new HashMap<ClassLoader, ClassLoader>();

    /**
     * Load an instance of interceptor, and keep it singleton. Create {@link AgentClassLoader} for each
     * targetClassLoader, as an extend classloader. It can load interceptor classes from plugins, activations folders.
     *
     * @param className         the interceptor class, which is expected to be found
     * @param targetClassLoader the class loader for current application context
     * @param <T>               expected type
     * @return the type reference.
     */
    public static <T> T load(String className,
                             ClassLoader targetClassLoader) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (targetClassLoader == null) {
            targetClassLoader = InterceptorInstanceLoader.class.getClassLoader();
        }
        String instanceKey = className + "_OF_" + targetClassLoader.getClass()
                .getName() + "@" + Integer.toHexString(targetClassLoader
                .hashCode());
        Object inst = INSTANCE_CACHE.get(instanceKey);
        if (inst == null) {
            // INSTANCE_LOAD_LOCK.lock();
            // ClassLoader pluginLoader;
            // try {
            //     pluginLoader = EXTEND_PLUGIN_CLASSLOADERS.get(targetClassLoader);
            //     if (pluginLoader == null) {
            //         pluginLoader = new AgentClassLoader(targetClassLoader);
            //         EXTEND_PLUGIN_CLASSLOADERS.put(targetClassLoader, pluginLoader);
            //     }
            // } finally {
            //     INSTANCE_LOAD_LOCK.unlock();
            // }
            inst = Class.forName(className, true, targetClassLoader).newInstance();
            INSTANCE_CACHE.put(instanceKey, inst);
        }
        return (T) inst;
    }
}
