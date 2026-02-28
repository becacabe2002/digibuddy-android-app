# DigiBuddy

DigiBuddy is an Android application designed to help users monitor and manage their digital well-being and stress levels. By tracking daily habits such as sleep, screen time, and notification frequency, DigiBuddy provides personalized insights and recommendations to foster a healthier relationship with technology.

## Features

- **Profile Management**: Support for multiple user profiles, allowing different individuals to track their data independently.
- **Data Logging**: Simple interface to log daily metrics including:
    - Sleep hours and sleep quality.
    - Device usage (hours per day).
    - Notification frequency.
    - Number of phone unlocks.
- **Interactive Dashboard**: Visualize your progress over time with dynamic charts for stress levels and device usage history.
- **Personalized Recommendations**: Receive actionable advice based on your logged data and pre-defined well-being rules.
- **Offline First**: Uses local storage (Room Database) to ensure your data is always accessible and private.

## Main User Flow

1.  **Onboarding/Profile Selection**: Upon launching the app, users can select an existing profile or create a new one by providing their name and age.
2.  **Dashboard**: After selecting a profile, the user is greeted with a dashboard showing their recent stress level trends and personalized health tips.
3.  **Logging Data**: Users navigate to the "Log" tab to input their daily statistics (e.g., "How many hours did you sleep?", "How many times did you unlock your phone?").
4.  **Insight Generation**: Once data is saved, the app recalculates the stress level estimate and updates the dashboard charts.
5.  **Reviewing Recommendations**: Users can read specific advice on the dashboard to help improve their digital habits based on the latest entries.

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Navigation**: Jetpack Navigation Compose
- **Database**: Room
- **Charts**: MPAndroidChart
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Custom ViewModel Factory
