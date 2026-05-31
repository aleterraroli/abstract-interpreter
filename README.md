# Abstract Interpreter

A university internship project focused on the theory and implementation of **Static Analysis** and **Abstract Interpretation** techniques.

The framework is developed in Java using **ANTLR4** for syntax recognition. It bypasses complex intermediate representations (like AST or CFG) to evaluate both concrete and abstract semantics directly on the parse tree nodes via the **Visitor Pattern**, combined with a static **Type System**.

---

## Theoretical Background

### Abstract Interpretation
Introduced by Patrick and Radhia Cousot in 1977, **Abstract Interpretation** is a theory of sound approximation of mathematical structures, primarily applied to the formal semantics of computer programs.

Since verifying non-trivial semantic properties of a program is undecidable (**Rice's Theorem**), an abstract interpreter replaces the concrete, infinite domain of execution values with a simplified, finite **Abstract Domain** (structured as a *Poset* or a *Lattice*).

### The Sign Lattice
In this project, the concrete domain of integers $\mathbb{Z}$ is abstracted into the **Sign Domain**, structured as a complete lattice:

```text
       Top (⊤)  <- Non-deterministic / Unknown
      /   |   \
  Neg(-) Zero(0) Pos(+)
      \   |   /
     Bottom (⊥) <- Unreachable / Error State
```

* **$\bot$ (Bottom)**: Represents an uninitialized state, unreachable code, or a static runtime error (e.g., division by zero).
* **$-, 0, +$ (Neg, Zero, Pos)**: Represent strict semantic properties of concrete numbers.
* **$\top$ (Top)**: Represents total uncertainty. It is triggered when overlapping execution paths yield discordant signs or mathematical ambiguity.

### Soundness & Fixpoint Convergence
The analyzer computes a **Sound Approximation** of the program state: it is guaranteed to never lie, though it may lose precision to remain decidable.

1. **Conditional Branching (`If-Else`)**: Since the static guard value could evaluate to $\top$, both paths are explored. Their final abstract memories are reconciled at the join-point using the **Least Upper Bound (LUB)** operator:

$$\text{Memory}_{\text{final}} = \text{Memory}_{\text{Then}} \sqcup \text{Memory}_{\text{Else}}$$

2. **Loops (`While`)**: To solve the *Halting Problem* statically, loop analysis executes a **Fixpoint Computation**. The abstract interpreter evaluates the loop body iteratively until the abstract memory reaches a stationary state:

$$M_{i+1} = M_i \sqcup \text{body}(M_i)$$

Since the Sign Lattice has a finite height, convergence is mathematically guaranteed without falling into infinite loops.

---

## Pipeline Architecture

The framework processes source files through a strict multi-stage pipeline:

```text
[ Source Code ] 
       │
       ▼
 1. ANTLR4 Parser   ──> Generates Parse Tree Contexts
       │
       ▼
 2. Type System     ──> Validates static types (AbsTS); Aborts on Type Mismatch.
       │
       ▼ [If valid]
 3. Abstract Intp   ──> Evaluates Sign Domains (SignAbsIntp) via Fixpoint Calculation.
       │
       ▼
[ Static Report ]   ──> Outputs the inferred final state of all variables.
```

---

## Project Structure

```text
AbstractInterpreter/
│
├── docs/                  # Notes, formal definitions, and lattice diagrams
├── examples/              # Test suites (.txt files showcasing precise/imprecise/loops states)
│
├── src/
│   ├── main/
│   │   ├── antlr4/        # ANTLR4 Grammars (.g4) defining the language syntax
│   │   └── java/
│   │       └── it/univr/pl/
│   │           │
│   │           ├── exception/      # Static compilation and semantic exceptions
│   │           │
│   │           ├── type/           # Static Concrete Types (SimpleType) & Abstract Types (SignType)
│   │           │
│   │           ├── value/          # Concrete (IntValue) vs Abstract (SignValue) semantic wrappers
│   │           │
│   │           ├── visitor/        # The core engines: AbsTS (Type Checker) & SignAbsIntp (Abstract Interpreter)
│   │           │
│   │           ├── SignAbsMem.java # Abstract Memory mapping identifiers to SignValues
│   │           └── MainAbstractInterpreter.java # Main Test Bench runner
│   │
│   └── test/
└── pom.xml                # Maven configuration (ANTLR4 hooks and dependencies)
```

---

## Getting Started & Test Bench

The project includes an automated test bench that scans the `examples/` directory, prints the source code of each test case, runs the static validation, and computes the signs.

### Prerequisites
* **JDK 21+** (Optimized for JDK 24)
* **Maven**

### Compilation
Compile the project and trigger ANTLR4 parser generation using:
```bash
mvn clean compile
```

### Running the analyzer

Execute the main compiler test bench runner:

```bash
mvn exec:java -Dexec.mainClass="it.univr.pl.MainAbstractInterpreter"
```

### Expected output

The test suite demonstrates the foundational guarantees of abstract interpretation:

* **Precise Branches**: If both paths agree on an output sign, the analyzer retains exact precision (e.g., +).
* **Conservativeness**: Operations like $\text{Pos} - \text{Pos}$ will safely degrade into TOP ($\top$) rather than risking an unsound guess.
* **Loop Halting**: Loops (such as increments up to a constant boundary) trigger the fixpoint engine, converging stably into TOP in milliseconds instead of looping forever.
* **Type Guarding**: Well-typed properties are enforced before the sign-transfer functions take over.

