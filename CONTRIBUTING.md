# How to contribute

In the first place, thank you for thinking about contributing to KMMBridge!
Here you can find a set of guidelines for pitching in.

## Questions

If you have any questions, please, contact us in the Kotlin [Community Slack](https://kotlinlang.slack.com/) in the [touchlab-support](https://kotlinlang.slack.com/archives/CTJB58X7X) channel. To join the Kotlin Community Slack, [request access here](http://slack.kotlinlang.org/).

For direct assistance, please [reach out to Touchlab](https://touchlab.co/contact-us/) to discuss support options.

## Set up environment

For instructions on how to set up your environment and run the project, refer to the [documentation website](https://touchlab.github.io/KMMBridge/) and [quickstart with KMM tutorial](https://touchlab.co/quick-start-with-kmmbridge-1-hour-tutorial/).

## Create an issue

If you have stumbled across a bug or have a good feature suggestion / enhancement, you can create an [issue](https://github.com/touchlab/KMMBridge/issues), but please don't mistake it for the general KMM helpline. You can get answers for general questions in Slack. Please, fill in carefully all the info the **issue template** suggests. It will save us time when investigating the problem. There might be a bit of a delay until we get to your ticket, so we ask for your patience.

## Submit a merge request

If you wish to participate in submitting code changes, to start with, you can look for issues tagged with **good first issue** (if there is none look for an issue that looks adequate to your skills).
In case you feel like making significant changes or adding features, please discuss with the team first before you start working on it, to ensure we are on the same page.
When your fix / feature is ready, create a merge request using the **pull request template** and fill in as much information as possible.
All merge requests need to pass a code review from our team member, and subsequently they are approved or rejected with a reason. It might take some time before we get to your merge request, but don't worry, it didn't get lost.

## Lint

We use Lint and KtLint in our projects to ensure the Kotlin code guidelines are met. Please, check that your suggested changes follow these guidelines as well by running `./gradlew :ktlintCheck` or you can use [KtLint plugin](https://plugins.jetbrains.com/plugin/15057-ktlint-unofficial-) for IntelliJ IDEA or Android Studio. Some existing files may not conform with this standard, please be careful about refactoring them as part of your merge request, with many refactored lines the actual changes may get lost when reviewing.

## Testing

Before creating a merge request, make sure all the tests pass by `./gradlew test` to run the tests locally.
