#!/bin/sh

java -cp target/PA3_G19-1.0-SNAPSHOT.jar Client.PClient & 
java -cp target/PA3_G19-1.0-SNAPSHOT.jar Monitor.PMonitor & 
java -cp target/PA3_G19-1.0-SNAPSHOT.jar LB.PLoadBalancer & 
java -cp target/PA3_G19-1.0-SNAPSHOT.jar Server.PServer & 

