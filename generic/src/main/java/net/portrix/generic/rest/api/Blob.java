package net.portrix.generic.rest.api;

import java.time.LocalDateTime;

/**
 * @author Patrick Bittner on 01.08.17.
 */
public class Blob {

    private String name;

    private LocalDateTime lastModified;

    private byte[] data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
