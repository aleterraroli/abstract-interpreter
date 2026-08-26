## Abstract Interpreter

A university internship project focused on the theory and implementation of Static Analysis and Abstract Interpretation techniques. The framework is developed in Java using ANTLR4 for syntax recognition. It bypasses complex intermediate representations (like AST or CFG) to evaluate both concrete and abstract semantics directly on the parse tree nodes via the Visitor Pattern, combined with a static Type System.

---

### Theoretical Background

**Abstract Interpretation**
Introduced by Patrick and Radhia Cousot in 1977, Abstract Interpretation is a theory of sound approximation of mathematical structures, primarily applied to the formal semantics of computer programs. Since verifying non-trivial semantic properties of a program is undecidable (Rice's Theorem), an abstract interpreter replaces the concrete, infinite domain of execution values with a simplified, finite Abstract Domain (structured as a Poset or a Lattice).

**The Extended Sign Lattice**
In this project, the concrete domain of integers $\mathbb{Z}$ is abstracted into the Sign Domain, structured as a complete lattice. It has been extended to support more granular and precise analysis:
*   $\bot$ **(Bottom)**: Represents an uninitialized state, unreachable code, or a static runtime error (e.g., division by zero).
*   **$-, 0, +$ (Neg, Zero, Pos)**: Represent strict semantic properties of concrete numbers.
*   **$0+, 0-, \neq 0$ (Zero-Plus, Zero-Minus, Not-Zero)**: Extended abstractions to handle ambiguous operations, such as integer division (e.g., $\text{POS} / \text{POS} = 0+$) and speculative conditional joins.
*   $\top$ **(Top)**: Represents total uncertainty. It is triggered when overlapping execution paths yield discordant signs or mathematical ambiguity.

**String Domain via Finite State Automata (FSA)**
To support dynamic string manipulation, the interpreter implements an automata-based abstract semantics (based on arXiv:1808.07827).
*   **Regular Languages**: Instead of simple flat lattices, string variables are mapped to the regular language recognized by a Finite State Automaton (FSA) using the `dk.brics.automaton` library.
*   **Least Upper Bound (LUB)**: Reconciling string variables in conditional branches corresponds directly to calculating the Union of their automata.
*   **Fixpoint & Widening**: Loops involving strings avoid infinite growth by applying a widening operator. If the automaton's state count exceeds a safe threshold during iteration, it safely relaxes to $\top$ (any string), ensuring rapid convergence.

**Soundness & Fixpoint Convergence**
The analyzer computes a Sound Approximation of the program state: it is guaranteed to never lie, though it may lose precision to remain decidable.
*   **Conditional Branching (If-Else)**: Since the static guard value could evaluate to $\top$, both paths are explored speculatively. Their final abstract memories are reconciled at the join-point using the Least Upper Bound (LUB) operator:
    $$\text{Memory}_{\text{final}} = \text{Memory}_{\text{Then}} \sqcup \text{Memory}_{\text{Else}}$$
*   **Loops (While)**: To solve the Halting Problem statically, loop analysis executes a Fixpoint Computation. The abstract interpreter evaluates the loop body iteratively until the abstract memory reaches a stationary state:
    $$M_{i+1} = M_i \sqcup \text{body}(M_i)$$
    Convergence is mathematically guaranteed without falling into infinite loops.

---

### Future Implementations: Array Abstraction

The next milestone for the project involves the integration of Arrays into the language. Two abstract models are currently under evaluation:
*   **Smash Abstraction**: Collapses the entire array into a single abstract variable, continuously applying LUB on all inserted elements. This simplifies implementation but causes the loss of exact positional data.
*   **Index-Sensitive Abstraction**: Maps abstract indices (or intervals) to their respective values, maintaining higher precision. This approach requires complex mathematical logic, notably Weak Updates. When an accessed index is statically unknown ($\top$), the analyzer must conservatively fuse (LUB) the new data with the existing data to preserve soundness without overwriting valid states.

