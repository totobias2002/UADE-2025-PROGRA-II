# 🍽️ Sistema de Gestión de Pedidos y Entregas
Trabajo Práctico – Programación II – UADE  
Sistema desarrollado utilizando exclusivamente **estructuras de datos propias (TDAs)**.

---

## 📌 Descripción del proyecto
Este sistema simula el funcionamiento de un restaurante que gestiona pedidos, cocina, entregas y reportes.  
Fue desarrollado cumpliendo los requerimientos de la materia, utilizando **solo TDAs implementados por el alumno**, sin colecciones de Java.

---

## 🧩 Tecnologías y TDAs utilizados

### ✔ DynamicPriorityQueueADT (cola con prioridad)
- Maneja el orden de atención de pedidos.
- VIP → prioridad 0  
- Normal → prioridad 1  

### ✔ DynamicStackADT (pila)
- Utilizada para representar rutas de entrega dentro del grafo.
- Permite mostrar la ruta en orden inverso (destino → origen).

### ✔ StaticGraphADT (grafo estático)
- Representa el mapa de zonas del delivery.
- Cada arista tiene un peso en kilómetros.
- Se usa para calcular el costo del envío.

### ✔ Arrays fijos
- Almacenamiento de pedidos, clientes, estadísticas, repartidores y platos.

---

## 🧱 Arquitectura del sistema

### **Clases principales:**
- `Menu` → interfaz principal para el usuario.
- `ControladorRestaurante` → lógica del sistema.
- `Pedido` → contiene platos, estado, costos y datos del cliente.
- `Cliente` → nombre, teléfono, condición VIP.
- `Plato` → ID, nombre y precio.
- `Repartidor` → entrega pedidos, registra disponibilidad.
- `Validador` → validación de entradas del usuario.

### **TDAs usados directamente:**
- `DynamicPriorityQueueADT`
- `DynamicStackADT`
- `StaticGraphADT`

---

## 🔄 Flujo del sistema

1. **Registrar pedido**  
   - Cliente (nombre, teléfono, VIP)  
   - Tipo de entrega: local o delivery  
   - Zona de entrega  
   - Elección de platos (mínimo uno)  
   - Subtotal, costo de envío y total  

2. **Procesar pedido**  
   - Pasa a “En cocina”.

3. **Preparar pedido**  
   - Todos los platos pasan a “listos”.  
   - Estado → “Listo para entregar”.

4. **Entregar pedido**  
   - Repartidor registra entrega.  
   - Estado → “Entregado”.

5. **Reportes**  
   - Estado general de pedidos  
   - Repartidores y entregas  
   - Cliente con más pedidos  
   - Platos más vendidos  
   - Distancias del grafo  
   - Ruta usando pila  

---

## 📊 Reportes incluidos

### ✔ Estado de pedidos  
Ordenados por prioridad, mostrando:  
- prioridad  
- ID  
- cliente  
- total  
- repartidor  
- estado  

### ✔ Repartidores  
- Vehículo  
- Disponibilidad  
- Cantidad de entregas realizadas  

### ✔ Cliente con más pedidos  
- Ranking de clientes según frecuencia

### ✔ Platos más vendidos  
- Contador por cada plato

### ✔ Distancias del grafo  
- Kilómetros desde UADE hacia cada zona

### ✔ Ruta con pila  
- Representación “destino → origen” usando DynamicStackADT

---

## 🛡️ Validaciones implementadas

- Nombre → solo letras  
- Teléfono → solo números  
- Opciones → dentro de un rango  
- Pedido → al menos un plato  
- Menú → manejo de errores  
- Datos consistentes en cada etapa  

---

## 💰 Cálculo de costos

- **Subtotal:** suma de platos  
- **Costo delivery:** distancia (km) × 300  
- **Total final:** subtotal + delivery  

Distancias obtenidas desde el grafo.

---

## 🚀 Cómo ejecutar el sistema

1. Abrir el proyecto en IntelliJ IDEA o cualquier IDE Java.  
2. Compilar todo el paquete `org.uade.algorithm`.  
3. Ejecutar la clase `Menu`.  
4. Operar mediante la consola.

---

## 🧪 Pruebas (Opcional)
El sistema permite fácilmente la incorporación de pruebas unitarias para:

- Validar TDAs  
- Verificar el ciclo de vida de un pedido  
- Testear reportes  
- Testear rutas con pila  

---

## 🧾 Notas importantes
- No se usan archivos externos (sin persistencia), tal como indica la consigna.  
- No se utilizan colecciones de Java.  
- Todo se maneja mediante TDAs propios + arrays.

---

## 👨‍💻 Autor
- *Tobias Rodriguez* – Estudiante de Programación II – UADE  
