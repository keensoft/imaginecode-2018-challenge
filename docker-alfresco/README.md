# Alfresco Community Docker

Deployment template based in official [Docker Composition](https://github.com/Alfresco/acs-community-deployment/tree/master/docker-compose) provided by Alfresco.

You should review volumes, configuration, modules & tuning parameters before using this composition in **Production** environments.

## Source Images

* [alfresco 6.0.7-ga](https://github.com/Alfresco/acs-community-packaging/blob/master/docker-alfresco/Dockerfile)
* [share 6.0](https://github.com/Alfresco/share/blob/6.0/packaging/docker/Dockerfile)
* [postgres 10](https://github.com/docker-library/postgres/blob/master/10/Dockerfile)
* [alfresco-search-services 1.2.0](https://github.com/Alfresco/SearchServices/blob/master/packaging/src/docker/Dockerfile)
* [content-app master:latest](https://hub.docker.com/r/alfresco/alfresco-content-app/)

## Volumes

A directory named `volumes` is located in the root folder to store configuration, data and log files.

```bash
$ tree volumes
volumes
├── config
│   ├── alfresco-global.properties
│   ├── ext-share-config-custom.xml
│   ├── nginx.conf
│   └── nginx.htpasswd
├── data
│   ├── alf-repo-data
│   └── solr-data
└── logs
    ├── alfresco
    ├── nginx
    └── share
```

**Configuration** files are available at `config` folder:

* `alfresco-global.properties` for Repository
* `ext-share-config-custom.xml` for Share
* `nginx.conf` for HTTP Proxy
* `nginx.htpasswd` for Basic Auth credentials to access SOLR Web Console

**Data** wil be persisted automatically in `data` folder. Once launched, Docker will create three subfolders for following services:

* `alf-repo-data` for Content Store
* `solr-data` for Indexes

>> For Linux hosts, set `solr-data` folder permissions to user with UID 1001, as `alfresco-search-services` is using an container user named `solr` with UID 1001.

**Logs** folder includes log files for:

* `alfresco` contains Tomcat repository logs
* `nginx` contains HTTP Proxy logs
* `share` contains Tomcat share logs

### Thesis Model

Aspect **Thesis** is available at `alfresco/shared/library-model.xml` file.


# How to use this composition

## Start Docker

Start docker and check the ports are correctly bound.

```bash
$ docker-compose up -d
$ docker ps --format '{{.Names}}\t{{.Image}}\t{{.Ports}}'
proxy        nginx:stable-alpine                           0.0.0.0:80->80/tcp
share        docker-alfresco_share                         8080/tcp
content-app  alfresco/alfresco-content-app:master-latest   80/tcp
solr6        alfresco/alfresco-search-services:1.2.0       8983/tcp
alfresco     docker-alfresco_alfresco                      8080/tcp
postgres     postgres:10                                   5432/tcp
```

### Viewing System Logs

You can view the system logs by issuing the following.

```bash
$ docker-compose logs -f
```

Logs for every service are also available at `volumes/logs` folder.

## Access

Use the following username/password combination to login.

 - User: admin
 - Password: admin

Alfresco and related web applications can be accessed from the below URIs when the servers have started.

```
http://localhost               - Alfresco Content Application
http://localhost/share         - Alfresco Share WebApp
http://localhost/alfresco      - Alfresco Repository (REST)
http://localhost/solr          - Alfresco Search Services (Basic Auth, admin/admin by default)
```
