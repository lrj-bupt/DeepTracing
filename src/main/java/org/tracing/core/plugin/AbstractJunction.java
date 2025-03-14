package org.tracing.core.plugin;

import net.bytebuddy.matcher.ElementMatcher;

public abstract class AbstractJunction<V> implements ElementMatcher.Junction<V> {
    @Override
    public <U extends V> Junction<U> and(ElementMatcher<? super U> elementMatcher) {
        return new Conjunction<U>(this, elementMatcher);
    }

    @Override
    public <U extends V> Junction<U> or(ElementMatcher<? super U> elementMatcher) {
        return new Disjunction<U>(this, elementMatcher);
    }


}
