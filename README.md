# Abstract Interpreter

A university internship project focused on the theory and implementation of **Static Analysis** and **Abstract Interpretation** techniques for programming languages.

The project aims to progressively build a modular framework capable of parsing, representing, and statically analyzing programs through abstract semantic approximations.

---

# Objectives

The main goals of the project are:

- Study formal semantics of programming languages
- Understand and implement Abstract Interpretation
- Build Control Flow Graphs (CFGs)
- Develop concrete and abstract semantics
- Implement sound static analyses
- Design reusable abstract domains
- Experiment with fixpoint computations and widening operators

---

# Project Structure

```text
AbstractInterpreter/
│
├── docs/                  # Notes, papers, diagrams
├── examples/              # Example programs and test cases
│
├── src/
│   ├── main/
│   │   ├── antlr4/        # ANTLR grammars
│   │   └── java/
│   │       └── it/univr/pl/
│   │
│   │           ├── parser/
│   │           ├── ast/
│   │           ├── visitor/
│   │           ├── cfg/
│   │           ├── semantics/
│   │           ├── abstractdomain/
│   │           ├── analysis/
│   │           ├── fixpoint/
│   │           ├── interpreter/
│   │           └── util/
│   │
│   └── test/
│
└── python-prototypes/     # Rapid experimental prototypes