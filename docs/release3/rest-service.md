# REST service formats

This document provides a complete overview of the REST API, including available endpoints, request and response formats, and authentication requirements.

## General

The REST API uses **JWT-based authentication** for protected endpoints. 
Requests with a missing or invalid token will return a `403 Forbidden` response.

**Note:**
The only unprotected endpoint is `POST /auth/login`.

The token must be included in the `Authorization` header using the following format:
```
Authorization: Bearer <jwt-token>
```

## Authentication endpoints

The auth resource has two endpoints: 

| Method | Endpoint    | Description                                   |
| ------ | ----------- | --------------------------------------------- |
| POST   | /auth/login | Logs in, and potentially registers a new user |
| GET    | /auth/me    | Retrieves the current user                    |

### `POST /auth/login`

Used to obtain a JWT token.

**Request Body:**

```json
{
  "username": "<username>",
  "password": "<password>"
}
```

- Authenticates the user if credentials match an existing user.
- Creates a new user if the username does not exist.

**Response:**

```json
{
  "jwtToken": "<jwt-token>"
}
```

### `GET /auth/me`

Retrieves information about the currently authenticated user.

**Response:**

```json
{
  "id": <id>,
  "username": "<username>",
  "displayName": "<display-name>"
}
```

## User endpoints

The user resource has three endpoints:

| Method | Endpoint        | Description            |
| ------ | --------------- | ---------------------- |
| GET    | /api/users      | Retrieves all users    |
| GET    | /api/users/{id} | Retrieves a user by id |
| PUT    | /api/users/{id} | Updates a user by id   |

### `GET /api/users`

Retrieves a list of all users.

**Response:**

```json
[
  {
    "id": <id>,
    "username": "<username>",
    "displayName": "<display-name>"
  },
  ...
]
```

### `GET /api/users/{id}`

Retrieves information about a specific user.

**Path parameters:**

| Parameter | Type |
| --------- | ---- |
| id        | Long |

**Response:**

```json
{
  "id": <id>,
  "username": "<username>",
  "displayName": "<display-name>"
}
```

### `PUT /api/users/{id}`

Performs a full update of a user.

**Path parameters:**

| Parameter | Type |
| --------- | ---- |
| id        | Long |

**Request Body:**

```json
{
  "username": "<username>",
  "displayName": "<display-name>",
  "password": "<password>"
}
```

**Response:**

```json
{
  "id": <id>,
  "username": "<username>",
  "displayName": "<display-name>"
}
```

## Post endpoints

The post resource has five endpoints:

| Method | Endpoint              | Description                                       |
| ------ | --------------------- | ------------------------------------------------- |
| GET    | /api/posts            | Retrieves all posts                               |
| GET    | /api/posts/{id}       | Retrieves a post by id                            |
| POST   | /api/posts            | Creates a new post                                |
| POST   | /api/posts/{id}/likes | Toggles a like on the post by the user            |
| GET    | /api/posts/mine       | Retrieves posts created by the authenticated user |

### `GET /api/posts`

Retrieves all posts.

**Response:**

```json
[
  {
    "id": <id>,
    "content": "<content>",
    "likes": <likes>,
    "reTweets": <re-tweets>,
    "commentsAmount": <comments-amount>,
    "author": {
      "id": <id>,
      "username": "<username>",
      "displayName": "<display-name>"
    },
    "likedByUsers": [<user-id>, ...],
    "originalPostId": <original-post-id>,
    "type": <ORIGINAL|RETWEET>
  },
  ...
]
```

### `GET /api/posts/{id}`

Retrieves a specific post.

**Path parameters:**

| Parameter | Type |
| --------- | ---- |
| id        | Long |

**Response:**

```json
{
  "id": <id>,
  "content": "<content>",
  "likes": <likes>,
  "reTweets": <re-tweets>,
  "commentsAmount": <comments-amount>,
  "author": {
    "id": <id>,
    "username": "<username>",
    "displayName": "<display-name>"
  },
  "likedByUsers": [<user-id>, ...],
  "originalPostId": <original-post-id>,
  "type": <ORIGINAL|RETWEET>
}
```

### `POST /api/posts`

Creates a new post.
If `originalPostId` is provided, a retweet is created.

**Request Body:**

```json
{
  "content": "<content>",
  "originalPostId": <original-post-id>
}
```

**Response:**

```json
{
  "id": <id>,
  "content": "<content>",
  "likes": <likes>,
  "reTweets": <re-tweets>,
  "commentsAmount": <comments-amount>,
  "author": {
    "id": <id>,
    "username": "<username>",
    "displayName": "<display-name>"
  },
  "likedByUsers": [<user-id>, ...],
  "originalPostId": <original-post-id>,
  "type": <ORIGINAL|RETWEET>
}
```

### `POST /api/posts/{id}/likes`

Toggles whether the authenticated user has liked the post.

**Path parameters:**

| Parameter | Type |
| --------- | ---- |
| id        | Long |

**Response:**

```json
{
  "id": <id>,
  "content": "<content>",
  "likes": <likes>,
  "reTweets": <re-tweets>,
  "commentsAmount": <comments-amount>,
  "author": {
    "id": <id>,
    "username": "<username>",
    "displayName": "<display-name>"
  },
  "likedByUsers": [<user-id>, ...],
  "originalPostId": <original-post-id>,
  "type": <ORIGINAL|RETWEET>
}
```

### `GET /api/posts/mine`

Retrieves posts created by the authenticated user.

**Response:**

```json
[
  {
    "id": <id>,
    "content": "<content>",
    "likes": <likes>,
    "reTweets": <re-tweets>,
    "commentsAmount": <comments-amount>,
    "author": {
      "id": <id>,
      "username": "<username>",
      "displayName": "<display-name>"
    },
    "likedByUsers": [<user-id>, ...],
    "originalPostId": <original-post-id>,
    "type": <ORIGINAL|RETWEET>
  },
  ...
]
```
