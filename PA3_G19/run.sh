#!/bin/sh

gnome-terminal --tab -- java -cp target/PA3_G19-1.0-SNAPSHOT.jar Client.PClient
gnome-terminal --tab -- java -cp target/PA3_G19-1.0-SNAPSHOT.jar Monitor.PMonitor
gnome-terminal --tab -- java -cp target/PA3_G19-1.0-SNAPSHOT.jar LB.PLoadBalancer
gnome-terminal --tab -- java -cp target/PA3_G19-1.0-SNAPSHOT.jar Server.PServer

