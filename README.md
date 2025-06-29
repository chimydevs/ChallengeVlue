## Vlue Challenge

Vlue Challenge is an Android application built with Kotlin, Jetpack Compose, and Google Maps SDK, designed to demonstrate modern Android development best practices. It allows users to pick favorite locations on a map, see distances in miles, edit or delete them, and customize the map view.

## 📂 Project Structure

<details> <summary><strong>data</strong> <em>(click here to expand)</em></summary> <span>&emsp;» local</span></br> <span>&emsp;» repository</span></br> <span>&emsp;&emsp;» FavoriteRepository.kt</span></br> </details> <details> <summary><strong>di</strong> <em>(click here to expand)</em></summary> <span>&emsp;» KoinModule.kt</span></br> </details> <details> <summary><strong>domain</strong> <em>(click here to expand)</em></summary> <span>&emsp;» usecase</span></br> <span>&emsp;&emsp;» FavoriteUseCase.kt</span></br> <span>&emsp;&emsp;» FavoriteLocation.kt</span></br> </details> <details> <summary><strong>ui</strong> <em>(click here to expand)</em></summary> <span>&emsp;» components</span></br> <span>&emsp;&emsp;» BottomNavLayout.kt</span></br> <span>&emsp;&emsp;» EditFavoriteDialog.kt</span></br> <span>&emsp;&emsp;» FavoriteItem.kt</span></br> <span>&emsp;&emsp;» MapTypeSelector.kt</span></br> <span>&emsp;» favorites</span></br> <span>&emsp;&emsp;» FavoriteScreen.kt</span></br> <span>&emsp;» main</span></br> <span>&emsp;&emsp;» AppContent.kt</span></br> <span>&emsp;&emsp;» MapScreen.kt</span></br> <span>&emsp;&emsp;» MapViewUtils.kt</span></br> <span>&emsp;» settings</span></br> <span>&emsp;&emsp;» SettingsScreen.kt</span></br> <span>&emsp;» theme</span></br> <span>&emsp;&emsp;» Color.kt</span></br> <span>&emsp;&emsp;» Theme.kt</span></br> <span>&emsp;&emsp;» Type.kt</span></br> </details> <details> <summary><strong>root</strong> <em>(click here to expand)</em></summary> <span>&emsp;» MainActivity.kt</span></br> <span>&emsp;» MainApplication.kt</span></br> </details>

## Features

- Google Maps Integration: Centered on Miami by default, displays user location, markers, and distance calculations in miles.

- Save Favorites: Pick any location, give it a name, and it saves with its coordinates and resolved address.
- Edit & Delete Favorites: Rename or remove saved locations easily.
- Map Type Selector: Switch between Normal, Satellite and Hybrid views.
- Full-Screen Mode: Hide bottom navigation for immersive map exploration.
- Dynamic Theming: Automatically adapts colors and typography to match system preferences.
- Dark Theme Support: Looks great in dark mode, following Material guidelines for readability and contrast.
- Landscape Support: The UI is responsive and works seamlessly in both portrait and landscape orientations.
- Koin for DI: Handles dependency injection for a clean, modular and testable architecture.


## Video
Map Screen - Favorites Screen - Settings Screen

https://github.com/user-attachments/assets/4acff0c1-dc41-4bf5-82ea-920162bf41ab


## ⚙️ How to run

  - Clone the repository:
  git clone https://github.com/your-username/vlue-challenge.git
  - Open in Android Studio.
  Tested with Android
  - Run on an emulator or device with Google Play Services.
  Grant location permissions when prompted.
  - Alternatively, download the latest APK from the Releases section
and install it directly on your Android device.

## Technical decisions

  - MVVM + Clean Layers: ViewModel → UseCase → Repository for separation of concerns.
  - Koin: Easy DI framework to inject the MapViewModel and FavoriteUseCase.
  - Compose: Modern declarative UI.
  - Material Theme3: Blue palette to align with Vlue branding and use some icons.
  - Google Maps SDK: For map rendering and interaction.
  - Geocoder: Resolves lat/lng to human-readable addresses.

##  Possible future improvements

  - Use Room to persist favorites even after app restarts.
  - Move distance calculation to a shared UseCase layer.
  - Add unit tests and UI tests.
  - Support offline caching of maps and geocoding.

## Notes for reviewers
  - The bottom nav automatically hides in full-screen mode to maximize map view.
  - Each favorite marker snippet shows the distance + address.
  - Dependency injection with Koin makes the app easily testable and scalable.
