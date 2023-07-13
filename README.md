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
1. Material 3
2. Navigation Component
3. Kotlin coroutines
4. Jetpack Datastore
5. Lifecycle (ViewModel + LiveData)
6. Dagger Hilt
7. Ktor Client with okhttp
8. Content Negotiation
9. Kotlin serilization
10. Client Logging.
### Links
Server for usage [com.darkndev.ktor-auth](https://github.com/DarkNDev/com.darkndev.ktor-auth)
