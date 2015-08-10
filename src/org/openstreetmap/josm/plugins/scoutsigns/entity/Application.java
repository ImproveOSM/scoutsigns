package org.openstreetmap.josm.plugins.scoutsigns.entity;


/**
 * Defines the application business entity.
 *
 * @author Beata
 * @version $Revision$
 */
public class Application {

    private final String name;
    private final String version;


    /**
     * Builds a new object with the given arguments.
     *
     * @param name the application's name
     * @param version the application's version
     */
    public Application(final String name, final String version) {
        this.name = name;
        this.version = version;
    }


    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof Application) {
            final Application other = (Application) obj;
            result = ObjectUtil.bothNullOrEqual(name, other.getName());
            result = result && ObjectUtil.bothNullOrEqual(version, other.getVersion());
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ObjectUtil.hashCode(name);
        result = prime * result + ObjectUtil.hashCode(version);
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (name != null) {
            sb.append(name);
        }
        if (version != null) {
            sb.append(", ").append(version);
        }
        return sb.toString();
    }
}