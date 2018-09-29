This is the **Starter Challenge** for [ImagineCode 2018](https://imaginecode.org/), hosted in EINA Unizar from September 28th to 30th.

# Training Chatbots with Content

In May 2018, Google demostrated how a Chatbot could make an appointment on a *real* hairdressing salon speaking with a human in a phone call. 

[https://www.youtube.com/watch?v=D5VN56jQMWM](https://www.youtube.com/watch?v=D5VN56jQMWM)

*Chatbot* software is designed to provide conversational solutions to identified *intents* or goals. This conversation can be performed by using raw text (chatting services) or natural voice. 

Every major software manufacturer has its own *Cloud Service* to provide these features:

* [Google Duplex](https://ai.google/)
* [Microsoft Luis](https://www.luis.ai/home)
* [IBM Watson](https://www.ibm.com/watson/)
* [Amazon Alexa](https://developer.amazon.com/es/alexa)
* [Apple Siri](https://developer.apple.com/sirikit/)

When developing on these platforms, your source code is executed in the Cloud and your data is also stored out of your control.

## The Open Source alternative

But not every **Conversational AI Engine** has to be used in the Cloud, there are some Open Source alternatives that can be executed on premises. 

[Rasa](https://rasa.com/products/rasa-stack/) is providing Open Source conversational AI tools based in NLU for developers and the product can be easily deployed by using [Docker](https://hub.docker.com/r/rasa/rasa_nlu/).

Additionally, when dealing with **Content**, there are also some Open Source ECM products available. We recommend you to use [Alfresco](https://community.alfresco.com), which is an standard CMIS Repository that can be also deployed by using [Docker](https://github.com/keensoft/docker-alfresco) in your development environment.

## The Challenge

Let's say we have a Document Library including a set of information for every Final Degree Project in the University. 

We can define different *intents* for our Chatbot service:

* Send an email to someone including references for an specific topic, year, area/department or author
* Download a package with references for an specific topic, year, area/department or author

*Sample conversation*

```
User: Hello 
Bot:  Hey, how can I help you?
User: I am looking for Docker related projects... Any
      suggestions? 
Bot:  We have 432 projects including Docker topics inside, would you like 
      to filter? 
User: Sure! How many projects were presented in 2017?
Bot:  We have 23 projects in 2017 including Docker, do you want me to send an email with the list or do you want to download the contents?
User: Can you send an email with this list to Angel?
Bot:  Great, just sent the email for you.
User: Thanks a lot!
Bot:  Glad I could help!
```

*Sample architecture*

```
[Chatbot] > [RASA]   > [CMIS] > [Alfresco]
              ^^ 
          [Training] > [CMIS] > [Alfresco]

```

*Sample content model*

Aspect THESIS

* Property TOPIC (string)
* Property YEAR (number)
* Property AREA (string)
* Property AUTHOR (string)

>> Content model implementation is provided by default in **docker-alfresco** project.

## The Mug Contest

Participants can solve an additional *mini-puzzle*. The winner will be the first *pull-request* to this repository with the right implementation for `es.keensoft.alfresco.mug.MugContest.getPdfDocuments()` method according to requirements set in JUnit Test available at `es.keensoft.alfresco.mug.test.TestMugContest.getPdfDocuments()`.

More details at **CMIS Client** project.

# Additional resources

We are providing default configurations for:

* RASA UI, the software required for the Chatbot
* Alfresco, the software required for the Content
* CMIS Client, the software required to upload and retrieve information from Alfresco

## Software requirements

* Docker 18
  * [Mac](https://store.docker.com/editions/community/docker-ce-desktop-mac)
  * [Windows](https://store.docker.com/editions/community/docker-ce-desktop-windows)
  * [Ubuntu](https://store.docker.com/editions/community/docker-ce-server-ubuntu)
* [Docker Compose 1.2](https://docs.docker.com/compose/install/)
* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
* [Maven 3.2+](https://maven.apache.org/download.cgi)

## How to run RASA in Docker

Building Docker image and starting default RASA UI.

```bash
$ cd rasa-docker

$ docker build --tag keensoft/rasa_nlu .

$ docker run -p 5000:5000 keensoft/rasa_nlu
```

Testing the chatbot by using the command line in Docker Container.

```bash
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
f0240da37c1e        keensoft/rasa_nlu   "./entrypoint.sh sta…"   4 seconds ago       Up 3 seconds        0.0.0.0:5000->5000/tcp   epic_engelbart

$ docker exec -it f0240da37c1e /bin/bash

$ tree models
models
└── current
    ├── dialogue
    │   ├── domain.json
    │   ├── domain.yml
    │   ├── policy_0_MemoizationPolicy
    │   │   ├── featurizer.json
    │   │   └── memorized_turns.json
    │   ├── policy_1_KerasPolicy
    │   │   ├── featurizer.json
    │   │   ├── keras_arch.json
    │   │   ├── keras_policy.json
    │   │   └── keras_weights.h5
    │   ├── policy_metadata.json
    │   └── stories.md
    └── nlu_model
        └── default
            └── model_20180910-142902
                ├── checkpoint
                ├── intent_classifier_tensorflow_embedding.ckpt.data-00000-of-00001
                ├── intent_classifier_tensorflow_embedding.ckpt.index
                ├── intent_classifier_tensorflow_embedding.ckpt.meta
                ├── intent_classifier_tensorflow_embedding_encoded_all_intents.pkl
                ├── intent_classifier_tensorflow_embedding_inv_intent_dict.pkl
                ├── intent_featurizer_count_vectors.pkl
                ├── metadata.json
                └── training_data.json

$ python -m rasa_core.run -d models/current/dialogue -u models/current/nlu_model/default/model_20180910-142902

Bot loaded. Type a message and press enter:
Hello
Hey, how can I help you?
I would like to join a meetup
Rasa Bots Berlin meetup is definitely worth checking out! They are having an event today at Behrenstraße 42. Would you like to join?
No, thanks.
Bye!
```  

## How to run Alfresco in Docker

Building Alfresco image and starting default Alfresco Repository.

```bash
$ cd docker-alfresco

$ docker-compose up --build
```

Alfresco includes a collection of services running as containers inside Docker.

```bash
$ docker ps --format '{{.Names}}\t{{.Image}}\t{{.Ports}}'
proxy        nginx:stable-alpine                         0.0.0.0:80->80/tcp
share        docker-alfresco_share                       8080/tcp
content-app  alfresco/alfresco-content-app:master-latest 80/tcp
solr6        alfresco/alfresco-search-services:1.2.0     8983/tcp
alfresco     docker-alfresco_alfresco                    8080/tcp
postgres     postgres:10                                 5432/tcp
```

A Web Proxy has been included to make easier accessing to these services:

* [http://localhost/alfresco](http://localhost/alfresco): Repository backend and CMIS Server
* [http://localhost/share](http://localhost/share): UI for the Repository

You should be able to access both URLs once Docker is up & running.

>> Content Model for THESIS is just deployed inside this Alfresco Server. You can find definition for reference at `docker-alfresco/alfresco/shared/library-model.xml` folder.

## How to use CMIS Client

A sample Spring Boot Client to gather information from CMIS Server has been created at `cmis-client` folder. 

Building and running the program can be done by using following commands.

```bash
$ cd cmis-client

$ mvn clean package

$ java -jar target/cmis-client-1.1.0.jar --action=dump
```
