-- 1. Añadir la columna de rol a la tabla de usuarios
ALTER TABLE usuarios ADD COLUMN role VARCHAR(50);

-- 2. Asignar un rol por defecto ('USER') a todos los usuarios existentes que no tengan uno
UPDATE usuarios SET role = 'USER' WHERE role IS NULL;

-- 3. Hacer que la columna de rol no pueda ser nula
ALTER TABLE usuarios ALTER COLUMN role SET NOT NULL;

-- 4. Crear un usuario administrador por defecto si no existe
-- La contraseña por defecto es "123456" (hasheada con BCrypt)
INSERT INTO usuarios (login, password, role)
VALUES ('admin@forohub.com', '$2a$10$3z0.C.sCoM9N/p22s.wLh.2/PAH4c3M5G50r.B.sR9.1v.jY.gZ.a', 'ADMIN')
ON CONFLICT (login) DO NOTHING;
