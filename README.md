# Sistema de Gestión de Pedidos - Restaurante

## Trabajo Práctico: Algoritmos II / Programación II

### Descripción
Este proyecto es un **sistema de gestión de pedidos para un restaurante** que ofrece comida para llevar y entregas a domicilio.  
El sistema permite registrar pedidos, clasificar por prioridad, gestionar la preparación en cocina, asignar repartidores y generar reportes de análisis.

El objetivo es organizar eficientemente los pedidos y demostrar conocimientos en estructuras de datos y programación.

### Integrantes
- **Nombre 1**  
- **Nombre 2**  

### Requerimientos del Sistema

#### 1. Ingreso y Clasificación de Pedidos
- Registrar cada pedido con:
  - Nombre del cliente
  - Lista de platos pedidos
  - Tipo de pedido: para llevar o envío a domicilio
  - Nivel de prioridad: VIP o Normal
- Clasificación de pedidos según nivel de prioridad
- Platos disponibles cargados en el sistema

#### 2. Preparación de Platos en Cocina
- Cada pedido tiene N platos que se procesan hasta estar listos
- Los cocineros toman los platos terminados para entregar al repartidor o cliente según el tipo de pedido

#### 3. Entrega de Pedidos
- Registro de repartidores disponibles
- Asignación de pedidos listos al siguiente repartidor disponible
- Repartidor sigue el camino más corto entre los distintos puntos de la ciudad
- Al finalizar la entrega, el repartidor vuelve a estar disponible

#### 4. Reportes y Análisis
- Número de pedidos pendientes
- Número de pedidos finalizados
- Cantidad de pedidos entregados por cada repartidor
- Cliente con mayor número de pedidos
- Platos más pedidos

### Consideraciones
- Sistema ejecutado desde la **línea de comandos**
- Menú interactivo para realizar acciones sobre el sistema
- Pruebas unitarias para validar módulos
- Prioridades pueden representarse de manera distinta a un `String` (ej. enums o valores numéricos)
- No se requiere base de datos ni archivos externos para guardar información

### Escenario Inicial
Al ejecutar la aplicación, se cargan los siguientes datos:
- 10 repartidores
- 5 pedidos
- Platos disponibles en el restaurante

### Cómo Ejecutar
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/usuario/nombre-del-repo.git
