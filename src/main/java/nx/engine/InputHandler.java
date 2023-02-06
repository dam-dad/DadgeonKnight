package nx.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class InputHandler {
    final private Set<KeyCode> activeKeys = new HashSet<>();
    final private Set<MouseButton> activeButtons = new HashSet<>();
    final private Queue<Double> scrollValues = new LinkedList<Double>();
    
    long lastScrollTime = 0;
    final long SCROLL_TIME_THRESHOLD = 200; // 200 milliseconds

    // Hacer esto bien
    public static double posX;
    public static double posY;
    
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

            posX = event.getX();
            posY = event.getY();
        }
    };
    
    public EventHandler<ScrollEvent> scrollInputHandler = new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent event) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastScrollTime) > SCROLL_TIME_THRESHOLD) {
                scrollValues.clear();
            }
            lastScrollTime = currentTime;
            scrollValues.add(event.getDeltaY());
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