# LBG Assignment

The app consists of two screens: one for displaying a list of news items fetched from a public news API, and the other for showing the details of a selected news item. This is a modular MVVM clean architecture app that implements a structured and scalable approach using multiple modules, ensuring maintainability, testability, and separation of concerns.

## Folder Structure

### **App Module**
- **di**: `AppModule` provides global dependencies.
- **navigation**: Manages screen navigation (`AppNavigation`).
- **ui.screen**: Screens for the app:
  - `MainActivity`: App entry point.
  - `ItemListScreen`: Displays news list.
  - `ItemDetailScreen`: Displays selected news details.
- **viewmodel**: `MainViewModel` holds business logic.
- **MyApplication**: Initializes the app.

### **Core Module**
- **di**: `CoreModule` provides core dependencies.
- **model**: Defines shared models (`Result`).
- **util**: Common utilities (`Constants`, `NetworkHelper`).

### **Data Module**
- **api**: `ApiService` for API calls, `BaseResponse` for API responses.
- **di**: `DataModule` and repository binding.
- **mapper**: Maps `ItemDto` to domain models.
- **repository**: `NewsRepository` fetches news.
- **util**: Handles network responses.

### **Domain Module**
- **di**: `DomainModule` binds domain dependencies.
- **model**: Defines domain model `Item`.
- **repository**: `ItemRepository` for fetching data.
- **usecase**: `GetItemsUseCase` to retrieve news.

## App Screens

- **ItemListScreen**: Displays a list of news fetched from the public API.
  
  ![News List Screenshot](https://github.com/koushik16github/LBG-Assignment/blob/main/Screenshot_20250106_185105.png)

- **ItemDetailScreen**: Displays detailed news content when an item is selected.
  
  ![News Details Screenshot](https://github.com/koushik16github/LBG-Assignment/blob/main/Screenshot_20250106_185133.png)

## Dependencies
- **Compose** for UI design
- **Retrofit** for API calls
- **Dagger**/ **Hilt** for DI
- **ViewModel** for UI data
- **Coroutines** for async tasks
- **JUnit** and **MockK** for unit testing
