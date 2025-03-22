package com.aqua.user;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user in the system.
 * This class contains user information and their associated role.
 */
@Getter
@Setter
public class User {
    
    private final int id;
    private String name;
    private Role role;

    /**
     * Constructor for User
     * @param id The user's unique identifier
     * @param name The user's name
     * @param role The user's role
     */
    public User(int id, String name, Role role) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (role == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }
        
        this.id = id;
        this.name = name;
        this.role = role;
    }

    /**
     * Gets the user's ID
     * @return The user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the user's name
     * @return The user name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name
     * @param name The new name
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Gets the user's role
     * @return The user role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the user's role
     * @param role The new role
     */
    public void setRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
} 