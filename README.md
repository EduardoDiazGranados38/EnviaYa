# EnvíaYa

Proyecto académico de Ingeniería de Software I sobre pruebas, mantenimiento y
evolución de un calculador de costos de envío.

## Estado

El repositorio contiene el borrador documentado de las Partes 1–4 del parcial.
La implementación Java, las pruebas automatizadas y la medición de cobertura
todavía están pendientes.

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

- [Avance del informe: Partes 1–4](docs/avance-parcial.md)

## Próximos pasos

1. Crear la traducción Java del código heredado, preservando su lógica.
2. Etiquetar esa línea base como `v1.0.0`, según lo solicita el parcial.
3. Automatizar los casos con JUnit y medir cobertura con JaCoCo.
4. Registrar defectos antes de corregirlos; después refactorizar y probar la
   evolución de zona insular.

El repositorio local aún no está vinculado ni publicado en GitHub. No se debe
crear una etiqueta de versión de producto hasta que exista el código heredado
Java que dicha etiqueta debe identificar.
