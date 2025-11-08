
# Challenges during development


## Merge Conflicts

One of the challenges we encountered was having multiple team members working on overlapping parts of the project at the same time.
For example, while one person was implementing retweets, others were working on the REST API and UI features.
Many of these tasks affected the `PostController` class, which led to a series of merge conflicts that took longer than expected to resolve.


In order to address the issue, we attempted to merge the branches in an order that we believed would result in the fewest merge conflicts.
This approach did help reduce some of the workload, but determining the optimal merge order is not always straightforward.
When multiple features evolve in parallel and depend on shared components, it can be difficult to predict how the changes will interact until the merge actually takes place.


This experience highlighted the importance of **frequent, smaller merges** and ongoing communication within the team. Instead of letting branches diverge significantly over time, integrating changes more regularly can make conflicts easier to detect and resolve early, before they become large and time-consuming.


---

## Consistent Code Style and Structure

Because different team members used different IDEs and personal configuration settings, our codebase initially suffered from inconsistent formatting and styling.
Each IDE had its own default formatter and linter rules, which meant that code written by one team member often triggered formatting warnings for others.
This not only made the code harder to read but also contributed to unnecessary merge conflicts when seemingly minor formatting differences were interpreted as code changes.

To resolve this issue, we agreed on a **shared formatting standard** and required that all code be formatted using the **Red Hat Java extension for VS Code**,
regardless of which IDE each member preferred for development.
By standardizing our formatters, we were able to maintain a cleaner and more consistent codebase.
This reduced noise in commit histories, minimized formatting-related merge conflicts, and improved overall readability and maintainability of the project.

---
