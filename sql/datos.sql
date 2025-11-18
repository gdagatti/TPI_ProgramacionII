USE empresa;

-- INSERCIONES VALIDAS--
-- Legajo 1 --
INSERT INTO empresa.legajo (eliminado, nro_legajo, categoria, estado, fecha_alta, observaciones)
VALUES (0, 'LEG-0001', 'JR', 'ACTIVO', '2025-10-01', 'Alta inicial');
-- Empleado 1 --
INSERT INTO empresa.empleado (eliminado, nombre, apellido, dni, email, fecha_ingreso, area, legajo_id)
VALUES (0, 'Ayelen', 'Masseroni', 'DNI-39600348', 'masseroni@hotmail.com', '2025-10-01', 'DESARROLLO',
        (SELECT id FROM empresa.legajo WHERE nro_legajo='LEG-0001'));

-- Legajo 2--
INSERT INTO empresa.legajo (eliminado, nro_legajo, categoria, estado, fecha_alta, observaciones)
VALUES (0, 'LEG-0002', 'SSR', 'ACTIVO', '2024-04-01', 'Alta inicial 2');
USE empresa;
-- Empleado 2 --
INSERT INTO empresa.empleado (eliminado, nombre, apellido, dni, email, fecha_ingreso, area, legajo_id)
VALUES (0, 'German', 'Dagatti', 'DNI-80808666', 'dagatti@gmail.com', '2024-05-10', 'INFRAESTRUCTURA',
        (SELECT id FROM empresa.legajo WHERE nro_legajo='LEG-0002'));


-- Carga Masiva--
USE empresa;

-- Insertar 10.000 legajos --
INSERT INTO legajo (eliminado, nro_legajo, categoria, estado, fecha_alta, observaciones)
SELECT
  0,
  CONCAT('LEG-SIM-', LPAD(n, 6, '0'))                           AS nro_legajo,
  ELT((n MOD 3)+1, 'JR','SSR','SR')                             AS categoria,
  ELT((n MOD 2)+1, 'ACTIVO','INACTIVO')                         AS estado,
  DATE_ADD('2019-01-01', INTERVAL (n MOD 2200) DAY)             AS fecha_alta,
  'Generado por carga masiva simple'                             AS observaciones
FROM (
  SELECT d1.i + d2.i*10 + d3.i*100 + d4.i*1000 + 1 AS n
  FROM (SELECT 0 i UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
        UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d1
  CROSS JOIN (SELECT 0 i UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
        UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d2
  CROSS JOIN (SELECT 0 i UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
        UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d3
  CROSS JOIN (SELECT 0 i UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
        UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d4
) gen
LEFT JOIN legajo L ON L.nro_legajo = CONCAT('LEG-SIM-', LPAD(gen.n, 6, '0'))
WHERE L.id IS NULL;

-- Insertar 10.000 empleados (uno por cada legajo) --
SET @rn := 0;
INSERT INTO empleado (eliminado, nombre, apellido, dni, email, fecha_ingreso, area, legajo_id)
SELECT
  0,
  CONCAT('Nombre', (@rn := @rn + 1))                                 AS nombre,
  CONCAT('Apellido', @rn)                                            AS apellido,
  CONCAT('DNI-', LPAD(4000000 + @rn, 8, '0'))                   AS dni,      -- único
  CONCAT('empleado', @rn, '@empresa.com')                            AS email,    -- válido
  DATE_ADD('2019-02-01', INTERVAL (@rn MOD 2200) DAY)                AS fecha_ingreso,
  ELT((@rn MOD 5)+1, 'DESARROLLO','INFRAESTRUCTURA','RRHH','FINANZAS','COMERCIAL') AS area,
  L.id                                                                AS legajo_id
FROM legajo L
LEFT JOIN empleado E ON E.legajo_id = L.id
WHERE L.nro_legajo LIKE 'LEG-SIM-%'
  AND E.id IS NULL; -- no duplica--