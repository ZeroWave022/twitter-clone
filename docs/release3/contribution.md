# Team Contributions

Each team member has provided a short summary (max 200 words) of their contributions to the project.  
This includes implemented features, collaboration, code reviews, problem-solving efforts, and other relevant work.

---

## Member 1 – _Daniel Grøtan Gregusson_

My main responsibility for this release was implementing REST API authentication and developing the API client service.

On the server side, I added a security configuration that verifies the `Authorization` header in incoming requests to protected endpoints and rejects those with invalid JWT tokens. To issue tokens, I created an authentication resource that validates user credentials and returns a signed JWT.

On the client I introduced a significant architectural change by replacing the existing `UserRepository` and `PostRepository` pattern with a centralized `ApiClientService`. This refactor caused several merge conflicts but ultimately simplified our codebase and improved maintainability.

I actively participated in code reviews for most pull requests, helping to identify bugs and suggest improvements. Additionally, I collaborated closely with Martin and Adrian on the delete post feature, where we encountered several implementation challenges and worked together to find efficient solutions.

---

## Member 2 – _Nathaniel Førrisdahl_

During this project, I implemented several core features related to post creation and retweet functionality.
I created the Post class and developed the makeNewPostController, ensuring that new posts could be created and handled consistently across the system.
I also implemented retweets end-to-end, which required coordinated changes in the API layer, persistence layer, service logic, and PostController.
In addition to backend work, I implemented the user interface for retweeting and ensured the retweet functionality was fully integrated and intuitive from a user perspective. 

To maintain code reliability, I wrote tests for the makeNewPostController and PostController to test retweet functionality and UI.
I participated in reviewing and providing feedback on pull requests to support code quality and team collaboration.
Another key contribution was troubleshooting build and runtime issues in Eclipse Che, helping ensure that the project could run smoothly in the shared development environment.
Overall, my work contributed to both the development of major features and the stability of the project setup. 

---

## Member 3 – _Full Name_

**Contribution Summary:**  
_Replace this text with your paragraph (max 200 words)._

---

## Member 4 – _Full Name_

**Contribution Summary:**  
_Replace this text with your paragraph (max 200 words)._

---

## Member 5 – _Full Name_

**Contribution Summary:**  
_Replace this text with your paragraph (max 200 words)._

---

## Member 6 – _Full Name_

**Contribution Summary:**  
_Replace this text with your paragraph (max 200 words)._

---
