# CLIENTE API MARVEL (KOTLIN)

Aplicación Kotlin que consume el Api de Marvel y recuperar una lista de personajes.
La arquitectura utilizada es MVVM (Model View ViewModel)

![mvvm](https://user-images.githubusercontent.com/19550736/47259068-57edf780-d49c-11e8-9190-8fb8ff21b052.png)

## Librerias usadas

  * [Arch] (https://developer.android.com/jetpack/arch/): una colección de bibliotecas que lo ayudan a diseñar aplicaciones sólidas, probables y fáciles de mantener. Comience con clases para administrar el ciclo de vida de los componentes de la interfaz de usuario y manejar la persistencia de datos.
  * [DataBinding] (https://developer.android.com/topic/libraries/data-binding/) - - Vincula de forma declarativa datos observables a elementos de la interfaz de usuario.
  * [LiveData] (https://developer.android.com/topic/libraries/architecture/livedata): crea objetos de datos que notifican las vistas cuando cambia la base de datos subyacente.
  * [Room] (https://developer.android.com/topic/libraries/architecture/room): acceda a la base de datos SQLite de su aplicación con objetos en la aplicación y comprobaciones en tiempo de compilación.
  * [ViewModel] (https://developer.android.com/topic/libraries/architecture/viewmodel) - - Almacena datos relacionados con la interfaz de usuario que no se destruyen en las rotaciones de aplicaciones. Programe fácilmente tareas asincrónicas para una ejecución óptima.

  * [retrofit] (https://square.github.io/retrofit/) - - Un cliente HTTP con seguridad de tipos para Android y Java
  * [okhttp] (https://square.github.io/okhttp/) - - HTTP es la forma de red de aplicaciones modernas. Así es como intercambiamos datos y medios. Hacer HTTP de manera eficiente hace que sus cosas se carguen más rápido y ahorra ancho de banda.
  * [dagger] (https://dagger.dev) - - Dagger es un marco de trabajo de inyección de dependencias en tiempo de compilación totalmente estático para Java, Kotlin y Android. Es una adaptación de una versión anterior creada por Square y ahora mantenida por Google.
  * [gson] (https://github.com/google/gson) - - Gson es una biblioteca de Java que se puede utilizar para convertir objetos Java en su representación JSON
  * [glide] (https://github.com/bumptech/glide) - - Glide es un marco de carga de imágenes y administración de medios de código abierto rápido y eficiente para Android que incluye la decodificación de medios, el almacenamiento en caché de disco y memoria y la agrupación de recursos en una interfaz simple y fácil de usar.
  * [shapeOfview] (https://github.com/florent37/ShapeOfView) - - Dar una forma personalizada a cualquier vista de Android útil para Material Design 2
 
 ## Configuración

 Debemos crear en app/ el fichero de configuración keys.properties con las claves publicas y provadas de la API

    Ej:
    MARVEL_PUBLIC_KEY="7d92ae6ce2031f1d735806xxxxxxxxxx"
    MARVEL_PRIVATE_KEY="c93bfdf925dc911b37e1392d15f03exxxxxxxxxx"
