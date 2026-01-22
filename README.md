# StyleSwap – Android Mini E-Commerce App

StyleSwap is a Jetpack Compose–based mini e-commerce Android application that demonstrates modern Android development practices such as MVVM architecture, repository pattern, API integration using Retrofit, asynchronous programming with Coroutines/Flow, and persistent local storage using Room.

The app fetches products from FakeStoreAPI and provides browsing, search, sorting, product detail viewing, and a wishlist feature stored locally.

## Demo Video
Drive Link: https://drive.google.com/file/d/1_hDMX-t0zxM3DmAG-jhEFaZ_Mp4KdYBq/view?usp=sharing

## Features
- Product feed with category tabs (Women / Men)
- Search products by title
- Sorting options:
  - Price: Low to High
  - Price: High to Low
  - Rating: High to Low
- Product detail screen
- Wishlist functionality:
  - Add/remove wishlist items
  - Wishlist screen to view saved products
  - Persistent storage using Room database
- Bottom navigation for Home and Wishlist

## Tech Stack
- Language: Kotlin
- UI: Jetpack Compose, Material 3
- Architecture: MVVM + Repository Pattern
- Networking: Retrofit, OkHttp Logging Interceptor
- Async: Kotlin Coroutines, Flow/StateFlow
- Local Database: Room
- Navigation: Navigation Compose
- Image Loading: Coil

## Architecture Overview
The project follows MVVM with a repository layer:

- UI Layer: Compose screens, reusable components, navigation
- ViewModel Layer: Holds state using StateFlow and manages UI events
- Repository Layer: Centralized logic for API calls and database actions
- Data Layer:
  - Remote: FakeStoreAPI (Retrofit)
  - Local: Room Database (Wishlist)

## Project Structure
com.example.styleswap
    - MainActivity.kt

    - core
        - navigation
            - BottomNav.kt
            - Routes.kt
        - util
            - Resource.kt
            - SortOption.kt

    - data
        - local
            - dao
                - WishlistDao.kt
            - db
                - AppDatabase.kt
                - DatabaseProvider.kt
            - entity
                - WishlistEntity.kt

        - remote
            - api
                - ApiClient.kt
                - FakeStoreApi.kt
            - dto
                - ProductDto.kt

        - repository
            - ProductRepository.kt
            - WishlistRepository.kt

    - ui
        - components
            - ProductCard.kt

        - screens
            - home
                - HomeScreen.kt
                - WomenFeedScreen.kt
            - details
                - ProductDetailScreen.kt
            - wishlist
                - WishlistScreen.kt

        - theme
            - Color.kt
            - Theme.kt
            - Type.kt

    - viewmodel
        - FeedViewModel.kt
        - ProductDetailViewModel.kt
        - WishlistViewModel.kt


## API Used
- FakeStoreAPI
  - Women category endpoint: `https://fakestoreapi.com/products/category/women%27s%20clothing`
  - Men category endpoint: `https://fakestoreapi.com/products/category/men%27s%20clothing`

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/PoojahariniK/StyleSwap.git
Open the project in Android Studio.

Sync Gradle files.

Run the app on an emulator or a physical device (minSdk 24).

Notes
Wishlist persistence is handled through Room, ensuring saved items remain available after app restarts.

Network calls and UI states are managed using Coroutines + Flow, ensuring responsive UI updates.

Future Improvements
Add pagination/infinite scroll for product feed

Add filter by price range and rating range

Improve UI with better product listing layout and animations

Add offline caching for API results

Add unit tests for repositories and viewmodels

Author
Poojaharini K
