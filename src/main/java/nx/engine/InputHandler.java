package nx.engine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler implements EventHandler<KeyEvent> {
    final private Set<KeyCode> activeKeys = new HashSet<>();

    @Override
    public void handle(KeyEvent event) {
        if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
            activeKeys.add(event.getCode());
        } else if (KeyEvent.KEY_RELEASED.equals(event.getEventType())) {
            activeKeys.remove(event.getCode());
        }
    }

    public Set<KeyCode> getActiveKeys() {
        return Collections.unmodifiableSet(activeKeys);
    }
    
    public void ClearActiveKeys() {
    	activeKeys.clear();
    }
}