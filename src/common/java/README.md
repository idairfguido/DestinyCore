# Java Port of Common.h

This directory contains the Java implementation of the C++ `Common.h` header file from the DestinyCore project.

## Overview

The `Common.java` file provides a complete Java adaptation of the C++ common utilities and data structures. This conversion follows Java best practices and idioms while maintaining functional compatibility with the original C++ implementation.

## Package Structure

```
org.destinycore.common
└── Common.java
```

## Key Conversions

### 1. Enumerations
- **TimeConstants**: Time-related constants (MINUTE, HOUR, DAY, etc.)
- **AccountTypes**: Security level enumeration (PLAYER, MODERATOR, GAMEMASTER, etc.)
- **LocaleConstant**: Internationalization locale constants
- **DiscordMessageChannel**: Discord channel types

All enums include:
- Value accessors
- Static factory methods (`fromValue()`)
- Type-safe implementations

### 2. Data Classes
- **DiscordMessage**: Message structure with channel information
- **LocalizedString**: Multi-language string support

### 3. AnyData Class
The `AnyData` class replaces C++ `boost::any` functionality using:
- `ConcurrentHashMap` for thread-safe storage
- Java generics for type safety
- Type-safe getters with default values
- Counter increment operations

Key features:
```java
AnyData data = new AnyData();
data.set("key", 42);
Integer value = data.getValue("key", 0);
boolean exists = data.exist("key");
data.increment("counter", 1);
```

### 4. Utility Methods
- `parseUnsignedLong()`: Replaces C++ `atoul`
- `parseLong()`: Replaces C++ `atoll`
- `stringize()`: Replaces C++ `STRINGIZE` macro
- `getLocaleByName()`: Locale lookup functionality

### 5. Constants
- `DEFAULT_LOCALE`: Default locale constant
- `MAX_LOCALES`: Maximum number of locales
- `M_PI`: Mathematical constant PI
- `MAX_QUERY_LEN`: Maximum query length

### 6. Thread-Safe Queue
- `DISCORD_MESSAGE_QUEUE`: Concurrent queue for Discord messages

## Differences from C++ Version

### Removed
- All TrinityCore-specific references and includes
- Platform-specific code (Windows/Unix conditionals)
- C++ macro definitions (replaced with static methods)
- Direct `boost::any` usage (replaced with Java generics)
- System-level includes and dependencies

### Added
- Proper Java package structure
- JavaDoc documentation
- Thread-safe implementations using concurrent collections
- Type-safe enum implementations with helper methods
- Builder-style getters and setters
- Additional utility methods for common operations

## Usage Example

```java
import org.destinycore.common.Common;
import org.destinycore.common.Common.*;

// Using time constants
int oneHour = TimeConstants.HOUR.getValue();

// Working with locales
LocaleConstant locale = LocaleConstant.getLocaleByName("frFR");
String localeName = locale.getName();

// Using AnyData for flexible storage
AnyData gameData = new AnyData();
gameData.set("playerHealth", 100);
gameData.set("playerName", "Hero");
int health = gameData.getValue("playerHealth", 0);

// Increment counter
gameData.increment("killCount");

// Discord messaging
DiscordMessage msg = new DiscordMessage();
msg.setChannel(DiscordMessageChannel.DISCORD_WORLD_A);
msg.setMessage("Hello World!");
Common.DISCORD_MESSAGE_QUEUE.offer(msg);

// Localized strings
LocalizedString localizedText = new LocalizedString();
localizedText.setString(LocaleConstant.LOCALE_enUS, "Hello");
localizedText.setString(LocaleConstant.LOCALE_frFR, "Bonjour");
String greeting = localizedText.getString(LocaleConstant.LOCALE_enUS);
```

## Build Requirements

- Java 8 or higher
- No external dependencies required (self-contained)

## Thread Safety

The following components are thread-safe:
- `AnyData` class (uses `ConcurrentHashMap`)
- `DISCORD_MESSAGE_QUEUE` (uses `ConcurrentLinkedQueue`)

## License

This code maintains the original GNU General Public License v2 from the TrinityCore and MaNGOS projects.

## Notes

- The implementation is fully self-contained and does not depend on external frameworks
- All functionality has been adapted to Java idioms and best practices
- Type safety is enforced through Java generics where applicable
- The code follows standard Java naming conventions (camelCase for methods, UPPER_CASE for constants)
