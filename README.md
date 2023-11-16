# ATM with Apache Thrift
### Build and Run Process
The build process happens automatically by running ```run.sh```.
<br>
The list below describes how it builds and runs the project:
1. <b><i>(This is not configured by the script, but it is necessary):</i></b> Configure a ```./thrift/services.thrift``` file with service definitions and namespaces of the generated code.
2. Create a docker volume.
3. Use the Apache Thrift Docker image as a code generator with the ```./thrift/services.thrift``` file as an argument. Also, use the docker volume previously created for this container.
4. Copy the generated code files from the container to the ```./thrift/``` directory.
5. Remove the ```gen-py``` and ```gen-java``` folders from ```./thrift/``` directory.
6. Run docker-compose.
<br>

### How to run
  1. ```chmod +x run.sh```
  2. ```sudo ./run.sh```: Generates Thrift code in client and server directories and runs docker-compose.
  3. ```sudo docker start -a -i apachethriftatm-client-1``` : Runs the Thrift client interactively with a simple CLI interface.
