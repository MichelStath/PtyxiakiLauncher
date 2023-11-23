# PtyxiakiLauncher

# ContactDbHelper Class Documentation

## Overview

The `ContactDbHelper` class manages a SQLite database for storing contact information in Android applications. It provides essential functionality for creating and managing a contact table, as well as performing basic CRUD operations.

## Constructor

- **Description:** Initializes a `ContactDbHelper` instance.
- **Parameters:** 
  - `context`: The application context.

## Database and Table Metadata

- **Constants:**
  - `DATABASE_NAME`: The name of the SQLite database file.
  - `DATABASE_VERSION`: The version of the SQLite database.
  - `TABLE_NAME`: The name of the table storing contact information.
  - `COLUMN_ID`: The primary key column for contact entries.
  - `COLUMN_CONTACT_NAME`: The column storing contact names.
  - `COLUMN_CONTACT_PHONE`: The column storing contact phone numbers.

## `onCreate` Method

- **Description:** Called during the initial database creation.
- **Functionality:** Executes an SQL query to create the `my_contacts` table with specified columns.

## `onUpgrade` Method

- **Description:** Called when a database upgrade is required.
- **Functionality:** Drops the existing `my_contacts` table and initiates a call to `onCreate` for creating a new table.

## `addContact` Method

- **Description:** Adds a new contact to the `my_contacts` table.
- **Parameters:** 
  - `contact`: A representation of the new contact.

## `getAllContacts` Method

- **Description:** Retrieves all contacts from the `my_contacts` table.
- **Returns:** A list of contact representations.

## `deleteContact` Method

- **Description:** Deletes a specified contact from the `my_contacts` table.
- **Parameters:** 
  - `contact`: A representation of the contact to be deleted.

This documentation provides a concise overview of the `ContactDbHelper` class, explaining its purpose, constructor, methods, and their functionalities.

# DateTimeThread Class Documentation

## Overview

The `DateTimeThread` class is a utility for managing and updating date and time information in Android applications. It operates as a background thread, ensuring consistent updates for display components.

## Constructor

- **Description:** Initializes a `DateTimeThread` instance.
- **Parameters:**
  - `handler`: A handler for managing updates.
  - `tv1`: The TextView for displaying the date.
  - `tv2`: The TextView for displaying the time.

## `start` Method Override

- **Description:** Initiates the thread.
- **Note:** This method overrides the default `start` method to log the initiation.

## `run` Method Override

- **Description:** Runs the thread, continuously updating date and time information.
- **Functionality:**
  - Utilizes a PowerManager to check if the screen is interactive.
  - Stops updates if the screen is not interactive.

## `updateTimeRunnable` Runnable

- **Description:** A scheduled task for updating date and time information.
- **Functionality:**
  - Updates the associated TextViews with the current date and time.
  - Schedules the next update after a 1-second delay.

## `isThreadRunning` Method

- **Description:** Checks if the thread is currently running.
- **Returns:** `true` if the thread is running, `false` otherwise.

## `setThreadRunning` Method

- **Description:** Updates the running status of the thread.
- **Parameters:**
  - `threadIsRunning`: A boolean value indicating whether the thread should run or stop.

## `stopThread` Method

- **Description:** Stops the thread and logs the action.

This documentation offers a comprehensive overview of the `DateTimeThread` class, explaining its purpose, constructor, methods, and their respective functionalities.
