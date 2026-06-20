# IntegraCountry

Sistema de gestión para un barrio cerrado (country / barrio privado). Permite administrar residentes, reclamos y solicitudes, reservas de espacios comunes, expensas y el control de accesos al barrio. Está desarrollado en Java con interfaz gráfica Swing y aplica varios patrones de diseño vistos en la materia.

## Cómo correr el proyecto

El proyecto no usa librerías externas: solo necesitás un JDK (versión 17 o superior). La clase principal es `Main.java`, que está en la raíz del proyecto.

Desde un IDE (IntelliJ o VS Code con el Extension Pack for Java), abrí la carpeta del proyecto y ejecutá `Main`. Se abre la pantalla de acceso en pantalla completa.

## Usuarios de prueba

El sistema arranca con estas cuentas ya cargadas. Se ingresa con usuario y contraseña.

| Rol | Usuario | Contraseña |
|-----|---------|------------|
| Administrador | `admin` | `1234` |
| Guardia | `guardia` | `1234` |
| Residente | `juan` | `1234` |
| Residente | `maria` | `1234` |

El administrador también puede dar de alta nuevos residentes desde su panel, asignándoles usuario y contraseña.

## Qué puede hacer cada rol

**Administrador**: gestionar residentes (alta y listado), ver y avanzar el estado de las solicitudes, aprobar o rechazar reservas, y generar las expensas del mes.

**Guardia**: registrar ingresos y egresos al barrio (con validación), consultar las visitas autorizadas pendientes, y reportar incidentes de seguridad.

**Residente**: cargar reclamos, solicitar reservas de espacios comunes, autorizar visitas a su lote, y ver y pagar sus expensas.

## Reglas de negocio implementadas

El sistema no solo registra datos, también valida:

- **Control de accesos**: un residente solo entra si su DNI está registrado en el sistema. Una visita, proveedor o delivery solo entra si un residente la autorizó previamente para su lote. Cada autorización sirve para un único ingreso. Un egreso solo se registra si la persona tiene un ingreso previo sin salida.
- **Reservas**: no se puede reservar un espacio que ya está tomado en la misma fecha y horario.
- **Expensas**: cada expensa calcula su total (monto base más multas) y maneja su estado (pendiente, paga, vencida).
- **Solicitudes**: al avanzar una solicitud se le asigna un empleado automáticamente, y cuando se resuelve sale de la vista activa pero queda registrada en el sistema.

## Estructura del proyecto

```
Main.java                  Punto de entrada
interfaces/                Interfaces generales (Cancelable)
modelo/
  abstractas/              Clases base (Usuario, Solicitud)
  enums/                   Enumerados (Prioridad, NivelRiesgo, etc.)
  espacios/                Barrio, Lote, Reserva, Ingreso, Autorizacion
  expensas/                Expensa
  solicitudes/             Reclamo, TareaMantenimiento, IncidenteSeguridad
  usuarios/                Administrador, Guardia, Residente, Propietario, etc.
patrones/
  observer/                Observer (notificación de cambios de estado)
  state/                   State (ciclo de vida de las solicitudes)
  strategy/                Strategy (canales de notificación)
  factory/                 Factory Method (creación de solicitudes)
  facade/                  Facade (AdministracionFacade)
repositorio/               Almacenamiento en memoria de cada entidad
servicio/                  AsignadorEmpleado (asignación round-robin)
vistas/                    Ventanas y componentes de UI
paneles/                   Paneles de cada funcionalidad
tests/                     Pruebas de los patrones por consola
```

## Patrones de diseño aplicados

- **Observer**: cuando una solicitud cambia de estado, notifica automáticamente a sus observadores (las notificaciones).
- **State**: la solicitud cambia su comportamiento según su estado (pendiente, en curso, resuelta).
- **Strategy**: las notificaciones se envían por distintos canales (interno, email, SMS) con una interfaz común.
- **Factory Method**: la fábrica crea el tipo de solicitud correcto (reclamo, tarea o incidente).
- **Facade**: `AdministracionFacade` centraliza las operaciones y oculta la complejidad del resto del sistema a las vistas.

## Limitaciones conocidas

- **Persistencia en memoria**: todos los datos viven mientras el programa está abierto. Al cerrarlo se reinician a los valores precargados. No hay base de datos ni archivos.
- **Contraseñas en texto plano**: en un sistema real se almacenarían cifradas (hash). Acá se guardan tal cual por simplicidad, ya que el foco del trabajo son los patrones de diseño.
- **Autenticación simple**: el login valida usuario y contraseña pero no implementa medidas de seguridad adicionales.
