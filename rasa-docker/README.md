# Rasa Docker

This is a Docker image including Rasa Core and Rasa NLU products.

**Sample Data** has been copied from:

* [How to handle multiple intents per input using Rasa NLU TensorFlow pipeline](https://blog.rasa.com/how-to-handle-multiple-intents-per-input-using-rasa-nlu-tensorflow-pipeline/)
* [GitHub: RasaHQ/tutorial-tf-pipeline](https://github.com/RasaHQ/tutorial-tf-pipeline)

## Building the image

Before using the service, it's required to build the Docker Image. Also, everytime that the `Dockerfile` file is modified or the contents of the data folder changes, a new build is required. 

```bash
$ docker build --tag keensoft/rasa_nlu .
```

The same tag `keensoft/rasa_nlu` can be used for every building process or a new tag can be used for different versions, p. e. `keensoft\rasa_nlu:0.8.0` 

## Running the container

This image has been built from [official Rasa NLU Docker Image](https://hub.docker.com/r/rasa/rasa_nlu/), so also the [REST API](https://rasa.com/docs/nlu/http/) will be available for testing at HTTP port 5000. 

To run the container, use following command.

```bash
$ docker run -p 5000:5000 keensoft/rasa_nlu
```

## Running the ChatBot

In order to test the ChatBot, default command line interpreter can be accessed from inside Docker Container.

You need to identify the *container id* of your running Rasa NLU container.

```bash
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
f0240da37c1e        keensoft/rasa_nlu   "./entrypoint.sh sta…"   4 seconds ago       Up 3 seconds        0.0.0.0:5000->5000/tcp   epic_engelbart
```

After that, you can access the container by SSH and use Rasa CORE by command line.

```
$ docker exec -it f0240da37c1e /bin/bash

$ python -m rasa_core.run -d models/current/dialogue -u models/current/nlu_model/default/model_20180910-142902

Bot loaded. Type a message and press enter:
```

*Models* folder is generated when building Docker Image, so the name for the training data folder (`model_20180910-142902`) may be different across the installations.


# Creating a new model

There are different resources to be modified in order to produce a new ChatBot model:

* `domain.yml`, including **intents**, **templates** and **actions** catalog
* `data\nlu_data.md`, including *sample* sentences for the **intents**
* `data\stories.md`, including **intents** pathways for the dialog

After modifying any of these resources, you'll need to rebuild the Docker Image.
