package net.portrix.generic.model.type.resolved.generic;

import com.google.common.base.Objects;
import com.google.common.reflect.TypeToken;
import net.portrix.generic.model.type.resolved.ResolvedExecutable;
import net.portrix.generic.model.type.resolved.ResolvedParameter;

import java.lang.annotation.ElementType;

/**
 * @author Patrick Bittner on 04.05.2014.
 */
public class GenericResolvedParameter<X> extends AbstractGenericResolvedAnnotatedElement implements ResolvedParameter<X> {

    private final ResolvedExecutable<X> enclosingExecutable;

    private final ResolvedParameter<X> resolvedParameter;

    private TypeToken<?> type;

    public GenericResolvedParameter(final ResolvedExecutable<X> enclosingExecutable,
                                    final ResolvedParameter<X> resolvedParameter) {
        super(resolvedParameter);
        this.enclosingExecutable = enclosingExecutable;
        this.resolvedParameter = resolvedParameter;
    }

    @Override
    public ElementType getElementType() {
        return resolvedParameter.getElementType();
    }

    @Override
    public int getPosition() {
        return resolvedParameter.getPosition();
    }

    @Override
    public ResolvedExecutable<X> getEnclosingExecutable() {
        return enclosingExecutable;
    }

    @Override
    public TypeToken<?> getType() {
        if (type == null) {
            type = getEnclosingExecutable().getEnclosingType().getType().resolveType(resolvedParameter.getType().getType());
        }
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getType(), getEnclosingExecutable(), getPosition());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ResolvedParameter) {
            final ResolvedParameter<?> other = (ResolvedParameter<?>) obj;

            return Objects.equal(getType(), other.getType())
                    && Objects.equal(getEnclosingExecutable(), other.getEnclosingExecutable())
                    && Objects.equal(getPosition(), other.getPosition());
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", getType())
                .toString();
    }

}
