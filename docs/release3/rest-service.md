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

```jsonc
{
  "username": "username",
  "password": "password",
}
```

- Authenticates the user if credentials match an existing user.
- Creates a new user if the username does not exist.

**Response:**

```jsonc
{
  "jwtToken": "jwt token",
}
```

### `GET /auth/me`

Retrieves information about the currently authenticated user.

**Response:**

```jsonc
{
  "id": 1,
  "username": "username",
  "displayName": "display name",
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

```jsonc
[
  {
    "id": 1,
    "username": "username",
    "displayName": "display name",
  },
  /* more users */
]
```

### `GET /api/users/{id}`

Retrieves information about a specific user.

**Path parameters:**

| Parameter | Type |
| --------- | ---- |
| id        | Long |

**Response:**

```jsonc
{
  "id": 1,
  "username": "username",
  "displayName": "display name",
}
```

### `PUT /api/users/{id}`

Performs a full update of a user.

**Path parameters:**

| Parameter | Type |
| --------- | ---- |
| id        | Long |

**Request Body:**

```jsonc
{
  "username": "username",
  "displayName": "display name",
  "password": "password",
}
```

**Response:**

```jsonc
{
  "id": 1,
  "username": "username",
  "displayName": "display name",
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

```jsonc
[
  {
    "id": 1,
    "content": "content",
    "likes": 10,
    "reTweets": 3,
    "commentsAmount": 0,
    "author": {
      "id": 2,
      "username": "username",
      "displayName": "display name",
    },
    "likedByUsers": [1, 3],
    "originalPostId": null, // or number
    "type": "ORIGINAL", // or RETWEET
  },
  /* more posts */
]
```

### `GET /api/posts/{id}`

Retrieves a specific post.

**Path parameters:**

| Parameter | Type |
| --------- | ---- |
| id        | Long |

**Response:**

```jsonc
{
  "id": 1,
  "content": "content",
  "likes": 10,
  "reTweets": 3,
  "commentsAmount": 0,
  "author": {
    "id": 2,
    "username": "username",
    "displayName": "display name",
  },
  "likedByUsers": [1, 3],
  "originalPostId": null, // or number
  "type": "ORIGINAL", // or RETWEET
}
```

### `POST /api/posts`

Creates a new post.
If `originalPostId` is provided, a retweet is created.

**Request Body:**

```jsonc
{
  "content": "content",
  "originalPostId": 1, // or null for normal post
}
```

**Response:**

```jsonc
{
  "id": 1,
  "content": "content",
  "likes": 10,
  "reTweets": 3,
  "commentsAmount": 0,
  "author": {
    "id": 2,
    "username": "username",
    "displayName": "display name",
  },
  "likedByUsers": [1, 3],
  "originalPostId": null, // or number
  "type": "ORIGINAL", // or RETWEET
}
```

### `POST /api/posts/{id}/likes`

Toggles whether the authenticated user has liked the post.

**Path parameters:**

| Parameter | Type |
| --------- | ---- |
| id        | Long |

**Response:**

```jsonc
{
  "id": 1,
  "content": "content",
  "likes": 10,
  "reTweets": 3,
  "commentsAmount": 0,
  "author": {
    "id": 2,
    "username": "username",
    "displayName": "display name",
  },
  "likedByUsers": [1, 3],
  "originalPostId": null, // or number
  "type": "ORIGINAL", // or RETWEET
}
```

### `GET /api/posts/mine`

Retrieves posts created by the authenticated user.

**Response:**

```jsonc
[
  {
    "id": 1,
    "content": "content",
    "likes": 10,
    "reTweets": 3,
    "commentsAmount": 0,
    "author": {
      "id": 2,
      "username": "username",
      "displayName": "display name",
    },
    "likedByUsers": [1, 3],
    "originalPostId": null, // or number
    "type": "ORIGINAL", // or RETWEET
  },
  /* more posts */
]
```
