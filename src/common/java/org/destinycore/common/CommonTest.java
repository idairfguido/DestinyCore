package org.destinycore.common;

import org.destinycore.common.Common.*;

/**
 * Simple test/example program for Common.java
 * Demonstrates the usage of various components.
 */
public class CommonTest {
    
    public static void main(String[] args) {
        System.out.println("=== DestinyCore Common Library Test ===\n");
        
        testTimeConstants();
        testAccountTypes();
        testLocaleConstants();
        testDiscordMessage();
        testLocalizedString();
        testAnyData();
        testUtilityMethods();
        
        System.out.println("\n=== All Tests Completed Successfully ===");
    }
    
    private static void testTimeConstants() {
        System.out.println("--- Time Constants ---");
        System.out.println("1 MINUTE = " + TimeConstants.MINUTE.getValue() + " seconds");
        System.out.println("1 HOUR = " + TimeConstants.HOUR.getValue() + " seconds");
        System.out.println("1 DAY = " + TimeConstants.DAY.getValue() + " seconds");
        System.out.println("1 WEEK = " + TimeConstants.WEEK.getValue() + " seconds");
        System.out.println();
    }
    
    private static void testAccountTypes() {
        System.out.println("--- Account Types ---");
        for (AccountTypes type : AccountTypes.values()) {
            System.out.println(type.name() + " = " + type.getValue());
        }
        
        AccountTypes admin = AccountTypes.fromValue(3);
        System.out.println("Value 3 corresponds to: " + admin.name());
        System.out.println();
    }
    
    private static void testLocaleConstants() {
        System.out.println("--- Locale Constants ---");
        
        // Test getLocaleByName
        LocaleConstant locale1 = LocaleConstant.getLocaleByName("frFR");
        System.out.println("Locale 'frFR': " + locale1.name() + " (value=" + locale1.getValue() + ")");
        
        LocaleConstant locale2 = LocaleConstant.getLocaleByName("ptBR");
        System.out.println("Locale 'ptBR': " + locale2.name() + " (value=" + locale2.getValue() + ")");
        
        LocaleConstant defaultLocale = LocaleConstant.getLocaleByName("unknown");
        System.out.println("Unknown locale defaults to: " + defaultLocale.name());
        
        // Display all locales
        System.out.println("\nAll available locales:");
        String[] localeNames = LocaleConstant.getLocaleNames();
        for (String name : localeNames) {
            System.out.print(name + " ");
        }
        System.out.println("\n");
    }
    
    private static void testDiscordMessage() {
        System.out.println("--- Discord Message ---");
        
        DiscordMessage msg = new DiscordMessage();
        msg.setChannel(DiscordMessageChannel.DISCORD_WORLD_A);
        msg.setMessage("Welcome to DestinyCore!");
        msg.setCharacterName("TestHero");
        msg.setGm(false);
        
        System.out.println(msg);
        
        // Add to queue
        Common.DISCORD_MESSAGE_QUEUE.offer(msg);
        System.out.println("Message added to queue. Queue size: " + Common.DISCORD_MESSAGE_QUEUE.size());
        
        // Poll from queue
        DiscordMessage retrieved = Common.DISCORD_MESSAGE_QUEUE.poll();
        System.out.println("Retrieved from queue: " + (retrieved != null ? retrieved.getMessage() : "null"));
        System.out.println();
    }
    
    private static void testLocalizedString() {
        System.out.println("--- Localized String ---");
        
        LocalizedString greeting = new LocalizedString();
        greeting.setString(LocaleConstant.LOCALE_enUS, "Hello");
        greeting.setString(LocaleConstant.LOCALE_frFR, "Bonjour");
        greeting.setString(LocaleConstant.LOCALE_deDE, "Hallo");
        greeting.setString(LocaleConstant.LOCALE_esES, "Hola");
        greeting.setString(LocaleConstant.LOCALE_ptBR, "Olá");
        
        System.out.println("English: " + greeting.getString(LocaleConstant.LOCALE_enUS));
        System.out.println("French: " + greeting.getString(LocaleConstant.LOCALE_frFR));
        System.out.println("German: " + greeting.getString(LocaleConstant.LOCALE_deDE));
        System.out.println("Spanish: " + greeting.getString(LocaleConstant.LOCALE_esES));
        System.out.println("Portuguese: " + greeting.getString(LocaleConstant.LOCALE_ptBR));
        System.out.println();
    }
    
    private static void testAnyData() {
        System.out.println("--- AnyData Class ---");
        
        AnyData data = new AnyData();
        
        // Test basic set/get
        data.set("playerName", "Hero");
        data.set("playerLevel", 42);
        data.set("playerHealth", 100.5);
        data.set("isOnline", true);
        
        System.out.println("Player Name: " + data.getValue("playerName", "Unknown"));
        System.out.println("Player Level: " + data.getValue("playerLevel", 0));
        System.out.println("Player Health: " + data.getValue("playerHealth", 0.0));
        System.out.println("Is Online: " + data.getValue("isOnline", false));
        
        // Test exist
        System.out.println("Key 'playerName' exists: " + data.exist("playerName"));
        System.out.println("Key 'playerMana' exists: " + data.exist("playerMana"));
        
        // Test increment
        data.set("killCount", 10);
        System.out.println("Initial kill count: " + data.getValue("killCount", 0));
        data.increment("killCount");
        System.out.println("After increment: " + data.getValue("killCount", 0));
        data.increment("killCount", 5);
        System.out.println("After increment by 5: " + data.getValue("killCount", 0));
        
        // Test incrementOrProcCounter
        data.set("counter", 0);
        boolean result1 = data.incrementOrProcCounter("counter", 3);
        System.out.println("Counter < 3: " + result1 + ", value: " + data.getValue("counter", -1));
        
        result1 = data.incrementOrProcCounter("counter", 3);
        System.out.println("Counter < 3: " + result1 + ", value: " + data.getValue("counter", -1));
        
        result1 = data.incrementOrProcCounter("counter", 3);
        System.out.println("Counter >= 3: " + result1 + ", value (should be removed): " + data.getValue("counter", -1));
        
        // Test size and clear
        System.out.println("Data size: " + data.size());
        data.clear();
        System.out.println("After clear, size: " + data.size());
        System.out.println();
    }
    
    private static void testUtilityMethods() {
        System.out.println("--- Utility Methods ---");
        
        // Test parseLong
        long value1 = Common.parseLong("12345");
        System.out.println("parseLong('12345') = " + value1);
        
        // Test parseUnsignedLong
        long value2 = Common.parseUnsignedLong("9876543210");
        System.out.println("parseUnsignedLong('9876543210') = " + value2);
        
        // Test edge cases
        System.out.println("parseLong('') = " + Common.parseLong(""));
        System.out.println("parseLong('   ') = " + Common.parseLong("   "));
        System.out.println("parseLong(null) = " + Common.parseLong(null));
        System.out.println("parseUnsignedLong('invalid') = " + Common.parseUnsignedLong("invalid"));
        
        // Test stringize
        String str = Common.stringize(42);
        System.out.println("stringize(42) = '" + str + "'");
        System.out.println("stringize(null) = '" + Common.stringize(null) + "'");
        
        // Test constants
        System.out.println("M_PI = " + Common.M_PI);
        System.out.println("MAX_QUERY_LEN = " + Common.MAX_QUERY_LEN);
        System.out.println("DEFAULT_LOCALE = " + Common.DEFAULT_LOCALE.name());
        System.out.println();
    }
}
