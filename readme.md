## sample sbt project for neo4j unmanaged extensions in scala

For people who like scala a lot.

### add this to your neo4j-server.properties

```
org.neo4j.server.thirdparty_jaxrs_classes=unmanagedscala=/unmanagedscala
```

### if you're using this example exactly, you'll want...

to copy the lift-json jar and the paranamer.jar files to the `neo4j/lib/`

### then you'll be able to get to your service at (assuming defaults):

```
curl -X GET http://localhost:7474/unmanagedscala/service/fof/300

{"300":23201}
```

`fofallnodes` gives a count of all friends of friends for each node... try it (if your graph isn't too big). :)
