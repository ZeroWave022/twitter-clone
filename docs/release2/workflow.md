# Workflow

Our workflow describes the step-by-step process we follow to implement and integrate new features or changes in the project.

1. Task planning

   - New features, bug fixes, and other tasks are created as GitHub Issues.
   - Each issue is assigned to a team member or shared among multiple members.

2. Branching

   - Each branch contains the work for the GitHub Issue
   - Branch names follow a consistent pattern, e.g., `feat/<short-description>`

3. Implementation

   - The assigned developer implements the feature locally.
   - Pair programming is used when helpful to improve quality and share knowledge.
   - The feature is tested to ensure it works correctly with existing code.

4. Code Review and Pull Requests

   - Once the issue is completed, a pull request is created with a clear title following Conventional Commits.
   - At least one team member performs a code review before merging.
   - Comments from the review are addressed, and changes are applied if needed.

5. Merging

   - After approval, the PR is merged into the development/main branch

6. Issue Completion

   - Once the feature is integrated, the corresponding GitHub Issue is closed.
