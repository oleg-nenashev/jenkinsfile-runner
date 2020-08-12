Pipeline Library demo
=====================

This demo shows execution of a simple Declarative Pipeline,
powered by the Jenkinsfile Runner vanilla package.

## Running in Docker

```
docker run --rm -v $(pwd)/Jenkinsfile:/workspace/Jenkinsfile jenkins4eval/jenkinsfile-runner:1.0-beta-11
```

## Running (without Docker)

Once Jenkinsfile Runner is built locally, the demo can be launched as...

```bash
java -jar ../../app/target/jenkinsfile-runner-standalone.jar -p ../../vanilla-package/target/plugins/ -w ../../vanilla-package/target/war/jenkins.war -f . 
```

## Profiling

JAVA_OPTS=-XX:StartFlightRecording=disk=true,dumponexit=true,filename=recording.jfr,maxsize=1024m,maxage=1d,settings=profile,path-to-gc-roots=true ../../app/target/appassembler/bin/jenkinsfile-runner -p ../../vanilla-package/target/plugins/ -w ../../vanilla-package/target/war/jenkins.war -f . 
