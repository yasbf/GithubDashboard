# GithubDashboard

#### Android application to search repositories for specific user using GitHub API

This project demonstrates how to use Kotlin, Android Architecture Components, and Coroutines to perform remote API calls and cache data in Room database

#### Structure:

1. Search screen - performs user search via GitHub API, displays his repositories in list and store result in local DB
2. Details screen - shows user name, url and repositorie name, language, description, stars and git url

### Architecture Components
This application implements the following concepts :
- ViewModel
- LiveData
- DataBinding
- Room
 
 ### Libraries
* [AndroidX][AndroidX] 
* [Kotlin][Kotlin]
* [Android Architecture Components][arch] ViewModel, LiveData, DataBinding, ROOM
* [Coroutines][coroutines]
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading

[AndroidX]: https://developer.android.com/jetpack/androidx
[Kotlin]: https://kotlinlang.org/docs/tutorials/kotlin-android.html
[arch]: https://developer.android.com/arch
[coroutines]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide
