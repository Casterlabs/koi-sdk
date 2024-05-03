# koi-sdk / Java

Requires Java 11 or greater.

## Dependency Information

We use GitHub packages + our own resolver for our deployment and hosting.

<details>
  <summary>Maven</summary>
  
  ```xml
        <repositories>
            <repository>
                <id>casterlabs-maven</id>
                <url>https://repo.casterlabs.co/maven</url>
            </repository>
        </repositories>
  ```
  ```xml
        <dependency>
            <groupId>co.casterlabs</groupId>
            <artifactId>koi-sdk</artifactId>
            <version>VERSION_OR_HASH</version>
        </dependency>
  ```
</details>

## Optional dependencies

<details>
  <summary>co.casterlabs.EmojiApi:Index</summary>
  
  Used to deserialize information from an `EmojiFragment`. You can safely omit this dependency if you only use the `html` or `raw` values. This is required if you plan on creating your own custom events that include Emojis.
  ```xml
        <repositories>
            <repository>
                <id>jitpack.io</id>
                <url>https://jitpack.io</url>
            </repository>
        </repositories>
  ```
  ```xml
        <dependency>
            <groupId>co.casterlabs.EmojiApi</groupId>
            <artifactId>Index</artifactId>
            <version>3.5.2</version>
        </dependency>
  ```
</details>

<details>
  <summary>org.unbescape:unbescape</summary>
  
  Used to safely generate the `html` fields in `ChatFragment`s. This is only required if you plan on creating your own custom events.
  ```xml
        <dependency>
            <groupId>org.unbescape</groupId>
            <artifactId>unbescape</artifactId>
            <version>1.1.6.RELEASE</version>
        </dependency>
  ```
</details>
