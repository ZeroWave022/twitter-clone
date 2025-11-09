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

## Member 2 – _Full Name_

**Contribution Summary:**  
_Replace this text with your paragraph (max 200 words)._

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
