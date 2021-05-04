# Notes importants
Aquest fitxer és per explicar un parell de coses que hem afegit les quals no eren als vídeos de classe
però que ens han fet falta per tal que funcionés.

## Problema amb gateway i discovery
En el moment en que vam afegir gateway, aquest al principi llegia bé els hosts i ports, però en quant
el vam configurar per tal que s'apuntés al discovery al iniciar, va començar a detectar les aplicacions
subscrites en comptes de localhost amb el nom del portàtil. Per tal de canviar això vam afegir

### Application.yml a config-repo
Per tal que arribi a tots els microserveis que es subscriuen al discovery:
```
spring.cloud.discovery.enabled: true
eureka.instance.hostname: localhost
```
### Gateway.yml a config-repo
```
spring.cloud.discovery.enabled: true
spring.cloud.gateway.discovery.locator.lower-case-service-id: true
spring.cloud.gateway.discovery.locator.enabled: true
eureka.instance.hostname: localhost
```

## Execució per a centralized logs
No vam utilitzar els dockers sinó que vam descarregar l'últim jar contenidor executable de zipkin
versió "zipkin-server-2.23.2-exec.jar" descarregable a: https://zipkin.io/pages/quickstart

Abans d'executar-ho a la command line fem set d'una variable just abans per tal d'assegurar-nos que escolta a través de rabbit
perquè sinó no ho detectava, executant:
```
SET RABBIT_URI=amqp://localhost
```
