package com.KimJesus.forohub.model;

public enum Role {
    ADMIN,  // Rol con todos los permisos
    USER,   // Rol para usuarios registrados (pueden publicar y responder)
    READER  // Rol para lectores (solo pueden ver contenido)
}
