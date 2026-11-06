# Assignment 6 — Visitor Pattern: CS2 Loadout

**Course:** Software Design Patterns
**Pattern:** Visitor (behavioral)
**Language:** Java

## About the project

A Counter-Strike 2 player's loadout (rifle, grenades) is a **stable object structure**: the kinds of equipment rarely change, but we want to run different operations on it, such as calculating the total cost or printing a report.

The Visitor pattern puts each operation in its own visitor class. New operations can be added without modifying the equipment classes.

## How to run

From the project root:

```bash
javac -d out src/*.java src/equipment/*.java src/visitor/*.java
java -cp out Main
```

Or open the project in IntelliJ IDEA and run `Main`.

### Example output

```
PLAYER: Monesy
Total Price: 5550
----Loadout Report----
 Rifle -Name: AWP -Price: 4750 -DMG per bullet: 115
 Grenade -name: Flashbang -price: 200 -DMG: 1
 Grenade -name: Smoke -price: 300 -DMG: 1
 Grenade -name: HE Grenade -price: 300 -DMG: 67
```

## Project structure

```
src/
├── Main.java                         Client
├── Loadout.java                      Object Structure
├── equipment/
│   ├── Equipment.java                Element interface
│   ├── Rifle.java                    Concrete Element
│   ├── Grenade.java                  Concrete Element
│   └── Visitor.java                  Visitor interface
└── visitor/
    ├── CostCalculatorVisitor.java    Concrete Visitor
    └── LoadoutReportVisitor.java     Concrete Visitor
```

## Pattern components

| Component | Requirement | Implementation |
|---|---|---|
| Element Interface | Declares `accept(Visitor visitor)` | `Equipment` declares `accept(Visitor visitor)` and `getName()` |
| Concrete Elements | At least two; `accept()` calls the matching `visitX()` passing itself | `Rifle` calls `visitor.visitRifle(this)`, `Grenade` calls `visitor.visitGrenade(this)` |
| Visitor Interface | One `visitX()` per Concrete Element | `Visitor` declares `visitRifle(Rifle)` and `visitGrenade(Grenade)` |
| Concrete Visitors | At least two, each implementing every `visitX()` | `CostCalculatorVisitor` (total price), `LoadoutReportVisitor` (text report) |
| Object Structure | Collection the client traverses, calling `accept()` on each element | `Loadout` stores a `List<Equipment>`; `accept(visitor)` loops and calls `item.accept(visitor)` |

## How double dispatch works

Java chooses which overridden method to run based on the runtime type of **one** object (single dispatch). The Visitor pattern needs the result to depend on **two** runtime types, the element and the visitor, so it chains two calls:

```
loadout.accept(costVisitor)
 └─ item.accept(costVisitor)           item is declared as Equipment, but is really a Rifle
     └─ Rifle.accept(...) runs         DISPATCH #1: chosen by the element's runtime type
         └─ visitor.visitRifle(this)   visitor is declared as Visitor, but is really CostCalculatorVisitor
             └─ CostCalculatorVisitor.visitRifle(...) runs   DISPATCH #2: chosen by the visitor's runtime type
                 └─ totalCost += 4750
```

1. **First dispatch:** `item.accept(visitor)` runs the `accept()` of the element's real class (`Rifle` or `Grenade`).
2. **Second dispatch:** inside that class, `this` has the exact type, so `visitor.visitRifle(this)` or `visitor.visitGrenade(this)` is called. Java then runs that method in the visitor's real class.

The method that finally runs depends on both the element type and the visitor type, with no `instanceof` checks and no casts.

## Clean Code principles

### 1. Meaningful names distinguishing Element vs. Visitor responsibilities
- Elements are named after domain objects: `Rifle`, `Grenade`, and their common interface `Equipment`.
- Visitors are named after the operation they perform and end in `Visitor`: `CostCalculatorVisitor`, `LoadoutReportVisitor`.
- Visit methods name the element they handle: `visitRifle`, `visitGrenade`.
- The packages separate the roles: `equipment` holds what is visited, `visitor` holds the operations.

### 2. No `instanceof` / type-checking chains
There is no `instanceof` and no casting anywhere in the project. Without the pattern, calculating cost would need a chain like:

```java
if (item instanceof Rifle) { total += ((Rifle) item).getPrice(); }
else if (item instanceof Grenade) { total += ((Grenade) item).getPrice(); }
```

Double dispatch replaces this completely: each element calls the correct `visitX(this)` itself. If a new element type is added, the compiler forces every visitor to implement its new `visitX()` method, so no case can be silently forgotten.

### 3. Single Responsibility Principle
Each class has one reason to change:
- `CostCalculatorVisitor` only calculates the total price, across all element types.
- `LoadoutReportVisitor` only builds the text report, across all element types.
- `Rifle` and `Grenade` only store their data and accept visitors. They contain no cost or report logic.
- `Loadout` only stores the equipment and traverses it.
- `Main` only builds the loadout, runs the visitors and prints the results.

### 4. Minimal, well-justified encapsulation trade-offs
Visitors need to read element data (price, damage, name) to do their work, which is the main trade-off of the Visitor pattern. It is kept as small as possible:
- All element fields are `private final`, so they cannot change after creation.
- Elements expose **getters only, no setters**, so visitors can read data but never modify equipment.
- `Loadout.getItems()` returns `Collections.unmodifiableList(items)`, so code outside cannot add or remove items behind the loadout's back.

### 5. Programming to interfaces
- `Loadout` stores a `List<Equipment>`, not concrete classes, so it does not need to know which items are rifles or grenades.
- `Loadout.accept()` and `Equipment.accept()` take the `Visitor` interface, so any visitor can be passed in.
- `Main` only calls `loadout.accept(...)` and never touches individual elements.

## Extensibility

Adding a new operation (for example, total damage or export to JSON) only requires a **new class** implementing `Visitor`. It is passed to `loadout.accept(...)` with **no changes** to `Equipment`, `Rifle`, `Grenade` or `Loadout`. This follows the Open/Closed Principle.

The trade-off is that adding a new **element** type (for example, `Pistol`) requires adding `visitPistol()` to the `Visitor` interface and implementing it in every visitor. Visitor fits this project because the equipment types are stable while the operations can grow.
