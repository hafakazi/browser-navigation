# Student Info
* Name: Hafa Kazi
* Course: CS 3345
* Date: 09/28/2025

# Browser Navigation — README

A Java console app that simulates a web browser’s navigation using my own data structures – Stack and Queue ADT (no java.util.ArrayList / LinkedList) – that extends the iterable interface. It supports visiting sites, going back/forward, viewing/clearing history, and saving/restoring a session from disk.

## Assignment Checklist

- Custom doubly linked list: BrowserLinkedList<T> (implements Iterable<T>)
- Custom circular, dynamically-resizing array: BrowserArrayList<T> (implements Iterable<T>)
- Stack built on the linked list: BrowserStack<T> (throws EmptyStackException)
- Queue built on the circular array: BrowserQueue<T>
- Iterator to traverse and save the stack: StackIterator<T>
- Browser logic: BrowserNavigation
    - visitWebsite(url) pushes current → back stack, clears forward stack, enqueues history
    - goBack(), goForward() (throw/catch EmptyStackException)
    - showHistory(), clearHistory()
    - closeBrowser() saves session using iterators
    - restoreLastSession() reconstructs stacks/queue
- Driver: Main interactive console app

java.util types used: Iterator, NoSuchElementException, EmptyStackException, and Scanner for I/O.

## Project Structure
.
├─ BrowserLinkedList.java   # custom doubly linked list (Iterable<T>)
├─ BrowserArrayList.java    # custom circular array (Iterable<T>)
├─ BrowserStack.java        # stack on top of BrowserLinkedList
├─ BrowserQueue.java        # queue on top of BrowserArrayList
├─ StackIterator.java       # read-only iterator over BrowserStack
├─ BrowserNavigation.java   # browser operations + save/restore
└─ Main.java                # console driver

## Build & Run

Requirements: Java 8+

    # Compile
    javac *.java

    # Run
    java Main

## Console

Commands:
    1. visit <url> | 2. back | 3. forward | 4. history | 5. clear | 6. save | 7. restore | 8. exit

Examples:
    > 1 a.com
    Now at [a.com]
    > visit b.com
    Now at [b.com]
    > 1 c.com
    Now at [c.com]
    > back
    Now at [b.com]
    > forward
    Now at [c.com]
    > history
    1. a.com
    2. b.com
    3. c.com
    > save
    Browser session saved.
    > exit

    > restore
    Previous session restored.

Error outputs:
- Back with no previous page: No previous page available.
- Forward with no next page: No forward page available.
- History when empty: No browsing history available.

## How it Works

    # Data Structures
    - Back / Forward: BrowserStack<String>
        - Internals: BrowserLinkedList<T> for O(1) push/pop at head.
    - History: BrowserQueue<String>
        - Internals: BrowserArrayList<T> for O(1) via circular buffer.
    - Custom Iteration:
        - BrowserLinkedList and BrowserArrayList both implement Iterable<T>.
        - StackIterator<T> consumes the stack top → bottom without modification (used by closeBrowser()).

    # Operations
    - visitWebsite(url):
        - If a page is open, push it on back.
        - forward is cleared.
        - Enqueue url to history.
        - Set currentPage = url.
    - goBack():
        - Push currentPage to forward, pop from back to become currentPage.
    - goForward():
        - Push currentPage to back, pop from forward to become currentPage.

## session_data.txt
- Includes Test Cases.

CURRENT
<currentPage or empty line>

BACK <N>
<top of back stack>
...
<bottom of back stack>

FORWARD <M>
<top of forward stack>
...
<bottom of forward stack>

HISTORY <K>
<first visited>
...
<last visited>

- Saving writes stacks using StackIterator (top → bottom).
- Restoring rebuilds stacks by pushing bottom → top so the original top element remains on top.

## Time & Space Complexity

| Operation                | Structure           | Time           | Space note           |
| ------------------------ | ------------------- | -------------- | -------------------- |
| push / pop (Stacks)      | BrowserLinkedList   | O(1)           | O(n) total nodes     |
| offer / poll (Queue)     | BrowserArrayList    | Amortized O(1) | Resizes x2 when full |
| peek, isEmpty, size      | Both                | O(1)           | —                    |
| iterator(for-each)       | Lists/Arrays        | O(n)           | —                    |
| closeBrowser()           | All                 | O(n)           | Writes all elements  |
| restoreLastSession()     | All                 | O(n)           | Reads all elements   |

* n = number of stored items across history and stacks

## Design Notes

- No ArrayList/LinkedList from java.util: implemented BrowserArrayList and BrowserLinkedList.
- Iterator requirement: both collections implement Iterable<T>; 
                        StackIterator<T> is used when serializing.
- Exceptions: goBack() / goForward() throw EmptyStackException, and prints messages from Main.
- Forward clears on visit.
- Queue (history): FIFO using circular buffer.

## Testing Checklist

- Back/forward when empty → expected error messages.
- Visit after going back → forward stack is cleared.
- History order remains chronological.

- Save, restart, restore → current page, stacks, and history preserved.
