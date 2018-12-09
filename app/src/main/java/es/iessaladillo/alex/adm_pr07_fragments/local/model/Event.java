package es.iessaladillo.alex.adm_pr07_fragments.local.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Event<T> {
 
    private T content;
    private boolean handled;
 
    public Event(@NonNull T content) {
        this.content = content;    
    }
 
    public boolean hasBeenHandled() {
        return handled;
    }

    @Nullable
    public T getContentIfNotHandled() {
        if (handled) {
            return null;
        } else {
            handled = true;
            return content;
        }
    }
 
    @NonNull
    public T peekContent() {
        return content;    
    }
 
}