# Code quality

The code quality of our project is ensured by our well-defined work-practices.md and workflow.md, which can be read about in the separate markdown files.

In addition, we use various testing tools to ensure that the code works correctly.
When developing a new feature, testing is performed before the PR is merged.
We use JUnit to write and run unit tests for individual classes and methods, ensuring that each component behaves as expected.

We also use several tools to maintain code quality:

- Checkstyle: For code style and formatting checks
- SpotBugs: For static analysis and bug detection
- JaCoCo: For code coverage analysis
- Red Hat Java Linter

During code reviews, we check for code consistency, readability, logical errors, potential bugs, and adherence to our coding standards.
Any issues found are commented on and addressed before merging.
We are fortunate to have several experienced developers on our team, so such issues are usually detected and corrected quickly.
