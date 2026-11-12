# Notas de versión 2.1.0 (borrador)

**Estado:** borrador; pendiente de integrar la refactorización `2.0.0` y la
solicitud de cambio en `main`.

## Resumen

Agrega la zona insular de San Andrés para el código personalizado
`CAMBIO=0`.

## Cambios destacados

- La zona `insular` agrega un recargo del 70 % sobre la tarifa base.
- Los envíos insulares ignoran los cupones; se mantienen los descuentos de
  cliente y el orden de recargos acordado.
- La política insular se implementa como una estrategia independiente.
- Se añaden casos de aceptación CR-01–CR-04, pruebas unitarias de estrategia y
  análisis de impacto en la trazabilidad.
- La validación en la rama de trabajo ejecuta 53 pruebas: 53 aprobadas, 0
  fallidas. JaCoCo registra 100 % de instrucciones, líneas y ramas.

## Compatibilidad

Esta función no añade parámetros a la firma pública `calc`; es compatible con
la interfaz `2.0.0`. Se propone el incremento MINOR `2.1.0`.

La etiqueta `v2.1.0` se creará después de integrar y validar la cadena de
solicitudes de cambio; esta nota sigue siendo un borrador hasta entonces.
