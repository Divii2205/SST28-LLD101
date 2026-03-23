Design structure:
- a tool that creates and manages support/incident tickets

Immutable class:
- class whose objects cannot be changed after they are created.
- IncidentTicket

Current problems in the design:
- multiple constructors
- public setters
- validation scattered across the codebase
- objects can be modified after being "created", causing audit/log inconsistencies

Refractor:
- private final fields, made the class final so that it cannot be inherited further
- intialise all the fields when the object is created as these fields must be initialised only once
- no setters
- enable safe getters: wherever we have mutable datatypes like arraylists, we maintain a deepcopy

But,
- When a class has many fields, creating objects using constructors becomes confusing and hard to maintain. We might have to make constructors for all possible combinations of the fields.

To make it simpler for the client, 
- Introduced a builder class 
- This builder helps create objects step by step in a readable and flexible way.
- Once the object has its fields set through the builder, we build() it to make it immutable, that's when we set all the values inside the immutable class and return it
- So we have an IncidentTicket.builder().<all parameters>.build() that return an immutable Incident Ticket object
