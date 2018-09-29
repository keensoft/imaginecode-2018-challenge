# CMIS Client

CMIS Client for Alfresco Repository using Browser Binding 1.1.0

## Requirements

* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
* [Maven 3.2+](https://maven.apache.org/download.cgi)

## Configuration

Default configuration available at `src/main/resources/application.properties`

```
cmis.server=http://localhost
cmis.user.name=admin
cmis.user.pass=admin

alfresco.version=6.0.7

logging.level.es.keensoft=DEBUG
```

## Building

Source code can be compiled by using default Maven command.

```
$ mvn clean package
```

Command Line Executable JAR artifact `cmis-client-1.1.0.jar` will be created in `target` folder.

>> Every change in the source code requires a new compilation


## Running

Default program includes three actions:

* `upload`: This action uploads documents from `src/main/resources/openlibrary` folder to Alfresco Repository including `ksic:Thesis` values (author, year, topic, area)

```bash
$ java -jar target/cmis-client-1.1.0.jar --action=upload
```

* `dump`: This action dumps in the log file (console by default) the information of every `ksic:Thesis` document in Alfresco Repository 

```bash
$ java -jar target/cmis-client-1.1.0.jar --action=dump
```

* `search`: This action dumps in the log file (console by default) the information of a search criteria over `ksic:Thesis` documents in Alfresco Repository

```bash
$ java -jar target/cmis-client-1.1.0.jar --action=search --criteria="ksic:Area like 'Cryptography'"
$ java -jar target/cmis-client-1.1.0.jar --action=search --criteria="CONTAINS('intelligence')"
```

## Suggested resources

* Sample codes for CMIS Client: [https://chemistry.apache.org/docs/cmis-samples/index.html](https://chemistry.apache.org/docs/cmis-samples/index.html)
* Sample code for Spring Boot application: [https://spring.io/guides/gs/spring-boot/](https://spring.io/guides/gs/spring-boot/)
* Concepts about Alfresco Content Model: [https://docs.alfresco.com/community/concepts/metadata-model-define.html](https://docs.alfresco.com/community/concepts/metadata-model-define.html)

# The Mug Contest

Participants can solve an additional *mini-puzzle*. The winner will be the first *pull-request* to this repository with the right implementation for `es.keensoft.alfresco.mug.MugContest.getPdfDocuments()` method according to requirements set in JUnit Test available at `es.keensoft.alfresco.mug.test.TestMugContest.getPdfDocuments()`.

**NOTE** Initial scenario for this test is an Alfresco Repository with `upload` action executed once!

