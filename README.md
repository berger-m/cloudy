# cloudy
`cloudy` is the top level project of a Java Spring web app. The project has many parts, and each part is in its own subproject. Ordinarily those subprojects would be in isolated git submodules, however for the purposes of simplicity they are all in the `cloudy` repository.
New submodules can be added by creating a gradle project in the 'submodules' directory where they will be automatically discovered.  

## Compile/Test/Run Instructions
### Linux
```
cd <PATH/TO/CLONE/DIR/>cloudy
./gradlew clean build
```
This will build and run the automated tests.  
To launch the cloudy webapp execute:

```
./gradlew :ui-tl-cloudy:bootRun
```

The app will be listening for requests on `http://localhost:8080/climate/summary`

### Windows
```
cd <PATH/TO/CLONE/DIR/>cloudy
gradlew.bat clean build
gradlew.bat :ui-tl-cloudy:bootRun
```

## Subprojects
At the moment there are two subprojects of `cloudy`:
* [ds-rdb-cloudy](subprojects/ds-rdb-cloudy/README.md)
* [ui-tl-cloudy](subprojects/ui-tl-cloudy/README.md)
  
## Subproject Naming Convention
* **ds**- prefix for a 'DataStore', followed by the type of data store (rdb-|noSql-|fs-|cache-), followed by the name of the data store (for rdb the name would be the schema, for filesystems it might be the directory, and so on)  
* **rdb**- prefix for the representation of the schema in the 'RelationalDataBase', followed by data store name (oracle-|postgreSQL-)  
* **ui**- prefix for presenting the schema in the 'RelationalDataBase' to a human, followed by the type of framework (tl-|jsf-)
* **api**- prefix for anything that will be externally facing, followed by the type of the service/protocol (rest-|soap-|...), followed by data store name  
* **noSql**- prefix for the representation of the schema in the NoSQL database, followed by data store name  

The first subprojects created were for the 'sampleHR' schema: 'ds-rdb-sampleHR', 'api-rest-sampleHR'

## Project Utils

### CheckStyle

The default configuration file (`config/checkstyle/checkstyle.xml`) came from [link to source](https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/main/resources/sun_checks.xml)

### SpotBugs

From time to time SpotBugs will mistakenly identify perfectly valid code as an error. One way to resolve this is to use the 
`@SuppressFBWarnings(value="SPOT_BUGS_CODE",justification="Something")` (annotation from package `edu.umd.cs.findbugs.annotations.SuppressFBWarnings`)   
The `SPOT_BUGS_CODE` can be determined by looking up the message on [this SpotBugs page](https://spotbugs.readthedocs.io/en/stable/bugDescriptions.html)