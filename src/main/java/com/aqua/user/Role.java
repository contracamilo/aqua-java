package com.aqua.user;

/**
 * Representa un rol de usuario en el sistema.
 * Esta clase define los diferentes roles que pueden tener los usuarios.
 */
public class Role {
    
    private final String roleName;

    /**
     * Constructor para Role
     * @param roleName El nombre del rol
     */
    public Role(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol no puede ser nulo o vacío");
        }
        this.roleName = roleName;
    }

    /**
     * Obtiene el nombre del rol
     * @return El nombre del rol
     */
    public String getRoleName() {
        return roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleName.equals(role.roleName);
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }
} 