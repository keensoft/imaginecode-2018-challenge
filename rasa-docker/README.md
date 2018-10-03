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

# Telegram integration

You first need to retrieve some credentials, once you have them you can either attach the input channel running the provided rasa_core.run script, or you can attach it in your own code.

## Getting Credentials

How to get the Telegram credentials: You need to set up a Telegram bot.

* To create the bot, go to: https://web.telegram.org/#/im?p=@BotFather, enter /newbot and follow the instructions.
* At the end you should get your access_token and the username you set will be your verify.

In this repository following parameters are pre-configured in `credentials.yml` file:

```
  access_token: "588830968:AAFqcre3RQmEEZ-pwU-9DLNiBlykAWGKBTM"
  verify: "rasademo_bot"
```

## Exposing Docker Rasa Container to Internet 

Telegram API must communicate with your Docker container by using a public IP, as it's an Internet service.

You can use https://ngrok.com/ to create a local webhook from your machine that is Publicly available on the internet so you can use it with Telegram.

The command to run a ngrok instance for port 5002 for example would be:

```bash
$ ngrok httpd 5002

Session Status                online
Account                       Angel Borroy (Plan: Free)
Version                       2.2.8
Region                        United States (us)
Web Interface                 http://127.0.0.1:4040
Forwarding                    http://5a9d299a.ngrok.io -> localhost:5002
Forwarding                    https://5a9d299a.ngrok.io -> localhost:5002
```

>> Ngrok is only needed if you don’t have a public IP and are testing locally


## Rebuilding the image

Once `ngrok` has been started, public SSL address must be updated in `credentials.yml` file:

```
  webhook_url: "https://5a9d299a.ngrok.io/webhooks/telegram/webhook"
```

Next step is to build again the image, as we need to include credentials details.

```
$ docker build --tag keensoft/rasa_nlu .
```

## Running the ChatBot integrated with Telegram API

And finally your ChatBot will be available for Telegram by running the container.

```
$ docker run -p 5000:5000 -p 5002:5002 keensoft/rasa_nlu
```

## Testing this project with your credentials

* Create your own credentials and bot name at https://web.telegram.org/#/im?p=@BotFather
* Update `credentials.yml` file with your `access_token` and `verify` (this is the bot name)
* Start `ngrok` program and find Fordwaring to HTTPs URL
* Update `credentials.yml` file with the URL at `webhook_url` including `/webhooks/telegram/webhook` context path
* Rebuild your Docker image
* Run your Docker container mapping port 5002 