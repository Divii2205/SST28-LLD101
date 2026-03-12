Design structure:
- PulseMeter collects runtime metrics (counters) and exposes them globally so any part of the app can increment counters 

Singleton class:
- allows creation of only one instance of a class

Current problems in the design:
- Not thread safe
- Reflection can create multiple instances
- Serialization/deserialization can produce a new instance

Refractor:
- Maintain an INSTANCE variable of type MetricsRegistry
- Made INSTANCE variable volatile - thread safe - Without volatile, one thread may update its CPU cache with the new object reference, while other threads still see null

- Made the constructor private to make it lazy loading and inside it, checked for value of INSTANCE variable, if it is not null it will throw an exception => this check is to block reflection from creating another instance

- made the getInstance() static to let it create a single instance upon class initialisation ---- added a double lock and enable synchronisation
- When Java deserializes an object, it creates a new instance of the class internally - to avoid this - Java has a special method called readResolve() that returns the existing singleton instance

- MetricsLoader uses new MetricRegistry() instead of singleton
- so we instead use MetricsRegistry.getInstance()