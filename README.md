![App Screenshot](https://i.imgur.com/yA0nYUs.png)

# Cache

Its a Java framework that handles data in the memory.


## Features

- Individual expiration time


## Future features

- Events and Listeners
- Better handling complexity

## Code

![App Screenshot](https://i.imgur.com/HHAvmTR.png)
The Builder class, used to instance the framework and build a cache.


## Usage Example

```java
public class CacheExample {

    public static void main(String[] args) {
        // Create a cache with an expiration time of 5 seconds (5000 milliseconds)
        MemoryCache<String, String> cache = new CacheBuilder<String, String>()
                .expirationTime(5000)
                .build();

        // Add an entry to the cache
        cache.put("key1", "value1");

        // Retrieve the entry from the cache
        try {
            String value = cache.get("key1");
            System.out.println("Retrieved value: " + value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Wait for 6 seconds to allow the entry to expire
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Try to retrieve the entry again after expiration
        try {
            String value = cache.get("key1");
            System.out.println("Retrieved value: " + value);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Expected to catch an exception since the entry has expired
        }
    }
}
```

