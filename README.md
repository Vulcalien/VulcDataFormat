# VulcDataFormat Java

This is a Java library for the [VDF file format](https://github.com/Vulcalien/VulcDataFormat-Specification).

Using this library, it's possible to read, create and edit VDF files, both in textual or binary format.

## Basic usage example
Let's say we have a file: `data.vdf` (textual VDF)
```vdf
{
    boolean "isRaining": false,

    byte "temperature": 70,

    float "PI": 3.14,

    byte[] "favourite_bytes": [
        0, -127, 128
    ]
}
```

### Parse a file
First of all, we should read the VDF object written in that file.
```java
VDFObject obj = new VDFObject();
try(Reader reader = new FileReader("./data.vdf")) {
    obj.parse(reader);
}
```

### Get a value
Now that we have the object, we can get a value.
```java
boolean isRaining = obj.getBoolean("isRaining");
```

### Add a value
If we want to add a new value, it's not harder than getting one.
```java
obj.setString("name", "John");
```

## Who may find this library useful
VDF is very similar to JSON, but it supports types. Also, this library can be used to generate Binary VDF files.\
This library is perfect for programmers who need to store both small and large amounts of data in an efficient and easy way.

## Useful VDF format features
The format supports all Java types, arrays of them and two data structures, `object` and `list`. The binary format is very useful, since it's faster to read and write, and it occupies much less memory.

VDF specification (both binary and textual formats) can be found [here](https://github.com/Vulcalien/VulcDataFormat-Specification).
