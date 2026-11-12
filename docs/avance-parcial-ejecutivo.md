# Síntesis ejecutiva

El trabajo cubrió requisitos, diseño de pruebas, automatización, corrección de
defectos, refactorización, gestión de configuración y la solicitud CR-00 para
la zona insular. Se corrigieron seis causas raíz del código heredado, se separó
el cálculo mediante el patrón Estrategia y se publicó EnvíaYa 2.1.0.

| Línea base | Corrección | Entrega 2.1.0 |
|---|---|---|
| 21 de 44 pruebas aprobadas | 48 de 48 aprobadas antes de CR-00 | 53 de 53 aprobadas |

JaCoCo reportó 100 % en instrucciones, líneas y ramas para la entrega final.
La cobertura completa no sustituye la verificación de requisitos; ambos
resultados se presentan por separado.

Este resumen prioriza resultados. La matriz completa CP-01–CP-42, los caminos
WB, el registro detallado de defectos y los cálculos ampliados están en el
[informe detallado](./avance-parcial.md).

## Parte 1 — Requisitos y criterios de aceptación

Los parámetros del código estudiantil `310040` son: PMAX=45 kg, L1=3 kg,
L2=10 kg, recargo rural=10 %, nacional=40 %, descuento VIP=10 %, recargo de
fin de semana=$2.000 y `CAMBIO=0`. La regla general de R8 establece mínimo de
$5.000 y redondeo a centenas con empates hacia arriba. La solicitud CR-00
añade la zona insular de San Andrés.

| ID | Regla de aceptación |
|---|---|
| R1 | Aceptar `0 < peso <= 45 kg`; rechazar el resto con `peso inválido`. |
| R2 | Aceptar zonas admitidas y días enteros del 1 al 7. |
| R3 | Hasta 3 kg: $6.000; hasta 10 kg: $9.000; después: $9.000 + $800 por kg proporcional. |
| R4 | Urbana: 0 %; rural: 10 %; nacional: 40 %. CR-00 agrega insular: 70 %. |
| R5 | Sumar $2.000 los días 6 y 7, antes de descuentos. |
| R6 | Descuento frecuente: 5 %; VIP: 10 %; cupón `ENVIO10`: 10 %. Si se combinan, cliente antes del cupón. |
| R7 | Ignorar cupones desconocidos; en zona insular, ignorar cualquier cupón. |
| R8 | Aplicar el mínimo de $5.000 y redondear al múltiplo de $100 más cercano, HALF_UP. |

Las historias cubren validación y tarifa base (HU-01), recargos (HU-02),
descuentos y costo final (HU-03), y el envío insular (HU-04). Los criterios
representativos verifican límites, errores, orden de recargos/descuentos,
mínimo y redondeo; la matriz íntegra conserva cada expectativa por ID.

## Parte 2 — Plan de pruebas

El alcance comprobó por separado la traducción Java del legado, la versión
corregida/refactorizada y la evolución CR-00. Se usaron pruebas unitarias, de
integración, de sistema y de aceptación con Java, JUnit Jupiter 5.10.2,
JaCoCo 0.8.12, Maven 3.9.9 y Temurin JDK 8u512.

La estrategia combinó particiones de equivalencia, límites, tabla de decisión,
caminos independientes y regresión. Cada ejecución se contrastó con un
requisito antes de declarar un defecto. Se registraron resultados aprobados,
fallidos, errores y omitidos; la cobertura se informa como medición, no como
prueba autónoma de conformidad. Se excluyeron UI, persistencia y servicios
externos porque no forman parte del enunciado.

## Parte 3 — Diseño de caja negra

Se cubrieron los límites de peso 0, L1=3, L2=10 y PMAX=45; zonas urbana, rural
y nacional; días hábiles y fin de semana; clientes normal, frecuente y VIP;
y cupones ausente, válido e inválido. La tabla de decisión abarca las 18
combinaciones de cliente, cupón y fin de semana. Se diseñaron 42 casos CP,
incluidas entradas inválidas y valores inmediatamente a ambos lados de los
límites.

| Caso de referencia | Cálculo esperado |
|---|---|
| 45 kg, urbana, normal | $9.000 + (45−10)×$800 = $37.000 |
| 4 kg, nacional, normal | $9.000 + 40 % = $12.600 |
| 4 kg, frecuente y cupón válido | $9.000×0,95×0,90 = $7.695 → $7.700 |
| 3 kg, VIP y cupón válido | $4.860 antes del mínimo → $5.000 |
| 11,4375 kg | $10.150 → $10.200 (empate HALF_UP) |
| 4 kg, rural, sábado, frecuente | ($9.000+$900+$2.000)×0,95 = $11.305 → $11.300 |

Los cálculos usan valores decimales exactos (`BigDecimal` desde texto) y se
comparan numéricamente, evitando errores de escala o truncamiento.

## Parte 4 — Diseño de caja blanca

El grafo del método heredado contiene 29 nodos, 41 aristas, un componente
conectado y 13 nodos predicado. Por la fórmula `M=E−N+2P`, la complejidad
ciclomática es 14; coincide con predicados más uno y con el número de regiones.
Se diseñaron 14 caminos independientes, más pruebas de condiciones atómicas y
casos adicionales para contrastar requisitos.

El flujo analizado es: validar entrada → tarifa base → recargo de zona →
recargo de fin de semana → descuento del cliente → cupón → mínimo y redondeo
→ salida. La suite blanca evidenció, entre otros, el límite heredado de 50 kg,
la falta de validación de zona/día, el sábado omitido y el truncamiento. Los
detalles de cada nodo, rama y camino están en el informe completo.

## Parte 5 — Automatización, ejecución y defectos

La traducción de línea base se probó con 44 casos: 21 aprobados, 23 fallidos,
0 errores y 0 omitidos. Los fallos se agruparon en seis causas raíz:

1. Límite máximo fijo de 50 kg en vez de PMAX=45.
2. Mensaje de peso inválido sin la tilde exigida.
3. Zona desconocida aceptada.
4. Rango de días sin validar.
5. Recargo de sábado omitido.
6. Truncamiento en vez de redondeo HALF_UP.

JaCoCo midió la línea base con 127/127 instrucciones, 27/27 líneas y 28/28
ramas cubiertas (100 %). Esto no implica que el resultado sea correcto: varias
pruebas cubren ramas cuyo comportamiento incumple requisitos. Las correcciones
se integraron de forma secuencial y la regresión posterior pasó 44/44 casos.

## Parte 6 — Mantenimiento y refactorización

Los principales olores eran método largo, números mágicos, parámetro `flag`,
obsesión por primitivos, lista larga de parámetros, condicionales por tipo de
zona y asignación redundante. La refactorización extrajo funciones y
constantes, retiró la impresión controlada por `flag` y aplicó Estrategia para
las políticas de zona. La fábrica rechaza zonas desconocidas.

| Métrica | Legado | Refactorización |
|---|---:|---:|
| Complejidad de `calc` | 14 | 1 |
| SLOC de producción | 52 | 119 |
| Pruebas aprobadas | 21/44 | 48/48 |
| Cobertura de instrucciones/líneas/ramas | 100 % en clase heredada | 100 % en producción: 190/190, 47/47 y 26/26 |

El aumento de SLOC refleja responsabilidades y estrategias explícitas; la
complejidad total de métodos nuevos se distribuye entre cálculo, validación,
fábrica y políticas.

## Parte 7 — Gestión de configuración

El trabajo quedó integrado en `main` mediante ramas y PR: correcciones DEF-01
a DEF-06 (PR #1), refactorización (PR #2), zona insular (PR #3) y publicación
2.1.0 (PR #4). La PR #5 actualizó la documentación de entrega y el historial de
tiempos. Las etiquetas conservan la línea base `v1.0.0`, la ruptura de firma
`v2.0.0` y la evolución compatible `v2.1.0`. Se documentaron los cambios en
CHANGELOG y notas de versión; las fechas de commit se conservaron.

## Parte 8 — Solicitud de cambio y análisis de impacto

Como `CAMBIO=0`, CR-00 incorpora San Andrés con recargo insular del 70 % e
ignora cupones. Se mantuvo el orden de negocio y no se alteraron las demás
políticas:

| Aceptación | Resultado |
|---|---:|
| 4 kg, insular, sin cupón | $9.000×1,70 = $15.300 |
| Mismos datos con `ENVIO10` | $15.300; cupón ignorado |
| Frecuente con cupón | $15.300×0,95 = $14.535 → $14.500 |
| Sábado con cupón | $15.300+$2.000 = $17.300; cupón ignorado |

Las pruebas CR-01–CR-03 fallaron antes de implementar, como se esperaba; se
añadió después la cuarta aceptación de sábado. CR-00 es mantenimiento
adaptativo y elevó la versión MINOR de 2.0.0 a 2.1.0. La verificación final
pasó 53/53 pruebas; JaCoCo reportó 221/221 instrucciones, 56/56 líneas y
30/30 ramas (100 %). La complejidad agregada fue 37. Evidencias:
[regresión final](./evidence/regresion-final-53.png),
[aceptación CR-00](./evidence/pruebas-zona-insular.png),
[cobertura JaCoCo](./evidence/cobertura-zona-insular.png),
[línea base](./evidence/pruebas-legado.png) e
[informe detallado](./avance-parcial.md).
