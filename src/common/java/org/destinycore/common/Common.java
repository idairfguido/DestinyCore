/*
 * Copyright (C) 2008-2018 TrinityCore <https://www.trinitycore.org/>
 * Copyright (C) 2005-2009 MaNGOS <http://getmangos.com/>
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.destinycore.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Common utilities and constants for DestinyCore.
 * This class provides enumerations, data structures, and utility methods
 * commonly used throughout the application.
 */
public final class Common {

    private Common() {
        // Utility class, prevent instantiation
    }

    // ==================== Time Constants ====================

    /**
     * Time constants for common time conversions.
     */
    public enum TimeConstants {
        MINUTE(60),
        HOUR(60 * 60),
        DAY(60 * 60 * 24),
        WEEK(60 * 60 * 24 * 7),
        MONTH(60 * 60 * 24 * 30),
        YEAR(60 * 60 * 24 * 30 * 12),
        IN_MILLISECONDS(1000);

        private final int value;

        TimeConstants(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // ==================== Account Types ====================

    /**
     * Account security levels.
     */
    public enum AccountTypes {
        SEC_PLAYER(0),
        SEC_MODERATOR(1),
        SEC_GAMEMASTER(2),
        SEC_ADMINISTRATOR(3),
        SEC_CONSOLE(4); // Must be always last, accounts must have less security level

        private final int value;

        AccountTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static AccountTypes fromValue(int value) {
            for (AccountTypes type : values()) {
                if (type.value == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid AccountType value: " + value);
        }
    }

    // ==================== Locale Constants ====================

    /**
     * Locale constants for internationalization.
     */
    public enum LocaleConstant {
        LOCALE_enUS(0, "enUS"),
        LOCALE_koKR(1, "koKR"),
        LOCALE_frFR(2, "frFR"),
        LOCALE_deDE(3, "deDE"),
        LOCALE_zhCN(4, "zhCN"),
        LOCALE_zhTW(5, "zhTW"),
        LOCALE_esES(6, "esES"),
        LOCALE_esMX(7, "esMX"),
        LOCALE_ruRU(8, "ruRU"),
        LOCALE_none(9, "none"),
        LOCALE_ptBR(10, "ptBR"),
        LOCALE_itIT(11, "itIT");

        private final int value;
        private final String name;

        LocaleConstant(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        /**
         * Get LocaleConstant by name.
         * @param name The locale name (e.g., "enUS", "frFR")
         * @return The corresponding LocaleConstant, or LOCALE_enUS if not found
         */
        public static LocaleConstant getLocaleByName(String name) {
            if (name == null) {
                return LOCALE_enUS;
            }
            for (LocaleConstant locale : values()) {
                if (locale.name.equalsIgnoreCase(name)) {
                    return locale;
                }
            }
            return LOCALE_enUS; // Default to enUS (including enGB case)
        }

        /**
         * Get LocaleConstant by value.
         * @param value The locale value
         * @return The corresponding LocaleConstant
         */
        public static LocaleConstant fromValue(int value) {
            for (LocaleConstant locale : values()) {
                if (locale.value == value) {
                    return locale;
                }
            }
            throw new IllegalArgumentException("Invalid LocaleConstant value: " + value);
        }

        /**
         * Get all locale names.
         * @return Array of locale names
         */
        public static String[] getLocaleNames() {
            LocaleConstant[] values = values();
            String[] names = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                names[i] = values[i].name;
            }
            return names;
        }
    }

    // ==================== Discord Message Channel ====================

    /**
     * Discord message channel types.
     */
    public enum DiscordMessageChannel {
        DISCORD_WORLD_A(1),
        DISCORD_WORLD_H(2),
        DISCORD_TICKET(3);

        private final int value;

        DiscordMessageChannel(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static DiscordMessageChannel fromValue(int value) {
            for (DiscordMessageChannel channel : values()) {
                if (channel.value == value) {
                    return channel;
                }
            }
            throw new IllegalArgumentException("Invalid DiscordMessageChannel value: " + value);
        }
    }

    // ==================== Discord Message ====================

    /**
     * Represents a Discord message with channel information.
     */
    public static class DiscordMessage {
        private DiscordMessageChannel channel;
        private String message;
        
        // Channel specific
        private String characterName;
        private boolean isGm;

        public DiscordMessage() {
        }

        public DiscordMessage(DiscordMessageChannel channel, String message) {
            this.channel = channel;
            this.message = message;
        }

        public DiscordMessageChannel getChannel() {
            return channel;
        }

        public void setChannel(DiscordMessageChannel channel) {
            this.channel = channel;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCharacterName() {
            return characterName;
        }

        public void setCharacterName(String characterName) {
            this.characterName = characterName;
        }

        public boolean isGm() {
            return isGm;
        }

        public void setGm(boolean gm) {
            isGm = gm;
        }

        @Override
        public String toString() {
            return "DiscordMessage{" +
                    "channel=" + channel +
                    ", message='" + message + '\'' +
                    ", characterName='" + characterName + '\'' +
                    ", isGm=" + isGm +
                    '}';
        }
    }

    // ==================== Discord Message Queue ====================

    /**
     * Thread-safe queue for Discord messages.
     */
    public static final ConcurrentLinkedQueue<DiscordMessage> DISCORD_MESSAGE_QUEUE = 
            new ConcurrentLinkedQueue<>();

    // ==================== Localized String ====================

    /**
     * Represents a string localized in multiple languages.
     */
    public static class LocalizedString {
        private final String[] strings;
        private static final int TOTAL_LOCALES = LocaleConstant.values().length;

        public LocalizedString() {
            this.strings = new String[TOTAL_LOCALES];
        }

        /**
         * Set localized string for a specific locale.
         * @param locale The locale
         * @param value The localized string
         */
        public void setString(LocaleConstant locale, String value) {
            if (locale != null && locale.getValue() < TOTAL_LOCALES) {
                strings[locale.getValue()] = value;
            }
        }

        /**
         * Get localized string for a specific locale.
         * @param locale The locale
         * @return The localized string, or null if not set
         */
        public String getString(LocaleConstant locale) {
            if (locale != null && locale.getValue() < TOTAL_LOCALES) {
                return strings[locale.getValue()];
            }
            return null;
        }

        /**
         * Get localized string by index.
         * @param index The locale index
         * @return The localized string, or null if not set
         */
        public String getString(int index) {
            if (index >= 0 && index < TOTAL_LOCALES) {
                return strings[index];
            }
            return null;
        }

        /**
         * Get all localized strings.
         * @return Array of localized strings
         */
        public String[] getAllStrings() {
            return strings.clone();
        }
    }

    // ==================== Constants ====================

    public static final int OLD_TOTAL_LOCALES = 9;
    public static final LocaleConstant DEFAULT_LOCALE = LocaleConstant.LOCALE_enUS;
    public static final int MAX_LOCALES = 11;
    public static final double M_PI = 3.14159265358979323846;
    public static final int MAX_QUERY_LEN = 32 * 1024;

    // ==================== Utility Methods ====================

    /**
     * Convert string to unsigned long (equivalent to atoul).
     * @param str The string to convert
     * @return The unsigned long value
     */
    public static long parseUnsignedLong(String str) {
        if (str == null) {
            return 0L;
        }
        try {
            String trimmed = str.trim();
            if (trimmed.isEmpty()) {
                return 0L;
            }
            return Long.parseUnsignedLong(trimmed);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * Convert string to long (equivalent to atoll).
     * @param str The string to convert
     * @return The long value
     */
    public static long parseLong(String str) {
        if (str == null) {
            return 0L;
        }
        try {
            String trimmed = str.trim();
            if (trimmed.isEmpty()) {
                return 0L;
            }
            return Long.parseLong(trimmed);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * Stringize a value (equivalent to STRINGIZE macro).
     * @param value The value to convert to string
     * @return String representation
     */
    public static String stringize(Object value) {
        return String.valueOf(value);
    }

    // ==================== AnyData Class ====================

    /**
     * Generic data storage using type-safe HashMap.
     * This class provides a flexible way to store and retrieve typed data
     * using string keys, similar to boost::any functionality.
     */
    public static class AnyData {
        private final Map<String, Object> dataMap;

        public AnyData() {
            this.dataMap = new ConcurrentHashMap<>();
        }

        /**
         * Set a value for the given key.
         * @param key The key
         * @param value The value to store
         * @param <T> The type of value
         */
        public <T> void set(String key, T value) {
            if (key != null) {
                dataMap.put(key, value);
            }
        }

        /**
         * Get a value for the given key with a default value.
         * @param key The key
         * @param defaultValue The default value if key doesn't exist
         * @param <T> The type of value
         * @return The stored value or default value
         */
        @SuppressWarnings("unchecked")
        public <T> T getValue(String key, T defaultValue) {
            if (key == null) {
                return defaultValue;
            }
            Object value = dataMap.get(key);
            if (value == null) {
                return defaultValue;
            }
            try {
                return (T) value;
            } catch (ClassCastException e) {
                return defaultValue;
            }
        }

        /**
         * Get a value for the given key.
         * @param key The key
         * @param <T> The type of value
         * @return The stored value or null
         */
        public <T> T getValue(String key) {
            return getValue(key, null);
        }

        /**
         * Check if a key exists.
         * @param key The key to check
         * @return true if key exists, false otherwise
         */
        public boolean exist(String key) {
            return key != null && dataMap.containsKey(key);
        }

        /**
         * Remove a key and its value.
         * @param key The key to remove
         */
        public void remove(String key) {
            if (key != null) {
                dataMap.remove(key);
            }
        }

        /**
         * Increment an integer value by a given amount.
         * @param key The key
         * @param increment The amount to increment
         * @return The new value after increment
         */
        public int increment(String key, int increment) {
            Integer currentValue = getValue(key, 0);
            int newValue = currentValue + increment;
            set(key, newValue);
            return newValue;
        }

        /**
         * Increment an integer value by 1.
         * @param key The key
         * @return The new value after increment
         */
        public int increment(String key) {
            return increment(key, 1);
        }

        /**
         * Increment a counter or process if it reaches max value.
         * @param key The key
         * @param maxVal The maximum value
         * @param increment The amount to increment
         * @return true if counter reached max value (and was removed), false otherwise
         */
        public synchronized boolean incrementOrProcCounter(String key, int maxVal, int increment) {
            int newValue = increment(key, increment);
            if (newValue < maxVal) {
                return false;
            }
            remove(key);
            return true;
        }

        /**
         * Increment a counter or process if it reaches max value (increment by 1).
         * @param key The key
         * @param maxVal The maximum value
         * @return true if counter reached max value (and was removed), false otherwise
         */
        public boolean incrementOrProcCounter(String key, int maxVal) {
            return incrementOrProcCounter(key, maxVal, 1);
        }

        /**
         * Clear all data.
         */
        public void clear() {
            dataMap.clear();
        }

        /**
         * Get the number of stored entries.
         * @return The size of the data map
         */
        public int size() {
            return dataMap.size();
        }

        /**
         * Check if the data map is empty.
         * @return true if empty, false otherwise
         */
        public boolean isEmpty() {
            return dataMap.isEmpty();
        }

        /**
         * Get all keys.
         * @return Set of all keys
         */
        public java.util.Set<String> keySet() {
            return new java.util.HashSet<>(dataMap.keySet());
        }
    }
}
