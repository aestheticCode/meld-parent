package net.portrix.generic.rest.api;

import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Patrick Bittner on 27.07.17.
 */
public class UploadMeta {

    private final String fileName;

    private final UUID uuid;

    private final int chunk;

    private final int size;

    public UploadMeta(String fileName, UUID uuid, int chunk, int size) {
        this.fileName = fileName;
        this.uuid = uuid;
        this.chunk = chunk;
        this.size = size;
    }

    public static UploadMeta read(String content) {
        final StringTokenizer tokenizer = new StringTokenizer(content, ";");
        final Pattern pattern = Pattern.compile("(.*)=(.*)");

        UUID uuid = null;
        String fileName = null;
        int size = 0;
        int chunk = 0;
        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            Matcher matcher = pattern.matcher(token);
            if (matcher.matches()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                switch (key) {
                    case "filename": {
                        fileName = value;
                    }
                    break;

                    case "uuid": {
                        uuid = UUID.fromString(value);
                    }
                    break;

                    case "size": {
                        size = Integer.parseInt(value);
                    }
                    break;

                    case "chunk": {
                        chunk = Integer.parseInt(value);
                    }
                    break;

                }
            }
        }

        return new UploadMeta(fileName, uuid, chunk, size);
    }

    public String getFileName() {
        return fileName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getChunk() {
        return chunk;
    }

    public int getSize() {
        return size;
    }
}
