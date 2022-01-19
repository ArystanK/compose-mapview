# MapView
Composable UI component for Desktop, Android and Browser

## Setup
In local.properties, add keys:
```
mapTilerSecretKey=xXxXxXxXxXxXx
sdk.dir=Path to Android SDK
```
Where **mapTilerSecretKey** is secret API key for cloud.maptiler.com  
Here you can get this key: https://cloud.maptiler.com/maps/streets/ (register and look at url field `?key=...#`)

## Usage
```Kotlin
MapView(
    modifier = Modifier.fillMaxSize(),
    mapTilerSecretKey = MAPTILER_SECRET_KEY,
    latitude =  60.00,
    longitude = 29.75,
    startScale = 500.0,
    onMapViewClick = { latitude, longitude ->
        println("Hello, Geo coordinates, (lat $latitude, lon $longitude)")
        true
    }
)
```

## Run samples targets
```./gradlew sample-desktop:run```  
```./gradlew sample-android:installDebug #(connect device first)```  
```./gradlew sample-browser:jsBrowserRun```  

## Describe sources
Проект разбит на несколько includeBuild, чтобы нормально проходил импорт в Idea.
Состоит из следующих подпроектов:

### include-mapview
Тут лежит мультиплатформенный MapView с общей логикой инициализации.    

### include-ui-android-desktop
Общий UI код между Android и Desktop. Всё максимально вынесено в commonMain sourceSet    
MapViewAndroidDesktop.kt - отрисовка на Canvas и обработка pointer ввода.

### include-ui-browser
MapViewBrowser.kt - отрисовка в html `<canvas>`

### include-io-android-desktop
Общий код между Android и Desktop для запросов в сеть и кэширования на диск.  
Для простоты этот модуль подключен как plugins { kotlin("jvm") } 

### include-model
Мультиплатформенная логика.  
В этом модуле нет зависимости на Compose и в будующем можно переиспользовать для iOS (например в SwiftUI)

### include-tile-image
Мультиплатформенная картинка TileImage. Вынес её в отдельный модуль, чтобы не было зависимости на Compose.

### include-config
Содержит конфиги для настройки и отладки

### unit-tests
Чтобы хорошо работала отладка в Idea - все тесты находятся в этом модуле

