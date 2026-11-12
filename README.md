# EnvíaYa

Proyecto académico de Ingeniería de Software I sobre pruebas, mantenimiento y
evolución de un calculador de costos de envío.

## Estado

El informe documenta las Partes 1–6. La línea base heredada conserva sus
fallos intencionales en `v1.0.0`; las correcciones DEF-01 a DEF-06 están
integradas en `main`. La refactorización con Estrategia está en la rama
`refactor/estrategia`, con 48 pruebas aprobadas. La Parte 7 está en progreso;
la Parte 8 está pendiente.

## Parámetros personalizados

Código estudiantil: `182310040`

Últimos seis dígitos: `310040`

| Parámetro | Valor |
|---|---:|
| PMAX | 45 kg |
| L1 | 3 kg |
| L2 | 10 kg |
| RR | 10 % |
| RN | 40 % |
| V | 10 % |
| S | $2.000 |
| CAMBIO | 0 |

## Documentación

- [Avance del informe: Partes 1–6](docs/avance-parcial.md)
- [Historial de cambios](CHANGELOG.md)
- [Borrador de notas de versión 2.0.0](docs/notas-version-2.0.0.md)

## Próximos pasos

1. Integrar y versionar la refactorización como `2.0.0` por el cambio de
   interfaz al retirar `flag`.
2. Escribir primero las pruebas de la zona insular e implementarla como cambio
   menor; documentar impacto, nueva versión y evidencias.

El repositorio local está vinculado con GitHub. La etiqueta `v1.0.0` identifica
la traducción Java del código heredado, todavía sin corregir.

## Ejecutar las pruebas

Con JDK 8 o posterior y Maven instalados:

```text
mvn test
```

En `v1.0.0` algunas pruebas fallan a propósito para revelar defectos; el
resultado de esa línea base no es el resultado final del proyecto. En la rama
`refactor/estrategia`, `mvn clean verify` ejecuta la suite actual y genera el
informe JaCoCo.
