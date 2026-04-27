
# Smart Distributor Inventory Tracker

A lightweight Android application built with Java designed to help small-scale vendors and distributors track fast-moving stock (FMCG). Ideal for managing supply chain logistics at the edge, logging inbound/outbound goods, and getting immediate low-stock alerts.

## Features
* Inbound & Outbound Logging: Easily add new stock or deduct sold stock.
* Dynamic Low-Stock Alerts: Automatically warns the user when inventory dips below a configurable threshold (e.g., 20 units).
* Offline-First: Fully functional without an internet connection using an embedded SQLite database.
* Simple & Intuitive UI: Built with speed in mind for fast data entry.

## Tech Stack
* Language: Java
* Environment: Android Studio
* Database: SQLite (using SQLiteOpenHelper)
* UI: XML View-based layout

## Setup & Installation
1. Clone this repository:
   git clone https://github.com/dev-tiwari-404/Distributor-Inventory-Tracker.git
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an Android emulator or a physical device connected via USB/Wi-Fi.

## Project Structure
* MainActivity.java: Handles the UI logic, processes transactions, and updates low-stock warnings.
* InventoryDbHelper.java: Manages the SQLite database creation, versioning, and stock updates/queries.
* activity_main.xml: Contains the UI layout (EditTexts, Buttons, TextViews).

## Future Enhancements
* [ ] Add RecyclerView to view a complete list of current inventory items.
* [ ] Integrate Barcode/QR Code scanning for faster item entry.
* [ ] Export inventory reports to CSV/Excel formats.
* [ ] Cloud synchronization (e.g., Firebase) to sync data with a central dashboard.

