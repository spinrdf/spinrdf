SpinRDF : Contributing
======================

The project welcomes contributions, large and small, from anyone.

The processes described here are guidelines, rather than absolute
requirements.

## Contributions

Contributions are made with Github pull requests and should tests and
documentation as needed.

## Workflow

Flow](https://guides.github.com/introduction/flow/). We test PRs out
locally before merging as an alternative to "deploying a branch".

### Pull Request

To make a contribution:

* On github, fork http://github.com/spinrdf/spinrdf into you github
  account.
* Create a branch in your fork for the contribution.
* Make your changes. Include the Apache License source header at the top
  of each file.
* Generate a pull request via github. Further changes to your branch
  will automatically show up in the pull request, including rebasing
  the branch and squashing commits.

### Discussion

A project committer will review the contribution and coordinate any
project-wide discussion needed. Review and discussion of the pull
request itself takes place on github.

### Merge

When the pul request is agreed, a committer will merge the code into
master and close the pull request.

## Code

Code style is about making the code clear for the next person
who looks at the code.

The project prefers code to be formatted in the common java style with
sensible deviation for short forms.

The project does not enforce a particular style but asks for:

* Kernighan and Ritchie style "Egyptian brackets" braces.
* Spaces for indentation
* No `@author` tags.
* One statement per line
* Indent level 4 for Java
* Indent level 2 for XML

See, for illustration:
https://google.github.io/styleguide/javaguide.html#s4-formatting

The codebase has a long history - not all of it follows this style.

The code should have no warnings, in particular, use `@Override` and types
for generics, and don't declared checked exceptions that are not used.
Use `@SuppressWarnings("unused")` as necessary.

Please don't mix reformatting and functional changes; it makes it harder
to review.

## Legal

All contributions are licensed under the Apache License 2.0.

You, as an individual, must be entitled to make the contribution to the
project. If the contribution is part of your employment, please arrange
this before making the contribution.

At some time in the future, the sinrdf project may request an Apache software
grant. This grants additional rioghts so that the code can more easily
incoprorated in releases by a Apache Software Foundation Project.

For a large contributions, the project may also ask for a specific software
grant from the contributor.

If in doubt, or if you have any questions, ask. Legal issues are easier
to deal with if done before contributing, rather than after.

The project cannot accept contributions with unclear ownership nor
contributions containing work by other people without a clear agreement
from those people.
