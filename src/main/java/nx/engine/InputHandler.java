package nx.engine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InputHandler {
    final private Set<KeyCode> activeKeys = new HashSet<>();
    final private Set<MouseButton> activeButtons = new HashSet<>();

    
    public EventHandler<KeyEvent> keyInputHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
                activeKeys.add(event.getCode());
            } else if (KeyEvent.KEY_RELEASED.equals(event.getEventType())) {
                activeKeys.remove(event.getCode());
            }
        }
    };
    
    public EventHandler<MouseEvent> mouseInputHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (MouseEvent.MOUSE_PRESSED.equals(event.getEventType())) {
                activeButtons.add(event.getButton());
            } else if (MouseEvent.MOUSE_RELEASED.equals(event.getEventType())) {
                activeButtons.remove(event.getButton());
            }
        }
    };

    public Set<KeyCode> getActiveKeys() {
        return Collections.unmodifiableSet(activeKeys);
    }
    public Set<MouseButton> getActiveButtons() {
        return Collections.unmodifiableSet(activeButtons);
    }
    public void ClearActiveKeys() {
    	activeKeys.clear();
    }
    public void ClearActiveButtons() {
    	activeButtons.clear();
    }
}