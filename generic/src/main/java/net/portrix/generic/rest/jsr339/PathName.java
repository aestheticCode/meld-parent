package net.portrix.generic.rest.jsr339;

import com.google.common.base.Objects;

/**
 * @author by Patrick Bittner on 09.06.15.
 */
public class PathName {

    private final String path;

    private final String name;

    public PathName(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("path", path)
                .add("name", name)
                .toString();
    }
}
