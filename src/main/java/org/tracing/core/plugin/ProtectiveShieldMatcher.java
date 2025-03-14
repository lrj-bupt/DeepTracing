package org.tracing.core.plugin;

import net.bytebuddy.matcher.ElementMatcher;

public class ProtectiveShieldMatcher<T> extends ElementMatcher.Junction.AbstractBase<T> {
    private final ElementMatcher<? super T> matcher;

    public ProtectiveShieldMatcher(ElementMatcher<? super T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(T target) {
        try {
            return this.matcher.matches(target);
        } catch (Throwable t) {

            return false;
        }
    }

    @Override
    public String toString() {
        return "ProtectiveShieldMatcher(" + matcher + ')';
    }
}
