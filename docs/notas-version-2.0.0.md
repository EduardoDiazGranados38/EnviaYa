# Notas de versión 2.0.0

**Estado:** integrada en `main`; etiqueta `v2.0.0`.

## Resumen

La versión estable reúne las correcciones DEF-01 a DEF-06 y la
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

## Etiqueta y empaquetado

La etiqueta `v2.0.0` identifica la integración de la refactorización mediante
la PR #2. En ese commit Maven todavía indicaba `2.0.0-SNAPSHOT`; no se publicó
un artefacto Maven independiente para esa versión. La primera versión Maven
estable del proyecto es `2.1.0`.

La zona insular de San Andrés se documenta como una solicitud de cambio
separada, con versión menor `2.1.0`, en [sus notas de versión](./notas-version-2.1.0.md).
