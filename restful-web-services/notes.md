# RESTful Web Services

Social Media Application

User -> Posts

- Retrieve all Users - `GET /users`
- Create a User - `POST /users`
- Retrieve one User - `GET /users/{id} -> /users/1`
- Delete a User - `DELETE /users/{id} -> /users/1`


- Retrieve all posts for a User - `GET /users/{id}/posts`
- Create a Post for a User - `POST /users/{id}/posts`
- Retrieve details of a Post - `GET /users/{id}/posts/{post_id}`

- HATEOAS - Hypermedia as the Engine of Application State
- Internationalization - i18n
- HAL - JSON Hypertext Application Language

40. Step 26 - Versioning Restful services, basic approach with URI's