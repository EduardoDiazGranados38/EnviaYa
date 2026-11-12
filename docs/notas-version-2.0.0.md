# Notas de versión 2.0.0 (borrador)

**Estado:** borrador; todavía no publicada ni etiquetada.

## Resumen

La próxima versión estable reúne las correcciones DEF-01 a DEF-06 y la
refactorización del cálculo con el patrón Estrategia para las zonas urbana,
rural y nacional.

## Cambios destacados

- Validación del peso personalizado, zona y rango de días.
- Recargo de fin de semana aplicado sábado y domingo.
- Redondeo al múltiplo de $100 más cercano, con empates hacia arriba.
- Métodos de cálculo separados por responsabilidad y constantes con nombre.
- Políticas de recargo por zona aisladas mediante Estrategia.
- Cobertura de regresión: 48 pruebas aprobadas, sin fallos.

## Cambio incompatible

Se eliminó el argumento booleano `flag` del método público `calc` junto con
su impresión por consola. Las llamadas existentes deben cambiar de
`calc(peso, zona, cliente, día, cupón, flag)` a
`calc(peso, zona, cliente, día, cupón)`. Esta ruptura de interfaz justifica
el incremento MAJOR desde la línea base `1.0.0`.

La zona insular de San Andrés no está incluida en esta versión; corresponde a
la solicitud de cambio y versión menor prevista para la Parte 8.
