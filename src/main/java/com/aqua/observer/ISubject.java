package com.aqua.observer;

/**
 * Interface for the Subject in the Observer pattern.
 * Implementations of this interface will manage observers and notify them of changes.
 */
public interface ISubject {
    
    /**
     * Registers a new observer
     * @param observer The observer to register
     */
    void registerObserver(IObserver observer);
    
    /**
     * Removes an observer
     * @param observer The observer to remove
     */
    void removeObserver(IObserver observer);
    
    /**
     * Notifies all registered observers of changes
     */
    void notifyObservers();
} 