# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [2.1.0] - 2026-11-11

### Added

- San Andrés insular zone with a 70% surcharge; valid coupons are ignored for
  insular shipments.
- Change-impact and regression tests for the personalized CR-00 request.

### Validation

- `mvn clean verify`: 53 tests passed, with no failures.
- JaCoCo: 100% instruction, line, and branch coverage.

## [2.0.0] - 2026-11-11

### Added

- Zone strategies for urban, rural, and national surcharge policies.
- Unit tests for each zone strategy and invalid zone selection.

### Changed

- Extracted calculation, validation, discount, surcharge, minimum, and rounding
  steps into focused methods.
- Replaced tariff, limit, percentage, and rounding literals with named
  constants.
- Removed the `flag` parameter and its console output from the public
  calculator API. This is a breaking API change.

### Fixed

- Enforced the personalized maximum weight and exact invalid-weight message.
- Rejected unsupported zones and days outside 1–7.
- Applied the weekend surcharge on both Saturday and Sunday.
- Rounded to the nearest $100, with ties rounded upward.

## [1.0.0] - 2026-11-11

### Added

- Tagged the Java translation of the inherited shipping calculator as the
  unchanged legacy baseline.
