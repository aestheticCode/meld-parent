package net.portrix.generic.ddd;

import java.util.UUID;

public interface FormController<F> {

    F create();

    F read(UUID id);

    F save(F form);

    F update(UUID id, F form);

    void delete(UUID id);
}
