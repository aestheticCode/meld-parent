package net.portrix.generic.ddd;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.DocumentId;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Patrick Bittner on 31/01/15.
 */
@MappedSuperclass
public abstract class AbstractAggregate implements Aggregate {

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    //MySQL @Column(columnDefinition = "BINARY(16)")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @Id
    private UUID id;

    @Version
    private int version;

    private Instant created;

    private Instant modified;

    @PreUpdate
    private void postUpdate() {
        modified = Instant.now();
    }

    @PrePersist
    private void postCreate() {
        modified = Instant.now();
        created = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getModified() {
        return modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractAggregate)) return false;

        AbstractAggregate that = (AbstractAggregate) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
