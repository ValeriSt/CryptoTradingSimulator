# Crypto Trading Simulator

This project is a full-stack cryptocurrency trading simulator. It allows users to buy and sell virtual cryptocurrency using live price data from the Kraken WebSocket API.

## Features

* Live price updates for top 13 cryptocurrencies (somehow couldn't work to show top 20)
* Virtual account balance and holdings
* Buy and sell with dynamic price calculation
* Transaction history with profit/loss tracking
* Dark themed UI with price movement indicators

---

## Project Structure

```
CryptoTradingSim/
├── crypto-trade-frontend/                  # React frontend with Vite
│   ├── index.html
│   ├── vite.config.js
│   ├── package.json
│   ├── main.jsx
│   ├── App.jsx
│   ├── components/
│   └── index.css
├── src/                       # Java backend (Spring Boot)
│   └── main/java/com/example/cryptotradesim/
│       ├── controller/
│       ├── service/
│       ├── dao/
│       ├── model/
│       └── CryptoTradeSimApplication.java
├── build.gradle
├── settings.gradle
├── .gitignore
├── README.md
└── ...
```

---

## Requirements

### Backend:

* Java 21/22
* Gradle
* PostgreSQL (configured in application.properties)

### Frontend:

* Node.js
* Vite
* React

---

## Setup Instructions

### Note on Initial Database Setup
In some environments, running the backend with ./gradlew bootRun may cause the process to hang or freeze around 80% due to Gradle and Spring DevTools issues.

If that happens:
You can manually start the application in your IDE (e.g., IntelliJ, Eclipse, VS Code):

Run CryptoTradeSimApplication.java directly via the green Run arrow
This will correctly start the Spring Boot server and automatically initialize the database schema.

Make sure your local PostgreSQL server is running (or whichever DB you use), and the application.properties (or .yml) file contains correct DB connection details.

### Backend (Spring Boot)

1. Set up PostgreSQL and and use the schema.sql file.
2. Configure `application.properties` with your DB credentials
3. Run with:

```bash
./gradlew bootRun
```

### Frontend (React)

1. Navigate to `crypto-trade-frontend/`
2. Install dependencies:

```bash
npm install
```

3. Run the app:

```bash
npm run dev
```

4. Open [http://localhost:5173](http://localhost:5173)

5. Screenshots of the application

![Screenshot 2025-05-22 155237](https://github.com/user-attachments/assets/5acad1f4-d6d6-4279-b69c-f4bbf9608142)
![Screenshot 2025-05-22 155148](https://github.com/user-attachments/assets/2e17c7f3-41f4-472a-b903-ef89a7a2c113)
![Screenshot 2025-05-22 130728](https://github.com/user-attachments/assets/476fc8e9-527a-47c8-9697-7c4d6c2d2d91)
![Screenshot 2025-05-22 130721](https://github.com/user-attachments/assets/2afe438c-eb0b-4d59-8576-3408f1da1865)
![Screenshot 2025-05-22 130659](https://github.com/user-attachments/assets/79fc8166-0a03-4d65-9366-b7e44345c16a)
![Screenshot 2025-05-22 130640](https://github.com/user-attachments/assets/bd6776ed-757f-4d78-8a5d-a55eeed6ffcc)
![Screenshot 2025-05-22 130632](https://github.com/user-attachments/assets/b0f6b7e2-0cd0-43d0-9522-5580b07be68d)
![Screenshot 2025-05-22 130602](https://github.com/user-attachments/assets/d4b5ec4e-a67a-45c5-a319-27cb22155f25)
![Screenshot 2025-05-22 130540](https://github.com/user-attachments/assets/699c8f70-9bdd-4675-90e2-c9d87c86e054)
![Screenshot 2025-05-22 130533](https://github.com/user-attachments/assets/fd3bce08-3c1a-4806-85f8-69438e5bcabd)

