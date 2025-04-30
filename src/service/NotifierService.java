package service;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static service.ReverseEvent.CLEAR_TEXT_FIELD;

public class NotifierService {
    private final Map<ReverseEvent, List<EventListener>> listeners = new HashMap<>() {
        {
            put(CLEAR_TEXT_FIELD, new ArrayList<>());
        }
    };

    public void subscribe(final ReverseEvent eventType, EventListener listener) {
        List<EventListener> selectedListeners = listeners.get(eventType);
        selectedListeners.add(listener);
    }

    public void notify(final ReverseEvent eventType) {
        listeners.get(eventType).forEach(listener -> listener.update(eventType));
    }
}
