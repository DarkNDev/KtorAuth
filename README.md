# KtorAuth
User authentication client app with Ktor server
### Usage
1. App Configuration before starting - Set BASE_URL of server with port in Urls object present in .utils package. If server is hosted locally make sure clearTextTraffic set to true in Manifest under application tag otherwise remove it-
```
android:usesCleartextTraffic="true"
```
2. Demo -

https://github.com/DarkNDev/KtorAuth/assets/49820671/6a7b06dc-dd65-42b9-8f22-a18856fda1d8


### Libraries Used
Material 3, Navigation Component, Kotlin coroutines, Jetpack Datastore, Lifecycle (ViewModel + LiveData), Dagger Hilt, Ktor Client with okhttp, Content Negotiation, Kotlin serilization and Client Logging.
### Links
Server for usage [com.darkndev.ktor-auth](https://github.com/DarkNDev/com.darkndev.ktor-auth)
