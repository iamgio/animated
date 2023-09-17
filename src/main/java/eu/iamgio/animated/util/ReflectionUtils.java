package eu.iamgio.animated.util;

/**
 * Utilities for reflective operations.
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * @param name name of the class to search for
     * @param packages packages to scan, in order
     * @return the matching class found in one of the given packages
     * @throws ClassNotFoundException if no class is found
     */
    public static Class<?> findClassInPackages(String name, String... packages) throws ClassNotFoundException {
        for (String pack : packages) {
            try {
                return Class.forName(pack + "." + name);
            } catch (ClassNotFoundException ignored) {}
        }

        throw new ClassNotFoundException(name);
    }
}
