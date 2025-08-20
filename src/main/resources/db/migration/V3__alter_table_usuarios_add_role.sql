-- Crear un usuario administrador por defecto si no existe
-- La contraseña por defecto es "123456" (hasheada con BCrypt)
INSERT INTO usuarios (login, password, role)
VALUES ('admin@forohub.com', '$2a$10$3z0.C.sCoM9N/p22s.wLh.2/PAH4c3M5G50r.B.sR9.1v.jY.gZ.a', 'ADMIN')
ON CONFLICT (login) DO NOTHING;
