-- ========================
-- 1. Datos iniciales: GradoSeccion
-- ========================

INSERT INTO grado_seccion (id, grado, seccion)
VALUES (1, '1er Grado', 'A');

INSERT INTO grado_seccion (id, grado, seccion)
VALUES (2, '2do Grado', 'B');

INSERT INTO grado_seccion (id, grado, seccion)
VALUES (3, '3er Grado', 'C');

-- ========================
-- 2. Datos iniciales: Users
--    (Administrador, Profesores, Estudiantes)
-- ========================

-- a) Un Admin
INSERT INTO users (id, nombre, email, password, rol, grado_seccion_id)
VALUES (100, 'Administrador General', 'admin@roosevelt.com', 'admin123', 'ADMIN', NULL);

-- b) Profesores
--    Nota: grado_seccion_id NULL, porque solo se usa si rol=ESTUDIANTE
INSERT INTO users (id, nombre, email, password, rol, grado_seccion_id)
VALUES (200, 'Juan Cubeñas', 'pjuan@roosevelt.com', 'prof123', 'PROFESOR', NULL);

INSERT INTO users (id, nombre, email, password, rol, grado_seccion_id)
VALUES (201, 'María Sinfunetes', 'pmaria@roosevelt.com', 'prof123', 'PROFESOR', NULL);

-- c) Estudiantes
--    Asignados a un grado_seccion
INSERT INTO users (id, nombre, email, password, rol, grado_seccion_id)
VALUES (300, 'Pedro Linares', 'epedro@roosevelt.com', 'est123', 'ESTUDIANTE', 1);

INSERT INTO users (id, nombre, email, password, rol, grado_seccion_id)
VALUES (301, 'Ana Pajares', 'eana@roosevelt.com', 'est123', 'ESTUDIANTE', 1);

INSERT INTO users (id, nombre, email, password, rol, grado_seccion_id)
VALUES (302, 'Luis Espelucín', 'eluis@roosevelt.com', 'est123', 'ESTUDIANTE', 2);

-- ========================
-- 3. Datos iniciales: Cursos
--    Cada curso asociado a una grado_seccion
-- ========================

INSERT INTO cursos (id, nombre, codigo, grado_seccion_id)
VALUES (10, 'Matemática', 'MAT-01', 1);

INSERT INTO cursos (id, nombre, codigo, grado_seccion_id)
VALUES (11, 'Comunicación', 'COM-01', 1);

INSERT INTO cursos (id, nombre, codigo, grado_seccion_id)
VALUES (12, 'Física', 'FIS-02', 2);

-- ========================
-- 4. Datos iniciales: HorarioCurso
--    Asociados a cada curso
-- ========================

-- Para Matemática (id=10)
INSERT INTO horario_curso (id, dia, hora_inicio, hora_fin, curso_id)
VALUES (1000, 'Lunes', '08:00', '09:00', 10);

INSERT INTO horario_curso (id, dia, hora_inicio, hora_fin, curso_id)
VALUES (1001, 'Miércoles', '10:00', '11:00', 10);

-- Para Comunicación (id=11)
INSERT INTO horario_curso (id, dia, hora_inicio, hora_fin, curso_id)
VALUES (1100, 'Martes', '09:00', '10:00', 11);

INSERT INTO horario_curso (id, dia, hora_inicio, hora_fin, curso_id)
VALUES (1101, 'Jueves', '09:00', '10:00', 11);

-- Para Física (id=12)
INSERT INTO horario_curso (id, dia, hora_inicio, hora_fin, curso_id)
VALUES (1200, 'Martes', '08:00', '09:00', 12);

INSERT INTO horario_curso (id, dia, hora_inicio, hora_fin, curso_id)
VALUES (1201, 'Viernes', '08:00', '09:00', 12);

-- ========================
-- 5. Datos iniciales: AsignacionProfesor
-- ========================

-- Asignar "Profesor Juan" (id=200) a Matemática (id=10) y Comunicación (id=11)
INSERT INTO asignacion_profesor (id, profesor_id, curso_id)
VALUES (10000, 200, 10);

INSERT INTO asignacion_profesor (id, profesor_id, curso_id)
VALUES (10001, 200, 11);

-- Asignar "Profesor María" (id=201) a Física (id=12)
INSERT INTO asignacion_profesor (id, profesor_id, curso_id)
VALUES (10002, 201, 12);

-- ========================
-- 6. Datos iniciales: Notas
--    Relacion de un estudiante (rol=ESTUDIANTE) en un curso, con una nota
-- ========================

-- Estudiante Pedro (id=300), en curso Matemática (id=10)
INSERT INTO notas (id, curso_id, estudiante_id, nota)
VALUES (9000, 10, 300, 15.5);

-- Estudiante Pedro (id=300), en curso Comunicación (id=11)
INSERT INTO notas (id, curso_id, estudiante_id, nota)
VALUES (9001, 11, 300, 13.0);

-- Estudiante Ana (id=301), en curso Matemática (id=10)
INSERT INTO notas (id, curso_id, estudiante_id, nota)
VALUES (9002, 10, 301, 17.0);
