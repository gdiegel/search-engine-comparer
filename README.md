# search-engine-comparer

## Usage

```
# Check out project
git clone git@github.com:gdiegel/search-engine-comparer.git
cd search-engine-comparer
# Build project
mvn package
# Execute artifact
./target/search-engine-comparer-1.0.0-SNAPSHOT.jar
```

The search terms default to `.net` and `java`. They are configured as external Spring configuration values in `application.yaml`
and can be overridden via the usual Spring mechanisms, e.g. via command line argument:

```
./target/search-engine-comparer-1.0.0-SNAPSHOT.jar --search.terms=apple,banana
```

## Adding a search engine

To add another search engine follow these steps:

* Add configuration values to `application.yaml`
* Add a provider by extending `ProviderBase` and implementing the `SearchProvider` interface
* Add a search result representation by implementing `SearchResult`. This is necessary to map the response of the search API.

## Requirements

* Maven 3.x.x
* JDK >= 16
