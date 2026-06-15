-- Preload Usuarios (Password: password)
INSERT INTO usuario (username, password, role) VALUES ('admin', '$2a$10$P74Y3nh4NJ5PqdEoFl1AauxBCvvoCtciCnKh0mLMiLyyFFmsi8CZC', 'ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO usuario (username, password, role) VALUES ('user', '$2a$10$P74Y3nh4NJ5PqdEoFl1AauxBCvvoCtciCnKh0mLMiLyyFFmsi8CZC', 'ROLE_USER') ON CONFLICT DO NOTHING;

-- Preload Buses
INSERT INTO bus (cod_bus, mod_bus, placa_bus, cap_bus) VALUES ('BUS01', 'Mercedes-Benz', 'ABC-1234', 40) ON CONFLICT DO NOTHING;
INSERT INTO bus (cod_bus, mod_bus, placa_bus, cap_bus) VALUES ('BUS02', 'Scania', 'XYZ-5678', 45) ON CONFLICT DO NOTHING;

-- Preload Clientes
INSERT INTO cliente (cod_cli, nom_cli, ape_cli, edad_cli, sexo_cli) VALUES ('CLI01', 'Juan', 'Perez', '30', 'M') ON CONFLICT DO NOTHING;
INSERT INTO cliente (cod_cli, nom_cli, ape_cli, edad_cli, sexo_cli) VALUES ('CLI02', 'Maria', 'Gomez', '25', 'F') ON CONFLICT DO NOTHING;

-- Preload Destinos
INSERT INTO destino (cod_dest, ciu_dest, cost_dest) VALUES ('D001', 'Lima', 50.00) ON CONFLICT DO NOTHING;
INSERT INTO destino (cod_dest, ciu_dest, cost_dest) VALUES ('D002', 'Cusco', 120.00) ON CONFLICT DO NOTHING;

-- Preload Programaciones
INSERT INTO programacion (id_prog, cod_bus, fecha, hora) VALUES (1, 'BUS01', '2026-06-20', '08:00:00') ON CONFLICT DO NOTHING;
INSERT INTO programacion (id_prog, cod_bus, fecha, hora) VALUES (2, 'BUS02', '2026-06-21', '14:30:00') ON CONFLICT DO NOTHING;

-- Reset sequence for programacion identity column in Postgres
SELECT setval(pg_get_serial_sequence('programacion', 'id_prog'), coalesce(max(id_prog), 1)) FROM programacion;
