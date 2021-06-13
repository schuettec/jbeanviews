# ReMap - How to contribute

First of all: Feel free to contribute to this project! We appreciate any help to develop this library and make it nice, stable and feature complete.

The JBeanViews project was started to minimize the need of converter classes and corresponding unit tests so support hexagonal architecture as much as possible. Unlike other mapping frameworks, JBeanViews aims to work completely deterministically. Plus that, JBeanViews makes sure that the mapping for the view is complete and targets all view fields.

This project provides formatter configurations for Eclipse and IntelliJ IDEA as well as a build plugin to format automatically. Use goal `formatter:format` to achieve this.

## Contribution checklist

The following list summarizes the things you should check before contributing:

- build pipeline is successful
- README was extended
- tests were added
- your changes in the public API should be backwards compatible. This is a bit challenging, because we
currently have no testing for that.
- if you add dependencies to this library, we might have to reject the PR.

## Contributing Features

When contributing features, any suggestions to extend the README of this project are welcome :-)

### Testing features

When writing test for this project, please make sure to create a new package for your test. This avoids unnecessary dependencies to other test classes.

## Contributing bug fixes

When contributing bug fixes, please first write a test that provokes the bug. Please use the package `com.github.schuettec.jbeanviews.regression` for that.

## Contribution resources

You can find the IDE formatter rule set in `shared/config`.


