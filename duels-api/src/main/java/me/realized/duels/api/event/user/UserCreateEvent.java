package me.realized.duels.api.event.user;

import java.util.Objects;
import javax.annotation.Nonnull;
import me.realized.duels.api.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a new {@link User} is created.
 */
public class UserCreateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final User user;

    public UserCreateEvent(@Nonnull final User user) {
        Objects.requireNonNull(user, "user");
        this.user = user;
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
