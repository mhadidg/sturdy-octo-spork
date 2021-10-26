## Assumptions

- A **word** is a text with at least one character. Empty and blank text are invalid words.
- A **notebook entry** is a text composed of a set of words separated by one or more whitespaces.
- The task demands an implementation of a **single** endpoint to determine both the frequency of word occurrence and
  similar words in a given notebook entry.
    - The endpoint should accept both *word* and *notebook entry* as a parameters (either via URL parameters or
      form-data parameters),
- There are no obligation to write an implementation of Levenshtein distance algorithm. We are free to use a library for
  it.
- There is no performance requirement imposed as a condition for passing the test.

## Implementation notes

- Concurrent hash set view via `ConcurrentHashMap.newKeySet()` is used to create performant thread-safe set.
- To facilitate parallel execution, `.stream().parallel().forEach(...)` has been used to process words of a notebook
  entry in parallel.

## If I had more time, I would...

split the current endpoint into `/word/frequency` (determine the frequency of word occurrence)
and `/word/similar` (find similar words). This should yield better design and scalability (via pagination), especially
for the latter one. Yet, the implementation need to cache a possibly expensive computation (depending on the average
size of the notebook entries) of determining the frequency of word occurrence and reuse it to find similar words.

## Acknowledgements

I've spent approximately 5 hours to implement the exercise divided as follows:

- 3 hours for implementation.
- 1.5 hour for unit testing.
- half an hour for writing this README.md.