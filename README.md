# ADIF parser and generator

[![Build Status](https://travis-ci.org/MarSik/adif.svg?branch=master)](https://travis-ci.org/MarSik/adif)

This library is meant to be used for reading and writing of ADIF data (ADI format only for now, although the XML based ADIX is almost the same).

The current format I am targeting is ADIF 3.1.0 as described at the [ADIF site](http://www.adif.org/).

This code is released under the Apache License 2.0

## Adding this library to your project

This library is released on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22adif%22%20g%3A%22org.marsik.ham%22) so just add the dependency to your `pom.xml`:

```
<dependency>
    <groupId>org.marsik.ham</groupId>
    <artifactId>adif</artifactId>
    <version>1.1</version>
</dependency>
```

The other option is to use [Jitpack.io](https://jitpack.io/) by configuring a repository and dependency in the following way:

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```
<dependency>
    <groupId>com.github.MarSik</groupId>
    <artifactId>adif</artifactId>
    <version>1.1</version>
</dependency>
```

## Reading ADIF files

```
import org.marsik.ham.adif.Adif3
import org.marsik.ham.adif.AdiReader

AdiReader reader = new AdiReader();
BufferedReader buffInput = ...; // get buffered reader from stream or file
Optional<Adif3> adif = reader.read(buffInput);
```

Note: For reading Logbook of the World adi files enable the quirks mode 
with `reader.setQuirksMode(true);`.  The quirks mode accepts submodes (i.e PSK31)
in the modes fields and translates them to the appropriate mode (i.e. PSK).

## Writing ADIF files

```
import org.marsik.ham.adif.Adif3Record
import org.marsik.ham.adif.AdifHeader
import org.marsik.ham.adif.AdiWriter

AdiWriter writer = new AdiWriter();
writer.append(/* AdifHeader */)
for Adif3Record record: /* some data source */ {
  writer.append(record);
}
writer.toString() // -> to some output
```
