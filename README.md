# EnvíaYa

Proyecto académico de Ingeniería de Software I sobre pruebas, mantenimiento y
evolución de un calculador de costos de envío.

## Estado

El informe documenta las Partes 1–8. La línea base heredada conserva sus
fallos intencionales en `v1.0.0`. Las correcciones, refactorización y solicitud
insular ya están integradas en `main` mediante las PR #1, #2 y #3. La versión
actual es `2.1.0`, con 53 pruebas aprobadas.

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

- [Avance del informe: Partes 1–8](docs/avance-parcial.md)
- [Historial de cambios](CHANGELOG.md)
- [Notas de versión 2.0.0](docs/notas-version-2.0.0.md)
- [Notas de versión 2.1.0](docs/notas-version-2.1.0.md)

Las etiquetas `v1.0.0`, `v2.0.0` y `v2.1.0` identifican, respectivamente, la
línea base heredada, la refactorización incompatible de la API y la evolución
compatible con zona insular.

## Ejecutar las pruebas

Con JDK 8 o posterior y Maven instalados:

```text
mvn test
```

En `v1.0.0` algunas pruebas fallan a propósito para revelar defectos; el
resultado de esa línea base no es el resultado final del proyecto. En `main`,
`mvn clean verify` ejecuta la suite actual y genera el informe JaCoCo.
