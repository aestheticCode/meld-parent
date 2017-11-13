package net.portrix.meld.channel.meld.image;

import net.portrix.generic.rest.api.UploadMeta;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Patrick Bittner on 28.07.17.
 */
@SessionScoped
public class MeldImageSession implements Serializable {

    private String fileName;

    private byte[] fileContent;

    private final List<Optional<byte[]>> chunks = new ArrayList<>();

    public List<Optional<byte[]>> getChunks() {
        return chunks;
    }

    public void initialize(int capacity) {
        chunks.clear();
        for (int i = 0; i < capacity; i++) {
            chunks.add(Optional.empty());
        }
    }

    public void fillArray(UploadMeta meta) {
        fileContent = new byte[meta.getSize()];

        for (Optional<byte[]> optionalChunk : chunks) {
            if (optionalChunk.isPresent()) {
                System.arraycopy(optionalChunk.get(), 0, fileContent, chunks.indexOf(optionalChunk) * 1024, optionalChunk.get().length);
            }
        }
    }

    public Optional<byte[]> set(int index, Optional<byte[]> element) {
        return chunks.set(index, element);
    }

    public boolean isInitialized() {
        return chunks.isEmpty();
    }

    public void clear() {
        chunks.clear();
        fileContent = new byte[0];
        fileName = "blank.jpg";
    }

    public boolean isCompleted() {
        return chunks
                .stream()
                .allMatch(Optional::isPresent);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

}
