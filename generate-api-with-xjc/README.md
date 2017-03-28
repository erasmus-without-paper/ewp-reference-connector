## Generate library for EWP Connector API

This is a maven project for generating a java libray (JAR) for the EWP Connector API.

## Build and install library
```
mvn clean install
```

## Note
This library includes stable APIs and APIs still in progress.
When an API gets stable, this project will be updated to use schema files directly in github.

## Included APIs
* Discovery Manifest API `stable-v4`
* Echo API `stable-v1`
* Registry API `stable-v1`

* Institutions API `v0.5.0`
* Organization Units API `v0.3.0`
* Courses API `v0.3.0`
* Simple Course Replication API `v1.0.0-rc4`
* Interinstitutional Agreements API `v0.3.0`
* Interinstitutional Agreement CNR API `v0.1.1`
* Outgoing Mobilities API `v0.2.0`
* Outgoing Mobility CNR API `v0.1.0`
* Transcripts of Records API `v0.1.0`
