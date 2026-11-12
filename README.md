# EnvíaYa

Proyecto académico de Ingeniería de Software I sobre pruebas, mantenimiento y
evolución de un calculador de costos de envío.

## Estado

El repositorio contiene el borrador documentado de las Partes 1–5, la
traducción Java del código heredado y la primera suite automatizada de JUnit.
La línea base heredada conserva sus fallos intencionales en `v1.0.0`; las
correcciones DEF-01 a DEF-06 ya están integradas en `main`. La refactorización
se trabaja por separado en la rama `refactor/calculator`.

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

- [Avance del informe: Partes 1–5](docs/avance-parcial.md)

## Próximos pasos

1. Aplicar el patrón Estrategia a las reglas de zona.
2. Implementar y probar la evolución de zona insular.

El repositorio local está vinculado con GitHub. La etiqueta `v1.0.0` identifica
la traducción Java del código heredado, todavía sin corregir.

## Ejecutar las pruebas

Con JDK 8 o posterior y Maven instalados:

```text
mvn test
```

En `v1.0.0` algunas pruebas fallan a propósito para revelar defectos; el
resultado de esa línea base no es el resultado final del proyecto.
