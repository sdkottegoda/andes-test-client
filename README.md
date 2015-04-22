# Andes Test Client

Performance testing client for WSO2 Message Broker.

## Build

```
 mvn clean install
```

## Run

Extract andes-test-client-\<VERSION\>-pack.zip in `target/` directory and execute 
```
sh client.sh
```

## Configure Test Cases

Use `conf/client.yaml` to configure test cases

## Results

After running a test go to `logs/metrics/` directory to view the metrics csv output.
To generate graphs of the results use `result-viewer.html` 
