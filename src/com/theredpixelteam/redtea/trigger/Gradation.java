package com.theredpixelteam.redtea.trigger;

import java.util.Optional;

public interface Gradation {
    public default Optional<String> getTitle()
    {
        return Optional.empty();
    }

    public String getName();

    public int getOrdinal();
}
