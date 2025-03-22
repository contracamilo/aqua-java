# Sistema de Gestión de Agua (Aqua)

Sistema de gestión de recursos hídricos que permite monitorear y controlar el suministro de agua desde diferentes fuentes.

## Características Principales

- **Gestión de Fuentes de Agua**
  - Soporte para múltiples tipos de fuentes (ríos y pozos)
  - Monitoreo en tiempo real de niveles de agua
  - Control de calidad del agua
  - Capacidad de agregar, actualizar y eliminar fuentes

- **Sistema de Monitoreo**
  - Monitoreo continuo de niveles de agua
  - Alertas automáticas para niveles críticos
  - Seguimiento de calidad del agua
  - Notificaciones en tiempo real

- **Distribución de Agua**
  - Múltiples estrategias de distribución:
    - Distribución Equitativa
    - Distribución por Prioridad
    - Distribución Justa
  - Cálculo automático de disponibilidad
  - Gestión de asignaciones

- **Generación de Reportes**
  - Reportes históricos detallados
  - Exportación a Excel
  - Información en tiempo real
  - Estadísticas de uso

- **Interfaz de Usuario**
  - Interfaz gráfica intuitiva
  - Visualización en tiempo real
  - Panel de alertas
  - Gestión de fuentes de agua
  - Generación y exportación de reportes

## Requisitos del Sistema

- Java 17 o superior
- Maven 3.6 o superior
- Dependencias:
  - Apache POI (para exportación a Excel)
  - Lombok (para reducción de código boilerplate)

## Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/contracamilo/aqua-java.git
cd aqua
```

2. Compilar el proyecto:
```bash
mvn clean install
```

3. Ejecutar la aplicación:
```bash
mvn exec:java -Dexec.mainClass="com.aqua.Main"
```

## Instrucciones de Uso

### Inicio del Sistema
1. Al ejecutar la aplicación, se abrirá la interfaz gráfica principal
2. El sistema se iniciará automáticamente y comenzará a monitorear las fuentes de agua
3. Se cargarán datos de ejemplo (ríos y pozos) para demostración

### Gestión de Fuentes de Agua
1. **Agregar Nueva Fuente**
   - Haga clic en el botón "Agregar Fuente"
   - Complete el formulario con:
     - ID: Número único
     - Tipo: Seleccione RIVER o WELL
     - Capacidad: Volumen en metros cúbicos
     - Ubicación: Descripción del lugar
     - Calidad: Estado del agua (GOOD, FAIR, POOR)
   - Haga clic en "Guardar"

2. **Actualizar Fuente**
   - Seleccione la fuente en la lista
   - Haga clic en "Editar"
   - Modifique los campos necesarios
   - Haga clic en "Guardar"

3. **Eliminar Fuente**
   - Seleccione la fuente en la lista
   - Haga clic en "Eliminar"
   - Confirme la eliminación

### Monitoreo y Alertas
- Los niveles de agua se muestran en tiempo real en la interfaz
- Las alertas aparecerán automáticamente en el panel de alertas
- Los niveles críticos se mostrarán en rojo
- Tipos de alertas:
  - Nivel de agua bajo
  - Calidad deteriorada
  - Capacidad crítica

### Distribución de Agua
1. **Configurar Estrategia**
   - Seleccione la estrategia deseada:
     - Equitativa: Distribuye el agua por igual
     - Prioridad: Basada en niveles de prioridad
     - Justa: Considera necesidades específicas

2. **Realizar Distribución**
   - Haga clic en "Distribuir Agua"
   - El sistema calculará la distribución óptima
   - Los resultados se mostrarán en la interfaz

### Generación de Reportes
1. **Generar Reporte**
   - Haga clic en "Generar Reporte"
   - El reporte se mostrará en la interfaz
   - Incluye:
     - Estado actual de las fuentes
     - Historial de distribuciones
     - Estadísticas de uso

2. **Exportar a Excel**
   - Haga clic en "Exportar a Excel"
   - Seleccione la ubicación para guardar
   - El archivo incluirá:
     - Datos de todas las fuentes
     - Historial de alertas
     - Estadísticas de distribución

### Solución de Problemas Comunes
1. **No se muestran alertas**
   - Verifique que el sistema esté iniciado
   - Compruebe la configuración de umbrales
   - Revise la conexión con las fuentes

2. **Error al exportar reportes**
   - Verifique que Apache POI esté instalado
   - Compruebe permisos de escritura
   - Asegúrese que Excel no esté abierto

3. **Problemas de distribución**
   - Verifique la disponibilidad de agua
   - Compruebe la configuración de la estrategia
   - Revise los niveles de prioridad

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── aqua/
│   │           ├── Main.java
│   │           ├── alert/
│   │           ├── config/
│   │           ├── distribution/
│   │           ├── domain/
│   │           ├── monitoring/
│   │           ├── observer/
│   │           ├── report/
│   │           ├── repository/
│   │           ├── system/
│   │           └── ui/
│   └── resources/
└── test/
    └── java/
        └── com/
            └── aqua/
```

## Patrones de Diseño Implementados

- **Observer**: Para notificación de cambios en niveles de agua y alertas
- **Strategy**: Para diferentes estrategias de distribución de agua
- **MVC**: Para la separación de la interfaz de usuario, lógica de negocio y datos
- **Repository**: Para el acceso y gestión de datos de fuentes de agua

## Funcionalidades Detalladas

### Gestión de Fuentes de Agua
- Agregar nuevas fuentes (ríos y pozos)
- Actualizar información de fuentes existentes
- Eliminar fuentes
- Monitorear niveles y calidad

### Sistema de Alertas
- Alertas por nivel crítico de agua
- Alertas por calidad deteriorada
- Alertas por capacidad crítica
- Historial de alertas

### Distribución de Agua
- Distribución equitativa entre destinatarios
- Distribución basada en prioridades
- Distribución justa considerando necesidades
- Cálculo de disponibilidad

### Reportes
- Generación de reportes históricos
- Exportación a Excel
- Información detallada de fuentes
- Estadísticas de uso

## Contribución

1. Fork el proyecto
2. Crear una rama para tu feature
3. Commit tus cambios
4. Push a la rama
5. Abrir un Pull Request

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Contacto

Link del Proyecto: [aqua-java](https://github.com/contracamilo/aqua-java.git) 