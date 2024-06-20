# Forohub - Challenge Backend Alura

Desafío alura llamado ForoHub: en él, vamos a replicar este proceso a nivel de back end y, para eso, crearemos una API REST usando Spring.

<br>

## Pre requisitos

### Variables de Entorno

Deberas agregar las siguientes variables de entorno para que la aplicacion pueda funcionar, a continuacion el nombre que cada variable de entorno debe tener

<table border="1">
    <tr style="text-align: center;">
        <td>VARIABLE</td>
        <td>DESCRIPCIÓN</td>
    </tr>
    <tr>
        <td>ALURA_HOST</td>
        <td>ruta a la base de datos con su puerto</td>
    </tr>
    <tr>
        <td>ALURA_DB</td>
        <td>nombre de la base de datos</td>
    </tr>
    <tr>
        <td>ALURA_DB_USR</td>
        <td>usuario de la base de datos</td>
    </tr>
    <tr>
        <td>ALURA_DB_PWD</td>
        <td>password de acceso a la base de datos</td>
    </tr>
    <tr>
        <td>ALURA_JWT_KEY</td>
        <td>tu key para ser usado en JWT</td>
    </tr>
</table>

<br>

## Aplicación

### Usuarios

#### *Registrar*
```json
POST
/api/auth/register

{
    "name": "Tu Nombre",
    "email": "Tu Email",
    "password": "Tu Password"
}
```

#### *Login*
```json
POST
/api/auth/login

{
    "email": "Tu Email",
    "password": "Tu Password"
}
```

#### *Listar Usuarios*
```json
GET
/users
```

#### *Detalle de un Usuario*
```json
DEL
/users/id
```

#### *Actualizar Usuario*
```json
PUT
/users/id

{
    "name": "Tu nuevo nombre",
    "email": "Tu nuevo correo",
    "password": "Tu nueva password"
}
```

### Topicos

#### *Agregar Topico*
```json
POST
/topics

{
    "title":"Tu Titulo",
    "message":"Tu Mensaje",
    "author":id del usuario,
    "course":id del curso
}
```

#### *Actualizar Topico*
```json
PUT
/topics/id

{
    "title":"Tu Nuevo Titulo",
    "message":"Tu Nuevo Mensaje",
    "author":id del usuario,
    "course":id del nuevo curso
}
```

#### *Eliminar Topico*
```json
DEL
/topics/id
```

#### *Listar Topicos*
```json
GET
/topics
```

#### *Listar Topico Especifico*
```json
GET
/topics/detail/id
```