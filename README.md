## Abstract Interpreter

A university internship project focused on the theory and implementation of Static Analysis and Abstract Interpretation techniques[cite: 1, 2]. The framework is developed in Java using ANTLR4 for syntax recognition[cite: 1, 2]. It bypasses complex intermediate representations (like AST or CFG) to evaluate both concrete and abstract semantics directly on the parse tree nodes via the Visitor Pattern, combined with a static Type System[cite: 1, 2].

---

### Theoretical Background

**Abstract Interpretation**
Introduced by Patrick and Radhia Cousot in 1977, Abstract Interpretation is a theory of sound approximation of mathematical structures, primarily applied to the formal semantics of computer programs[cite: 1, 2]. Since verifying non-trivial semantic properties of a program is undecidable (Rice's Theorem), an abstract interpreter replaces the concrete, infinite domain of execution values with a simplified, finite Abstract Domain (structured as a Poset or a Lattice)[cite: 1, 2].

**The Extended Sign Lattice**
In this project, the concrete domain of integers $\mathbb{Z}$ is abstracted into the Sign Domain, structured as a complete lattice[cite: 1, 2]. It has been extended to support more granular and precise analysis[cite: 2]:
*   $\bot$ **(Bottom)**: Represents an uninitialized state, unreachable code, or a static runtime error (e.g., division by zero)[cite: 1, 2].
*   **$-, 0, +$ (Neg, Zero, Pos)**: Represent strict semantic properties of concrete numbers[cite: 1, 2].
*   **$0+, 0-, \neq 0$ (Zero-Plus, Zero-Minus, Not-Zero)**: Extended abstractions to handle ambiguous operations, such as integer division (e.g., $\text{POS} / \text{POS} = 0+$) and speculative conditional joins[cite: 2].
*   $\top$ **(Top)**: Represents total uncertainty[cite: 1, 2]. It is triggered when overlapping execution paths yield discordant signs or mathematical ambiguity[cite: 1, 2].

**String Domain via Finite State Automata (FSA)**
To support dynamic string manipulation, the interpreter implements an automata-based abstract semantics (based on arXiv:1808.07827)[cite: 2].
*   **Regular Languages**: Instead of simple flat lattices, string variables are mapped to the regular language recognized by a Finite State Automaton (FSA) using the `dk.brics.automaton` library[cite: 2].
*   **Least Upper Bound (LUB)**: Reconciling string variables in conditional branches corresponds directly to calculating the Union of their automata[cite: 2].
*   **Fixpoint & Widening**: Loops involving strings avoid infinite growth by applying a widening operator[cite: 2]. If the automaton's state count exceeds a safe threshold during iteration, it safely relaxes to $\top$ (any string), ensuring rapid convergence[cite: 2].

**Soundness & Fixpoint Convergence**
The analyzer computes a Sound Approximation of the program state: it is guaranteed to never lie, though it may lose precision to remain decidable[cite: 1, 2].
*   **Conditional Branching (If-Else)**: Since the static guard value could evaluate to $\top$, both paths are explored speculatively[cite: 1, 2]. Their final abstract memories are reconciled at the join-point using the Least Upper Bound (LUB) operator[cite: 1, 2]:
    $$\text{Memory}_{\text{final}} = \text{Memory}_{\text{Then}} \sqcup \text{Memory}_{\text{Else}}$$
*   **Loops (While)**: To solve the Halting Problem statically, loop analysis executes a Fixpoint Computation[cite: 1, 2]. The abstract interpreter evaluates the loop body iteratively until the abstract memory reaches a stationary state[cite: 1, 2]:
    $$M_{i+1} = M_i \sqcup \text{body}(M_i)$$
    Convergence is mathematically guaranteed without falling into infinite loops[cite: 1, 2].

---

### Future Implementations: Array Abstraction

The next milestone for the project involves the integration of Arrays into the language[cite: 2]. Two abstract models are currently under evaluation[cite: 2]:
*   **Smash Abstraction**: Collapses the entire array into a single abstract variable, continuously applying LUB on all inserted elements[cite: 2]. This simplifies implementation but causes the loss of exact positional data[cite: 2].
*   **Index-Sensitive Abstraction**: Maps abstract indices (or intervals) to their respective values, maintaining higher precision[cite: 2]. This approach requires complex mathematical logic, notably Weak Updates[cite: 2]. When an accessed index is statically unknown ($\top$), the analyzer must conservatively fuse (LUB) the new data with the existing data to preserve soundness without overwriting valid states[cite: 2].

---

### Pipeline, Structure & Getting Started

**Pipeline Architecture**
The framework processes source files through a strict multi-stage pipeline[cite: 1, 2]: 
*(Insert your previous pipeline description here)*

**Project Structure**
*(Insert your previous project structure here)*

**Getting Started & Test Bench**
The project includes an automated test bench that scans the `examples/` directory, prints the source code of each test case, runs the static validation, and computes the signs[cite: 1, 2]. 
*(Insert your previous prerequisites and compilation commands here)*