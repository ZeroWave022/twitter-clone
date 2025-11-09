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
I created the `Post` class and developed the `makeNewPostController`, ensuring that new posts could be created and handled consistently across the system.
I also implemented retweets end-to-end, which required coordinated changes in the **API layer**, **persistence layer**, **service logic**, and `PostController`.

In addition to backend development, I implemented the **user interface for retweeting** and ensured the retweet functionality was fully integrated and intuitive from a user perspective.

To maintain code reliability, I wrote tests for the `makeNewPostController`,
the retweet handling in the `PostController`, and the UI to verify that the functionality behaved as intended.
I also participated in reviewing and providing feedback on pull requests to help support code quality and team collaboration.

Another key contribution was troubleshooting build and runtime issues in **Eclipse Che**, helping ensure that the project could run smoothly in the shared development environment.
Overall, my work contributed to both the development of major features and the stability of the project setup.

---

## Member 3 – _Martin Alexander Kaminski_

**Contribution Summary:**  

In the Twitter Clone project, I have provided guidance and support from start to finish.
Using the knowledge acquired outside of university subjects, I helped team members design and implement features, as well as using Git efficiently.

I have helped members of the team in setting up Git using SSH keys, explained and resolved multiple merge conflicts and reviewed numerous pull requests using built-in GitHub suggestions to simplify collaboration. Further assistance I've provided is documented using `Co-authored-by` in commit messages.
I also organized tens of issues and pull requests using tags, statuses, naming conventions, and issue linking.

I have implemented the use of [Hibernate ORM](https://hibernate.org/orm/) and [Jakarta Persistence (JPA)](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/index.html).
This has been essential to enable us to use a SQL Database like SQLite, avoiding the usage of JSON or CSV files.
The related classes can be found in the [`persistence`](/twitter-clone/persistence/src/main/java/persistence/) module.

Furthermore, I have also set up JaCoCo, checkstyle formatting, and written extensive documentation (often together with Henrik).
I've also worked on improving the code quality and maintaining consistent formatting to keep our code quality standards high.

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
