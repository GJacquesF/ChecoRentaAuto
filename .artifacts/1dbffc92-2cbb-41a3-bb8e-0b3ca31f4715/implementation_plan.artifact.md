# Plan de Implementación: Mejoras en Gestión de Vehículos y Rentas

Este plan detalla las modificaciones necesarias para mejorar la búsqueda de vehículos, agregar filtrado por etiquetas y enriquecer el proceso de entrega con datos de estado del vehículo.

## Cambios Propuestos

### Modelos de Datos

#### [MODIFY] [Vehiculo.kt](file:///home/jax/AndroidStudioProjects/ChecoRentasAutos/app/src/main/java/com/example/checorentasautos/main/data/Vehiculo.kt)
- Agregar propiedad `etiqueta: String?` para permitir la clasificación de vehículos.

#### [MODIFY] [Renta.kt](file:///home/jax/AndroidStudioProjects/ChecoRentasAutos/app/src/main/java/com/example/checorentasautos/main/data/Renta.kt)
- Agregar propiedades para la entrega: `gasolina: String?`, `kilometraje: Int?`, `observaciones: String?`, `checkRapido: Boolean`.

---

### Registro de Vehículos

#### [MODIFY] [activity_reg_vehiculos.xml](file:///home/jax/AndroidStudioProjects/ChecoRentasAutos/app/src/main/res/layout/activity_reg_vehiculos.xml)
- Agregar un campo de entrada para la "Etiqueta" (ej. Económico, Lujo, SUV).

#### [MODIFY] [RegVehiculosActivity.kt](file:///home/jax/AndroidStudioProjects/ChecoRentasAutos/app/src/main/java/com/example/checorentasautos/main/ui/activity/RegVehiculosActivity.kt)
- Capturar y guardar la etiqueta al registrar un nuevo vehículo.

---

### Consulta y Búsqueda de Vehículos

#### [MODIFY] [activity_consulta_vehiculos.xml](file:///home/jax/AndroidStudioProjects/ChecoRentasAutos/app/src/main/res/layout/activity_consulta_vehiculos.xml)
- Agregar un `TextInputLayout` con un `EditText` para búsqueda por placa.
- Agregar un `ChipGroup` o selector para filtrar por etiquetas.

#### [MODIFY] [item_vehiculo.xml](file:///home/jax/AndroidStudioProjects/ChecoRentasAutos/app/src/main/res/layout/item_vehiculo.xml)
- Agregar un indicador visual para la etiqueta del vehículo.

#### [MODIFY] [VehiculoAdapter.kt](file:///home/jax/AndroidStudioProjects/ChecoRentasAutos/app/src/main/java/com/example/checorentasautos/main/ui/adapter/VehiculoAdapter.kt)
- Implementar `Filterable` para permitir búsquedas dinámicas.
- Agregar un listener para clics en los elementos de la lista.

#### [MODIFY] [ConsultaVehiculosActivity.kt](file:///home/jax/AndroidStudioProjects/ChecoRentasAutos/app/src/main/java/com/example/checorentasautos/main/ui/activity/ConsultaVehiculosActivity.kt)
- Implementar la lógica de filtrado por placa y etiqueta.
- Configurar el click en un vehículo para abrir `RentaVehiculoActivity` pasando la placa.

---

### Proceso de Renta (Entrega)

#### [MODIFY] [activity_renta_vehiculo.xml](file:///home/jax/AndroidStudioProjects/ChecoRentasAutos/app/src/main/res/layout/activity_renta_vehiculo.xml)
- Agregar campos para:
    - Nivel de Gasolina (Selector/Dropdown).
    - Kilometraje (Campo numérico).
    - Observaciones (Campo de texto multilínea).
    - Check Rápido (Switch o Checkbox).

#### [MODIFY] [RentaVehiculoActivity.kt](file:///home/jax/AndroidStudioProjects/ChecoRentasAutos/app/src/main/java/com/example/checorentasautos/main/ui/activity/RentaVehiculoActivity.kt)
- Recibir la placa del vehículo desde el Intent y pre-seleccionarlo.
- Capturar los nuevos campos de entrega y guardarlos en el objeto `Renta`.

## Plan de Verificación

### Pruebas Manuales
1. **Registro:** Verificar que se pueda asignar una etiqueta a un vehículo nuevo.
2. **Consulta:**
    - Probar la búsqueda por placa escribiendo en tiempo real.
    - Probar el filtrado seleccionando diferentes etiquetas.
    - Hacer clic en un vehículo y verificar que redirija a la pantalla de renta con el vehículo ya seleccionado.
3. **Renta:**
    - Completar una renta llenando los datos de gasolina, kilometraje y observaciones.
    - Verificar que la renta se guarde correctamente con estos datos.
