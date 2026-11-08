# Project instructions

## Requirements and decisions

- Treat the exercise brief as the source of truth.
- Do not invent requirements, outcomes, tests, or completion claims.
- State uncertainty plainly. Label decisions as either a requirement from the brief or an assumption.
- Add an assumption to the README and obtain the project owner's confirmation before implementing behaviour that relies on it.
- Record the confirmed decision in the README when an ambiguity is resolved.

## Scope

- Build only what the exercise asks for.
- Do not add speculative features, abstractions, dependencies, configuration, or error handling beyond the stated needs.
- Prefer the Java standard library unless an approved requirement needs a dependency.

## Design and code

- Use clear, human-readable Java with descriptive names and small, focused methods.
- Keep data models small and free of business decisions.
- Place each business rule in a focused class with one clear responsibility.
- Apply SOLID principles when they improve clarity, changeability, or testability; do not add layers merely to satisfy a principle.
- Keep money as integer cents during calculation; format it only at input/output boundaries.
- Keep parsing, trip matching, fare calculation, CSV writing, and application startup separate.

## Quality checks

- Add a focused test for each stated business rule and each confirmed edge case.
- Make processing deterministic: sort events when required and define the output order.
- Verify the required example input produces the required output.
- Run the relevant checks before each commit and report exactly what was run and its result.

## Delivery process

- Work in small pieces and stop for review before starting the next piece.
- Make small, single-purpose commits with imperative, descriptive messages.
- Do not mix refactoring, new behaviour, and unrelated formatting in one commit.
- Keep the README current with run instructions, confirmed assumptions, and the completed deliverables.
