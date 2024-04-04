# Digital Persona U.are.U Console Application

This is a application for interacting with Digital Persona U.are.U fingerprint readers using Java and Spring Boot.

## Overview

This application provides endpoints to interact with Digital Persona U.are.U fingerprint readers. It allows capturing fingerprint images, enrolling new fingerprints, and comparing captured fingerprints with enrolled ones.

## Prerequisites

- Java 8 or higher
- Gradle
- Digital Persona U.are.U SDK
- Spring Boot
- SDK

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/SharshenovJR/digital-persona-uareu-web.git

2. Navigate to the project directory:

   ```bash
   cd digital-persona-uareu-web
   
4. Build the project using Gradle:
   
   ```bash
   gradle build
   
6. Run the application:

   ```bash
   java -jar build/libs/digital-persona-uareu-web.jar

## Usage

Ensure that the Digital Persona U.are.U fingerprint reader is connected to your computer.
Open your web browser and navigate to http://localhost:8282.
Use the provided endpoints to interact with the fingerprint reader:
- /v2/scanner/devices: Get a list of connected fingerprint reader devices.
- /v2/scanner/capture/image: Capture a fingerprint image.
- /v2/scanner/capture: Capture a fingerprint and return its details.
- /v2/scanner/enroll: Enroll a new fingerprint.
- /v2/scanner/compare: Compare a captured fingerprint with enrolled ones.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or create a pull request.
