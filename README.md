# Jet Fitness App

Jet Fitness App is a modern fitness tracking application built using **Kotlin** and **Jetpack Compose**. It follows the **MVVM architecture** and provides users with the ability to track their workout progress and body weight daily. The app supports offline storage using **Room Database** and synchronizes data with the backend powered by **Ktor** and **MongoDB**.

## 🛠 Tech Stack

- **Frontend**: Kotlin, Jetpack Compose, MVVM Architecture
- **Backend**: Ktor, MongoDB Atlas
- **Local Storage**: Room Database
- **Networking**: Ktor Client
- **Authentication**: JWT Authentication

## 🚀 Features

- **User Authentication** (JWT-based signup and login)
- **Workout Progress Tracking**
- **Daily Weight Logging**
- **Offline Data Storage (Room Database)**
- **Data Synchronization with Backend (Ktor Server, MongoDB)**
- **Update Workout Progress Multiple Times Before Saving to Backend**

## 📌 How It Works

1. **Sign Up/Login** using JWT authentication.
2. **Log Today's Workout Progress** (exercise details, sets, reps, etc.).
3. **Log Today's Weight**.
4. **Data is First Stored in Room Database** for offline access.
5. **Users Can Update Progress Multiple Times** before finalizing.
6. **Once Finalized, Data is Sent to the Backend** (Ktor + MongoDB).

## 🔧 Installation

### Backend Setup (Ktor Server)

1. Clone the repository.
2. Navigate to the backend folder.
3. Run `ktor` server:
   ```bash
   ./gradlew run
   ```
4. Ensure MongoDB is configured and running.

### Frontend Setup (Android App)

1. Clone the repository.
2. Open the project in **Android Studio**.
3. Sync dependencies.
4. Run the application on an emulator or physical device.

## 📡 API Endpoints (Ktor Backend)

| Method | Endpoint    | Description           |
| ------ | ----------- | --------------------- |
| POST   | `/signup`   | User Signup           |
| POST   | `/login`    | User Login (JWT)      |
| POST   | `/workout`  | Add workout progress  |
| POST   | `/weight`   | Log daily weight      |
| GET    | `/progress` | Fetch workout history |

## 📷 Screenshots



## 👤 Author

**Shashank Narayan Juwar**

---

Feel free to contribute, open issues, or reach out if you have any suggestions! 🚀

