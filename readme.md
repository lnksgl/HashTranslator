Для запуск проекта:
1.docker-compose up в корневой папке
2.gradle flywayMigrate -i в проекте authorization-service
3.запуск 2х проектов в idea

Стандартный админ: email - admin / password - admin

----------------------------------------------------------------------------------------------------
Авторизация: localhost:8081/api/applications/auth/signin (POST)
json-request:
{
    "email": "admin",
    "password": "admin"
}

json-response:
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3MzczNjc3MSwiZXhwIjoxNjczODIzMTcxfQ.YkDJR9f6m45a338-xV00uqzIRPSuY0DJRn3KtLLC5Yw",
    "type": "Bearer",
    "id": 177,
    "email": "admin",
    "roles": [
        "ROLE_ADMIN"
    ]
}

----------------------------------------------------------------------------------------------------
Создание пользователей только для админов: localhost:8081/api/applications/auth/signup (POST)
Authorization 
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3MzczNjc3MSwiZXhwIjoxNjczODIzMTcxfQ.YkDJR9f6m45a338-xV00uqzIRPSuY0DJRn3KtLLC5Yw

json-request:
{
    "email": "user",
    "password": "user",
    "roles": ["User"]
}

json-response: User successfully created.

----------------------------------------------------------------------------------------------------
Список всех пользователей только для админов: localhost:8081/api/applications/users (GET)
Authorization 
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3MzczNjc3MSwiZXhwIjoxNjczODIzMTcxfQ.YkDJR9f6m45a338-xV00uqzIRPSuY0DJRn3KtLLC5Yw

json-response:
[
    {
        "id": 177,
        "email": "admin",
        "password": "$2a$10$oDEfA0ASTrfUy6xchHTr7OR8bb5W8yaNzm4aJoAcl6xX97BEggFiW",
        "roles": [
            {
                "id": 1,
                "name": "ROLE_ADMIN"
            }
        ]
    },
    {
        "id": 2,
        "email": "user",
        "password": "$2a$10$I1qUVfQDDkGz5XwI7Vtwj.nipfyvUzsZWoEaYzKhHTq156HSpBTba",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_USER"
            }
        ]
    }
]

----------------------------------------------------------------------------------------------------
Удаление пользователей только для админов: localhost:8081/api/applications/users/2 (DELETE)
Authorization 
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3MzczNjc3MSwiZXhwIjoxNjczODIzMTcxfQ.YkDJR9f6m45a338-xV00uqzIRPSuY0DJRn3KtLLC5Yw

json-response: User successfully deleted.

----------------------------------------------------------------------------------------------------
Создание запроса расшифровки хэшей только для авторизованных: localhost:8081/api/applications (POST)
Authorization 
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3MzczNjc3MSwiZXhwIjoxNjczODIzMTcxfQ.YkDJR9f6m45a338-xV00uqzIRPSuY0DJRn3KtLLC5Yw

json-request:
{
  "hashes": [
    "c4ca4238a0b923820dcc509a6f75849b",
    "c81e728d9d4c2f636f067f89cc14862c",
    "eccbc87e4b5ce2fe28308fd9f2a7baf3"
  ]
}

json-response: 63c33894de396570e41caa2a

----------------------------------------------------------------------------------------------------
Получение результат расшифровки хэшей только для авторизованных: localhost:8081/api/applications/63c33894de396570e41caa2a (GET)
Authorization 
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3MzczNjc3MSwiZXhwIjoxNjczODIzMTcxfQ.YkDJR9f6m45a338-xV00uqzIRPSuY0DJRn3KtLLC5Yw

json-response:
{
    "id": "63c33894de396570e41caa2a",
    "hashes": {
        "c81e728d9d4c2f636f067f89cc14862c": "2",
        "eccbc87e4b5ce2fe28308fd9f2a7baf3": "3",
        "c4ca4238a0b923820dcc509a6f75849b": "1"
    }
}

