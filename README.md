# Spin RDF

Welcome to SpinRDF, an implement of the SPIN API for [Apache Jena](http://jena/apache.org).

For more information on SPIN, please see http://spinrdf.org/.

The project welcomes contributions, large and small, from anyone.

For contribution, please see "[Guide to Contributing](.github/CONTRIBUTING.md).

The project is licensed with the [Apache License](LICENSE).

## Build and install

The project is managed by the POM file (`pom.xml`), so simply build with Maven:
```
git clone git@github.com:spinrdf/spinrdf.git
cd spinrdf
mvn verify
```
and the resulting jar is in the `./target` directory.

To put into the local Maven repository:
```
mvn install
```

## (Example) Add spinrdf to jena-fuseki on Docker

To add spinrdf to a fuseki server as custom code, put the jar to `$FUSEKI_BASE` directory.

For example, to run a fuseki server with spinrdf on Docker:
```
docker run --rm -p 3030:3030 -e ADMIN_PASSWORD=pw123 -v "$(pwd)"/spinrdf.jar:/fuseki/spinrdf.jar stain/jena-fuseki:3.11.0
```

See the [`fuseki-server`](https://github.com/apache/jena/blob/main/jena-fuseki2/apache-jena-fuseki/fuseki-server) script for more information.

## Original code

This git repository started with original material taken from the
following publicly available files:

http://topquadrant.com/repository/spin/org/topbraid/spin/2.0.0/spin-2.0.0-distribution.zip
http://topquadrant.com/repository/spin/org/topbraid/spin/2.0.0/spin-2.0.0/pom

Downloaded December 11th, 2016.

with the following changes made by TopQuadrant Inc:

 * Add license headers to all files.
 * Repackage under org.spinrdf
 * Organise into a maven project.

These original materials are copyright TopQuadrant Inc and licensed with the
[Apache Software License](https://www.apache.org/licenses/LICENSE-2.0).
