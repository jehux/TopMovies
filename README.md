# 🎬 TopMovies App

TopMovies es una aplicación Android que muestra una lista de películas populares utilizando **The Movie Database (TMDb)**. Está construida siguiendo el patrón **MVVM con Clean Architecture**, lo que garantiza un código modular, testable y escalable. Incluye autenticación con Firebase y persistencia de datos con Room.

---

## Video de funcionamiento

[![Título del video](https://img.youtube.com/vi/0GwUUoVxXXY/hqdefault.jpg)](https://www.youtube.com/watch?v=0GwUUoVxXXY)

##  Características

- Inicio de sesión con correo electrónico mediante **Firebase Authentication**.
-  Arquitectura **MVVM + Clean Architecture**.
-  Consumo de API con **Retrofit2**.
-  Almacenamiento local con **Room**.
-  Muestra las 10 mejores películas (Top Rated).
-  Vista de detalle individual por cada película.
-  Cacheo inteligente: la **primera vez** consulta la API y guarda los datos en Room. Las siguientes veces, carga directamente desde la base de datos.

---

##  Arquitectura

```text
Presentation (Activities/ViewModels)
│
├── Domain (UseCases + Entities)
│
└── Data (Repositories, RetrofitService, Room DB)
```

---

##  Tecnologías Usadas

-  **Kotlin**
-  **MVVM + Clean Architecture**
-  **Retrofit2** – Consumo de servicios REST
-  **Room** – Persistencia local
-  **Firebase Authentication**
-  **Coroutines + Flow**
-  **Glide** – Carga de imágenes
-  **Hilt** – Inyección de dependencias

---

## 📸 Capturas de Pantalla

![Poster de película](https://i.imgur.com/coiwxOg.jpeg) 

---

##  Cómo probar el proyecto

1. Clona el repositorio:

   ```bash
   git clone https://github.com/tu-usuario/top-movies-app.git
   cd top-movies-app
   ```

2. Agrega tu archivo `google-services.json` en la carpeta `app/`.

3. Crea una cuenta gratuita en [TMDb](https://www.themoviedb.org/) y reemplaza tu API key en la configuración de Retrofit.

4. Ejecuta el proyecto en Android Studio (API 21+).

---

##  Recursos

- API de películas proporcionada por [The Movie Database (TMDb)](https://www.themoviedb.org/).
- Autenticación y servicios backend proporcionados por [Firebase](https://firebase.google.com/).

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT.
