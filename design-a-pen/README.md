# Pen Design - Low Level Design

## Introduction
This assignment focuses on designing a flexible and extensible pen system, a problem that has also appeared in SDE II interviews (e.g., Amazon). The goal is to model real-world pen behavior using clean object-oriented principles.

---

## Functional Requirements

The pen should support the following operations:

- `start()` → prepares the pen for writing  
- `write(String text)` → writes the given text  
- `refill(String color)` → refills ink and may change color  
- `close()` → stops the writing operation  

---

## Important Constraints

- `start()` must be invoked before calling `write()` ❗  
- Writing behavior varies depending on the pen type  
- Refill logic is also dependent on pen type  
- Every refill restores ink level to 100%  

---

## Variations Considered

### 1. Types of Pens
- Ink Pen  
- Ball Pen  
- Gel Pen  

### 2. Writing Mechanism
- Cap-based  
- Click-based  

### 3. Ink Color Behavior
- Fixed color  
- Color change allowed during refill  

---

## Extended Requirement

- Introduced a `Grip` feature that affects writing behavior  
- Constraint: Existing implementation should not be modified (legacy-safe extension)

---

## Implementation Notes

- The solution follows modular and extensible design principles  
- All implementation code is available in the `src` directory  
- The entry point is the `Main3` class (acts as the client)  
- A class diagram is included in the `docs` folder for better understanding  

---

## Summary

The design ensures:
- Easy extensibility for new pen types or features  
- Separation of concerns  
- Compliance with given constraints without breaking existing code  
