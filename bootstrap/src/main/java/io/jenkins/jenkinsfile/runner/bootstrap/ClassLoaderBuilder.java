package io.jenkins.jenkinsfile.runner.bootstrap;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kohsuke Kawaguchi
 */
public class ClassLoaderBuilder {

    private static final Logger LOGGER = Logger.getLogger(ClassLoaderBuilder.class.getName());

    private final ClassLoader parent;
    private final List<URL> jars = new ArrayList<>();

    @CheckForNull
    private final String name;

    public ClassLoaderBuilder(ClassLoader parent) {
        this(parent, null);
    }

    public ClassLoaderBuilder(ClassLoader parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return name != null ? name : Integer.toString(hashCode());
    }

    /**
     * Recursively scan a directory to find jars
     */
    public ClassLoaderBuilder collectJars(File dir, FileFilter filter) throws IOException {
        File[] children = dir.listFiles();
        if (children!=null) {
            for (File child : children) {
                if (child.isDirectory()) {
                    collectJars(child, filter);
                } else {
                    if (child.getName().endsWith(".jar") && filter.accept(child)) {
                        jars.add(child.toURI().toURL());
                        LOGGER.log(Level.FINE, "Added JAR {0} to classloader \"{1}\"", new Object[] {child, getName()});
                    }
                }
            }
        }
        return this;
    }

    public ClassLoaderBuilder collectJars(File dir) throws IOException {
        return collectJars(dir,(File)->true);
    }

    public ClassLoader make() {
        return new URLClassLoader(jars.toArray(new URL[jars.size()]),parent);
    }
}
