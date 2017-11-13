package net.portrix.generic.ddd;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.DocumentId;

import javax.persistence.*;
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

    public UUID getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }
}
