Finn360 - Fintech AI Research Project
Finn360 is a mobile-first financial technology application designed to provide users with real-time market data across both traditional stock markets and cryptocurrency assets. This project is developed as a research initiative at Doğuş University, exploring the integration of microservice-ready backend architectures with native mobile performance.

📖 Project Overview
The primary goal of Finn360 is to democratize access to complex financial data. By aggregating data from global stock exchanges and the crypto ecosystem into a single, user-friendly mobile interface, it allows users to monitor their entire portfolio in one place.

The system is built on a Client-Server Architecture, separating the heavy lifting of data processing (Backend) from the presentation layer (Mobile App).

🚀 Key Features
Multi-Asset Market Tracking:

Stocks: Live monitoring of stock prices from major indices (Nasdaq).

Cryptocurrencies: Real-time tracking of top digital assets like Bitcoin and Ethereum.

Secure User Management: Robust authentication and authorization system using JSON Web Tokens (JWT) to protect user portfolios.

Detailed Asset Profiles:

Company financials and peer comparisons for stocks.

Market capitalization, trading volume, and 24h changes for cryptocurrencies.

Watchlist & Portfolio: Personalized dashboard allowing users to save and track specific stocks and coins.

AI-Driven Insights (In Development): Planned implementation of algorithmic analysis to interpret market movements.

🏗 Technology Stack
Backend Infrastructure
The server-side application is engineered for scalability and security, serving as the central hub for data processing and API orchestration.

Language: Java

Framework: Spring Boot (Web, Security, Data MongoDB)

Database: MongoDB Atlas (Cloud NoSQL)

Authentication: Spring Security + JWT

Build Tool: Maven / Gradle

Mobile Application (Client)
The frontend is a native Android application focused on performance and Material Design principles.

Language: Kotlin

Platform: Android SDK

UI Toolkit: XML Layouts

Networking: Retrofit & OkHttp

Concurrency: Kotlin Coroutines

Architecture Pattern: MVVM (Model-View-ViewModel)

🌐 API Integrations & Data Sources
Finn360 acts as a unified gateway to diverse financial markets by integrating specialized third-party services:

Finnhub API:

Primary source for traditional stock market data, candles (OHLCV), and company financial metrics.

CoinGecko API:

Used for comprehensive cryptocurrency data, including live prices, market capitalization, volume data, and metadata for thousands of coins.

🧩 System Architecture
Kod snippet'i

graph TD
    Client[Android App (Kotlin)] -->|HTTPS / REST| LB[API Gateway]
    LB --> Backend[Spring Boot Backend (Java)]
    Backend -->|Read/Write| DB[(MongoDB Atlas)]
    Backend -->|Fetch Stocks| API1[Finnhub API]
    Backend -->|Fetch Crypto| API2[CoinGecko API]
🎓 About the Project
This project constitutes a Requirements Analysis and Software Development Research effort. It focuses on:

Implementing secure RESTful APIs for hybrid financial data.

Handling asynchronous data streams from multiple providers (Stocks vs. Crypto).

Structuring NoSQL databases for diverse financial assets.

Developer: Talha Kasikci Institution: Doğuş University

Copyright © 2025 Finn360. All rights reserved.
