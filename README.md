# 🌏 Travel Recommendation Service 🌟

A dynamic travel recommendation system that helps users plan trips efficiently by leveraging **Dijkstra's algorithm** for shortest route calculations and Google APIs for interactive map visualization.

---

## 🚀 Features

### 🌍 Country Selection
- Choose from **South Korea**, **Japan**, or **Vietnam** to explore their top landmarks.

### 🏷️ Tag-Based Filtering
- Personalize recommendations by selecting **2 to 5 tags** such as "nature," "history," or "food."
- Tags are dynamically matched to landmarks based on user preferences.

### 🗺️ Optimized Route Planning
- Calculates shortest travel routes between landmarks using **Dijkstra's Algorithm**.
- Includes distances between the country's main airport and landmarks using the **Google Distance Matrix API**.

### 📌 Interactive Map
- Displays landmarks and optimized routes using **Google Maps API**.
- Clickable pins reveal landmark details like:
  - **Name**
  - **Tags**
  - **Visit Order**

### 💻 Responsive UI
- Accessible across devices with a fully interactive, responsive interface.

---

## 🛠️ Technology Stack

### **Backend**
- **Language**: Java
- **Framework**: Spring Boot
- **Algorithm**: Dijkstra's Algorithm for route optimization
- **APIs**:
  - Google Distance Matrix API
  - Google Maps JavaScript API

### **Frontend**
- **HTML5**, **CSS3**, **JavaScript**
- Responsive design with interactive map features.

---

## 🔧 How It Works

1. **Country Selection**  
   Users select a travel destination from **South Korea**, **Japan**, or **Vietnam**.

2. **Tag Preferences**  
   Choose **2 to 5 tags** to define your travel style (e.g., adventure, history, nature).

3. **Landmark Filtering**  
   The system finds all landmarks in the selected country that match the chosen tags.

4. **Distance Calculation**  
   Distances between landmarks and the country's main airport are fetched using the **Google Distance Matrix API**.

5. **Optimized Route Calculation**  
   The shortest travel route is calculated using **Dijkstra's Algorithm**, starting and ending at the airport.

6. **Interactive Map Display**  
   The calculated route is visualized on a Google Map:
   - **Pins** mark landmarks.
   - **Routes** connect the landmarks in visit order.
   - Clicking on pins shows detailed information about the landmarks.

---

## 📂 Project Structure
    TravelRecommendationService/ ├── pom.xml ├── src/ │ ├── main/ │ │ ├── java/ │ │ │ └── com.travelrecommendation/ │ │ │ ├── TravelRecommendationApplication.java │ │ │ ├── controllers/ │ │ │ │ └── LandmarkController.java │ │ │ ├── exceptions/ │ │ │ │ └── CustomException.java │ │ │ ├── models/ │ │ │ │ ├── Country.java │ │ │ │ └── Landmark.java │ │ │ ├── services/ │ │ │ │ ├── DistanceService.java │ │ │ │ └── RouteService.java │ │ │ └── utils/ │ │ │ └── ApiClient.java │ │ └── resources/ │ │ ├── static/ │ │ │ ├── index.html │ │ │ ├── styles.css │ │ │ └── app.js │ │ └── application.properties └── README.md


    
### Key Components
- **`controllers/`**: Handles API endpoints for country and tag selection, and route optimization.
- **`models/`**: Data classes for `Landmark` and `Country`.
- **`services/`**: 
  - **`DistanceService`**: Fetches distances using Google APIs.
  - **`RouteService`**: Implements Dijkstra's Algorithm.
- **`utils/`**: Helper classes like `ApiClient` for HTTP requests.
- **Frontend**: 
  - **`index.html`**: Main UI.
  - **`styles.css`**: Styling.
  - **`app.js`**: Handles user interactions and map visualization.
