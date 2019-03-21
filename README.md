TMDB
======
Requerimentos
------------
Para assinar o aplicativo, será necessário criar o arquivo `local.properties` com as credenciais da keystore na pasta raiz do projeto:
```groovy
KEY_PASSWORD=<<key store password>>
KEY_ALIAS=<<key alias>>
KEYSTORE_PATH=<<Caminho fisico da keystore>>
STORE_PASSWORD=<<key store password>>
```
Build
------------
Dentro do diretório raiz do projeto, execute:
`./gradlew app:assembleRelease`