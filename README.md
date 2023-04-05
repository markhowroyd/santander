# santander

An implementation of the pricing service coding example.


Assumptions,

The processing is sufficiently quick that we don't need to dispatch to another thread in the receiver, in order
to release the calling thread faster.  If we do then an intermediate store of some kind would be needed to ensure we don't
lose messages once acknowledged.

The system is just for FX, so storing the ccy pair key as a domain type makes more sense than a raw string, as it
can protect against some types of error

No persistence is required in this implementation

Timestamps are UTC


Notes,

For a larger domain object, or multiple sources/formats the parsing and validation steps would best be separated

If many more stages to the processing, a "pipelining" framework could be beneficial
