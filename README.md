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

